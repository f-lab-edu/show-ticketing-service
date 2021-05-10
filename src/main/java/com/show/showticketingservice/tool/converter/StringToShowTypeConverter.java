package com.show.showticketingservice.tool.converter;

import com.show.showticketingservice.model.enumerations.ShowType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToShowTypeConverter implements Converter<String, ShowType> {

    @Override
    public ShowType convert(String showType) {

        return ShowType.valueOf(Integer.valueOf(showType));
    }
}
