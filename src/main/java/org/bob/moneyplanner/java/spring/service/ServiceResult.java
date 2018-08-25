package org.bob.moneyplanner.java.spring.service;

import org.bob.moneyplanner.java.spring.model.Model;
import org.springframework.http.HttpStatus;

public class ServiceResult {
    private final HttpStatus httpStatus;
    private final String message;
    private Model model;

    public ServiceResult(HttpStatus httpStatus, String message, Model model) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
