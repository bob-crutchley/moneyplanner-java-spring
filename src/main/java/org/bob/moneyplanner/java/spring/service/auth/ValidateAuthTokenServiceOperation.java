package org.bob.moneyplanner.java.spring.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.bob.moneyplanner.java.spring.auth.Authenticator;
import org.bob.moneyplanner.java.spring.constant.AuthConstant;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.service.AuthToken;
import org.bob.moneyplanner.java.spring.model.service.ErrorResponse;
import org.bob.moneyplanner.java.spring.repository.AccountRepository;
import org.bob.moneyplanner.java.spring.service.ServiceOperation;
import org.bob.moneyplanner.java.spring.service.ServiceRequest;
import org.bob.moneyplanner.java.spring.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.logging.Logger;

@Component
public class ValidateAuthTokenServiceOperation extends ServiceOperation {

    private final Logger log = Logger.getLogger(ValidateAuthTokenServiceOperation.class.getName());
    private final AccountRepository accountRepository;
    private final Environment environment;

    @Autowired
    public ValidateAuthTokenServiceOperation(AccountRepository accountRepository,
                                             Environment environment) {
        this.accountRepository = accountRepository;
        this.environment= environment;
    }

    @Override
    public ServiceResponse execute(Model accountModel) {
        return null;
    }

    @Override
    public ServiceResponse execute(ServiceRequest serviceRequest) {
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
        if (serviceRequest.getAuthToken() == null) {
            serviceResponse.setModel(new ErrorResponse(AuthConstant.NO_AUTH_TOKEN_FOR_ACCOUNT.getValue()));
            return serviceResponse;
        }
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(environment.getProperty("application.secret")))
                    .parseClaimsJws(serviceRequest.getAuthToken()).getBody();
            AuthToken authToken = new AuthToken(Long.valueOf(claims.getId()), claims.getIssuedAt(), claims.getExpiration(), Long.valueOf((Integer)claims.get("id")));
            serviceResponse.setHttpStatus(HttpStatus.OK);
            serviceResponse.setModel(authToken);
        } catch(ExpiredJwtException e) {
            serviceResponse.setModel(new ErrorResponse(AuthConstant.AUTH_TOKEN_HAS_EXPIRED.getValue()));
        } catch (SignatureException e) {
            serviceResponse.setModel(new ErrorResponse(AuthConstant.INVALID_AUTH_TOKEN.getValue()));
        } catch (Exception e) {
            serviceResponse.setModel(new ErrorResponse(AuthConstant.AUTH_TOKEN_VALIDATION_FAILURE.getValue()));
        }
        return serviceResponse;
    }
}
