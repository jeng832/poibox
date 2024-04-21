package com.github.jeng832.converter;

import com.github.jeng832.model.ExcelSheet;
import org.apache.poi.ss.util.CellAddress;

public class ExcelConverter<T> {

    private final ExcelSheet sheet;
    private final boolean hasHeader;
    private final HeaderDirection headerDirection;
    private final CellAddress headerStartCell;
    private final CellAddress headerEndCell;

    ExcelConverter(ExcelSheet sheet, boolean hasHeader, HeaderDirection headerDirection,
                   CellAddress headerStartCell, CellAddress headerEndCell) {
        this.sheet = sheet;
        this.hasHeader = hasHeader;
        this.headerDirection = headerDirection;
        this.headerStartCell = headerStartCell;
        this.headerEndCell = headerEndCell;
    }

    public static ExcelConverterBuilder builder() {
        return new ExcelConverterBuilder();
    }

    public T toObject() {
        // TODO header의 내용을 읽고 가장 아래 row를 header목록으로 저장
        // T 객체에 property어노테이션으로 설정한 부분을 가져온다.
        // heaer이름과 property 값이 같으면 값을 set 한다..
        return null;
    }
}
