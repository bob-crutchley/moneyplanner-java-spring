package org.bob.moneyplanner.java.spring.model.service;

import org.bob.moneyplanner.java.spring.model.Model;

public class ErrorResponse extends Model {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
