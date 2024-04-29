package com.github.jeng832.converter;

import com.github.jeng832.model.NoSetterTestClass;
import com.github.jeng832.model.SetterTestClass;
import org.apache.poi.ss.util.CellAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExcelConverterTest {

    @Test
    public void convertWithNoSetter() throws IOException, ReflectiveOperationException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelConverter converter = ExcelConverter.builder()
                .excelFilePath(absolutePath)
                .sheetName("시트1")
                .hasHeader(true)
                .headerStartCell(new CellAddress("A1"))
                .headerEndCell(new CellAddress("G1"))
                .build();

        List<NoSetterTestClass> objects = converter.toObjects(NoSetterTestClass.class);
        assertNotNull(objects);
        Assertions.assertEquals(4, objects.size());
        System.out.println(objects);
    }

    @Test
    public void convertWithSetter() throws IOException, ReflectiveOperationException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelConverter converter = ExcelConverter.builder()
                .excelFilePath(absolutePath)
                .sheetName("시트1")
                .hasHeader(true)
                .headerStartCell(new CellAddress("A1"))
                .headerEndCell(new CellAddress("G1"))
                .build();

        List<SetterTestClass> objects = converter.toObjects(SetterTestClass.class);
        assertNotNull(objects);
        Assertions.assertEquals(4, objects.size());
        System.out.println(objects);

    }

}