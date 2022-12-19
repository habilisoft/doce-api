package com.habilisoft.doce.api.utils.export.enums;

public enum DataFormatType {

    FORMAT_GENERAL("General"),
    FORMAT_TEXT("@"),
    FORMAT_NUMBER("0"),
    FORMAT_NUMBER_00("0.00"),
    FORMAT_NUMBER_COMMA_SEPARATED1("#,##0.00"),
    FORMAT_NUMBER_COMMA_SEPARATED2("#,##0.00_-"),
    FORMAT_PERCENTAGE("0%"),
    FORMAT_PERCENTAGE_00("0.00%"),
    FORMAT_DATE_YYYYMMDD2("yyyy-mm-dd"),
    FORMAT_DATE_YYYYMMDD("yy-mm-dd"),
    FORMAT_DATE_DDMMYYYY("dd/mm/yy"),
    FORMAT_DATE_DMYSLASH("d/m/yy"),
    FORMAT_DATE_DMYMINUS("d-m-yy"),
    FORMAT_DATE_DMMINUS("d-m"),
    FORMAT_DATE_MYMINUS("m-yy"),
    FORMAT_DATE_XLSX14("mm-dd-yy"),
    FORMAT_DATE_XLSX15("d-mmm-yy"),
    FORMAT_DATE_XLSX16("d-mmm"),
    FORMAT_DATE_XLSX17("mmm-yy"),
    FORMAT_DATE_XLSX22("m/d/yy h:mm"),
    FORMAT_DATE_DATETIME("d/m/yy h:mm"),
    FORMAT_DATE_TIME1("h:mm AM/PM"),
    FORMAT_DATE_TIME2("h:mm:ss AM/PM"),
    FORMAT_DATE_TIME3("h:mm"),
    FORMAT_DATE_TIME4("h:mm:ss"),
    FORMAT_DATE_TIME5("mm:ss"),
    FORMAT_DATE_TIME6("h:mm:ss"),
    FORMAT_DATE_TIME7("i:s.S"),
    FORMAT_DATE_TIME8("h:mm:ss),@"),
    FORMAT_DATE_YYYYMMDDSLASH("yy/mm/dd),@"),
    FORMAT_CURRENCY_USD_SIMPLE("$#,##0.00_);($#,##0.00)"),
    FORMAT_CURRENCY_USD("$#,##0_-"),
    FORMAT_CURRENCY_EUR_SIMPLE("#,##0.00_-\"€\""),
    FORMAT_CURRENCY_EUR("#,##0_-\"€\"");

    private String dataType;

    DataFormatType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
