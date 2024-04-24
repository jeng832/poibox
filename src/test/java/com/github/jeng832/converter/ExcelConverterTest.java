package com.github.jeng832.converter;

import com.github.jeng832.model.TestClass;
import org.apache.poi.ss.util.CellAddress;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class ExcelConverterTest {

    @Test
    public void convert() throws IOException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelConverter<TestClass> converter = ExcelConverter.<TestClass>builder()
                .excelFilePath(absolutePath)
                .sheetName("시트1")
                .hasHeader(true)
                .headerStartCell(new CellAddress("A1"))
                .headerEndCell(new CellAddress("D1"))
                .build();

        converter.toObjects(TestClass.class);

    }

}