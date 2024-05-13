package io.github.jeng832.model;

import org.apache.poi.ss.util.CellAddress;

public class ExcelSheetHeaderCell {
    private CellAddress cellAddress;
    private String value;
    private boolean mergedCell = false;
    private ExcelSheetHeaderCell representativeCell = null;

    ExcelSheetHeaderCell(CellAddress cellAddress, String value) {
        this.cellAddress = cellAddress;
        this.value = value;
    }

    ExcelSheetHeaderCell(CellAddress cellAddress) {
        this(cellAddress, null);
    }

    public void makeMergedCell(ExcelSheetHeaderCell representativeCell) {
        this.mergedCell = true;
        this.representativeCell = representativeCell;
    }

    public String getValue() {
        return value;
    }
}
