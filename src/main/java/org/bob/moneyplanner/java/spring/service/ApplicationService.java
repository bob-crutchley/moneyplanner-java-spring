package org.bob.moneyplanner.java.spring.service;

import java.util.function.Function;

public abstract class ApplicationService {
    protected ServiceResponse serviceOperation(ServiceRequest serviceRequest, Function<ServiceRequest, ServiceResponse> exec) {
        return exec.apply(serviceRequest);
    }
}
