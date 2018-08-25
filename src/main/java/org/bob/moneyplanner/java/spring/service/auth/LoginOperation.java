package org.bob.moneyplanner.java.spring.service.auth;

import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.digest.DigestUtils;
import org.bob.moneyplanner.java.spring.model.Account;
import org.bob.moneyplanner.java.spring.model.Credentials;
import org.bob.moneyplanner.java.spring.model.Model;
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
        if (account != null && bothPasswordsMatch(credentials.getPassword(), account.getPassword())) {
            return new ServiceResult(HttpStatus.OK, "Logged in successfully", account);
        }
        return new ServiceResult(HttpStatus.UNAUTHORIZED, "Could  not log in", null);
    }

    private boolean bothPasswordsMatch(String requestedPlaintext, String actualHashed) {
        return DigestUtils.sha256Hex(requestedPlaintext).equals(actualHashed);
    }
}
