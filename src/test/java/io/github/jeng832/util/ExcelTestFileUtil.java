package io.github.jeng832.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTestFileUtil {

    public static final String TEST_FILE_DIR = "src/test/resources/temp";
    public static final String MULTI_LINE_HEADER_UNIT_FILE = "multi_line_header_unit.xlsx";

    public static Path createMultiLineHeaderUnitFile() throws IOException {
        Path dirPath = Paths.get(TEST_FILE_DIR);
        Files.createDirectories(dirPath); // 디렉토리가 없으면 생성
        Path filePath = dirPath.resolve(MULTI_LINE_HEADER_UNIT_FILE);

        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(filePath.toFile())) {

            Sheet sheet = workbook.createSheet("MultiLineHeader");

            // Header Row 1
            Row headerRow1 = sheet.createRow(0);
            Cell cellA1 = headerRow1.createCell(0);
            cellA1.setCellValue("ID");
            headerRow1.createCell(1); // B1 for merge
            Cell cellC1 = headerRow1.createCell(2);
            cellC1.setCellValue("Name");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1)); // Merge A1:B1

            // Header Row 2
            Row headerRow2 = sheet.createRow(1);
            headerRow2.createCell(0).setCellValue("Detail");
            headerRow2.createCell(1).setCellValue("Code");
            headerRow2.createCell(2).setCellValue("Full");

            // Data Row 1 (Unit 1)
            Row dataRow1_1 = sheet.createRow(2);
            dataRow1_1.createCell(0).setCellValue("ID001");
            dataRow1_1.createCell(1).setCellValue("CodeA");
            dataRow1_1.createCell(2).setCellValue("John Doe");

            // Data Row 2 (Unit 1)
            Row dataRow1_2 = sheet.createRow(3);
            dataRow1_2.createCell(0).setCellValue("Detail1");
            dataRow1_2.createCell(1).setCellValue("SubCode1");
            dataRow1_2.createCell(2).setCellValue("Additional Info 1");

            // Data Row 3 (Unit 2)
            Row dataRow2_1 = sheet.createRow(4);
            dataRow2_1.createCell(0).setCellValue("ID002");
            dataRow2_1.createCell(1).setCellValue("CodeB");
            dataRow2_1.createCell(2).setCellValue("Jane Smith");

            // Data Row 4 (Unit 2)
            Row dataRow2_2 = sheet.createRow(5);
            dataRow2_2.createCell(0).setCellValue("Detail2");
            dataRow2_2.createCell(1).setCellValue("SubCode2");
            dataRow2_2.createCell(2).setCellValue("Additional Info 2");

            workbook.write(fileOut);
        }
        return filePath;
    }

    // 필요한 경우 다른 테스트 파일 생성 메소드 추가

    public static void main(String[] args) {
        try {
            Path createdFile = createMultiLineHeaderUnitFile();
            System.out.println("Test file created: " + createdFile.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error creating test file: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 