package com.habilisoft.doce.api.utils.export.dto;

import com.habilisoft.doce.api.utils.export.domain.UserFilter;
import com.habilisoft.doce.api.utils.export.enums.ExportType;

import java.util.List;

public class ExportRequest {

    private String caption;
    private ExportType exportType;
    private List<String> userFieldsToExport;
    private List<UserFilter> userFilters;

    public ExportRequest() { }

    public ExportRequest(String caption, ExportType exportType, List<String> userFieldsToExport, List<UserFilter> userFilters) {
        this.caption = caption;
        this.exportType = exportType;
        this.userFieldsToExport = userFieldsToExport;
        this.userFilters = userFilters;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public ExportType getExportType() {
        return exportType;
    }

    public void setExportType(ExportType exportType) {
        this.exportType = exportType;
    }

    public List<String> getUserFieldsToExport() {
        return userFieldsToExport;
    }

    public void setUserFieldsToExport(List<String> userFieldsToExport) {
        this.userFieldsToExport = userFieldsToExport;
    }

    public List<UserFilter> getUserFilters() {
        return userFilters;
    }

    public void setUserFilters(List<UserFilter> userFilters) {
        this.userFilters = userFilters;
    }
}
