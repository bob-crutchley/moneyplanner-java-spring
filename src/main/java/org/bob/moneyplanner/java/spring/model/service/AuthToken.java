package org.bob.moneyplanner.java.spring.model.service;

import org.bob.moneyplanner.java.spring.model.Model;

import java.util.Date;

public class AuthToken extends Model {
    private Long id;
    private Date issueDate;
    private Date expirationDate;
    private Long accountId;

    public AuthToken(Long id, Date issueDate, Date expirationDate, Long accountId) {
        this.id = id;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
