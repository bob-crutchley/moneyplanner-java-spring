package org.bob.moneyplanner.java.spring.constant;

public enum ControllerConstant {
    REQUEST_FAILURE("Failed to process request");

    private final String value;
    ControllerConstant(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
