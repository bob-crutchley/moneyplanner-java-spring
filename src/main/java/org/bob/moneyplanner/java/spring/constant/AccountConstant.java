package org.bob.moneyplanner.java.spring.constant;

public enum AccountConstant {
    ACCOUNT_EXISTS_WITH_SAME_EMAIL("An account with that email already exists"),
    FAILED_TO_CREATE_ACCOUNT("Failed to create account, please try again later");

    private final String value;
    AccountConstant(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
