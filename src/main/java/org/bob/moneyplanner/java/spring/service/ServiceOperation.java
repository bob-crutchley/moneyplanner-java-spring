package org.bob.moneyplanner.java.spring.service;


import org.bob.moneyplanner.java.spring.model.Model;

public abstract class ServiceOperation {
    public abstract ServiceResponse execute(Model model);
    public abstract ServiceResponse execute(ServiceRequest serviceRequest);
}
