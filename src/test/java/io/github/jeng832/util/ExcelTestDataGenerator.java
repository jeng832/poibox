package io.github.jeng832.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Generates Excel files required for testing purposes.
 * Run the main method to create the files in src/test/resources/xlsx/.
 */
public class ExcelTestDataGenerator {

    private static final Path OUTPUT_DIR = Paths.get("src", "test", "resources", "xlsx");

    public static void main(String[] args) {
        try {
            // Ensure the output directory exists
            if (!Files.exists(OUTPUT_DIR)) {
                Files.createDirectories(OUTPUT_DIR);
            }

            createMismatchFile();
            createAnnotationFile();
            createBuilderOptionsFile();

            System.out.println("Successfully generated test Excel files in: " + OUTPUT_DIR.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error generating test Excel files: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createMismatchFile() throws IOException {
        Path filePath = OUTPUT_DIR.resolve("test_mismatch.xlsx");
        try (Workbook workbook = new XSSFWorkbook();
             OutputStream fileOut = new FileOutputStream(filePath.toFile())) {

            Sheet sheet = workbook.createSheet("mismatch");

            // Header Row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("순번");
            headerRow.createCell(1).setCellValue("이름");
            headerRow.createCell(2).setCellValue("숫자");

            // Data Row 1 (with mismatch)
            Row dataRow1 = sheet.createRow(1);
            dataRow1.createCell(0).setCellValue("abc"); // Type mismatch: String in Integer column
            dataRow1.createCell(1).setCellValue("테스트1");
            dataRow1.createCell(2).setCellValue(11);

            // Data Row 2
            Row dataRow2 = sheet.createRow(2);
            dataRow2.createCell(0).setCellValue(2);
            dataRow2.createCell(1).setCellValue("테스트2");
            dataRow2.createCell(2).setCellValue(12);

            workbook.write(fileOut);
        }
        System.out.println("- Created: " + filePath.getFileName());
    }

    private static void createAnnotationFile() throws IOException {
        Path filePath = OUTPUT_DIR.resolve("test_annotation.xlsx");
        try (Workbook workbook = new XSSFWorkbook();
             OutputStream fileOut = new FileOutputStream(filePath.toFile())) {

            Sheet sheet = workbook.createSheet("Sheet1");

            // Header Row (starts from B1)
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(1).setCellValue("Field Header 1"); // B1
            headerRow.createCell(2).setCellValue("Field Header 2"); // C1
            headerRow.createCell(3).setCellValue("Ignored Header"); // D1

            // Data Row
            Row dataRow = sheet.createRow(1);
            dataRow.createCell(1).setCellValue("Data Val 1");      // B2
            dataRow.createCell(2).setCellValue(2);                 // C2 (Numeric)
            dataRow.createCell(3).setCellValue("Should Be Ignored"); // D2

            workbook.write(fileOut);
        }
        System.out.println("- Created: " + filePath.getFileName());
    }

    private static void createBuilderOptionsFile() throws IOException {
        Path filePath = OUTPUT_DIR.resolve("test_builder_options.xlsx");
        try (Workbook workbook = new XSSFWorkbook();
             OutputStream fileOut = new FileOutputStream(filePath.toFile())) {

            // Sheet 1: MultiHeader
            Sheet sheet1 = workbook.createSheet("MultiHeader");
            Row header1_1 = sheet1.createRow(0); // Header Row 1
            header1_1.createCell(0).setCellValue("Group A");
            header1_1.createCell(1).setCellValue("Group A");
            header1_1.createCell(2).setCellValue("Group B");
            header1_1.createCell(3).setCellValue("Group B");

            Row header1_2 = sheet1.createRow(1); // Header Row 2 (mapping target)
            header1_2.createCell(0).setCellValue("ID");
            header1_2.createCell(1).setCellValue("Value");
            header1_2.createCell(2).setCellValue("ID"); // Duplicate header name example
            header1_2.createCell(3).setCellValue("Name");

            Row data1_1 = sheet1.createRow(2); // Data Row 1
            data1_1.createCell(0).setCellValue(1);
            data1_1.createCell(1).setCellValue("A1");
            data1_1.createCell(2).setCellValue(101);
            data1_1.createCell(3).setCellValue("Test1");

            Row data1_2 = sheet1.createRow(3); // Data Row 2
            data1_2.createCell(0).setCellValue(2);
            data1_2.createCell(1).setCellValue("A2");
            data1_2.createCell(2).setCellValue(102);
            data1_2.createCell(3).setCellValue("Test2");


            // Sheet 2: OffsetData
            Sheet sheet2 = workbook.createSheet("OffsetData");
            Row header2_1 = sheet2.createRow(0); // Header Row
            header2_1.createCell(0).setCellValue("ID");
            header2_1.createCell(1).setCellValue("Value");
            header2_1.createCell(2).setCellValue("Name");

            // Rows 2 and 3 are intentionally left empty

            Row data2_1 = sheet2.createRow(3); // Data Row 1 (starts at index 3)
            data2_1.createCell(0).setCellValue(1);
            data2_1.createCell(1).setCellValue("A1");
            data2_1.createCell(2).setCellValue("Test1");

            Row data2_2 = sheet2.createRow(4); // Data Row 2
            data2_2.createCell(0).setCellValue(2);
            data2_2.createCell(1).setCellValue("A2");
            data2_2.createCell(2).setCellValue("Test2");


            workbook.write(fileOut);
        }
        System.out.println("- Created: " + filePath.getFileName());
    }
} 