package com.account.enums;

public enum ProductCategory {

    COMPUTER("Computer"),PHONE("Phone"),CAMERA("Camera"),FURNITURE("Furniture"),CLOTHING("Clothing"),SHOES("Shoes");

    private final String value;

    private ProductCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
