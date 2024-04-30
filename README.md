# POI-Box
POI-Box는 Apache POI를 이용하여 Excel 파일을 Java 객체로 손쉽게 변환해주는 Java 라이브러리이다. POI-Box는 Java 1.8 이상이 필요하다.

## Building jar file
```shell
mvn package
```

## How to use
xml 파일의 "sheet1" 시트의 A1 ~ G1 셀 사이에 있는 헤더를 이용하는 표를 객체화 하기 위해서는 아래의 예시 코드를 참고하면 된다.
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
