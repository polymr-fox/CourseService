package com.mrfox.senyast4745.coursesevice.converters;

import javax.persistence.AttributeConverter;

public class SimpleStringConverter implements AttributeConverter<String[], String> {

    static final String SEPARATOR = " ";
    @Override
    public String convertToDatabaseColumn(String[] attribute) {
        if (attribute == null || attribute.length==0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (String s: attribute) {
            sb.append(s);
            sb.append(SEPARATOR);
        }

        return sb.toString();
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return dbData.split(SEPARATOR);
    }
}
