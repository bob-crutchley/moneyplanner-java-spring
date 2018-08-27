package org.bob.moneyplanner.java.spring.controller;

import com.google.gson.Gson;
import org.bob.moneyplanner.java.spring.auth.Authenticator;
import org.bob.moneyplanner.java.spring.constant.AuthConstant;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.bob.moneyplanner.java.spring.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/activate/{authToken}")
    @ResponseBody
    public ResponseEntity<Model> activate(@PathVariable String authToken) {
        Account account = new Account();
        account.setAuthToken(authToken);
        ServiceResult serviceResult = accountService.activate(account);
        return ResponseEntity.status(serviceResult.getHttpStatus()).body(serviceResult.getModel());
    }

    @RequestMapping("/test")
    @ResponseBody
    public ResponseEntity<Model> test(@RequestHeader(value = "Auth-Token") String sessionToken) {
        return runIfAuthenticated(sessionToken, (account) -> ResponseEntity.status(HttpStatus.OK).body(account));
    }
}
