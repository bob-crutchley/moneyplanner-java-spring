package org.bob.moneyplanner.java.spring.service.auth;

import org.bob.moneyplanner.java.spring.model.Credentials;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final LoginOperation loginOperation;

    @Autowired
    public AuthService(LoginOperation loginOperation) {
        this.loginOperation = loginOperation;
    }

    public ServiceResult login(Credentials credentials) {
        return loginOperation.execute(credentials);
    }
}
