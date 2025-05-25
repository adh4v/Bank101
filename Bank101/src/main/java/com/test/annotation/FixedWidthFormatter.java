package com.test.annotation;

import java.lang.reflect.RecordComponent;

public class FixedWidthFormatter {

    public static String formatRecord(Object record) {
        StringBuilder sb = new StringBuilder();
        format(record, sb);
        return sb.toString();
    }

    private static void format(Object record, StringBuilder sb) {
        if (record == null) return;

        Class<?> clazz = record.getClass();
        if (!clazz.isRecord()) return;

        for (RecordComponent component : clazz.getRecordComponents()) {
            try {
                FieldLength fieldLength = component.getAnnotation(FieldLength.class);
                Object value = component.getAccessor().invoke(record);

                if (fieldLength != null) {
                    int size = fieldLength.length();
                    String strVal = value == null ? "" : value.toString();
                    sb.append(padding(strVal, size));
                } else if (value != null && value.getClass().isRecord()) {
                    format(value, sb); // recursively handle nested records
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String padding(String str, int size) {
        return str.length() >= size ? str.substring(0, size) : String.format("%-" + size + "s", str);
    }
}


