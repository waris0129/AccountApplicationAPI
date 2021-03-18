package com.account.enums;

public enum UserRole {

    ADMIN("Admin"),MANAGER("Manager"),EMPLOYEE("Employee"),ROOT("Root");

    private final String value;

    private UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
