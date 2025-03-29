package io.github.jeng832.serializer;

import io.github.jeng832.annotation.ExcelProperty;
import io.github.jeng832.converter.ExcelConverterBuilderFactory;
import io.github.jeng832.exception.ExcelConvertException;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ExcelSerializerTest {

    @TempDir
    Path tempDir;
    private String testExcelPath;

    @BeforeEach
    void setUp() {
        testExcelPath = tempDir.resolve("test.xlsx").toString();
    }

    @Test
    void serialize_basic_types() throws ExcelConvertException, IOException {
        // 테스트 데이터 준비
        List<BasicTypesTestClass> objects = Arrays.asList(
            new BasicTypesTestClass("홍길동", 30, 175.5, new Date(), true),
            new BasicTypesTestClass("김철수", 25, 180.0, new Date(), false)
        );

        // Excel 파일 생성
        ExcelSerializer serializer = ExcelConverterBuilderFactory.create()
                .excelFilePath(testExcelPath)
                .sheetName("basic_types")
                .hasHeader(true)
                .headerStartCell("A1")
                .buildSerializer();

        serializer.serialize(objects, BasicTypesTestClass.class);

        // 생성된 파일 검증
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(testExcelPath))) {
            Sheet sheet = workbook.getSheet("basic_types");
            assertNotNull(sheet);

            // 헤더 검증
            Row headerRow = sheet.getRow(0);
            assertEquals("이름", headerRow.getCell(0).getStringCellValue());
            assertEquals("나이", headerRow.getCell(1).getStringCellValue());
            assertEquals("키", headerRow.getCell(2).getStringCellValue());
            assertEquals("생년월일", headerRow.getCell(3).getStringCellValue());
            assertEquals("활성", headerRow.getCell(4).getStringCellValue());

            // 데이터 검증
            Row dataRow1 = sheet.getRow(1);
            assertEquals("홍길동", dataRow1.getCell(0).getStringCellValue());
            assertEquals(30, dataRow1.getCell(1).getNumericCellValue());
            assertEquals(175.5, dataRow1.getCell(2).getNumericCellValue());
            assertTrue(dataRow1.getCell(4).getBooleanCellValue());

            Row dataRow2 = sheet.getRow(2);
            assertEquals("김철수", dataRow2.getCell(0).getStringCellValue());
            assertEquals(25, dataRow2.getCell(1).getNumericCellValue());
            assertEquals(180.0, dataRow2.getCell(2).getNumericCellValue());
            assertFalse(dataRow2.getCell(4).getBooleanCellValue());
        }
    }

    @Test
    void serialize_date_types() throws ExcelConvertException, IOException {
        // 테스트 데이터 준비
        List<DateTypesTestClass> objects = Arrays.asList(
            new DateTypesTestClass(
                new Date(),
                LocalDate.now(),
                LocalDateTime.now(),
                ZonedDateTime.now()
            )
        );

        // Excel 파일 생성
        ExcelSerializer serializer = ExcelConverterBuilderFactory.create()
                .excelFilePath(testExcelPath)
                .sheetName("date_types")
                .hasHeader(true)
                .headerStartCell("A1")
                .buildSerializer();

        serializer.serialize(objects, DateTypesTestClass.class);

        // 생성된 파일 검증
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(testExcelPath))) {
            Sheet sheet = workbook.getSheet("date_types");
            assertNotNull(sheet);

            // 헤더 검증
            Row headerRow = sheet.getRow(0);
            assertEquals("Date", headerRow.getCell(0).getStringCellValue());
            assertEquals("LocalDate", headerRow.getCell(1).getStringCellValue());
            assertEquals("LocalDateTime", headerRow.getCell(2).getStringCellValue());
            assertEquals("ZonedDateTime", headerRow.getCell(3).getStringCellValue());

            // 데이터 검증
            Row dataRow = sheet.getRow(1);
            assertNotNull(dataRow.getCell(0).getDateCellValue());
            assertNotNull(dataRow.getCell(1).getDateCellValue());
            assertNotNull(dataRow.getCell(2).getDateCellValue());
            assertNotNull(dataRow.getCell(3).getDateCellValue());
        }
    }

    @Test
    void serialize_empty_values() throws ExcelConvertException, IOException {
        // 테스트 데이터 준비
        List<EmptyValuesTestClass> objects = Arrays.asList(
            new EmptyValuesTestClass(null, null, null, null)
        );

        // Excel 파일 생성
        ExcelSerializer serializer = ExcelConverterBuilderFactory.create()
                .excelFilePath(testExcelPath)
                .sheetName("empty_values")
                .hasHeader(true)
                .headerStartCell("A1")
                .buildSerializer();

        serializer.serialize(objects, EmptyValuesTestClass.class);

        // 생성된 파일 검증
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(testExcelPath))) {
            Sheet sheet = workbook.getSheet("empty_values");
            assertNotNull(sheet);

            // 데이터 검증
            Row dataRow = sheet.getRow(1);
            assertEquals("", dataRow.getCell(0).getStringCellValue());
            assertEquals("", dataRow.getCell(1).getStringCellValue());
            assertEquals("", dataRow.getCell(2).getStringCellValue());
            assertEquals("", dataRow.getCell(3).getStringCellValue());
        }
    }

    @Test
    void serialize_without_header() throws ExcelConvertException, IOException {
        // 테스트 데이터 준비
        List<BasicTypesTestClass> objects = Arrays.asList(
            new BasicTypesTestClass("홍길동", 30, 175.5, new Date(), true)
        );

        // Excel 파일 생성
        ExcelSerializer serializer = ExcelConverterBuilderFactory.create()
                .excelFilePath(testExcelPath)
                .sheetName("no_header")
                .hasHeader(false)
                .headerStartCell("A1")
                .buildSerializer();

        serializer.serialize(objects, BasicTypesTestClass.class);

        // 생성된 파일 검증
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(testExcelPath))) {
            Sheet sheet = workbook.getSheet("no_header");
            assertNotNull(sheet);

            // 데이터만 있는지 확인
            Row dataRow = sheet.getRow(0);
            assertEquals("홍길동", dataRow.getCell(0).getStringCellValue());
            assertEquals(30, dataRow.getCell(1).getNumericCellValue());
            assertEquals(175.5, dataRow.getCell(2).getNumericCellValue());
            assertTrue(dataRow.getCell(4).getBooleanCellValue());
        }
    }

    // 테스트용 클래스들
    public static class BasicTypesTestClass {
        @ExcelProperty("이름")
        private String name;
        @ExcelProperty("나이")
        private Integer age;
        @ExcelProperty("키")
        private Double height;
        @ExcelProperty("생년월일")
        private Date birthDate;
        @ExcelProperty("활성")
        private Boolean active;

        public BasicTypesTestClass(String name, Integer age, Double height, Date birthDate, Boolean active) {
            this.name = name;
            this.age = age;
            this.height = height;
            this.birthDate = birthDate;
            this.active = active;
        }
    }

    public static class DateTypesTestClass {
        @ExcelProperty("Date")
        private Date date;
        @ExcelProperty("LocalDate")
        private LocalDate localDate;
        @ExcelProperty("LocalDateTime")
        private LocalDateTime localDateTime;
        @ExcelProperty("ZonedDateTime")
        private ZonedDateTime zonedDateTime;

        public DateTypesTestClass(Date date, LocalDate localDate, LocalDateTime localDateTime, ZonedDateTime zonedDateTime) {
            this.date = date;
            this.localDate = localDate;
            this.localDateTime = localDateTime;
            this.zonedDateTime = zonedDateTime;
        }
    }

    public static class EmptyValuesTestClass {
        @ExcelProperty("문자열")
        private String stringValue;
        @ExcelProperty("숫자")
        private Integer numberValue;
        @ExcelProperty("날짜")
        private Date dateValue;
        @ExcelProperty("불리언")
        private Boolean booleanValue;

        public EmptyValuesTestClass(String stringValue, Integer numberValue, Date dateValue, Boolean booleanValue) {
            this.stringValue = stringValue;
            this.numberValue = numberValue;
            this.dateValue = dateValue;
            this.booleanValue = booleanValue;
        }
    }
} 