package org.bob.moneyplanner.java.spring.controller;

import org.bob.moneyplanner.java.spring.auth.Authenticator;
import org.bob.moneyplanner.java.spring.constant.ControllerConstant;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.model.service.ErrorResponse;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.function.Function;

@Component
abstract class SecureController {

    private final Authenticator authenticator;

    public SecureController(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    ResponseEntity<Model> runIfAuthenticated(String sessionToken, Function<Account, ResponseEntity<Model>> exec) {
        ResponseEntity<Model> responseEntity;
        ServiceResult authenticationResult = authenticator.authenticateSessionToken(sessionToken);
        if(authenticationResult.getHttpStatus() == HttpStatus.OK) {
            try {
                responseEntity = exec.apply((Account) authenticationResult.getModel());
            } catch(Exception e) {
                e.printStackTrace();
                responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse(ControllerConstant.REQUEST_FAILURE.getValue()));
            }
        } else {
            responseEntity = ResponseEntity.status(authenticationResult.getHttpStatus())
                    .body(authenticationResult.getModel());
        }
        return responseEntity;
    }
}
