package com.habilisoft.doce.api.utils.export.domain;

import com.habilisoft.doce.api.utils.export.dto.FieldMetaInfo;
import com.habilisoft.doce.api.utils.export.dto.FieldValue;
import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface Exporter {

    void init(Exportable exportable) throws IOException;

    void createCaption(String caption);

    void createUserFilters(List<UserFilter> userFilters);

    void createFilter(UserFilter userFilter);

    void createHeaders(List<FieldMetaInfo> columns);

    void createHeader(FieldMetaInfo metaInfo);

    void createRows(List<List<FieldValue>> records);

    void createRow(List<FieldValue> record);

    void createCell(FieldValue value);

    Resource export(Exportable exportable) throws IOException, DocumentException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;

}
