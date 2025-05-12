package io.github.jeng832.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.util.CellAddress;

import io.github.jeng832.deserializer.ExcelDeserializer;
import io.github.jeng832.deserializer.ExcelDeserializerImpl;
import io.github.jeng832.exception.ExcelConvertException;
import io.github.jeng832.serializer.ExcelSerializer;
import io.github.jeng832.serializer.ExcelSerializerImpl;

class ExcelConverterBuilderImpl implements ExcelConverterBuilder {

    /**
     * 기본 설정값들
     */
    private static final HeaderDirection DEFAULT_HEADER_DIRECTION = HeaderDirection.HORIZONTAL;
    private static final CellAddress DEFAULT_HEADER_START_CELL = CellAddress.A1;
    private static final int DEFAULT_LINES_OF_UNIT = 1;

    private String excelFilePath = null;
    private String sheetName = null;
    private boolean hasHeader = true;
    private HeaderDirection headerDirection;
    private CellAddress headerStartCell;
    private CellAddress headerEndCell = null;
    private CellAddress contentsStartCell = null;
    private Integer linesOfUnit;

    // 필수 파라미터의 명시적 설정 여부를 추적하기 위한 플래그들
    // null 체크와 별개로 사용자가 해당 값을 명시적으로 설정했는지 확인합니다.
    // 이를 통해 기본값이 아닌 사용자가 의도적으로 설정한 값인지 구분할 수 있습니다.
    private boolean isExcelFilePathSet = false;
    private boolean isSheetNameSet = false;

    /**
     * 생성자에서 기본값들을 초기화합니다.
     */
    ExcelConverterBuilderImpl() {
        this.headerDirection = DEFAULT_HEADER_DIRECTION;
        this.headerStartCell = DEFAULT_HEADER_START_CELL;
        this.linesOfUnit = DEFAULT_LINES_OF_UNIT;
    }

    @Override
    public ExcelConverterBuilder excelFilePath(String excelFilePath) {
        if (excelFilePath == null || excelFilePath.trim().isEmpty()) {
            throw new IllegalArgumentException("엑셀 파일 경로는 비어있을 수 없습니다");
        }
        this.excelFilePath = excelFilePath;
        this.isExcelFilePathSet = true;
        return this;
    }

    @Override
    public ExcelConverterBuilder sheetName(String sheetName) {
        if (sheetName == null || sheetName.trim().isEmpty()) {
            throw new IllegalArgumentException("시트 이름은 비어있을 수 없습니다");
        }
        this.sheetName = sheetName;
        this.isSheetNameSet = true;
        return this;
    }

    @Override
    public ExcelConverterBuilder hasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
        return this;
    }

    /**
     * 헤더의 방향을 설정합니다.
     * 설정하지 않을 경우 기본값은 {@link HeaderDirection#HORIZONTAL} 입니다.
     */
    @Override
    public ExcelConverterBuilder headerDirection(HeaderDirection headerDirection) {
        if (headerDirection == null) {
            throw new IllegalArgumentException("헤더 방향은 null일 수 없습니다");
        }
        this.headerDirection = headerDirection;
        return this;
    }

    @Override
    public ExcelConverterBuilder headerStartCell(String cellAddress) {
        if (cellAddress == null || !isValidCellAddress(cellAddress)) {
            throw new IllegalArgumentException("유효하지 않은 셀 주소 형식입니다: " + cellAddress);
        }
        this.headerStartCell = new CellAddress(cellAddress);
        return this;
    }

    @Override
    public ExcelConverterBuilder headerEndCell(String cellAddress) {
        if (cellAddress == null || !isValidCellAddress(cellAddress)) {
            throw new IllegalArgumentException("유효하지 않은 셀 주소 형식입니다: " + cellAddress);
        }
        this.headerEndCell = new CellAddress(cellAddress);
        return this;
    }

    @Override
    public ExcelConverterBuilder contentsStartCell(String cellAddress) {
        if (cellAddress == null || !isValidCellAddress(cellAddress)) {
            throw new IllegalArgumentException("유효하지 않은 셀 주소 형식입니다: " + cellAddress);
        }
        this.contentsStartCell = new CellAddress(cellAddress);
        return this;
    }

    /**
     * 단위 데이터당 행 수를 설정합니다.
     * 설정하지 않을 경우 기본값은 1입니다.
     * 
     * @param linesOfUnit 단위 데이터당 행 수 (반드시 양수여야 함)
     * @throws IllegalArgumentException linesOfUnit이 0 이하인 경우
     */
    @Override
    public ExcelConverterBuilder linesOfUnit(int linesOfUnit) {
        if (linesOfUnit <= 0) {
            throw new IllegalArgumentException("linesOfUnit은 0보다 커야 합니다");
        }
        this.linesOfUnit = linesOfUnit;
        return this;
    }

    @Deprecated
    @Override
    public ExcelConverter build() throws ExcelConvertException {
        validateAll();
        initializePositions();
        return new ExcelConverterImpl(this);
    }

    @Override
    public ExcelSerializer buildSerializer() throws ExcelConvertException {
        validateAll();
        initializePositions();
        return new ExcelSerializerImpl(this);
    }

    @Override
    public ExcelDeserializer buildDeserializer() throws ExcelConvertException {
        validateAll();
        initializePositions();
        return new ExcelDeserializerImpl(this);
    }

    private void validateAll() throws ExcelConvertException {
        validateRequiredParameters();
        validateParameterDependencies();
    }

    private void validateRequiredParameters() throws ExcelConvertException {
        List<String> missingParams = new ArrayList<>();
        
        if (!isExcelFilePathSet || excelFilePath == null || excelFilePath.trim().isEmpty()) {
            missingParams.add("excelFilePath");
        }
        if (!isSheetNameSet || sheetName == null || sheetName.trim().isEmpty()) {
            missingParams.add("sheetName");
        }
        
        if (!missingParams.isEmpty()) {
            throw new ExcelConvertException(
                "필수 파라미터가 누락되었습니다: " + String.join(", ", missingParams)
            );
        }
    }

    private void validateParameterDependencies() throws ExcelConvertException {
        // 헤더 관련 설정 검증
        if (hasHeader) {
            if (headerStartCell == null) {
                throw new ExcelConvertException("헤더가 있는 경우 headerStartCell은 필수입니다");
            }
            
            if (headerEndCell != null) {
                if (headerStartCell.getRow() > headerEndCell.getRow() || 
                    headerStartCell.getColumn() > headerEndCell.getColumn()) {
                    throw new ExcelConvertException("headerEndCell은 headerStartCell보다 뒤에 위치해야 합니다");
                }
            }
        }

        // 콘텐츠 시작 위치 검증
        if (contentsStartCell != null && hasHeader && headerEndCell != null) {
            if (contentsStartCell.getRow() <= headerEndCell.getRow()) {
                throw new ExcelConvertException("콘텐츠 시작 위치는 헤더 끝 위치보다 뒤에 있어야 합니다");
            }
        }
    }

    /**
     * 헤더와 콘텐츠의 위치를 초기화합니다.
     * 1. 헤더가 있고 끝 셀이 지정되지 않은 경우, 시작 셀과 동일하게 설정
     * 2. 콘텐츠 시작 위치가 지정되지 않은 경우, 헤더 다음 행의 동일한 열에 위치하도록 설정
     */
    private void initializePositions() {
        if (hasHeader && headerEndCell == null) {
            headerEndCell = headerStartCell;
        }
        
        if (contentsStartCell == null && hasHeader && headerEndCell != null) {
            contentsStartCell = new CellAddress(headerEndCell.getRow() + 1, headerStartCell.getColumn());
        }
    }

    private boolean isValidCellAddress(String address) {
        try {
            new CellAddress(address);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String getExcelFilePath() {
        return excelFilePath;
    }

    @Override
    public String getSheetName() {
        return sheetName;
    }

    @Override
    public boolean hasHeader() {
        return hasHeader;
    }

    @Override
    public HeaderDirection getHeaderDirection() {
        return headerDirection;
    }

    @Override
    public String getHeaderStartCell() {
        if (headerStartCell == null) return null;
        return headerStartCell.formatAsString();
    }

    @Override
    public String getHeaderEndCell() {
        if (headerEndCell == null) return null;
        return headerEndCell.formatAsString();
    }

    @Override
    public String getContentsStartCell() {
        if (contentsStartCell == null) return null;
        return contentsStartCell.formatAsString();
    }

    @Override
    public Integer getLinesOfUnit() {
        return linesOfUnit;
    }
}
