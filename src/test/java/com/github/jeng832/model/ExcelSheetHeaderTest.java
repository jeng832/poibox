package com.github.jeng832.model;

import org.apache.poi.ss.util.CellAddress;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ExcelSheetHeaderTest {

    @Test
    public void make_excel_sheet_header() throws IOException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        Excel excel = Excel.of(absolutePath);
        assertNotNull(excel);
        ExcelSheet sheet = excel.getSheet("시트1");
        ExcelSheetHeader header = new ExcelSheetHeader(sheet, new CellAddress("A1"), new CellAddress("D1"));
        assertNotNull(header);
    }

}