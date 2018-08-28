package org.bob.moneyplanner.java.spring.controller;

import org.bob.moneyplanner.java.spring.auth.Authenticator;
import org.bob.moneyplanner.java.spring.constant.ControllerConstant;
import org.bob.moneyplanner.java.spring.model.Model;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.bob.moneyplanner.java.spring.model.service.ErrorResponse;
import org.bob.moneyplanner.java.spring.service.ServiceRequest;
import org.bob.moneyplanner.java.spring.service.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.function.Function;

@Component
abstract class SecureController {

    private final Authenticator authenticator;

    public SecureController(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    ResponseEntity<Model> authenticatedServiceRequest(HttpServletRequest httpServletRequest,
                                                      Class<?> modelClass,
                                                      Function<ServiceRequest, ServiceResponse> exec) {
        ResponseEntity<Model> responseEntity;

        ServiceResponse serviceResponse = validServiceRequest(httpServletRequest, modelClass, exec);

        return null;
    }

    ResponseEntity<Model> unauthenticatedServiceRequest(HttpServletRequest httpServletRequest,
                                                        Class<?> modelClass,
                                                        Function<ServiceRequest, ServiceResponse> exec) {
        ServiceResponse serviceResponse = validServiceRequest(httpServletRequest, modelClass, exec);

        return ResponseEntity.status(serviceResponse.getHttpStatus()).body(serviceResponse.getModel());
    }

    private ServiceResponse validServiceRequest(HttpServletRequest httpServletRequest,
                                                Class<?> modelClass,
                                                Function<ServiceRequest, ServiceResponse> exec) {
        // ServiceRequest serviceRequest;
        try {
            //serviceRequest = new ServiceRequest(httpServletRequest, modelClass);
            return exec.apply(new ServiceRequest(httpServletRequest, modelClass));
        } catch(IOException e) {
            ServiceResponse serviceResponse = new ServiceResponse();
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            serviceResponse.setModel(new ErrorResponse("Could not parse request body"));
            return serviceResponse;
        }
    }
}
