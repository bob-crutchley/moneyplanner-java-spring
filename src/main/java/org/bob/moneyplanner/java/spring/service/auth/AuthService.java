package org.bob.moneyplanner.java.spring.service.auth;

import org.bob.moneyplanner.java.spring.constant.AuthConstant;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
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
    private final LogoutOperation logoutOperation;

    @Autowired
    public AuthService(LoginOperation loginOperation,
                       LogoutOperation logoutOperation) {
        this.loginOperation = loginOperation;
        this.logoutOperation = logoutOperation;
    }

    public ServiceResult login(Credentials credentials) {
        return loginOperation.execute(credentials);
    }

    public ServiceResult logout(Account account) {
        return logoutOperation.execute(account);
    }
}
