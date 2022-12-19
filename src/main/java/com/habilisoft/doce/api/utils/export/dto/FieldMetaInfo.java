package com.habilisoft.doce.api.utils.export.dto;

import com.habilisoft.doce.api.utils.export.converters.ExportableFieldConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldMetaInfo {

    private static final Integer DEFAULT_ORDER = 99999999;
    private static final Class<? extends ExportableFieldConverter> DEFAULT_CONVERTER = ExportableFieldConverter.None.class;
    private String name;
    private String description;
    private Integer order;
    private Class<? extends ExportableFieldConverter> converter;
    private Field field;
    private Method method = null;

    public FieldMetaInfo(String name, String description) {
        this(name, description, DEFAULT_ORDER);
    }

    public FieldMetaInfo(String name, String description, Integer order) {
        this(name, description, order, DEFAULT_CONVERTER);
    }

    public FieldMetaInfo(String name, String description, Integer order, Class<? extends ExportableFieldConverter> converter) {
        this(name, description, order, converter, null);
    }

    public FieldMetaInfo(String name, String description, Integer order, Class<? extends ExportableFieldConverter> converter, Field field) {
        this.name = name;
        this.description = description;
        this.order = order;
        this.converter = converter;
        this.field = field;
    }

    public FieldMetaInfo(String name, String description, Integer order, Method method) {
        this.name = name;
        this.description = description;
        this.order = order;
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Class<? extends ExportableFieldConverter> getConverter() {
        return converter;
    }

    public void setConverter(Class<? extends ExportableFieldConverter> converter) {
        this.converter = converter;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
