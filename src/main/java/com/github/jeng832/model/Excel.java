package com.github.jeng832.model;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class Excel {

    private final Workbook workbook;

    private Excel(Workbook workbook) {
        this.workbook = workbook;
    }

    public static Excel of(String fileName) throws IOException {
        FileInputStream file = new FileInputStream(fileName);
        return new Excel(new XSSFWorkbook(file));
    }

    public ExcelSheet getSheet(String name) {
        return new ExcelSheet(workbook.getSheet(name));
    }

    public ExcelSheet getSheetWithHeader(String name, CellAddress headerStartCell, CellAddress headerEndCell) {
        return new ExcelSheet(workbook.getSheet(name), headerStartCell, headerEndCell);
    }

}
