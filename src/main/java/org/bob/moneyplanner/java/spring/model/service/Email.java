package org.bob.moneyplanner.java.spring.model.service;

import org.bob.moneyplanner.java.spring.model.Model;

public class Email extends Model {
    private String toAddress;
    private String fromAddress;
    private String subject;
    private String content;


    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getFrom() {
        return fromAddress;
    }

    public void setFrom(String from) {
        this.fromAddress = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
