package com.github.jeng832.converter;

import com.github.jeng832.model.Excel;
import com.github.jeng832.model.ExcelSheet;
import org.apache.poi.ss.util.CellAddress;

import java.io.IOException;

public class ExcelConverterBuilder<T> {

    private String excelFilePath = null;
    private String sheetName = null;
    private boolean hasHeader = false;
    private HeaderDirection headerDirection = HeaderDirection.HORIZONTAL;
    private CellAddress headerStartCell = CellAddress.A1;
    private CellAddress headerEndCell = null;

    ExcelConverterBuilder() {}

    public static <T> ExcelConverterBuilder<T> builder() {
        return new ExcelConverterBuilder<T>();
    }

    public ExcelConverterBuilder<T> excelFilePath(String excelFilePath) {
        this.excelFilePath = excelFilePath;
        return this;
    }

    public ExcelConverterBuilder<T> sheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public ExcelConverterBuilder<T> hasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
        return this;
    }

    public ExcelConverterBuilder<T> headerDirection(HeaderDirection headerDirection) {
        this.headerDirection = headerDirection;
        return this;
    }

    public ExcelConverterBuilder<T> headerStartCell(String headerStartCell) {
        this.headerStartCell = new CellAddress(headerStartCell);
        return this;
    }

    public ExcelConverterBuilder<T> headerEndCell(String headerEndCell) {
        this.headerEndCell = new CellAddress(headerEndCell);
        return this;
    }

    public ExcelConverter<T> build() throws IOException {
        ExcelSheet sheet = Excel.of(this.excelFilePath).getSheet(this.sheetName);
        return new ExcelConverter<>(sheet, this.hasHeader, this.headerDirection, this.headerStartCell, this.headerEndCell);
    }

}
