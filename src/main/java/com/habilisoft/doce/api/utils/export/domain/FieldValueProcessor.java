package com.habilisoft.doce.api.utils.export.domain;

import com.habilisoft.doce.api.utils.export.annotations.ExportableField;
import com.habilisoft.doce.api.utils.export.converters.ExportableFieldConverter;
import com.habilisoft.doce.api.utils.export.dto.FieldMetaInfo;
import com.habilisoft.doce.api.utils.export.dto.FieldValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class FieldValueProcessor {

    public static FieldValue getValue(Object entity, Field field) {
        final String name = field.getName();
        try {
            field.setAccessible(true);
            if (field.getAnnotation(ExportableField.class).converter().equals(ExportableFieldConverter.None.class)) {
                return new FieldValue(name, (field.get(entity) != null ? field.get(entity).toString() : ""));
            }
            ExportableFieldConverter converter = field.getAnnotation(ExportableField.class).converter().getDeclaredConstructor().newInstance();
            return new FieldValue(name, converter.getValue(field.get(entity)));
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
        }
        return new FieldValue(name, "");
    }

    public static FieldValue getValue(Object entity, Method method) {
        final String name = method.getName();
        String value = "";
        try {
            value = method.invoke(entity).toString();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return new FieldValue(name, value);
    }

    public static FieldValue getValue(Map map, FieldMetaInfo field) {
        final String name = field.getName();
        try {
            if (!field.getConverter().equals(ExportableFieldConverter.None.class)) {
                return new FieldValue(name, (map.get(name) != null ? map.get(name).toString() : ""));
            }
            ExportableFieldConverter converter = field.getConverter().getDeclaredConstructor().newInstance();
            return new FieldValue(name, converter.getValue(map));
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
        }
        return new FieldValue(name, "");
    }

}
