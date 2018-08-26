package org.bob.moneyplanner.java.spring.constant;

public enum AuthConstant {
    LOGIN_FAILURE("Login failed, please try again later"),
    ACCOUNT_DOES_NOT_EXIST("There isn't an account registered with that email"),
    INCORRECT_PASSWORD("The password you have provided is incorrect");

    private final String value;
    AuthConstant(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
