package org.bob.moneyplanner.java.spring.service.account;


import org.bob.moneyplanner.java.spring.model.Account;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final RegisterOperation registerOperation;

    @Autowired
    public AccountService(RegisterOperation registerOperation) {
        this.registerOperation = registerOperation;
    }

    public ServiceResult register(Account account) {
        return registerOperation.execute(account);
    }
}
