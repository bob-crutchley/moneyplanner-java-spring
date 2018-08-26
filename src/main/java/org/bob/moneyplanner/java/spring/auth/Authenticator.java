package org.bob.moneyplanner.java.spring.auth;

import io.jsonwebtoken.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;
import org.bob.moneyplanner.java.spring.constant.AuthConstant;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.model.service.ErrorResponse;
import org.bob.moneyplanner.java.spring.repository.AccountRepository;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class Authenticator {

    private final Logger log = Logger.getLogger(Authenticator.class.getName());
    private final Environment environment;
    private final String applicationSecret;
    private final AccountRepository accountRepository;
    @Autowired
    public Authenticator(Environment environment, AccountRepository accountRepository) {
        this.environment = environment;
        this.applicationSecret = environment.getProperty("application.secret");
        this.accountRepository = accountRepository;
    }

    public String getNewSessionToken(Account account) {
        Date newIssuedDate = new Date();
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] applicationSecretBytes = DatatypeConverter.parseBase64Binary(applicationSecret);
        Key signingKey = new SecretKeySpec(applicationSecretBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setId(new BigInteger(130, new SecureRandom()).toString(32))
                .setId(account.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(DateUtils.addMinutes(newIssuedDate, 10))
                .signWith(signatureAlgorithm, signingKey)
                .claim("id", account.getId())
                .compact();
    }

    public ServiceResult authenticateSessionToken(String sessionToken) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setHttpStatus(HttpStatus.UNAUTHORIZED);
        if (sessionToken == null) {
            serviceResult.setModel(new ErrorResponse(AuthConstant.NO_SESSION_FOR_ACCOUNT.getValue()));
            return serviceResult;
        }
        try {
            Claims claim = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(applicationSecret))
                    .parseClaimsJws(sessionToken).getBody();
            serviceResult.setHttpStatus(HttpStatus.OK);
            serviceResult.setModel(accountRepository.findAccountById(Long.valueOf((Integer)claim.get("id"))));
        } catch(ExpiredJwtException e) {
            serviceResult.setModel(new ErrorResponse(AuthConstant.SESSION_TOKEN_HAS_EXPIRED.getValue()));
        } catch (SignatureException e) {
            serviceResult.setModel(new ErrorResponse(AuthConstant.INVALID_SESSION_TOKEN.getValue()));
        } catch (Exception e) {
            serviceResult.setModel(new ErrorResponse(AuthConstant.TOKEN_AUTHENTICATION_FAILURE.getValue()));
        }
        return serviceResult;
    }

    public boolean plaintextMatchesSha1(String plaintext, String sha1) {
        return DigestUtils.sha256Hex(plaintext).equals(sha1);
    }
}
