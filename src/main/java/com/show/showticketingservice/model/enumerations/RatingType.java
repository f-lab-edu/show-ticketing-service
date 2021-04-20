package com.show.showticketingservice.model.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import com.show.showticketingservice.tool.handler.EnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;

@RequiredArgsConstructor
public enum RatingType implements EnumType {

    VIP(1),
    S(2),
    A(3);

    private final int value;

    @JsonCreator
    public static RatingType valueOf(int value) {
        switch (value) {
            case 1:
                return VIP;
            case 2:
                return S;
            case 3:
                return A;
            default:
                throw new AssertionError("Unknown value: " + value);
        }
    }

    @Override
    @JsonValue
    public int getValue() {
        return this.value;
    }

    @MappedTypes(RatingType.class)
    public static class TypeHandler extends EnumTypeHandler<RatingType> {
        public TypeHandler() {
            super(RatingType.class);
        }
    }
}
