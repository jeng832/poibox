package com.github.jeng832.model;

import com.github.jeng832.exception.ExcelConvertException;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExcelTest {
    @Test
    public void load_excel_file() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        Excel excel = Excel.of(absolutePath);
        assertNotNull(excel);
        ExcelSheet sheet = excel.getSheet("시트1");
        assertNotNull(sheet);
    }
}