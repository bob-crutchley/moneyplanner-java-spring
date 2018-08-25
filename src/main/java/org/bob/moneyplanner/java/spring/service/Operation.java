package org.bob.moneyplanner.java.spring.service;


import org.bob.moneyplanner.java.spring.model.Model;

public abstract class Operation {
    public abstract ServiceResult execute(Model model);
}
