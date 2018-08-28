package org.bob.moneyplanner.java.spring.service.mail;

import org.bob.moneyplanner.java.spring.constant.EmailConstant;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.model.service.Email;
import org.bob.moneyplanner.java.spring.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.bob.moneyplanner.java.spring.template.EmailTemplate;

@Service
public class MailService {

    private final Environment environment;
    private final SendRegistrationMailServiceOperation sendRegistrationMailOperation;

    @Autowired
    public MailService(Environment environment,
                       SendRegistrationMailServiceOperation sendRegistrationMailOperation) {
        this.environment = environment;
        this.sendRegistrationMailOperation = sendRegistrationMailOperation;
    }

    public ServiceResponse sendRegistrationEmail(Account account) {
        Email email = new Email();
        email.setSubject(EmailConstant.REGISTRATION_SUBJECT.getValue());
        email.setToAddress(account.getEmail());
        email.setFrom(environment.getProperty("spring.mail.username"));
        email.setContent(new EmailTemplate(EmailConstant.REGISTRATION_TEMPLATE_PATH.getValue(), environment).getContentForAccount(account));
        return sendRegistrationMailOperation.execute(email);
    }
}
