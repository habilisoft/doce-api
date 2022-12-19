package com.habilisoft.doce.api.utils.export.converters;

public abstract class ExportableFieldConverter<IN> {
    public ExportableFieldConverter() { }
    public abstract String getValue(IN inputValue);
    public abstract static class None extends ExportableFieldConverter<Object> { }
}
