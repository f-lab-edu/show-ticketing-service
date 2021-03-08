package com.show.showticketingservice.model.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.show.showticketingservice.tool.handler.EnumTypeHandler;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.MappedTypes;

@RequiredArgsConstructor
public enum UserType implements EnumType {

    GENERAL(1),
    MANAGER(2);

    private final int value;

    @JsonCreator
    public static UserType valueOf(int value) {
        switch (value) {
            case 1:
                return GENERAL;
            case 2:
                return MANAGER;
            default:
                throw new AssertionError("Unknown value: " + value);
        }
    }

    @Override
    @JsonValue
    public int getValue() {
        return this.value;
    }

    @MappedTypes(UserType.class)
    public static class TypeHandler extends EnumTypeHandler<UserType> {
        public TypeHandler() {
            super(UserType.class);
        }
    }
}
