# POI-Box
POI-Box는 Apache POI를 이용하여 Excel 파일을 Java 객체로 손쉽게 변환해주는 Java 라이브러리이다. POI-Box는 Java 1.8 이상이 필요하다.

## Building jar file
```shell
mvn package
```

## How to use
Excel 파일의 "sheet1" 시트의 A1 ~ G1 셀 사이에 있는 헤더를 이용하는 표를 객체화 하기 위한 예시이다.
Excel 파일의 시트에 있는 표를 저장할 객체의 필드에 어떤 열의 값을 저장할지 지정한다. 값의 지정은 @ExcelProperty 어노테이션을 이용한다.
@ExcelProperty의 value는 Excel 파일의 표의 column 이름을 사용한다.

아래와 같은 표를 객체로 변경한다고 가정하자

| number | 번호 | 이름      | 날짜           | formula | formula2 | date_time             |
|--------|----|---------|--------------|---------|----------|-----------------------|
| 1      | 11 | 이순신     | 2022. 10. 19 | 12      | #DIV/0!  | 2021. 01. 01 13:00:24 |
| 2      | 12 | James   | 2022. 10. 20 | 14      | 14.000   | 1983. 02. 24 1:00:24  |
| 3      | 13 | 타이거JK   | 2022. 10. 21 | 16      | 8.000    | 2021. 1. 21 0:00:00   |
| 4      | 14 | 특_수_문_자 | 2022. 10. 22 | 18      | 6.000    | 2021. 01. 04 13:00:24 |

먼저 표를 저장할 ExcelObject 객체로 정의 한다. 개별 column과 객체의 멤버는 @ExcelProperty를 이용한다.
```java
public class ExcelObject {

    @ExcelProperty("number")
    private Integer number;

    @ExcelProperty("번호")
    private int num;

    @ExcelProperty("이름")
    private String name;

    @ExcelProperty("날짜")
    private LocalDate time;

    @ExcelProperty("formula")
    private Integer sum;

    @ExcelProperty("formula2")
    private String divStr;

    @ExcelProperty("formula2")
    private Float divFloat;

    @ExcelProperty("date_time")
    private LocalDateTime dateTime;
}
```

ExcelConverter는 지정한 Excel 파일을 객체로 변경하기 위한 모듈이다. builder를 통해서 생성되며, 파일의 위치와 시트정보 그리고 헤더 관련 정보를 지정한다.
builder를 통해 생성된 ExcelConverter를 통해서 객체의 목록으로 저장할 수 있다.
```java
String filePath = ... /* file path of the xml file */;

ExcelConverter converter = ExcelConverter.builder()
        .excelFilePath(filePath)
        .sheetName("sheet1")
        .hasHeader(true)
        .headerStartCell(new CellAddress("A1"))
        .headerEndCell(new CellAddress("G1"))
        .build();

List<ExcelObject> objects = converter.toObjects(ExcelObject.class);
```
builder 에서 각 변수에 대한 설명은 아래 표를 참조한다.

| builder 항목        | 필수 여부 | 기본값                    | 설명                                                                        |
|-------------------|-------|------------------------|---------------------------------------------------------------------------|
| excelFilePath     | 필수    |                        | Excel 파일이 존재하는 위치                                                         |
| sheetName         | 필수    |                        | 객체로 변경시키고자 하는 표가 있는 시트의 이름                                                |
| hasHeader         | 비필수   | true                   | 표에 헤더가 존재하는지 여부                                                           |
| headerStartCell   | 비필수   | A1                     | 헤더의 가장 좌측 제일 상단 셀 주소                                                      |
| headerEndCell     | 비필수   |                        | 헤더의 가장 우측 제일 하단 셀 주소                                                      |
| linesOfUnit       | 비필수   |                        | 표 내용에서 단일 정보 단위의 줄 갯수headerStartCell과 headerEndCell로 계산 된 줄수와 반드시 같아야 한다. |
| contentsStartCell | 비필수   | 헤더의 가장 좌측 제일 하단 셀 아래 셀 | 내용이 시작하는 셀                                                                |