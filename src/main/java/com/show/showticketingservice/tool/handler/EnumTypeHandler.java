package com.show.showticketingservice.tool.handler;

import com.show.showticketingservice.model.enumerations.EnumType;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class EnumTypeHandler<E extends Enum<E>> implements TypeHandler<EnumType> {

    private final Class<E> type;

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, EnumType enumType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, enumType.getValue());
    }

    @Override
    public EnumType getResult(ResultSet resultSet, String columnName) throws SQLException {
        int value = resultSet.getInt(columnName);
        return getEnumType(value);
    }

    @Override
    public EnumType getResult(ResultSet resultSet, int columnIndex) throws SQLException {
        int value = resultSet.getInt(columnIndex);
        return getEnumType(value);
    }

    @Override
    public EnumType getResult(CallableStatement callableStatement, int parameterIndex) throws SQLException {
        int value = callableStatement.getInt(parameterIndex);
        return getEnumType(value);
    }

    private EnumType getEnumType(int value) {
        try {
            EnumType[] enumConstants = (EnumType[]) type.getEnumConstants();
            for (EnumType enumType : enumConstants) {
                if (enumType.getValue() == value) {
                    return enumType;
                }
            }
            return null;
        } catch (TypeException e) {
            throw new TypeException("Invalid type: " + type, e);
        }
    }
}
