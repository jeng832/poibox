package com.github.jeng832.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ExcelTest {
    @Test
    public void load_excel_file() throws IOException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        Excel excel = Excel.of(absolutePath);
        assertNotNull(excel);
        ExcelSheet sheet = excel.getSheet("시트1");
        assertNotNull(sheet);
    }
}