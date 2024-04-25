package com.github.jeng832.model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Optional;

public class ExcelSheet {

    private final Sheet sheet;

    ExcelSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public Cell getCell(String cellAddress) {
        CellAddress address = new CellAddress(cellAddress);
        int row = address.getRow();
        int column = address.getColumn();
        return this.sheet.getRow(row).getCell(column);
    }

    public Optional<String> getValueAsString(String cellCode) {
        Cell cell = getCell(cellCode);
        if (cell == null) return Optional.empty();
        return Optional.ofNullable(cell.getStringCellValue());
    }

    public Optional<String> getValueAsString(CellAddress cellAddress) {
        return getValueAsString(cellAddress.toString());
    }

    public Optional<Double> getValueAsDouble(String cellCode) {
        Cell cell = getCell(cellCode);
        if (cell == null) return Optional.empty();
        return Optional.of(cell.getNumericCellValue());
    }

    public Optional<Boolean> getValueAsBoolean(String cellCode) {
        Cell cell = getCell(cellCode);
        if (cell == null) return Optional.empty();
        return Optional.of(cell.getBooleanCellValue());
    }

    public boolean isStringCell(String cellCode) {
        Cell cell = getCell(cellCode);
        if (cell == null) return false;
        return CellType.STRING.equals(cell.getCellType());
    }

    public boolean isNumberCell(String cellCode) {
        Cell cell = getCell(cellCode);
        if (cell == null) return false;
        return CellType.NUMERIC.equals(cell.getCellType());
    }

    public boolean isFormulaCell(String cellCode) {
        Cell cell = getCell(cellCode);
        if (cell == null) return false;
        return CellType.FORMULA.equals(cell.getCellType());
    }

    public boolean isMergedCell(CellAddress cellAddress) {
        for (CellRangeAddress mergedRegion : sheet.getMergedRegions()) {
            if (mergedRegion.isInRange(cellAddress.getRow(), cellAddress.getColumn())) {
                return true;
            }
        }
        return false;
    }

    public CellAddress getRepresentativeMergedCell(CellAddress cellAddress) {
        for (CellRangeAddress mergedRegion : sheet.getMergedRegions()) {
            if (mergedRegion.isInRange(cellAddress.getRow(), cellAddress.getColumn())) {
                return new CellAddress(mergedRegion.getFirstRow(), mergedRegion.getFirstColumn());
            }
        }
        return cellAddress;
    }

    public int getLastRowNumber() {
        return this.sheet.getLastRowNum();
    }
}
