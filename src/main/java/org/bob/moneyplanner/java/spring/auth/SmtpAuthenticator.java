package org.bob.moneyplanner.java.spring.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.mail.PasswordAuthentication;

@Component
public class SmtpAuthenticator extends javax.mail.Authenticator {
    private final Environment environment;

    @Autowired
    public SmtpAuthenticator(Environment environment) {
        super();
        this.environment = environment;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        String username = environment.getProperty("spring.mail.username");
        String password = environment.getProperty("spring.mail.password");
        return new PasswordAuthentication(username, password);
    }
}