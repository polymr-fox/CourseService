package com.mrfox.senyast4745.coursesevice.converters;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Arrays;

import static com.mrfox.senyast4745.coursesevice.converters.SimpleStringConverter.SEPARATOR;

public class ListLongConverter implements AttributeConverter<ArrayList<Long>, String> {



    @Override
    public String convertToDatabaseColumn(ArrayList<Long> attribute) {

        if (attribute == null || attribute.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (Long s : attribute) {
            sb.append(s);
            sb.append(SEPARATOR);
        }

        return sb.toString();
    }

    @Override
    public ArrayList<Long> convertToEntityAttribute(String dbData) {
        return SimpleLongConverter.splitBySeparator(dbData, SEPARATOR);
    }




}
