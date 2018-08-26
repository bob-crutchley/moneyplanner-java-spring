package org.bob.moneyplanner.java.spring.service.account;

import org.apache.commons.codec.digest.DigestUtils;
import org.bob.moneyplanner.java.spring.constant.AccountConstant;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.repository.AccountRepository;
import org.bob.moneyplanner.java.spring.service.Operation;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.bob.moneyplanner.java.spring.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.bob.moneyplanner.java.spring.model.service.ErrorResponse;


@Component
public class RegisterOperation extends Operation {

    private final AccountRepository accountRepository;
    private final MailService mailService;

    @Autowired
    public RegisterOperation(AccountRepository accountRepository, MailService mailService) {
        this.accountRepository = accountRepository;
        this.mailService = mailService;
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
        ServiceResult emailServiceResult = mailService.sendRegistrationEmail(account);
        if (emailServiceResult.getHttpStatus() == HttpStatus.OK) {
            serviceResult.setHttpStatus(HttpStatus.OK);
            serviceResult.setModel(account);
        } else {
            serviceResult = emailServiceResult;
        }
        return serviceResult;
    }


}
