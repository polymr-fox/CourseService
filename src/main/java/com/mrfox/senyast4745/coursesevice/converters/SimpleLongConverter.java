package com.mrfox.senyast4745.coursesevice.converters;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

import static com.mrfox.senyast4745.coursesevice.converters.SimpleStringConverter.SEPARATOR;

public class SimpleLongConverter implements AttributeConverter<Long[], String> {
    @Override
    public String convertToDatabaseColumn(Long[] attribute) {

        if (attribute == null || attribute.length == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (Long s: attribute) {
            sb.append(s);
            sb.append(SEPARATOR);
        }

        return sb.toString();
    }

    @Override
    public Long[] convertToEntityAttribute(String dbData) {
        ArrayList<Long> longs = splitBySeparator(dbData, SEPARATOR);
        return longs.toArray(new Long[0]);
    }

    static ArrayList<Long> splitBySeparator(String dbData, String separator){
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }

        String[] tmp =  dbData.split(SEPARATOR);
        ArrayList<Long> longs = new ArrayList<>();
        for (String s: tmp) {
            longs.add(Long.parseLong(s));
        }
        return longs;
    }
}
