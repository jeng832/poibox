package io.github.jeng832.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.poi.ss.util.CellAddress;
import org.junit.jupiter.api.Test;

class ExcelSheetHeaderCellTest {

    @Test
    void getValue_shouldReturnOwnValue_whenNotMerged() {
        // Given: 병합되지 않은 셀
        CellAddress address = new CellAddress("A1");
        ExcelSheetHeaderCell cell = new ExcelSheetHeaderCell(address, "Value A1");

        // When: getValue 호출
        String value = cell.getValue();

        // Then: 자신의 값을 반환해야 함
        assertEquals("Value A1", value);
    }

    @Test
    void getValue_shouldReturnRepresentativeValue_whenMerged() {
        // Given: 병합된 셀 (A1이 대표, B1은 A1을 참조)
        CellAddress addressA1 = new CellAddress("A1");
        CellAddress addressB1 = new CellAddress("B1");

        ExcelSheetHeaderCell representativeCell = new ExcelSheetHeaderCell(addressA1, "Merged Value");
        ExcelSheetHeaderCell mergedCell = new ExcelSheetHeaderCell(addressB1); // 값 없이 생성
        mergedCell.makeMergedCell(representativeCell); // 대표 셀 설정

        // When: 병합된 셀(B1)의 getValue 호출
        String value = mergedCell.getValue();

        // Then: 대표 셀(A1)의 값을 반환해야 함
        assertEquals("Merged Value", value);
    }

    @Test
    void getValue_shouldReturnOwnValue_whenIsRepresentativeCell() {
        // Given: 병합 영역의 대표 셀 (자신이 대표)
        CellAddress addressA1 = new CellAddress("A1");
        ExcelSheetHeaderCell representativeCell = new ExcelSheetHeaderCell(addressA1, "Representative Value");
        // makeMergedCell은 호출되지 않음 (또는 다른 셀이 이 셀을 참조)

        // When: 대표 셀의 getValue 호출
        String value = representativeCell.getValue();

        // Then: 자신의 값을 반환해야 함
        assertEquals("Representative Value", value);
    }
    
    @Test
    void getValue_shouldReturnNull_whenNotMergedAndValueIsNull() {
        // Given: 병합되지 않았고, 생성 시 값도 null로 전달된 셀
        CellAddress addressC1 = new CellAddress("C1");
        ExcelSheetHeaderCell cellWithValueNull = new ExcelSheetHeaderCell(addressC1); // 값으로 null 전달 (두 번째 생성자 사용)

        // When: 해당 셀의 getValue 호출
        String value = cellWithValueNull.getValue();

        // Then: null을 반환해야 함
        assertNull(value);
    }
} 