package org.bob.moneyplanner.java.spring.service.auth;

import org.bob.moneyplanner.java.spring.auth.Authenticator;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.repository.AccountRepository;
import org.bob.moneyplanner.java.spring.service.ServiceOperation;
import org.bob.moneyplanner.java.spring.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class LogoutServiceOperation extends ServiceOperation {

    private final Logger log = Logger.getLogger(LogoutServiceOperation.class.getName());
    private final AccountRepository accountRepository;
    private final Authenticator authenticator;

    @Autowired
    public LogoutServiceOperation(AccountRepository accountRepository , Authenticator authenticator) {
        this.accountRepository = accountRepository;
        this.authenticator = authenticator;
    }

    @Override
    public ServiceResponse execute(Model accountModel) {
        Account account = (Account) accountModel;
        ServiceResponse serviceResponse = new ServiceResponse();
        account.setAuthToken("");
        accountRepository.save(account);
        serviceResponse.setHttpStatus(HttpStatus.OK);
        serviceResponse.setModel(account);
        return serviceResponse;
    }
}
