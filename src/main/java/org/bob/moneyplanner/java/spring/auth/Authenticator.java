package org.bob.moneyplanner.java.spring.auth;

import io.jsonwebtoken.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;
import org.bob.moneyplanner.java.spring.constant.AuthConstant;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.model.service.Credentials;
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
import java.util.Arrays;
import java.util.Date;
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

    public String getNewAuthToken(Account account, int hours) {
        Date newIssuedDate = new Date();
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] applicationSecretBytes = DatatypeConverter.parseBase64Binary(applicationSecret);
        Key signingKey = new SecretKeySpec(applicationSecretBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setId(new BigInteger(130, new SecureRandom()).toString(32))
                .setId(account.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(DateUtils.addHours(newIssuedDate, hours))
                .signWith(signatureAlgorithm, signingKey)
                .claim("id", account.getId())
                .compact();
    }

    public ServiceResult validateAuthToken(String authToken) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setHttpStatus(HttpStatus.UNAUTHORIZED);
        if (authToken == null) {
            serviceResult.setModel(new ErrorResponse(AuthConstant.NO_AUTH_TOKEN_FOR_ACCOUNT.getValue()));
            return serviceResult;
        }
        try {
            Claims claim = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(applicationSecret))
                    .parseClaimsJws(authToken).getBody();
            serviceResult.setHttpStatus(HttpStatus.OK);
            serviceResult.setModel(accountRepository.findAccountById(Long.valueOf((Integer)claim.get("id"))));
        } catch(ExpiredJwtException e) {
            serviceResult.setModel(new ErrorResponse(AuthConstant.AUTH_TOKEN_HAS_EXPIRED.getValue()));
        } catch (SignatureException e) {
            serviceResult.setModel(new ErrorResponse(AuthConstant.INVALID_AUTH_TOKEN.getValue()));
        } catch (Exception e) {
            serviceResult.setModel(new ErrorResponse(AuthConstant.AUTH_TOKEN_VALIDATION_FAILURE.getValue()));
        }
        return serviceResult;
    }

    public boolean checkPassword(Credentials credentials, Account account) {

        return DigestUtils.sha256Hex(account.getPasswordSalt() + credentials.getPassword()).equals(account.getPassword());
    }

    public String createRandomPasswordSalt() {
        char[] ch = AuthConstant.ALPHA_NUMERIC.getValue().toCharArray();
        char[] c = new char[50];
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 50; i++) {
            c[i] = ch[random.nextInt(ch.length)];
        }
        return new String(c);
    }
}
