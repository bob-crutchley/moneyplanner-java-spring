package org.bob.moneyplanner.java.spring.service.account;


import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.service.ApplicationService;
import org.bob.moneyplanner.java.spring.service.ServiceRequest;
import org.bob.moneyplanner.java.spring.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AccountApplicationService extends ApplicationService {

    private final Logger log = Logger.getLogger(AccountApplicationService.class.getName());
    private final RegisterServiceOperation registerOperation;
    private final ActivateAccountServiceOperation activateAccountOperation;

    @Autowired
    public AccountApplicationService(RegisterServiceOperation registerOperation,
                                     ActivateAccountServiceOperation activateAccountOperation) {
        this.registerOperation = registerOperation;
        this.activateAccountOperation = activateAccountOperation;
    }

    public ServiceResponse register(ServiceRequest serviceRequest) {
        return serviceOperation(serviceRequest, registerOperation::execute);
    }

    public ServiceResponse activate(Account account) {
        return activateAccountOperation.execute(account);
    }
}
