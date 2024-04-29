package com.github.jeng832.model;

import com.github.jeng832.converter.HeaderDirection;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Objects;

public class ExcelSheetHeader {

    private HeaderDirection headerDirection = HeaderDirection.HORIZONTAL;
    private CellAddress headerStartCell;
    private CellAddress headerEndCell;
    private ExcelSheetHeaderCell[][] cells;

    ExcelSheetHeader(Sheet sheet, CellAddress headerStartCell, CellAddress headerEndCell) {
        this.headerStartCell = Objects.nonNull(headerStartCell) ? headerStartCell : CellAddress.A1;
        this.headerEndCell = Objects.nonNull(headerEndCell) ? headerEndCell : CellAddress.A1;
        int headerWidth = getHeaderWidth();
        int headerHeight = getHeaderHeight();
        this.cells = new ExcelSheetHeaderCell[headerHeight][headerWidth];
        for (int i = 0; i < headerHeight; i++) {
            int currentRow = this.headerStartCell.getRow() + i;
            for (int j = 0; j < headerWidth; j++) {
                int currentCol = this.headerStartCell.getColumn() + j;
                CellAddress currentCell = new CellAddress(currentRow, currentCol);
                int row = currentCell.getRow();
                int column = currentCell.getColumn();
                String headerValue = sheet.getRow(row).getCell(column).getStringCellValue();
                ExcelSheetHeaderCell headerCell = new ExcelSheetHeaderCell(currentCell, headerValue);
                CellAddress representativeMergedCell = getRepresentativeMergedCell(sheet, currentCell);
                if (!currentCell.equals(representativeMergedCell)) {
                    int relativeRow = currentCell.getRow() - representativeMergedCell.getRow();
                    int relativeCol = currentCell.getColumn() - representativeMergedCell.getColumn();
                    headerCell.makeMergedCell(this.cells[i - relativeRow][j - relativeCol]);
                }
                this.cells[currentRow][currentCol] = headerCell;
            }
        }
    }

    private CellAddress getRepresentativeMergedCell(Sheet sheet, CellAddress cellAddress) {
        for (CellRangeAddress mergedRegion : sheet.getMergedRegions()) {
            if (mergedRegion.isInRange(cellAddress.getRow(), cellAddress.getColumn())) {
                return new CellAddress(mergedRegion.getFirstRow(), mergedRegion.getFirstColumn());
            }
        }
        return cellAddress;
    }

    int getHeaderHeight() {
        return this.headerEndCell.getRow() - this.headerStartCell.getRow() + 1;
    }

    int getHeaderWidth() {
        return this.headerEndCell.getColumn() - this.headerStartCell.getColumn() + 1;
    }

    String getHeaderValue(int headerRow, int headerColumn) {
        return this.cells[headerRow][headerColumn].getValue();
    }
}
