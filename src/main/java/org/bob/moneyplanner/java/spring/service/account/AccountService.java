package org.bob.moneyplanner.java.spring.service.account;


import org.bob.moneyplanner.java.spring.constant.AccountConstant;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.model.service.ErrorResponse;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AccountService {

    private final Logger log = Logger.getLogger(AccountService.class.getName());
    private final RegisterOperation registerOperation;
    private final ActivateAccountOperation activateAccountOperation;

    @Autowired
    public AccountService(RegisterOperation registerOperation,
                          ActivateAccountOperation activateAccountOperation) {
        this.registerOperation = registerOperation;
        this.activateAccountOperation = activateAccountOperation;
    }

    public ServiceResult register(Account account) {
        ServiceResult serviceResult = new ServiceResult();
        try {
            serviceResult = registerOperation.execute(account);
        } catch (Exception e) {
            log.severe("error registering account");
            e.printStackTrace();
            serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            serviceResult.setModel(new ErrorResponse(AccountConstant.FAILED_TO_CREATE_ACCOUNT.getValue()));
        }
        return serviceResult;
    }

    public ServiceResult activate(Account account) {
        return activateAccountOperation.execute(account);
    }
}
