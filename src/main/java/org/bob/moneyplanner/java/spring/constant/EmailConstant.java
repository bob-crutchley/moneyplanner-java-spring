package org.bob.moneyplanner.java.spring.constant;

public enum EmailConstant {
    EMAIL_TRANSPORT_FAILURE("Failed to send email"),
    REGISTRATION_SUBJECT("Money Planner Registration"),
    REGISTRATION_TEMPLATE_PATH("src/main/resources/views/email/registration.html");

    private final String value;
    EmailConstant(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
