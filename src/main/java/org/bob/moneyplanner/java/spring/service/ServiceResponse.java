package org.bob.moneyplanner.java.spring.service;

import org.bob.moneyplanner.java.spring.model.Model;
import org.springframework.http.HttpStatus;

public class ServiceResponse {
    private HttpStatus httpStatus;
    private Model model;

    public ServiceResponse(){}

    public ServiceResponse(HttpStatus httpStatus, Model model) {
        this.httpStatus = httpStatus;
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

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
