package com.account.enums;

public enum RegistrationType {

    CLIENT("Client"),SUPPLIER("Supplier"),BOTH("Both");

    private final String value;

    private RegistrationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
