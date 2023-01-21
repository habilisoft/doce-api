package com.habilisoft.doce.api.reporting.export;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Exporter {

    void init(Exportable exportable) throws IOException;

    void createCaption(String caption);

    void createUserFilters(List<UserFilter> userFilters);

    void createFilter(UserFilter userFilter);

    void createHeaders();

    void createHeader(String header);

    void createRows();

    void createRow(Map<String, Object> record);

    void createCell(String value);

    Resource export(Exportable exportable) throws IOException, DocumentException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;

}
