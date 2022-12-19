package com.habilisoft.doce.api.utils.export.domain;

import com.habilisoft.doce.api.utils.export.dto.FieldMetaInfo;
import com.habilisoft.doce.api.utils.export.dto.FieldValue;
import com.opencsv.CSVWriter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class CsvExporter implements Exporter {
    private ByteArrayOutputStream outputStream;
    private CSVWriter csvWriter;
    private Exportable exportable;

    @Override
    public Resource export(Exportable exportable) throws IOException {
        init(exportable);
        this.createHeaders(exportable.getFieldsMetaInfo());
        this.createRows(exportable.getFieldsValues());
        return this.generateResource();
    }

    @Override
    public void init(Exportable exportable) {
        this.exportable = exportable;
        this.outputStream = new ByteArrayOutputStream();
        this.csvWriter = new CSVWriter(new OutputStreamWriter(this.outputStream));
    }

    @Override
    public void createCaption(String caption) {
    }

    @Override
    public void createUserFilters(List<UserFilter> userFilters) {
        userFilters.forEach(this::createFilter);
    }

    @Override
    public void createFilter(UserFilter userFilter) {
    }

    @Override
    public void createHeaders(List<FieldMetaInfo> columns) {
        this.writeLine(columns.stream()
                .map(FieldMetaInfo::getDescription)
                .toArray(String[]::new));
    }

    @Override
    public void createHeader(FieldMetaInfo metaInfo) {
    }

    @Override
    public void createRows(List<List<FieldValue>> records) {
        records.forEach(this::createRow);
    }

    private void writeLine(String[] line) {
        this.csvWriter.writeNext(line);
    }

    @Override
    public void createRow(List<FieldValue> record) {
        this.writeLine(record.stream()
                .map(FieldValue::getValue)
                .toArray(String[]::new));
    }

    @Override
    public void createCell(FieldValue value) {

    }

    private Resource generateResource() throws IOException {
        this.csvWriter.flush();
        this.csvWriter.close();
        return new ByteArrayResource(outputStream.toByteArray());
    }

}
