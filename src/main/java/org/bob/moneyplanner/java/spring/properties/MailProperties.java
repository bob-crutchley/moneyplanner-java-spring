package org.bob.moneyplanner.java.spring.properties;

import org.springframework.core.env.Environment;
import java.util.Objects;
import java.util.Properties;

public class MailProperties extends Properties {
    public MailProperties(Environment environment) {
        super();
        this.put("mail.smtp.host", Objects.requireNonNull(environment.getProperty("spring.mail.host")));
        this.put("mail.smtp.port", Objects.requireNonNull(environment.getProperty("spring.mail.port")));
        this.put("mail.smtp.starttls.enable", Objects.requireNonNull(environment.getProperty("spring.mail.properties.mail.smtp.starttls.enable")));
        this.setProperty("mail.transport.protocol", "smtp");
        this.setProperty("mail.smtp.auth", Objects.requireNonNull(environment.getProperty("spring.mail.properties.mail.smtp.auth")));
    }
}
