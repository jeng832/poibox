package io.github.jeng832.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import io.github.jeng832.exception.ExcelConvertException;

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