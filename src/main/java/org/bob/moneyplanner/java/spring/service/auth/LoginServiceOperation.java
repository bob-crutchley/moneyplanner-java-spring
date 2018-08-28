package org.bob.moneyplanner.java.spring.service.auth;

import org.bob.moneyplanner.java.spring.auth.Authenticator;
import org.bob.moneyplanner.java.spring.constant.AuthConstant;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.model.service.Credentials;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.service.ErrorResponse;
import org.bob.moneyplanner.java.spring.repository.AccountRepository;
import org.bob.moneyplanner.java.spring.service.ServiceOperation;
import org.bob.moneyplanner.java.spring.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class LoginServiceOperation extends ServiceOperation {

    private final Logger log = Logger.getLogger(LoginServiceOperation.class.getName());
    private final AccountRepository accountRepository;
    private final Authenticator authenticator;

    @Autowired
    public LoginServiceOperation(AccountRepository accountRepository , Authenticator authenticator) {
        this.accountRepository = accountRepository;
        this.authenticator = authenticator;
    }

    @Override
    public ServiceResponse execute(Model credentialsModel) {
        Credentials credentials = (Credentials) credentialsModel;
        Account account = accountRepository.findAccountByEmail(credentials.getEmail());
        ServiceResponse serviceResponse = new ServiceResponse();
        if (account == null) {
            serviceResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            serviceResponse.setModel(new ErrorResponse(AuthConstant.ACCOUNT_DOES_NOT_EXIST.getValue()));
        } else if(!account.isAccountActivated()) {
            serviceResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
            serviceResponse.setModel(new ErrorResponse(AuthConstant.ACCOUNT_NOT_ACTIVATED.getValue()));
        } else if (authenticator.checkPassword(credentials, account)) {
            serviceResponse.setHttpStatus(HttpStatus.OK);
            account.setAuthToken(authenticator.getNewAuthToken(account, 24));
            return authenticator.validateAuthToken(account.getAuthToken());
        } else {
            serviceResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
            serviceResponse.setModel(new ErrorResponse(AuthConstant.INCORRECT_PASSWORD.getValue()));
        }
        return serviceResponse;
    }
}
