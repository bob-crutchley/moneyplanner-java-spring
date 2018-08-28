package org.bob.moneyplanner.java.spring.service.account;

import org.bob.moneyplanner.java.spring.auth.Authenticator;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.repository.AccountRepository;
import org.bob.moneyplanner.java.spring.service.ServiceOperation;
import org.bob.moneyplanner.java.spring.service.ServiceRequest;
import org.bob.moneyplanner.java.spring.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ActivateAccountServiceOperation extends ServiceOperation {

    private final Authenticator authenticator;
    private final AccountRepository accountRepository;

    @Autowired
    public ActivateAccountServiceOperation(Authenticator authenticator, AccountRepository accountRepository) {
        this.authenticator = authenticator;
        this.accountRepository = accountRepository;
    }

    @Override
    public ServiceResponse execute(Model model) {
        Account account = (Account) model;
        ServiceResponse authenticationServiceResponse = authenticator.validateAuthToken(account.getAuthToken());
        if (authenticationServiceResponse.getHttpStatus() == HttpStatus.OK) {
            account = (Account) authenticationServiceResponse.getModel();
            account.setAuthToken("");
            account.setAccountActivated(true);
            accountRepository.save(account);
        }
        return authenticationServiceResponse;
    }

    @Override
    public ServiceResponse execute(ServiceRequest serviceRequest) {
        return null;
    }
}
