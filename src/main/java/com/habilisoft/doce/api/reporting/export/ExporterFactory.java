package com.habilisoft.doce.api.reporting.export;


import com.habilisoft.doce.api.reporting.export.enums.ExportType;

public class ExporterFactory {
    public static Exporter getInstance(ExportType exportType){
        return switch (exportType){
            case csv -> new CsvExporter();
            case pdf -> new PdfExporter();
            case xlsx -> new ExcelExporter();
            default -> null;
        };
    }
}
