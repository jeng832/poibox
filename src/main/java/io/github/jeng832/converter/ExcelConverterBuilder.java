package io.github.jeng832.converter;

import io.github.jeng832.exception.ExcelConvertException;
import io.github.jeng832.serializer.ExcelSerializer;

public interface ExcelConverterBuilder {

    ExcelConverterBuilder excelFilePath(String excelFilePath);

    ExcelConverterBuilder sheetName(String sheetName);

    ExcelConverterBuilder hasHeader(boolean hasHeader);

    ExcelConverterBuilder headerDirection(HeaderDirection headerDirection);

    ExcelConverterBuilder headerStartCell(String cellAddress);

    ExcelConverterBuilder headerEndCell(String cellAddress);

    ExcelConverterBuilder contentsStartCell(String cellAddress);

    ExcelConverterBuilder linesOfUnit(int linesOfUnit);

    @Deprecated
    ExcelConverter build() throws ExcelConvertException;

    ExcelSerializer buildSerializer() throws ExcelConvertException;

    String getExcelFilePath();

    String getSheetName();

    boolean hasHeader();

    HeaderDirection getHeaderDirection();

    String getHeaderStartCell();

    String getHeaderEndCell();

    String getContentsStartCell();

    Integer getLinesOfUnit();
}
