package org.bob.moneyplanner.java.spring.constant;

public enum AuthConstant {
    LOGIN_FAILURE("Login failed, please try again later"),
    ACCOUNT_DOES_NOT_EXIST("There isn't an account registered with that email"),
    ACCOUNT_NOT_ACTIVATED("Your account hasn't been activated"),
    INCORRECT_PASSWORD("The password you have provided is incorrect"),
    AUTH_TOKEN_HAS_EXPIRED("Session has expired, please login again"),
    INVALID_AUTH_TOKEN("Session token is invalid"),
    NO_AUTH_TOKEN_FOR_ACCOUNT("There is no session for this account, create one by logging in"),
    AUTH_TOKEN_VALIDATION_FAILURE("Could not authenticate, please try again later"),
    ALPHA_NUMERIC("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");

    private final String value;
    AuthConstant(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
