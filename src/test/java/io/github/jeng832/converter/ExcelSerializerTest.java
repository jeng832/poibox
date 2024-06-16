package io.github.jeng832.converter;

import io.github.jeng832.exception.ExcelConvertException;
import io.github.jeng832.model.*;
import io.github.jeng832.serializer.ExcelSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExcelSerializerTest {

    @Test
    public void convert_without_setter() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelSerializer serializer = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("시트1")
                .hasHeader(true)
                .headerStartCell("A1")
                .headerEndCell("G1")
                .buildSerializer();

        List<NoSetterTestClass> objects = serializer.serialize(NoSetterTestClass.class);
        assertNotNull(objects);
        Assertions.assertEquals(4, objects.size());
        System.out.println(objects);
    }

    @Test
    public void convert_with_setter() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelSerializer serializer = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("시트1")
                .hasHeader(true)
                .headerStartCell("A1")
                .headerEndCell("G1")
                .buildSerializer();

        List<SetterTestClass> objects = serializer.serialize(SetterTestClass.class);
        assertNotNull(objects);
        Assertions.assertEquals(4, objects.size());
        System.out.println(objects);

    }

    @Test
    public void convert_multi_line_header() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelSerializer serializer = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("2line_header_1line_content")
                .hasHeader(true)
                .headerStartCell("B4")
                .headerEndCell("H4")
                .buildSerializer();
        List<NoSetterMultiLineHeaderTestClass> objects = serializer.serialize(NoSetterMultiLineHeaderTestClass.class);
        assertNotNull(objects);
        Assertions.assertEquals(4, objects.size());
        System.out.println(objects);
    }

    @Test
    public void convert_multi_line_header_with_setter() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelSerializer serializer = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("2line_header_1line_content")
                .hasHeader(true)
                .headerStartCell("B4")
                .headerEndCell("H4")
                .buildSerializer();
        List<SetterMultiLineHeaderTestClass> objects = serializer.serialize(SetterMultiLineHeaderTestClass.class);
        assertNotNull(objects);
        Assertions.assertEquals(4, objects.size());
        System.out.println(objects);
    }

    @Test
    public void convert_multi_line_header_multi_line_contents() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelSerializer serializer = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("2line_header_2line_content")
                .hasHeader(true)
                .headerStartCell("B3")
                .headerEndCell("H4")
                .buildSerializer();
        List<NoSetterMultiLineHeaderMultiLineContentsTestClass> objects = serializer.serialize(NoSetterMultiLineHeaderMultiLineContentsTestClass.class);
        assertNotNull(objects);
        System.out.println(objects);
        Assertions.assertEquals(2, objects.size());
    }

    @Test
    public void convert_multi_line_header_multi_line_contents_with_setter() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelSerializer serializer = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("2line_header_2line_content")
                .hasHeader(true)
                .headerStartCell("B3")
                .headerEndCell("H4")
                .buildSerializer();
        List<SetterMultiLineHeaderMultiLineContentsTestClass> objects = serializer.serialize(SetterMultiLineHeaderMultiLineContentsTestClass.class);
        assertNotNull(objects);
        System.out.println(objects);
        Assertions.assertEquals(2, objects.size());
    }

    @Test
    public void convert_multi_line_header_multi_line_contents_with_empty_row_between_header_and_contents() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelSerializer serializer = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("2line_header_2line_content_2")
                .hasHeader(true)
                .headerStartCell("B3")
                .headerEndCell("H4")
                .contentsStartCell("B7")
                .buildSerializer();
        List<NoSetterMultiLineHeaderMultiLineContentsTestClass> objects = serializer.serialize(NoSetterMultiLineHeaderMultiLineContentsTestClass.class);
        assertNotNull(objects);
        System.out.println(objects);
        Assertions.assertEquals(2, objects.size());
    }

    @Test
    public void convert_multi_line_header_multi_line_contents_with_empty_row_between_header_and_contents_with_setter() throws ExcelConvertException {
        Path resourceDirectory = Paths.get("src","test","resources", "xlsx", "test.xlsx");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        ExcelSerializer serializer = ExcelConverterBuilderFactory.create()
                .excelFilePath(absolutePath)
                .sheetName("2line_header_2line_content_2")
                .hasHeader(true)
                .headerStartCell("B3")
                .headerEndCell("H4")
                .contentsStartCell("B7")
                .buildSerializer();
        List<SetterMultiLineHeaderMultiLineContentsTestClass> objects = serializer.serialize(SetterMultiLineHeaderMultiLineContentsTestClass.class);
        assertNotNull(objects);
        System.out.println(objects);
        Assertions.assertEquals(2, objects.size());
    }

}