package com.habilisoft.doce.api.utils.export.enums;

public enum ExportType {
    pdf("PDF"),
    csv("CSV"),
    xlsx("Excel");
    private String name;

    ExportType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
