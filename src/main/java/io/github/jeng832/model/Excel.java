package io.github.jeng832.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;

import io.github.jeng832.exception.ExcelConvertException;
import io.github.jeng832.exception.ExceptionMessages;

public class Excel {

    private final Workbook workbook;

    private Excel(Workbook workbook) {
        this.workbook = workbook;
    }

    public static Excel of(String fileName) throws ExcelConvertException {
        try {
            return new Excel(WorkbookFactory.create(Files.newInputStream(Paths.get(fileName))));
        } catch (IOException e) {
            throw new ExcelConvertException(ExceptionMessages.EXCEL_CONVERT_EXCEPTION_CANNOT_LOAD_FILE, e);
        }
    }

    public ExcelSheet getSheet(String name) {
        return new ExcelSheet(workbook.getSheet(name));
    }

    public ExcelSheet getSheetWithHeader(String name, String headerStartCell, String headerEndCell) {
        return new ExcelSheet(workbook.getSheet(name), new CellAddress(headerStartCell), new CellAddress(headerEndCell));
    }

}
