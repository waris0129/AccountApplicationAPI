package com.account.enums;

public enum States {

            ALABAMA("Alabama"), ALASKA("Alaska"), AMERICAN_SAMOA("American Samoa"), ARIZONA("Arizona"), ARKANSAS(
            "Arkansas"), CALIFORNIA("California"), COLORADO("Colorado"), CONNECTICUT("Connecticut"), DELAWARE(
            "Delaware"), DISTRICT_OF_COLUMBIA("District of Columbia"), FEDERATED_STATES_OF_MICRONESIA(
            "Federated States of Micronesia"), FLORIDA("Florida"), GEORGIA("Georgia"), GUAM("Guam"), HAWAII(
            "Hawaii"), IDAHO("Idaho"), ILLINOIS("Illinois"), INDIANA("Indiana"), IOWA("Iowa"), KANSAS(
            "Kansas"), KENTUCKY("Kentucky"), LOUISIANA("Louisiana"), MAINE("Maine"), MARYLAND("Maryland"), MARSHALL_ISLANDS(
            "Marshall Islands"), MASSACHUSETTS("Massachusetts"), MICHIGAN("Michigan"), MINNESOTA("Minnesota"), MISSISSIPPI(
            "Mississippi"), MISSOURI("Missouri"), MONTANA("Montana"), NEBRASKA("Nebraska"), NEVADA("Nevada"), NEW_HAMPSHIRE("New Hampshire"), NEW_JERSEY("New Jersey"), NEW_MEXICO("New Mexico"), NEW_YORK(
            "New York"), NORTH_CAROLINA("North Carolina"), NORTH_DAKOTA("North Dakota"), NORTHERN_MARIANA_ISLANDS(
            "Northern Mariana Islands"), OHIO("Ohio"), OKLAHOMA("Oklahoma"), OREGON("Oregon"), PALAU("Palau"), PENNSYLVANIA("Pennsylvania"), PUERTO_RICO("Puerto Rico"), RHODE_ISLAND("Rhode Island"), SOUTH_CAROLINA(
            "South Carolina"), SOUTH_DAKOTA("South Dakota"), TENNESSEE("Tennessee"), TEXAS("Texas"), UTAH(
            "Utah"), VERMONT("Vermont"), VIRGIN_ISLANDS("Virgin Islands"), VIRGINIA("Virginia"), WASHINGTON(
            "Washington"), WEST_VIRGINIA("West Virginia"), WISCONSIN("Wisconsin"), WYOMING("Wyoming"), UNKNOWN(
            "Unknown");

    private final String value;

    private States(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
