package com.armando.timeattendance.api.web.employees.imports;

import com.armando.timeattendance.api.config.exceptions.ApplicationMessages;
import com.armando.timeattendance.api.domain.exceptions.EmployeeFileParseException;
import com.armando.timeattendance.api.domain.model.Employee;
import com.armando.timeattendance.api.domain.model.ImportEmployeeRequest;
import com.armando.timeattendance.api.domain.services.EmployeeImporter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created on 28/11/22.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/employees/import")
public class ImportEmployeesResource {
    private final EmployeeImporter importer;
    private final EmployeeCsvParser parser;
    private final ApplicationMessages messages;

    @PostMapping
    ResponseEntity<?> importEmployees(@RequestPart(value = "file") MultipartFile[] files) {
        InputStream inputStream;
        try {
            inputStream = files[0].getInputStream();
        } catch (IOException e) {
            throw new EmployeeFileParseException();
        }

        List<ImportEmployeeRequest> parsed = parser.parse(inputStream);
        List<Employee> employees = importer.importEmployees(parsed);
        String message = messages.getMessage("message.employees-imported", employees.size());

        return ResponseEntity.ok(
                Map.of(
                        "employees", employees,
                        "message", message
                )
        );
    }
}
