package com.armando.timeattendance.api.web.employees.imports;

import com.armando.timeattendance.api.domain.exceptions.EmployeeFileParseException;
import com.armando.timeattendance.api.domain.model.ImportEmployeeRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 28/11/22.
 */
@Slf4j
@Component
public class EmployeeCsvParser {
    public List<ImportEmployeeRequest> parse(InputStream inputStream) {
        Reader reader = new InputStreamReader(inputStream);
        List<CSVRecord> records;

        try {
            records = CSVFormat.Builder
                    .create()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setIgnoreHeaderCase(true)
                    .build()
                    .parse(reader)
                    .getRecords();

        } catch (IOException e) {
            throw new EmployeeFileParseException();
        }

        return records.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private ImportEmployeeRequest convert(CSVRecord record) {
        String NAME_HEADER = "nombre";
        String DOCUMENT_HEADER = "cedula";
        String GROUP_HEADER = "grupo";
        String LOCATION_HEADER = "ubicacion";
        String WORK_SHIFT_HEADER = "turno";

        return ImportEmployeeRequest.builder()
                .name(record.get(NAME_HEADER))
                .documentNumber(record.get(DOCUMENT_HEADER))
                .groupName(record.get(GROUP_HEADER))
                .locationName(record.get(LOCATION_HEADER))
                .workShiftName(record.get(WORK_SHIFT_HEADER))
                .line(record.getRecordNumber())
                .build();
    }
}
