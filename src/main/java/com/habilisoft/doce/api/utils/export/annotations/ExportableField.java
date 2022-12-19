package com.habilisoft.doce.api.utils.export.annotations;

import com.habilisoft.doce.api.utils.export.converters.ExportableFieldConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportableField {
    String displayName();
    int displayOrder() default 999999999;
    Class<? extends ExportableFieldConverter> converter() default ExportableFieldConverter.None.class;

}
