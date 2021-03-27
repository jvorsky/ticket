package org.hillel.persistence.entity.util;

import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.Objects;

public class YesNoConverter implements AttributeConverter<Boolean, String> {

    private enum YesNoType{
        YES("yes", true), NO("no", false);
        private final String dbValue;
        private final boolean entityValue;

        YesNoType(String dbValue, boolean entityValue) {
            this.dbValue = dbValue;
            this.entityValue = entityValue;
        }

        private static YesNoType getByDbValue(String value){
            if (StringUtils.isEmpty(value)) return NO;
            for (YesNoType type : values()){
                if (type.dbValue.endsWith(value)){
                    return type;
                }
            }
            return NO;
        }

        private static YesNoType getByEntityValue(Boolean value){
            if (Objects.isNull(value)) return NO;
            for (YesNoType type : values()){
                if (Objects.equals(type.entityValue, value)){
                    return type;
                }
            }
            return NO;
        }
    }

    @Override
    public String convertToDatabaseColumn(Boolean aBoolean) {
        return YesNoType.getByEntityValue(aBoolean).dbValue;
    }

    @Override
    public Boolean convertToEntityAttribute(String s) {
        return YesNoType.getByDbValue(s).entityValue;
    }
}
