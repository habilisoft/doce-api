package com.habilisoft.doce.api.reporting.export;

import com.habilisoft.doce.api.reporting.domain.model.ReportUIColumn;
import com.opencsv.CSVWriter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CsvExporter implements Exporter {
    private ByteArrayOutputStream outputStream;
    private CSVWriter csvWriter;
    private Exportable exportable;

    @Override
    public Resource export(Exportable exportable) throws IOException {
        init(exportable);
        this.createHeaders();
        this.createRows();
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
    public void createHeaders() {
        List<ReportUIColumn> columns = this.exportable.getReport().getUiColumns()
                .stream()
                .sorted(Comparator.comparing(ReportUIColumn::getDisplayOrder))
                .collect(Collectors.toList());
        this.writeLine(columns.stream()
                .map(ReportUIColumn::getHeader)
                .toArray(String[]::new));
    }

    @Override
    public void createHeader(String header) {
    }

    @Override
    public void createRows() {
        List<Map<String, Object>> records = this.exportable.getRecords();
        records.forEach(this::createRow);
    }

    private void writeLine(String[] line) {
        this.csvWriter.writeNext(line);
    }

    @Override
    public void createRow(Map<String,Object> record) {
        String[] row = this.exportable.getReport().getUiColumns()
                .stream()
                .sorted(Comparator.comparing(ReportUIColumn::getDisplayOrder))
                .map(column -> Optional.ofNullable(record.getOrDefault(column.getField(), ""))
                        .map(Object::toString).orElse(""))
                .toArray(String[]::new);

        this.writeLine(row);
    }

    @Override
    public void createCell(String value) {

    }

    private Resource generateResource() throws IOException {
        this.csvWriter.flush();
        this.csvWriter.close();
        return new ByteArrayResource(outputStream.toByteArray());
    }

}
