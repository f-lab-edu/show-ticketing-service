package com.show.showticketingservice.model.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.show.showticketingservice.tool.handler.EnumTypeHandler;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.MappedTypes;

@RequiredArgsConstructor
public enum ShowType implements EnumType {

    MUSICAL(1),
    THEATRICAL(2),
    CONCERT(3);

    private final int value;

    @JsonCreator
    public static ShowType valueOf(int value) {
        switch (value) {
            case 1:
                return MUSICAL;
            case 2:
                return THEATRICAL;
            case 3:
                return CONCERT;
            default:
                throw new AssertionError("Unknown value: " + value);
        }
    }

    @Override
    @JsonValue
    public int getValue() {
        return this.value;
    }

    @MappedTypes(ShowType.class)
    public static class TypeHandler extends EnumTypeHandler<ShowType> {
        public TypeHandler() {
            super(ShowType.class);
        }
    }
}
