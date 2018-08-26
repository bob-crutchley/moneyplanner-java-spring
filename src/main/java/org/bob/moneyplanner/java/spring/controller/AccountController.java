package org.bob.moneyplanner.java.spring.controller;

import com.google.gson.Gson;
import org.bob.moneyplanner.java.spring.auth.Authenticator;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.bob.moneyplanner.java.spring.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;

@Controller
public class AccountController extends SecureController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService, Authenticator authenticator) {
        super(authenticator);
        this.accountService = accountService;
    }

    @RequestMapping("/register")
    @ResponseBody
    public ResponseEntity<Model> register(@RequestBody String account) {
        ServiceResult serviceResult = accountService.register(new Gson().fromJson(account, Account.class));
        return ResponseEntity.status(serviceResult.getHttpStatus()).body(serviceResult.getModel());
    }

    @RequestMapping("/test")
    @ResponseBody
    public ResponseEntity<Model> test(@RequestHeader(value = "Session-Token") String sessionToken) {
        return runIfAuthenticated(sessionToken, (account) -> ResponseEntity.status(HttpStatus.OK).body(account));
    }
}
