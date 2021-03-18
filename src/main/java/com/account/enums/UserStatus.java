package com.account.enums;

public enum UserStatus {

    ACTIVE("Active"),PEND("Pend"),DELETED("Deleted");

    private final String value;

    private UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
