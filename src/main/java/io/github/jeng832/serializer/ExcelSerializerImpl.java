package io.github.jeng832.serializer;

import io.github.jeng832.annotation.ExcelProperty;
import io.github.jeng832.converter.ExcelConverterBuilder;
import io.github.jeng832.converter.HeaderDirection;
import io.github.jeng832.exception.ExcelConvertException;
import io.github.jeng832.exception.ExceptionMessages;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellAddress;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class ExcelSerializerImpl implements ExcelSerializer {

    private final String excelFilePath;
    private final String sheetName;
    private final boolean hasHeader;
    private final HeaderDirection headerDirection;
    private final CellAddress headerStartCell;
    private final CellAddress headerEndCell;
    private final CellAddress contentsStartCell;
    private final int linesOfUnit;

    public ExcelSerializerImpl(ExcelConverterBuilder builder) throws ExcelConvertException {
        if (builder.getExcelFilePath() == null || builder.getExcelFilePath().isEmpty()) {
            throw new ExcelConvertException(ExceptionMessages.EXCEL_CONVERT_EXCEPTION_FILE_PATH_NOT_EXIST);
        }
        if (builder.getSheetName() == null || builder.getSheetName().isEmpty()) {
            throw new ExcelConvertException(ExceptionMessages.EXCEL_CONVERT_EXCEPTION_SHEET_NOT_EXIST);
        }
        this.excelFilePath = builder.getExcelFilePath();
        this.sheetName = builder.getSheetName();
        this.hasHeader = builder.hasHeader();
        this.headerDirection = builder.getHeaderDirection();
        this.headerStartCell = new CellAddress(builder.getHeaderStartCell());
        this.headerEndCell = (builder.getHeaderEndCell() != null) ? new CellAddress(builder.getHeaderEndCell()) : null;
        this.linesOfUnit = (builder.getLinesOfUnit() != null) ? builder.getLinesOfUnit() : 1;
        this.contentsStartCell = (builder.getContentsStartCell() != null) ?
                new CellAddress(builder.getContentsStartCell()) :
                (this.hasHeader) ? 
                    ((this.headerEndCell != null) ? new CellAddress(this.headerEndCell.getRow() + 1, this.headerStartCell.getColumn()) : 
                    new CellAddress(this.headerStartCell.getRow() + 1, this.headerStartCell.getColumn())) :
                    this.headerStartCell;
    }

    @Override
    public <T> void serialize(List<T> objects, Class<T> clazz) throws ExcelConvertException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(sheetName);
            
            // ExcelProperty 어노테이션이 있는 필드들을 수집
            Map<String, Field> propertyFields = new LinkedHashMap<>();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(ExcelProperty.class)) {
                    ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                    propertyFields.put(excelProperty.value(), field);
                }
            }

            // 헤더 생성
            if (hasHeader) {
                Row headerRow = sheet.createRow(headerStartCell.getRow());
                int col = headerStartCell.getColumn();
                for (String header : propertyFields.keySet()) {
                    Cell cell = headerRow.createCell(col++);
                    cell.setCellValue(header);
                }
            }

            // 데이터 작성
            int rowNum = contentsStartCell.getRow();
            for (T object : objects) {
                Row row = sheet.createRow(rowNum++);
                int col = contentsStartCell.getColumn();
                
                for (Field field : propertyFields.values()) {
                    field.setAccessible(true);
                    Cell cell = row.createCell(col++);
                    setCellValue(cell, field.get(object));
                }
            }

            // 파일 저장
            try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
                workbook.write(fileOut);
            }
        } catch (IOException | IllegalAccessException e) {
            throw new ExcelConvertException(ExceptionMessages.EXCEL_CONVERT_EXCEPTION_CANNOT_WRITE_FILE, e);
        }
    }

    private void setCellValue(Cell cell, Object value) {
        if (value == null) {
            cell.setCellValue("");
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
            CellStyle dateStyle = cell.getSheet().getWorkbook().createCellStyle();
            dateStyle.setDataFormat(cell.getSheet().getWorkbook().createDataFormat().getFormat("yyyy-mm-dd"));
            cell.setCellStyle(dateStyle);
        } else if (value instanceof LocalDate) {
            cell.setCellValue(Date.from(((LocalDate) value).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            CellStyle dateStyle = cell.getSheet().getWorkbook().createCellStyle();
            dateStyle.setDataFormat(cell.getSheet().getWorkbook().createDataFormat().getFormat("yyyy-mm-dd"));
            cell.setCellStyle(dateStyle);
        } else if (value instanceof LocalDateTime) {
            cell.setCellValue(Date.from(((LocalDateTime) value).atZone(ZoneId.systemDefault()).toInstant()));
            CellStyle dateStyle = cell.getSheet().getWorkbook().createCellStyle();
            dateStyle.setDataFormat(cell.getSheet().getWorkbook().createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));
            cell.setCellStyle(dateStyle);
        } else if (value instanceof ZonedDateTime) {
            cell.setCellValue(Date.from(((ZonedDateTime) value).toInstant()));
            CellStyle dateStyle = cell.getSheet().getWorkbook().createCellStyle();
            dateStyle.setDataFormat(cell.getSheet().getWorkbook().createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));
            cell.setCellStyle(dateStyle);
        } else {
            cell.setCellValue(value.toString());
        }
    }
}
