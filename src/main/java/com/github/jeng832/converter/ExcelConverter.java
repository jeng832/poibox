package com.github.jeng832.converter;

import com.github.jeng832.annotation.ExcelProperty;
import com.github.jeng832.exception.ExcelConvertException;
import com.github.jeng832.exception.ExceptionMessages;
import com.github.jeng832.model.Excel;
import com.github.jeng832.model.ExcelPropertyMap;
import com.github.jeng832.model.ExcelSheet;
import org.apache.poi.ss.util.CellAddress;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class ExcelConverter {

    private final ExcelSheet sheet;
    private final boolean hasHeader;
    private final HeaderDirection headerDirection;
    private final CellAddress headerStartCell;
    private final CellAddress headerEndCell;
    private final CellAddress contentsStartCell;
    private final int linesOfUnit;

    private ExcelConverter(Builder builder) throws ExcelConvertException {
        if (builder.excelFilePath == null || builder.excelFilePath.isEmpty()) {
            throw new ExcelConvertException(ExceptionMessages.EXCEL_CONVERT_EXCEPTION_FILE_PATH_NOT_EXIST);
        }
        if (builder.sheetName == null || builder.sheetName.isEmpty()) {
            throw new ExcelConvertException(ExceptionMessages.EXCEL_CONVERT_EXCEPTION_SHEET_NOT_EXIST);
        }
        if (builder.hasHeader) {
            this.sheet = Excel.of(builder.excelFilePath).getSheetWithHeader(builder.sheetName, builder.headerStartCell, builder.headerEndCell);
        } else {
            this.sheet = Excel.of(builder.excelFilePath).getSheet(builder.sheetName);
        }
        this.hasHeader = builder.hasHeader;
        this.headerDirection = builder.headerDirection;
        this.headerStartCell = builder.headerStartCell;
        this.headerEndCell = builder.headerEndCell;
        this.linesOfUnit = (builder.linesOfUnit != null) ? builder.linesOfUnit : sheet.getHeaderHeight();
        if (this.sheet.getHeaderHeight() != this.linesOfUnit) {
            throw new ExcelConvertException("The lines of the content unit must be same with the height of the header");
        }
        this.contentsStartCell = (builder.contentsStartCell != null) ?
                builder.contentsStartCell :
                (headerEndCell != null) ? new CellAddress(headerEndCell.getRow() + 1, headerStartCell.getColumn()) : new CellAddress(headerStartCell.getRow() + 1, headerStartCell.getColumn());
    }

    public static Builder builder() {
        return new Builder();
    }


    public <T> List<T> toObjects(Class<T> clazz) throws ExcelConvertException {
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

        int contentStartRow = this.contentsStartCell.getRow();
        int contentEndRow = sheet.getLastNonEmptyRowNumber();
        int contentStartCol = this.contentsStartCell.getColumn();

        List<T> objects = new ArrayList<>();
        for (int i = contentStartRow; i <= contentEndRow - linesOfUnit + 1; i += linesOfUnit) {
            T object;
            try {
                object = clazz.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new ExcelConvertException(ExceptionMessages.EXCEL_CONVERT_EXCEPTION_CANNOT_CONSTRUCT_OBJECT , e);
            }
            for (int j = 0; j < sheet.getHeaderHeight(); j++) {
                for (int k = 0; k < sheet.getHeaderWidth(); k++) {
                    CellAddress cellAddress = new CellAddress(i + j, contentStartCol + k);

                    String headerValue = sheet.getHeaderValue(j, k);
                    Set<Field> fields = excelPropertyMap.getFields();
                    for (Field field : fields) {
                        String value = excelPropertyMap.getValue(field).orElse(null);
                        if (headerValue != null && headerValue.equals(value)) {
                            Optional<Method> optSetter = excelPropertyMap.getSetter(field);
                            if (optSetter.isPresent()) {
                                Method setter = optSetter.get();
                                setValue(object, setter, cellAddress);
                            } else {
                                setValue(object, field, cellAddress);
                            }
                        }
                    }
                }
            }

            objects.add(object);
        }

        return objects;
    }

    private <T> void setValue(T object, Method setter, CellAddress cellAddress) throws ExcelConvertException {
        Class<?>[] parameterTypes = setter.getParameterTypes();
        Class<?> parameterType = parameterTypes[0];
        if (sheet.isFormulaCell(cellAddress)) {
            if (parameterType.equals(String.class) && sheet.isStringFormulaCell(cellAddress)) {
                invokeSetter(setter, object, sheet.getValueAsString(cellAddress));
            } else if (sheet.isDateFormulaCell(cellAddress)) {
                if (parameterType.equals(Date.class)) {
                    invokeSetter(setter, object, sheet.getValueAsDate(cellAddress));
                } else if (parameterType.equals(LocalDate.class)) {
                    invokeSetter(setter, object, sheet.getValueAsDate(cellAddress).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                } else if (parameterType.equals(LocalDateTime.class)) {
                    invokeSetter(setter, object, sheet.getValueAsDate(cellAddress).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                } else if (parameterType.equals(ZonedDateTime.class)) {
                    invokeSetter(setter, object, sheet.getValueAsDate(cellAddress).toInstant().atZone(ZoneId.systemDefault()));
                }
            } else if (sheet.isNumberFormulaCell(cellAddress)) {
                if (parameterType.equals(Double.TYPE) || parameterType.equals(Double.class)) {
                    invokeSetter(setter, object, sheet.getValueAsDouble(cellAddress));
                } else if (parameterType.equals(Float.TYPE) || parameterType.equals(Float.class)) {
                    invokeSetter(setter, object, sheet.getValueAsDouble(cellAddress).floatValue());
                } else if (parameterType.equals(Integer.TYPE) || parameterType.equals(Integer.class)) {
                    invokeSetter(setter, object, sheet.getValueAsInteger(cellAddress));
                } else if (parameterType.equals(Long.TYPE) || parameterType.equals(Long.class)) {
                    invokeSetter(setter, object, sheet.getValueAsLong(cellAddress));
                }
            }
        } else if (sheet.isStringCell(cellAddress)) {
            invokeSetter(setter, object, sheet.getValueAsString(cellAddress));
        } else if (sheet.isDateCell(cellAddress)) {
            if (parameterType.equals(Date.class)) {
                invokeSetter(setter, object, sheet.getValueAsDate(cellAddress));
            } else if (parameterType.equals(LocalDate.class)) {
                invokeSetter(setter, object, sheet.getValueAsDate(cellAddress).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } else if (parameterType.equals(LocalDateTime.class)) {
                invokeSetter(setter, object, sheet.getValueAsDate(cellAddress).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            } else if (parameterType.equals(ZonedDateTime.class)) {
                invokeSetter(setter, object, sheet.getValueAsDate(cellAddress).toInstant().atZone(ZoneId.systemDefault()));
            }
        } else if (sheet.isNumberCell(cellAddress)) {
            if (parameterType.equals(Double.TYPE) || parameterType.equals(Double.class)) {
                invokeSetter(setter, object, sheet.getValueAsDouble(cellAddress));
            } else if (parameterType.equals(Float.TYPE) || parameterType.equals(Float.class)) {
                invokeSetter(setter, object, sheet.getValueAsDouble(cellAddress).floatValue());
            } else if (parameterType.equals(Integer.TYPE) || parameterType.equals(Integer.class)) {
                invokeSetter(setter, object, sheet.getValueAsInteger(cellAddress));
            } else if (parameterType.equals(Long.TYPE) || parameterType.equals(Long.class)) {
                invokeSetter(setter, object, sheet.getValueAsLong(cellAddress));
            }
        } else if (sheet.isBooleanCell(cellAddress)) {
            invokeSetter(setter, object, sheet.getValueAsBoolean(cellAddress));
        }
    }

    private void invokeSetter(Method setter, Object object, Object value) throws ExcelConvertException {
        try {
            setter.invoke(object, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ExcelConvertException(ExceptionMessages.EXCEL_CONVERT_EXCEPTION_CANNOT_SET_VALUE, e);
        }
    }

    private <T> void setValue(T object, Field field, CellAddress cellAddress) throws ExcelConvertException {
        field.setAccessible(true);
        Class<?> fieldType = field.getType();
        if (sheet.isFormulaCell(cellAddress)) {
            if (fieldType.equals(String.class) && sheet.isStringFormulaCell(cellAddress)) {
                setValue(field, object, sheet.getValueAsString(cellAddress));
            } else if (sheet.isDateFormulaCell(cellAddress)) {
                if (fieldType.equals(Date.class)) {
                    setValue(field, object, sheet.getValueAsDate(cellAddress));
                } else if (fieldType.equals(LocalDate.class)) {
                    setValue(field, object, sheet.getValueAsDate(cellAddress).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                } else if (fieldType.equals(LocalDateTime.class)) {
                    setValue(field, object, sheet.getValueAsDate(cellAddress).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                } else if (fieldType.equals(ZonedDateTime.class)) {
                    setValue(field, object, sheet.getValueAsDate(cellAddress).toInstant().atZone(ZoneId.systemDefault()));
                }
            } else if (sheet.isNumberFormulaCell(cellAddress)) {
                if (fieldType.equals(Double.TYPE) || fieldType.equals(Double.class)) {
                    setValue(field, object, sheet.getValueAsDouble(cellAddress));
                } else if (fieldType.equals(Float.TYPE) || fieldType.equals(Float.class)) {
                    setValue(field, object, sheet.getValueAsDouble(cellAddress).floatValue());
                } else if (fieldType.equals(Integer.TYPE) || fieldType.equals(Integer.class)) {
                    setValue(field, object, sheet.getValueAsInteger(cellAddress));
                } else if (fieldType.equals(Long.TYPE) || fieldType.equals(Long.class)) {
                    setValue(field, object, sheet.getValueAsLong(cellAddress));
                }
            }
        } else if (sheet.isStringCell(cellAddress)) {
            setValue(field, object, sheet.getValueAsString(cellAddress));
        } else if(sheet.isDateCell(cellAddress)) {
            if (fieldType.equals(Date.class)) {
                setValue(field, object, sheet.getValueAsDate(cellAddress));
            } else if (fieldType.equals(LocalDate.class)) {
                setValue(field, object, sheet.getValueAsDate(cellAddress).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } else if (fieldType.equals(LocalDateTime.class)) {
                setValue(field, object, sheet.getValueAsDate(cellAddress).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            } else if (fieldType.equals(ZonedDateTime.class)) {
                setValue(field, object, sheet.getValueAsDate(cellAddress).toInstant().atZone(ZoneId.systemDefault()));
            }

        } else if (sheet.isNumberCell(cellAddress)) {
            if (fieldType.equals(Double.TYPE) || fieldType.equals(Double.class)) {
                setValue(field, object, sheet.getValueAsDouble(cellAddress));
            } else if (fieldType.equals(Float.TYPE) || fieldType.equals(Float.class)) {
                setValue(field, object, sheet.getValueAsDouble(cellAddress).floatValue());
            } else if (fieldType.equals(Integer.TYPE) || fieldType.equals(Integer.class)) {
                setValue(field, object, sheet.getValueAsInteger(cellAddress));
            } else if (fieldType.equals(Long.TYPE) || fieldType.equals(Long.class)) {
                setValue(field, object, sheet.getValueAsLong(cellAddress));
            }
        } else if (sheet.isBooleanCell(cellAddress)) {
            setValue(field, object, sheet.getValueAsBoolean(cellAddress));
        }
    }

    private void setValue(Field field, Object object, Object value) throws ExcelConvertException {
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new ExcelConvertException(ExceptionMessages.EXCEL_CONVERT_EXCEPTION_CANNOT_SET_VALUE, e);
        }
    }

    public static class Builder {

        private String excelFilePath = null;
        private String sheetName = null;
        private boolean hasHeader = true;
        private HeaderDirection headerDirection = HeaderDirection.HORIZONTAL;
        private CellAddress headerStartCell = CellAddress.A1;
        private CellAddress headerEndCell = null;
        private CellAddress contentsStartCell = null;
        private Integer linesOfUnit = null;

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

        public Builder headerStartCell(String cellAddress) {
            this.headerStartCell = new CellAddress(cellAddress);
            return this;
        }

        public Builder headerEndCell(String cellAddress) {
            this.headerEndCell = new CellAddress(cellAddress);
            return this;
        }

        public Builder contentsStartCell(String cellAddress) {
            this.contentsStartCell = new CellAddress(cellAddress);
            return this;
        }

        public Builder linesOfUnit(int linesOfUnit) {
            this.linesOfUnit = linesOfUnit;
            return this;
        }

        public ExcelConverter build() throws ExcelConvertException {
            return new ExcelConverter(this);
        }
    }
}
