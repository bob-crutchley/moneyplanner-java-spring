package org.bob.moneyplanner.java.spring.controller;

import com.google.gson.Gson;
import org.bob.moneyplanner.java.spring.model.Account;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.bob.moneyplanner.java.spring.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

@Controller
public class AccountController {

    private final Logger log = Logger.getLogger(AccountController.class.getName());
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping("/register")
    @ResponseBody
    public ResponseEntity<Account> register(@RequestBody String account) {
        ServiceResult serviceResult = accountService.register(new Gson().fromJson(account, Account.class));
        return ResponseEntity.status(serviceResult.getHttpStatus()).body((Account)serviceResult.getModel());
    }

    @RequestMapping("/login")
    @ResponseBody
    public ResponseEntity<Account> login(@RequestBody String account) {
        ServiceResult serviceResult = accountService.register(new Gson().fromJson(account, Account.class));
        return ResponseEntity.status(serviceResult.getHttpStatus()).body((Account)serviceResult.getModel());
    }
}
