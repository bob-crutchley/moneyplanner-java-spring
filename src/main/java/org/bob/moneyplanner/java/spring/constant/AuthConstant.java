package org.bob.moneyplanner.java.spring.constant;

public enum AuthConstant {
    LOGIN_FAILURE("Login failed, please try again later"),
    ACCOUNT_DOES_NOT_EXIST("There isn't an account registered with that email"),
    INCORRECT_PASSWORD("The password you have provided is incorrect"),
    SESSION_TOKEN_HAS_EXPIRED("Session has expired, please login again"),
    INVALID_SESSION_TOKEN("Session token is invalid"),
    NO_SESSION_FOR_ACCOUNT("There is no session for this account, create one by logging in"),
    TOKEN_AUTHENTICATION_FAILURE("Could not authenticate, please try again later");

    private final String value;
    AuthConstant(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
