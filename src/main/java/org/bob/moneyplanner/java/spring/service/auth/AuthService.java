package org.bob.moneyplanner.java.spring.service.auth;

import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.model.service.Credentials;
import org.bob.moneyplanner.java.spring.service.ApplicationService;
import org.bob.moneyplanner.java.spring.service.ServiceRequest;
import org.bob.moneyplanner.java.spring.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AuthService extends ApplicationService {

    private final Logger log = Logger.getLogger(AuthService.class.getName());
    private final LoginServiceOperation loginOperation;
    private final LogoutServiceOperation logoutOperation;
    private final ValidateAuthTokenServiceOperation validateAuthTokenOperation;

    @Autowired
    public AuthService(LoginServiceOperation loginOperation,
                       LogoutServiceOperation logoutOperation,
                       ValidateAuthTokenServiceOperation validateAuthTokenOperation) {
        this.loginOperation = loginOperation;
        this.logoutOperation = logoutOperation;
        this.validateAuthTokenOperation = validateAuthTokenOperation;
    }

    public ServiceResponse login(Credentials credentials) {
        return loginOperation.execute(credentials);
    }

    public ServiceResponse logout(Account account) {
        return logoutOperation.execute(account);
    }
    public ServiceResponse validateAuthToken(ServiceRequest serviceRequest) {
        return serviceOperation(serviceRequest, validateAuthTokenOperation::execute);
    }
}
