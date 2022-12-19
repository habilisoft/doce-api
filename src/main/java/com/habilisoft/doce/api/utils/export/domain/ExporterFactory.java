package com.habilisoft.doce.api.utils.export.domain;

import com.habilisoft.doce.api.utils.export.enums.ExportType;

public class ExporterFactory {

    public static Exporter getInstance(ExportType exportType){
        if(exportType.equals(exportType.pdf)){
            return new PdfExporter();
        }else if(exportType.equals(ExportType.csv)){
            return  new CsvExporter();
        }else if(exportType.equals(ExportType.xlsx)){
            return new ExcelExporter();
        }
        throw new IllegalArgumentException("Invalid exportType value: " + exportType);
    }

}
