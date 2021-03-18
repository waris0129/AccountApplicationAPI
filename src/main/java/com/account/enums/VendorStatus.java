package com.account.enums;

public enum VendorStatus {
    ACTIVE("Active"),PEND("Pend"),DELETED("Deleted");

    private final String value;

    private VendorStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
