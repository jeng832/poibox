package com.github.jeng832.converter;

import com.github.jeng832.model.TestClass;
import org.apache.poi.ss.util.CellAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExcelConverterTest {

    @Test
    public void convert() throws IOException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelConverter converter = ExcelConverter.builder()
                .excelFilePath(absolutePath)
                .sheetName("시트1")
                .hasHeader(true)
                .headerStartCell(new CellAddress("A1"))
                .headerEndCell(new CellAddress("D1"))
                .build();

        List<TestClass> objects = converter.toObjects(TestClass.class);
        assertNotNull(objects);
        System.out.println(objects);

    }

}