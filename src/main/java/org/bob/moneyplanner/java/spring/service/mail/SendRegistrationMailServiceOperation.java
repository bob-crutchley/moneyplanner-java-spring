package org.bob.moneyplanner.java.spring.service.mail;

import org.bob.moneyplanner.java.spring.auth.SmtpAuthenticator;
import org.bob.moneyplanner.java.spring.constant.EmailConstant;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.service.Email;
import org.bob.moneyplanner.java.spring.model.service.ErrorResponse;
import org.bob.moneyplanner.java.spring.properties.MailProperties;
import org.bob.moneyplanner.java.spring.service.ServiceOperation;
import org.bob.moneyplanner.java.spring.service.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class SendRegistrationMailServiceOperation extends ServiceOperation {

    private final Environment environment;
    private final SmtpAuthenticator smtpAuthenticator;

    @Autowired
    public SendRegistrationMailServiceOperation(Environment environment,
                                                SmtpAuthenticator smtpAuthenticator) {
        this.environment = environment;
        this.smtpAuthenticator = smtpAuthenticator;
    }

    @Override
    public ServiceResponse execute(Model model) {
        Email email = (Email) model;
        ServiceResponse serviceResponse = new ServiceResponse();
        MimeMessage message;
        Session session = Session.getDefaultInstance(new MailProperties(environment), smtpAuthenticator);
        message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(email.getFrom()));
            message.setSubject(email.getSubject());
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getToAddress()));
            message.setText(email.getContent(), "utf-8", "html");
            message.saveChanges();
            Transport.send(message);
            serviceResponse.setHttpStatus(HttpStatus.OK);
            serviceResponse.setModel(email);
        } catch (MessagingException e) {
            e.printStackTrace();
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            serviceResponse.setModel(new ErrorResponse(EmailConstant.EMAIL_TRANSPORT_FAILURE.getValue()));
        }
        return serviceResponse;
    }
}
