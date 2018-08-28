package org.bob.moneyplanner.java.spring.service;

import com.google.gson.Gson;
import org.bob.moneyplanner.java.spring.model.Model;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

public class ServiceRequest {
    private final Model model;
    private final String remoteAddress;
    private final String authToken;

    public ServiceRequest(HttpServletRequest httpServletRequest, Class<?> modelClass) throws IOException {
        this.remoteAddress = httpServletRequest.getRemoteAddr();
        this.model = (Model) new Gson().fromJson(httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator())), modelClass);
        this.authToken = httpServletRequest.getHeader("Auth-Token");
    }


    public Model getModel() {
        return model;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getAuthToken() {
        return authToken;
    }
}
