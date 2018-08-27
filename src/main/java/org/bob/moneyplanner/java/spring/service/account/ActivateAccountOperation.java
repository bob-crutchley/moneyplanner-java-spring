package org.bob.moneyplanner.java.spring.service.account;

import org.bob.moneyplanner.java.spring.auth.Authenticator;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.repository.AccountRepository;
import org.bob.moneyplanner.java.spring.service.Operation;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ActivateAccountOperation extends Operation {

    private final Authenticator authenticator;
    private final AccountRepository accountRepository;

    @Autowired
    public ActivateAccountOperation(Authenticator authenticator, AccountRepository accountRepository) {
        this.authenticator = authenticator;
        this.accountRepository = accountRepository;
    }

    @Override
    public ServiceResult execute(Model model) {
        Account account = (Account) model;
        ServiceResult authenticationServiceResult = authenticator.validateAuthToken(account.getAuthToken());
        if (authenticationServiceResult.getHttpStatus() == HttpStatus.OK) {
            account = (Account) authenticationServiceResult.getModel();
            account.setAuthToken("");
            account.setAccountActivated(true);
            accountRepository.save(account);
        }
        return authenticationServiceResult;
    }
}
