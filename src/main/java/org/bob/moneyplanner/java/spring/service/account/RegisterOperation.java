package org.bob.moneyplanner.java.spring.service.account;

import org.apache.commons.codec.digest.DigestUtils;
import org.bob.moneyplanner.java.spring.constant.AccountConstant;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.repository.AccountRepository;
import org.bob.moneyplanner.java.spring.service.Operation;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.bob.moneyplanner.java.spring.model.service.ErrorResponse;

@Component
public class RegisterOperation extends Operation {

    private final AccountRepository accountRepository;

    @Autowired
    public RegisterOperation(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ServiceResult execute(Model accountModel) {
        Account account = (Account) accountModel;
        ServiceResult serviceResult = new ServiceResult();
        if (accountRepository.findAccountByEmail(account.getEmail()) != null) {
            serviceResult.setHttpStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setModel(new ErrorResponse(AccountConstant.ACCOUNT_EXISTS_WITH_SAME_EMAIL.getValue()));
            return serviceResult;
        }
        account.setPassword(DigestUtils.sha256Hex(account.getPassword()));
        accountRepository.save(account);
        serviceResult.setHttpStatus(HttpStatus.OK);
        serviceResult.setModel(account);
        return serviceResult;
    }
}
