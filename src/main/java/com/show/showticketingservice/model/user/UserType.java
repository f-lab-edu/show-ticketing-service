package com.show.showticketingservice.model.user;

public enum UserType {

    GENERAL(1),
    MANAGER(2);

    private final int value;

    UserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserType valueOf(int value) {
        switch (value) {
            case 1:
                return GENERAL;
            case 2:
                return MANAGER;
            default:
                throw new AssertionError("UnKnown value: " + value);
        }
    }
}
