package org.bob.moneyplanner.java.spring.service.auth;

import org.bob.moneyplanner.java.spring.constant.AuthConstant;
import org.bob.moneyplanner.java.spring.model.service.Credentials;
import org.bob.moneyplanner.java.spring.model.service.ErrorResponse;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AuthService {

    private final Logger log = Logger.getLogger(AuthService.class.getName());

    private final LoginOperation loginOperation;

    @Autowired
    public AuthService(LoginOperation loginOperation) {
        this.loginOperation = loginOperation;
    }

    public ServiceResult login(Credentials credentials) {
        ServiceResult serviceResult = new ServiceResult();
        try {
            serviceResult = loginOperation.execute(credentials);
        } catch (Exception e) {
            log.severe("error logging in");
            e.printStackTrace();
            serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            serviceResult.setModel(new ErrorResponse(AuthConstant.LOGIN_FAILURE.getValue()));
        }
        return serviceResult;
    }


}
