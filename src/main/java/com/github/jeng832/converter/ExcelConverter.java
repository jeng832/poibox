package com.github.jeng832.converter;

import com.github.jeng832.annotation.ExcelProperty;
import com.github.jeng832.model.Excel;
import com.github.jeng832.model.ExcelPropertyMap;
import com.github.jeng832.model.ExcelSheet;
import com.github.jeng832.model.ExcelSheetHeader;
import org.apache.poi.ss.util.CellAddress;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class ExcelConverter {

    private final ExcelSheet sheet;
    private final boolean hasHeader;
    private final HeaderDirection headerDirection;
    private final CellAddress headerStartCell;
    private final CellAddress headerEndCell;
    private final int linesOfUnit;

    private ExcelConverter(Builder builder) throws IOException {
        this.sheet = Excel.of(builder.excelFilePath).getSheet(builder.sheetName);
        this.hasHeader = builder.hasHeader;
        this.headerDirection = builder.headerDirection;
        this.headerStartCell = builder.headerStartCell;
        this.headerEndCell = builder.headerEndCell;
        this.linesOfUnit = builder.linesOfUnit;

    }

    public static Builder builder() {
        return new Builder();
    }


    public <T> List<T> toObjects(Class<T> clazz) {
        // TODO header의 내용을 읽고 가장 아래 row를 header목록으로 저장
        // T 객체에 property어노테이션으로 설정한 부분을 가져온다.
        // heaer이름과 property 값이 같으면 값을 set 한다..
        // 1. header 분석
        // 2. content를 쪼갠다.
        // 3. 반복문을 돌면서 저장

        ExcelSheetHeader header = new ExcelSheetHeader(this.sheet, this.headerStartCell, this.headerEndCell);
        ExcelPropertyMap excelPropertyMap = new ExcelPropertyMap();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ExcelProperty.class)) {
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);

                String setterName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                Method setter;
                try {
                    setter = clazz.getDeclaredMethod(setterName, field.getType());
                } catch (NoSuchMethodException e) {
                    setter = null;
                }
                excelPropertyMap.add(field, excelProperty.value(), setter);
            }
        }

        return null;
    }

    public static class Builder {

        private String excelFilePath = null;
        private String sheetName = null;
        private boolean hasHeader = false;
        private HeaderDirection headerDirection = HeaderDirection.HORIZONTAL;
        private CellAddress headerStartCell = CellAddress.A1;
        private CellAddress headerEndCell = null;
        private int linesOfUnit = 1;

        public Builder excelFilePath(String excelFilePath) {
            this.excelFilePath = excelFilePath;
            return this;
        }

        public Builder sheetName(String sheetName) {
            this.sheetName = sheetName;
            return this;
        }

        public Builder hasHeader(boolean hasHeader) {
            this.hasHeader = hasHeader;
            return this;
        }

        public Builder headerDirection(HeaderDirection headerDirection) {
            this.headerDirection = headerDirection;
            return this;
        }

        public Builder headerStartCell(CellAddress headerStartCell) {
            this.headerStartCell = headerStartCell;
            return this;
        }

        public Builder headerEndCell(CellAddress headerEndCell) {
            this.headerEndCell = headerEndCell;
            return this;
        }

        public Builder linesOfUnit(int linesOfUnit) {
            this.linesOfUnit = linesOfUnit;
            return this;
        }

        public ExcelConverter build() throws IOException {
            return new ExcelConverter(this);
        }
    }
}
