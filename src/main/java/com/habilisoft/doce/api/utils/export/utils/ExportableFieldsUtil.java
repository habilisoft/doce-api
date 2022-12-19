package com.habilisoft.doce.api.utils.export.utils;

import com.habilisoft.doce.api.utils.export.annotations.ExportableField;
import com.habilisoft.doce.api.utils.export.dto.FieldMetaInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExportableFieldsUtil {

    private static boolean isExportable(Field field) {
        return field.isAnnotationPresent(ExportableField.class);
    }

    private static boolean isExportable(Method method) {
        return method.isAnnotationPresent(ExportableField.class);
    }

    public static Stream<Method> getExportableMethods(Class clazz) {
        return Arrays
                .stream(clazz.getDeclaredMethods())
                .filter(ExportableFieldsUtil::isExportable);
    }

    public static Stream<Field> getExportableFields(Class clazz) {
        return Arrays
                .stream(clazz.getDeclaredFields())
                .filter(ExportableFieldsUtil::isExportable);
    }

    public static FieldMetaInfo mapToExportableField(Method method) {
        ExportableField ef = method.getAnnotation(ExportableField.class);
        return new FieldMetaInfo(method.getName(), ef.displayName(), ef.displayOrder(), method);
    }

    public static FieldMetaInfo mapToExportableField(Field field) {
        ExportableField ef = field.getAnnotation(ExportableField.class);
        return new FieldMetaInfo(field.getName(), ef.displayName(), ef.displayOrder(), ef.converter(), field);
    }

    public static List<FieldMetaInfo> getFieldsMetaInfo(Class clazz) {

        Stream<FieldMetaInfo> fieldsMetaInfo = getExportableFields(clazz)
                .map(ExportableFieldsUtil::mapToExportableField);

        Stream<FieldMetaInfo> methodsMetaInfo = getExportableMethods(clazz)
                .map(ExportableFieldsUtil::mapToExportableField);

        List<FieldMetaInfo> metaInfo = Stream
                .concat(fieldsMetaInfo, methodsMetaInfo)
                .sorted(Comparator.comparing(FieldMetaInfo::getOrder))
                .collect(Collectors.toList());

        return metaInfo;
    }

    public static List<FieldMetaInfo> getValidatedFieldsToExport(Class clazz, List<String> userFieldsToExport) {
        return getFieldsMetaInfo(clazz)
                .stream()
                .filter(f -> userFieldsToExport == null || (userFieldsToExport != null && userFieldsToExport.contains(f.getName())))
                .collect(Collectors.toList());
    }

    public static List<FieldMetaInfo> getValidatedFieldsToExport(List<FieldMetaInfo> fieldsMetaInfo, List<String> userFieldsToExport) {
        return fieldsMetaInfo
                .stream()
                .filter(f -> userFieldsToExport == null || (userFieldsToExport != null && userFieldsToExport.contains(f.getName())))
                .collect(Collectors.toList());
    }

}

