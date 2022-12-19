package com.habilisoft.doce.api.utils.export.domain;

import com.habilisoft.doce.api.utils.export.dto.FieldMetaInfo;
import com.habilisoft.doce.api.utils.export.dto.FieldValue;
import com.habilisoft.doce.api.utils.export.enums.DataFormatType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter implements Exporter {

    private static final String SHEET_NAME = "data";
    private static final Integer START_COLUMN_NUMBER = 1;
    private SXSSFWorkbook workbook;
    private SXSSFSheet sheet;
    private SXSSFRow currentRow;
    private Integer currentRowIndex;
    private Integer currentColumnNumber;
    private CellStyle cellStyleText = null;
    private CellStyle cellStyleDate = null;
    private CellStyle cellStyleFloat = null;
    private CellStyle cellStyleMoney = null;
    private CellStyle cellStyleInteger = null;
    private CellStyle cellStylePercent = null;
    private Exportable exportable;
    private Integer lastColumnIndex;

    @Override
    public Resource export(Exportable exportable) throws IOException {
        this.init(exportable);
        this.createCaption(exportable.getCaption());
        this.createUserFilters(exportable.getUserFilters());
        List<FieldMetaInfo> fieldsMetaInfo = exportable.getFieldsMetaInfo();
        this.createHeaders(fieldsMetaInfo);
        this.createRows(exportable.getFieldsValues());
        this.autoSizeColumns(fieldsMetaInfo.size());
        return this.generateResource();
    }

    private void autoSizeColumns(Integer columnsNumber) {
        sheet.trackAllColumnsForAutoSizing();
        for (int i = 0; i < columnsNumber; i++) {
            sheet.autoSizeColumn(i,true);
        }
    }

    @Override
    public void init(Exportable exportable) {
        this.exportable = exportable;
        this.workbook = new SXSSFWorkbook(100);
        this.sheet = this.workbook.createSheet(SHEET_NAME);
        this.currentRowIndex = 5;
        this.currentColumnNumber = START_COLUMN_NUMBER;
        this.lastColumnIndex = exportable.getFieldsMetaInfo().size() + START_COLUMN_NUMBER - 1;
    }

    private Resource generateResource() throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        workbook.write(bao);
        workbook.dispose();
        bao.flush();
        bao.close();
        return new ByteArrayResource(bao.toByteArray());
    }

    @Override
    public void createCaption(String caption) {
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(START_COLUMN_NUMBER);
        cell.setCellValue(new XSSFRichTextString(caption));
        CellStyle cellStyle = this.workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = this.workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 28);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 2, START_COLUMN_NUMBER, exportable.getFieldsMetaInfo().size() + START_COLUMN_NUMBER - 1));
    }

    @Override
    public void createUserFilters(List<UserFilter> userFilters) {
        if (userFilters == null || userFilters.size() == 0){
            return;
        }
        Row row = sheet.createRow(3);
        Cell cell = row.createCell(START_COLUMN_NUMBER);
        cell.setCellValue(new XSSFRichTextString("Este reporte ha sido generado basado en los siguientes criterios:"));
        sheet.addMergedRegion(new CellRangeAddress(3, 4, START_COLUMN_NUMBER, exportable.getFieldsMetaInfo().size() + START_COLUMN_NUMBER - 1));
        CellStyle cellStyle = this.workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = this.workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        addEmptyRow();
        userFilters.forEach(this::createFilter);
        addEmptyRow();
    }

    @Override
    public void createFilter(UserFilter userFilter) {
        if ((currentColumnNumber + 1) >= lastColumnIndex) {
            addEmptyRow();
        }
        // Key cell
        Font fontKey = this.workbook.createFont();
        fontKey.setFontHeightInPoints((short) 10);
        fontKey.setBold(true);

        CellStyle cellStyleKey = this.workbook.createCellStyle();
        cellStyleKey.setAlignment(HorizontalAlignment.RIGHT);
        cellStyleKey.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleKey.setFont(fontKey);

        SXSSFCell cellKey = this.currentRow.createCell(currentColumnNumber++);
        cellKey.setCellValue(new XSSFRichTextString(userFilter.getKey() + ": "));
        cellKey.setCellStyle(cellStyleKey);

        // Value cell
        Font fontValue = this.workbook.createFont();
        fontValue.setItalic(true);
        fontValue.setUnderline(((byte) 1));
        fontValue.setFontHeightInPoints((short) 9);

        CellStyle cellStyleValue = this.workbook.createCellStyle();
        cellStyleValue.setAlignment(HorizontalAlignment.LEFT);
        cellStyleValue.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleValue.setFont(fontValue);

        SXSSFCell cellValue = this.currentRow.createCell(currentColumnNumber++);
        cellValue.setCellValue(new XSSFRichTextString(userFilter.getValue()));
        cellValue.setCellStyle(cellStyleValue);
    }

    @Override
    public void createHeaders(List<FieldMetaInfo> columns) {
        addEmptyRow();
        this.exportable.getFieldsMetaInfo().forEach(this::createHeader);
        currentRowIndex += 1;
    }

    @Override
    public void createHeader(FieldMetaInfo metaInfo) {
        Cell cell = currentRow.createCell(currentColumnNumber);
        cell.setCellValue(new XSSFRichTextString(metaInfo.getDescription()));
        sheet.addMergedRegion(new CellRangeAddress(currentRowIndex-1, currentRowIndex, currentColumnNumber, currentColumnNumber));
        CellStyle cellStyle = this.workbook.createCellStyle();
        Font font = this.workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cell.setCellStyle(cellStyle);
        currentColumnNumber++;
    }

    @Override
    public void createRows(List<List<FieldValue>> records) {
        records.forEach(this::createRow);
    }

    @Override
    public void createRow(List<FieldValue> record) {
        addEmptyRow();
        record.forEach(this::createCell);
    }

    @Override
    public void createCell(FieldValue fieldValue) {
        SXSSFCell cell = currentRow.createCell(currentColumnNumber);
        cell.setCellValue(new XSSFRichTextString(fieldValue.getValue()));
        CellStyle cellStyle = this.getCellStyle("string");
        cell.setCellStyle(cellStyle);
        currentColumnNumber++;
    }

    private void addEmptyRow() {
        this.currentColumnNumber = START_COLUMN_NUMBER;
        this.currentRow = this.sheet.createRow(this.currentRowIndex++);
    }


    private CellStyle getCellStyle(String cellType) {
        DataFormat dataFormat = this.workbook.createDataFormat();
        switch (cellType) {
            case "date":
                if (this.cellStyleDate == null) {
                    this.cellStyleDate = this.workbook.createCellStyle();
                    this.cellStyleDate.setDataFormat(dataFormat.getFormat(DataFormatType.FORMAT_DATE_XLSX14.getDataType()));
                }
                return this.cellStyleDate;
            case "float":
                if (this.cellStyleFloat == null) {
                    this.cellStyleFloat = this.workbook.createCellStyle();
                    this.cellStyleFloat.setDataFormat(dataFormat.getFormat(DataFormatType.FORMAT_NUMBER_COMMA_SEPARATED1.getDataType()));
                }
                return this.cellStyleFloat;
            case "money":
                if (this.cellStyleMoney == null) {
                    this.cellStyleMoney = this.workbook.createCellStyle();
                    this.cellStyleMoney.setDataFormat(dataFormat.getFormat(DataFormatType.FORMAT_CURRENCY_USD_SIMPLE.getDataType()));
                }
                return this.cellStyleMoney;
            case "integer":
                if (this.cellStyleInteger == null) {
                    this.cellStyleInteger = this.workbook.createCellStyle();
                    this.cellStyleInteger.setDataFormat(dataFormat.getFormat(DataFormatType.FORMAT_NUMBER.getDataType()));
                }
                return this.cellStyleInteger;
            case "percent":
                if (this.cellStylePercent == null) {
                    this.cellStylePercent = this.workbook.createCellStyle();
                    this.cellStylePercent.setDataFormat(dataFormat.getFormat(DataFormatType.FORMAT_PERCENTAGE_00.getDataType()));
                }
                return this.cellStylePercent;
            default:
                if (this.cellStyleText == null) {
                    this.cellStyleText = this.workbook.createCellStyle();
                    this.cellStyleText.setDataFormat(dataFormat.getFormat(DataFormatType.FORMAT_TEXT.getDataType()));
                }
                return this.cellStyleText;
        }
    }

}

