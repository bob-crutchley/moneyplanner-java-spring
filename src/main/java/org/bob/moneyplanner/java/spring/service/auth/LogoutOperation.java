package org.bob.moneyplanner.java.spring.service.auth;

import org.bob.moneyplanner.java.spring.auth.Authenticator;
import org.bob.moneyplanner.java.spring.constant.AuthConstant;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.model.service.Credentials;
import org.bob.moneyplanner.java.spring.model.service.ErrorResponse;
import org.bob.moneyplanner.java.spring.repository.AccountRepository;
import org.bob.moneyplanner.java.spring.service.Operation;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class LogoutOperation extends Operation {

    private final Logger log = Logger.getLogger(LogoutOperation.class.getName());
    private final AccountRepository accountRepository;
    private final Authenticator authenticator;

    @Autowired
    public LogoutOperation(AccountRepository accountRepository , Authenticator authenticator) {
        this.accountRepository = accountRepository;
        this.authenticator = authenticator;
    }

    @Override
    public ServiceResult execute(Model accountModel) {
        Account account = (Account) accountModel;
        ServiceResult serviceResult = new ServiceResult();
        account.setAuthToken("");
        accountRepository.save(account);
        serviceResult.setHttpStatus(HttpStatus.OK);
        serviceResult.setModel(account);
        return serviceResult;
    }
}
