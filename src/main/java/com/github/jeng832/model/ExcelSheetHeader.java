package com.github.jeng832.model;

import com.github.jeng832.converter.HeaderDirection;
import org.apache.poi.ss.util.CellAddress;

import java.util.Objects;

public class ExcelSheetHeader {

    private HeaderDirection headerDirection = HeaderDirection.HORIZONTAL;
    private CellAddress headerStartCell;
    private CellAddress headerEndCell;
    private ExcelSheetHeaderCell[][] cells;

    public ExcelSheetHeader(ExcelSheet sheet, CellAddress headerStartCell, CellAddress headerEndCell) {
        this.headerStartCell = Objects.nonNull(headerStartCell) ? headerStartCell : CellAddress.A1;
        this.headerEndCell = Objects.nonNull(headerEndCell) ? headerEndCell : CellAddress.A1;
        int headerWidth = this.headerEndCell.getColumn() - this.headerStartCell.getColumn() + 1;
        int headerHeight = getHeaderHeight();
        this.cells = new ExcelSheetHeaderCell[headerHeight][headerWidth];
        for (int i = 0; i < headerHeight; i++) {
            int currentRow = this.headerStartCell.getRow() + i;
            for (int j = 0; j < headerWidth; j++) {
                int currentCol = this.headerStartCell.getColumn() + j;
                CellAddress currentCell = new CellAddress(currentRow, currentCol);
                ExcelSheetHeaderCell headerCell = new ExcelSheetHeaderCell(currentCell, sheet.getValueAsString(currentCell).orElse(null));
                CellAddress representativeMergedCell = sheet.getRepresentativeMergedCell(currentCell);
                if (!currentCell.equals(representativeMergedCell)) {
                    int relativeRow = currentCell.getRow() - representativeMergedCell.getRow();
                    int relativeCol = currentCell.getColumn() - representativeMergedCell.getColumn();
                    headerCell.makeMergedCell(this.cells[i - relativeRow][j - relativeCol]);
                }
                this.cells[currentRow][currentCol] = headerCell;
            }
        }
    }

    public int getHeaderHeight() {
        return this.headerEndCell.getRow() - this.headerStartCell.getRow() + 1;
    }
}
