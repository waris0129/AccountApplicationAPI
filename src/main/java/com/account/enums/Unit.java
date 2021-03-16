package com.account.enums;

public enum Unit {

    LB("LB"),GALLON("Gallon"), PACK("Pack"),INCH("Inch"),PIECE("Piece");

    private final String value;

    private Unit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
