package io.github.jeng832.converter;

import org.apache.poi.ss.util.CellAddress;

import io.github.jeng832.deserializer.ExcelDeserializer;
import io.github.jeng832.deserializer.ExcelDeserializerImpl;
import io.github.jeng832.exception.ExcelConvertException;
import io.github.jeng832.serializer.ExcelSerializer;
import io.github.jeng832.serializer.ExcelSerializerImpl;

class ExcelConverterBuilderImpl implements ExcelConverterBuilder {

    private String excelFilePath = null;
    private String sheetName = null;
    private boolean hasHeader = true;
    private HeaderDirection headerDirection = HeaderDirection.HORIZONTAL;
    private CellAddress headerStartCell = CellAddress.A1;
    private CellAddress headerEndCell = null;
    private CellAddress contentsStartCell = null;
    private Integer linesOfUnit = null;

    ExcelConverterBuilderImpl() {

    }

    @Override
    public ExcelConverterBuilder excelFilePath(String excelFilePath) {
        this.excelFilePath = excelFilePath;
        return this;
    }

    @Override
    public ExcelConverterBuilder sheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    @Override
    public ExcelConverterBuilder hasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
        return this;
    }

    @Override
    public ExcelConverterBuilder headerDirection(HeaderDirection headerDirection) {
        this.headerDirection = headerDirection;
        return this;
    }

    @Override
    public ExcelConverterBuilder headerStartCell(String cellAddress) {
        this.headerStartCell = new CellAddress(cellAddress);
        return this;
    }

    @Override
    public ExcelConverterBuilder headerEndCell(String cellAddress) {
        this.headerEndCell = new CellAddress(cellAddress);
        return this;
    }

    @Override
    public ExcelConverterBuilder contentsStartCell(String cellAddress) {
        this.contentsStartCell = new CellAddress(cellAddress);
        return this;
    }

    @Override
    public ExcelConverterBuilder linesOfUnit(int linesOfUnit) {
        this.linesOfUnit = linesOfUnit;
        return this;
    }

    @Deprecated
    @Override
    public ExcelConverter build() throws ExcelConvertException {
        return new ExcelConverterImpl(this);
    }

    @Override
    public ExcelSerializer buildSerializer() throws ExcelConvertException {
        return new ExcelSerializerImpl(this);
    }

    @Override
    public ExcelDeserializer buildDeserializer() throws ExcelConvertException {
        return new ExcelDeserializerImpl(this);
    }

    @Override
    public String getExcelFilePath() {
        return excelFilePath;
    }

    @Override
    public String getSheetName() {
        return sheetName;
    }

    @Override
    public boolean hasHeader() {
        return hasHeader;
    }

    @Override
    public HeaderDirection getHeaderDirection() {
        return headerDirection;
    }

    @Override
    public String getHeaderStartCell() {
        if (headerStartCell == null) return null;
        return headerStartCell.formatAsString();
    }

    @Override
    public String getHeaderEndCell() {
        if (headerEndCell == null) return null;
        return headerEndCell.formatAsString();
    }

    @Override
    public String getContentsStartCell() {
        if (contentsStartCell == null) return null;
        return contentsStartCell.formatAsString();
    }

    @Override
    public Integer getLinesOfUnit() {
        return linesOfUnit;
    }
}
