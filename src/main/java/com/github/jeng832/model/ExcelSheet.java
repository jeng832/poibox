package com.github.jeng832.model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public class ExcelSheet {

    private final Sheet sheet;
    private final ExcelSheetHeader header;

    ExcelSheet(Sheet sheet) {
        this.sheet = sheet;
        this.header = null;
    }

    ExcelSheet(Sheet sheet, CellAddress headerStartCell, CellAddress headerEndCell) {
        this.sheet = sheet;
        this.header = new ExcelSheetHeader(sheet, headerStartCell, headerEndCell);
    }

    private Cell getCell(CellAddress address) {
        int row = address.getRow();
        int column = address.getColumn();
        return this.sheet.getRow(row).getCell(column);
    }

    public String getValueAsString(CellAddress cellAddress) {
        Cell cell = getCell(cellAddress);
        if (cell == null) return null;
        return cell.getStringCellValue();
    }

    public Double getValueAsDouble(CellAddress cellAddress) {
        Cell cell = getCell(cellAddress);
        if (cell == null) return null;
        return cell.getNumericCellValue();
    }

    public Integer getValueAsInteger(CellAddress cellAddress) {
        Cell cell = getCell(cellAddress);
        if (cell == null) return null;
        return Double.valueOf(cell.getNumericCellValue()).intValue();
    }

    public Long getValueAsLong(CellAddress cellAddress) {
        Cell cell = getCell(cellAddress);
        if (cell == null) return null;
        return Double.valueOf(cell.getNumericCellValue()).longValue();
    }

    public Date getValueAsDate(CellAddress cellAddress) {
        Cell cell = getCell(cellAddress);
        if (cell == null) return null;
        return cell.getDateCellValue();
    }

    public Boolean getValueAsBoolean(CellAddress cellAddress) {
        Cell cell = getCell(cellAddress);
        if (cell == null) return null;
        return cell.getBooleanCellValue();
    }

    public boolean isStringCell(CellAddress cellAddress) {
        Cell cell = getCell(cellAddress);
        if (cell == null) return false;
        return CellType.STRING.equals(cell.getCellType());
    }

    public boolean isNumberCell(CellAddress cellAddress) {
        Cell cell = getCell(cellAddress);
        if (cell == null) return false;
        return CellType.NUMERIC.equals(cell.getCellType());
    }

    public boolean isFormulaCell(CellAddress cellAddress) {
        Cell cell = getCell(cellAddress);
        if (cell == null) return false;
        return CellType.FORMULA.equals(cell.getCellType());
    }

    public boolean isStringFormulaCell(CellAddress cellAddress) {
        if (!isFormulaCell(cellAddress)) return false;
        Cell cell = getCell(cellAddress);
        if (cell == null) return false;
        return CellType.STRING.equals(cell.getCachedFormulaResultType());
    }

    public boolean isNumberFormulaCell(CellAddress cellAddress) {
        if (!isFormulaCell(cellAddress)) return false;
        Cell cell = getCell(cellAddress);
        if (cell == null) return false;
        return CellType.NUMERIC.equals(cell.getCachedFormulaResultType());
    }

    public boolean isBooleanCell(CellAddress cellAddress) {
        Cell cell = getCell(cellAddress);
        if (cell == null) return false;
        return CellType.BOOLEAN.equals(cell.getCellType());
    }

    public boolean isDateCell(CellAddress cellAddress) {
        Cell cell = getCell(cellAddress);
        if (cell == null) return false;
        return CellType.NUMERIC.equals(cell.getCellType()) && DateUtil.isCellDateFormatted(cell);
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

    public int getHeaderHeight() {
        if (this.header == null) return 0;
        return this.header.getHeaderHeight();
    }

    public int getHeaderWidth() {
        if (this.header == null) return 0;
        return this.header.getHeaderWidth();
    }

    public Optional<String> getHeaderValue(int relativeCol, int relativeRow) {
        if (this.header == null) return Optional.empty();
        return Optional.ofNullable(header.getHeaderValue(relativeCol, relativeRow));
    }
}
