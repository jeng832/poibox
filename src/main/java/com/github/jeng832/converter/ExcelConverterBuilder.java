package com.github.jeng832.converter;

import com.github.jeng832.exception.ExcelConvertException;

public interface ExcelConverterBuilder {

    ExcelConverterBuilder excelFilePath(String excelFilePath);

    ExcelConverterBuilder sheetName(String sheetName);

    ExcelConverterBuilder hasHeader(boolean hasHeader);

    ExcelConverterBuilder headerDirection(HeaderDirection headerDirection);

    ExcelConverterBuilder headerStartCell(String cellAddress);

    ExcelConverterBuilder headerEndCell(String cellAddress);

    ExcelConverterBuilder contentsStartCell(String cellAddress);

    ExcelConverterBuilder linesOfUnit(int linesOfUnit);

    ExcelConverter build() throws ExcelConvertException;

    String getExcelFilePath();

    String getSheetName();

    boolean hasHeader();

    HeaderDirection getHeaderDirection();

    String getHeaderStartCell();

    String getHeaderEndCell();

    String getContentsStartCell();

    Integer getLinesOfUnit();
}
