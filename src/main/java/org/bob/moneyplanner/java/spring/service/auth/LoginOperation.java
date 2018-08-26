package org.bob.moneyplanner.java.spring.service.auth;

import org.apache.commons.codec.digest.DigestUtils;
import org.bob.moneyplanner.java.spring.constant.AuthConstant;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.model.service.Credentials;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.service.ErrorResponse;
import org.bob.moneyplanner.java.spring.repository.AccountRepository;
import org.bob.moneyplanner.java.spring.service.Operation;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class LoginOperation extends Operation {

    private final AccountRepository accountRepository;

    @Autowired
    public LoginOperation(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ServiceResult execute(Model credentialsModel) {
        Credentials credentials = (Credentials) credentialsModel;
        Account account = accountRepository.findAccountByEmail(credentials.getEmail());
        ServiceResult serviceResult = new ServiceResult();
        if (account == null) {
            serviceResult.setHttpStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setModel(new ErrorResponse(AuthConstant.ACCOUNT_DOES_NOT_EXIST.getValue()));
        } else if (DigestUtils.sha256Hex(credentials.getPassword()).equals(account.getPassword())) {
            serviceResult.setHttpStatus(HttpStatus.OK);
            serviceResult.setModel(account);
        } else {
            serviceResult.setHttpStatus(HttpStatus.UNAUTHORIZED);
            serviceResult.setModel(new ErrorResponse(AuthConstant.INCORRECT_PASSWORD.getValue()));
        }
        return serviceResult;
    }
}
