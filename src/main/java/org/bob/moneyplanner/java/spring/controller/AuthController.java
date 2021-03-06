package org.bob.moneyplanner.java.spring.controller;

import com.google.gson.Gson;
import org.bob.moneyplanner.java.spring.auth.Authenticator;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.model.service.Credentials;
import org.bob.moneyplanner.java.spring.service.ServiceResult;
import org.bob.moneyplanner.java.spring.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController extends SecureController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService, Authenticator authenticator) {
        super(authenticator);
        this.authService = authService;
    }

    @RequestMapping("/login")
    @ResponseBody
    public ResponseEntity<Model> login(@RequestBody String credentials) {
        ServiceResult serviceResult = authService.login(new Gson().fromJson(credentials, Credentials.class));
        return ResponseEntity.status(serviceResult.getHttpStatus()).body(serviceResult.getModel());
    }

    @RequestMapping("/logout")
    @ResponseBody
    public ResponseEntity<Model> logout(@RequestHeader(value = "Auth-Token") String authToken) {
        return runIfAuthenticated(authToken, (account) -> {
            ServiceResult serviceResult = authService.logout(account);
            return ResponseEntity.status(serviceResult.getHttpStatus()).body(serviceResult.getModel());
        });
    }
}
