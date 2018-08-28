package org.bob.moneyplanner.java.spring.service.account;

import org.apache.commons.codec.digest.DigestUtils;
import org.bob.moneyplanner.java.spring.auth.Authenticator;
import org.bob.moneyplanner.java.spring.constant.AccountConstant;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.repository.AccountRepository;
import org.bob.moneyplanner.java.spring.service.ServiceOperation;
import org.bob.moneyplanner.java.spring.service.ServiceRequest;
import org.bob.moneyplanner.java.spring.service.ServiceResponse;
import org.bob.moneyplanner.java.spring.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.bob.moneyplanner.java.spring.model.service.ErrorResponse;


@Component
public class RegisterServiceOperation extends ServiceOperation {

    private final AccountRepository accountRepository;
    private final MailService mailService;
    private final Authenticator authenticator;

    @Autowired
    public RegisterServiceOperation(AccountRepository accountRepository, MailService mailService, Authenticator authenticator) {
        this.accountRepository = accountRepository;
        this.mailService = mailService;
        this.authenticator = authenticator;
    }

    @Override
    public ServiceResponse execute(Model accountModel) {
        return null;
    }

    @Override
    public ServiceResponse execute(ServiceRequest serviceRequest) {
        Account account = (Account) serviceRequest.getModel();
        ServiceResponse serviceResponse = new ServiceResponse();
        if (accountRepository.findAccountByEmail(account.getEmail()) != null) {
            serviceResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            serviceResponse.setModel(new ErrorResponse(AccountConstant.ACCOUNT_EXISTS_WITH_SAME_EMAIL.getValue()));
            return serviceResponse;
        }
        account.setPasswordSalt(authenticator.createRandomPasswordSalt());
        account.setPassword(DigestUtils.sha256Hex(account.getPasswordSalt() + account.getPassword()));
        account.setAccountActivated(false);
        accountRepository.save(account);
        account = accountRepository.findAccountByEmail(account.getEmail());
        account.setAuthToken(authenticator.getNewAuthToken(account, 1));
        ServiceResponse emailServiceResponse = mailService.sendRegistrationEmail(account);
        if (emailServiceResponse.getHttpStatus() == HttpStatus.OK) {
            serviceResponse.setHttpStatus(HttpStatus.OK);
            serviceResponse.setModel(account);
        } else {
            serviceResponse = emailServiceResponse;
        }
        return serviceResponse;
    }


}
