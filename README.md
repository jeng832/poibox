# POI-Box
POI-Box is a Java library that easily converts Excel files into Java objects using Apache POI. POI-Box requires Java 1.8 or higher.

## Building jar file
```shell
mvn package
```

## How to use
This is an example of changing a table using the header between cells A1 to G1 of the "sheet1" of an Excel file into an object.
Specifies which column values are to be stored in the field of the object where the table in the sheet of the Excel file will be saved. The value is specified using the @ExcelProperty annotation.
The value of @ExcelProperty uses the column name of the table in the Excel file.

Assume that you want to change the table below into an object.

| number | 번호 | 이름      | 날짜           | formula | formula2 | date_time             |
|--------|----|---------|--------------|---------|----------|-----------------------|
| 1      | 11 | 이순신     | 2022. 10. 19 | 12      | #DIV/0!  | 2021. 01. 01 13:00:24 |
| 2      | 12 | James   | 2022. 10. 20 | 14      | 14.000   | 1983. 02. 24 1:00:24  |
| 3      | 13 | 타이거JK   | 2022. 10. 21 | 16      | 8.000    | 2021. 1. 21 0:00:00   |
| 4      | 14 | 특_수_문_자 | 2022. 10. 22 | 18      | 6.000    | 2021. 01. 04 13:00:24 |

First, use @ExcelProperty to specify the table column in which the field will be saved.
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

It can be saved as a list of objects through ExcelConverter. Obtain a builder through ExcelConverterBuilderFactory, and specify the file location, sheet information, and header-related information through the builder.
```java
String filePath = ... /* file path of the xml file */;

ExcelConverter converter = ExcelConverterBuilderFactory.create()
        .excelFilePath(filePath)
        .sheetName("sheet1")
        .hasHeader(true)
        .headerStartCell("A1")
        .headerEndCell("G1")
        .build();

List<ExcelObject> objects = converter.toObjects(ExcelObject.class);
```
Refer to the table below for a description of each variable in builder.

| Field            | Required | Default                   | Description                                                                            |
|------------------|----------|---------------------------|----------------------------------------------------------------------------------------|
| excelFilePath    | Required |                           | The location where the Excel file exists.                                              |
| sheetName        | Required |                           | The name of the sheet containing the table to be converted into an object.              |
| hasHeader        | Optional | true                      | Indicates whether the table has a header.                                              |
| headerStartCell  | Optional | A1                        | The top-left cell address of the header.                                                |
| headerEndCell    | Optional |                           | The bottom-right cell address of the header.                                             |
| linesOfUnit      | Optional |                           | The number of lines in the table representing a single unit of information. Must be the same as the number of lines calculated between headerStartCell and headerEndCell. |
| contentsStartCell| Optional | The cell below the bottom-left cell of the header. | The cell where the content begins.                                                     |
