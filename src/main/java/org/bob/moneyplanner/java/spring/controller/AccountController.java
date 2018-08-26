package org.bob.moneyplanner.java.spring.controller;

import com.google.gson.Gson;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.bob.moneyplanner.java.spring.service.account.AccountService;
import org.bob.moneyplanner.java.spring.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountController {

    private final AccountService accountService;
    private final AuthService authService;

    @Autowired
    public AccountController(AccountService accountService, AuthService authService) {
        this.accountService = accountService;
        this.authService = authService;
    }

    @RequestMapping("/register")
    @ResponseBody
    public ResponseEntity<Model> register(@RequestBody String account) {
        ServiceResult serviceResult = accountService.register(new Gson().fromJson(account, Account.class));
        return ResponseEntity.status(serviceResult.getHttpStatus()).body(serviceResult.getModel());
    }
}
