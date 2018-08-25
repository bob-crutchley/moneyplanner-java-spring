package org.bob.moneyplanner.java.spring.service.account;

import org.apache.commons.codec.digest.DigestUtils;
import org.bob.moneyplanner.java.spring.model.Account;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.repository.AccountRepository;
import org.bob.moneyplanner.java.spring.service.Operation;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class RegisterOperation extends Operation {

    private final Logger log = Logger.getLogger(RegisterOperation.class.getName());
    private final AccountRepository accountRepository;

    @Autowired
    public RegisterOperation(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ServiceResult execute(Model accountModel) {
        Account account = (Account) accountModel;
        account.setPassword(DigestUtils.sha256Hex(account.getPassword()));
        try {
            accountRepository.save(account);
        } catch (Exception e) {
            log.severe("Could  not create account:\n" + e.getMessage());
            return new ServiceResult(HttpStatus.INTERNAL_SERVER_ERROR, "Could Not Register Account", null);
        }
        return new ServiceResult(HttpStatus.OK, "Account Registered", account);
    }
}
