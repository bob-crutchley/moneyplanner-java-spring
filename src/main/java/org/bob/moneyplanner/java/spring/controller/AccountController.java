package org.bob.moneyplanner.java.spring.controller;

import org.bob.moneyplanner.java.spring.auth.Authenticator;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.model.service.Credentials;
import org.bob.moneyplanner.java.spring.service.ServiceResponse;
import org.bob.moneyplanner.java.spring.service.account.AccountApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccountController extends SecureController {

    private final AccountApplicationService accountService;

    @Autowired
    public AccountController(AccountApplicationService accountService, Authenticator authenticator) {
        super(authenticator);
        this.accountService = accountService;
    }

    @RequestMapping("/register")
    @ResponseBody
    public ResponseEntity<Model> register(HttpServletRequest httpServletRequest) {
        return  unauthenticatedServiceRequest(httpServletRequest, Credentials.class, accountService::register);
    }

    @RequestMapping("/activate/{authToken}")
    @ResponseBody
    public ResponseEntity<Model> activate(@PathVariable String authToken) {
        Account account = new Account();
        account.setAuthToken(authToken);
        ServiceResponse serviceResponse = accountService.activate(account);
        return ResponseEntity.status(serviceResponse.getHttpStatus()).body(serviceResponse.getModel());
    }

    @RequestMapping("/test")
    @ResponseBody
    public ResponseEntity<Model> test(@RequestHeader(value = "Auth-Token") String sessionToken) {
        return null;
        // return authenticatedServiceRequest(sessionToken, (account) -> ResponseEntity.status(HttpStatus.OK).body(account));
    }
}
