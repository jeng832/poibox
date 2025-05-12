package io.github.jeng832.converter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.jeng832.exception.ExcelConvertException;
import io.github.jeng832.model.NoSetterMultiLineHeaderMultiLineContentsTestClass;
import io.github.jeng832.model.NoSetterMultiLineHeaderTestClass;
import io.github.jeng832.model.NoSetterTestClass;
import io.github.jeng832.model.SetterMultiLineHeaderMultiLineContentsTestClass;
import io.github.jeng832.model.SetterMultiLineHeaderTestClass;
import io.github.jeng832.model.SetterTestClass;

class ExcelConverterTest {

    @Test
    public void convert_without_setter() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelConverter converter = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("시트1")
                .hasHeader(true)
                .headerStartCell("A1")
                .headerEndCell("G1")
                .build();

        List<NoSetterTestClass> objects = converter.toObjects(NoSetterTestClass.class);
        assertNotNull(objects);
        Assertions.assertEquals(4, objects.size());
        System.out.println(objects);
    }

    @Test
    public void convert_with_setter() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelConverter converter = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("시트1")
                .hasHeader(true)
                .headerStartCell("A1")
                .headerEndCell("G1")
                .build();

        List<SetterTestClass> objects = converter.toObjects(SetterTestClass.class);
        assertNotNull(objects);
        Assertions.assertEquals(4, objects.size());
        System.out.println(objects);

    }

    @Test
    public void convert_multi_line_header() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelConverter converter = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("2line_header_1line_content")
                .hasHeader(true)
                .headerStartCell("B4")
                .headerEndCell("H4")
                .build();
        List<NoSetterMultiLineHeaderTestClass> objects = converter.toObjects(NoSetterMultiLineHeaderTestClass.class);
        assertNotNull(objects);
        Assertions.assertEquals(4, objects.size());
        System.out.println(objects);
    }

    @Test
    public void convert_multi_line_header_with_setter() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelConverter converter = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("2line_header_1line_content")
                .hasHeader(true)
                .headerStartCell("B4")
                .headerEndCell("H4")
                .build();
        List<SetterMultiLineHeaderTestClass> objects = converter.toObjects(SetterMultiLineHeaderTestClass.class);
        assertNotNull(objects);
        Assertions.assertEquals(4, objects.size());
        System.out.println(objects);
    }

    @Test
    public void convert_multi_line_header_multi_line_contents() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelConverter converter = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("2line_header_2line_content")
                .hasHeader(true)
                .headerStartCell("B3")
                .headerEndCell("H4")
                .linesOfUnit(2)
                .build();
        List<NoSetterMultiLineHeaderMultiLineContentsTestClass> objects = converter.toObjects(NoSetterMultiLineHeaderMultiLineContentsTestClass.class);
        assertNotNull(objects);
        System.out.println(objects);
        Assertions.assertEquals(2, objects.size());
    }

    @Test
    public void convert_multi_line_header_multi_line_contents_with_setter() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelConverter converter = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("2line_header_2line_content")
                .hasHeader(true)
                .headerStartCell("B3")
                .headerEndCell("H4")
                .linesOfUnit(2)
                .build();
        List<SetterMultiLineHeaderMultiLineContentsTestClass> objects = converter.toObjects(SetterMultiLineHeaderMultiLineContentsTestClass.class);
        assertNotNull(objects);
        System.out.println(objects);
        Assertions.assertEquals(2, objects.size());
    }

    @Test
    public void convert_multi_line_header_multi_line_contents_with_empty_row_between_header_and_contents() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelConverter converter = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("2line_header_2line_content_2")
                .hasHeader(true)
                .headerStartCell("B3")
                .headerEndCell("H4")
                .contentsStartCell("B7")
                .linesOfUnit(2)
                .build();
        List<NoSetterMultiLineHeaderMultiLineContentsTestClass> objects = converter.toObjects(NoSetterMultiLineHeaderMultiLineContentsTestClass.class);
        assertNotNull(objects);
        System.out.println(objects);
        Assertions.assertEquals(2, objects.size());
    }

    @Test
    public void convert_multi_line_header_multi_line_contents_with_empty_row_between_header_and_contents_with_setter() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelConverter converter = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("2line_header_2line_content_2")
                .hasHeader(true)
                .headerStartCell("B3")
                .headerEndCell("H4")
                .contentsStartCell("B7")
                .linesOfUnit(2)
                .build();
        List<SetterMultiLineHeaderMultiLineContentsTestClass> objects = converter.toObjects(SetterMultiLineHeaderMultiLineContentsTestClass.class);
        assertNotNull(objects);
        System.out.println(objects);
        Assertions.assertEquals(2, objects.size());
    }

}