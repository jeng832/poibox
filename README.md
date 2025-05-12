[![Maven Central](https://img.shields.io/maven-central/v/io.github.jeng832/poibox.svg)](https://search.maven.org/artifact/io.github.jeng832/poibox)

# POI-Box
POI-Box is a Java library that simplifies the conversion between Excel files and Java objects using Apache POI. POI-Box requires Java 1.8 or higher.

## Requirements

- Java 1.8 or higher

## Dependency

Add the following to your `pom.xml`:
```xml
<dependency>
  <groupId>io.github.jeng832</groupId>
  <artifactId>poibox</artifactId>
  <version>1.0.2</version>
</dependency>
```
> **Note:** Apache POI and other dependencies are included automatically. You do not need to add them separately.

## How to Build
```shell
mvn package
```

## How to Use

### Convert Excel File to Java Objects (Deserialization)

This is an example of converting a table from an Excel file into objects. The table is located in "sheet1" and uses headers specified between cells A1 and G1.
To specify which column value should be stored in which field of the Java object, use the `@ExcelProperty` annotation. The value of `@ExcelProperty` should match the header name in the Excel table.

Let's assume we want to convert the following table into objects:

| number | 번호 | 이름      | 날짜           | formula | formula2 | date_time             |
|--------|----|---------|--------------|---------|----------|-----------------------|
| 1      | 11 | 이순신     | 2022. 10. 19 | 12      | #DIV/0!  | 2021. 01. 01 13:00:24 |
| 2      | 12 | James   | 2022. 10. 20 | 14      | 14.000   | 1983. 02. 24 1:00:24  |
| 3      | 13 | 타이거JK   | 2022. 10. 21 | 16      | 8.000    | 2021. 1. 21 0:00:00   |
| 4      | 14 | 특_수_문_자 | 2022. 10. 22 | 18      | 6.000    | 2021. 01. 04 13:00:24 |

First, use `@ExcelProperty` to specify the header corresponding to each field.
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

You can convert the Excel data into a list of objects using `ExcelDeserializer`. Obtain a builder via `ExcelConverterBuilderFactory`, and then specify the file path, sheet information, and header-related details through the builder.

```java
String filePath = ... /* Path to the Excel file */;

ExcelDeserializer deserializer = ExcelConverterBuilderFactory.create()
        .excelFilePath(filePath)
        .sheetName("sheet1")
        .hasHeader(true) // Default is true
        .headerStartCell("A1") // Default is A1
        .headerEndCell("G1") // Can be omitted if the header is a single row
        .buildDeserializer();

List<ExcelObject> objects = deserializer.deserialize(ExcelObject.class);
```

### Convert Java Objects to Excel File (Serialization)

This is an example of converting a list of Java objects into an Excel file. Use the `@ExcelProperty` annotation on the object's fields to specify the Excel headers.

```java
List<ExcelObject> objectsToSerialize = ... /* List of objects to serialize */;
String outputFilePath = ... /* Path for the output Excel file */;

ExcelSerializer serializer = ExcelConverterBuilderFactory.create()
        .excelFilePath(outputFilePath)
        .sheetName("Sheet1") // Default is Sheet0
        .hasHeader(true) // Default is true
        .headerStartCell("A1") // Default is A1
        .buildSerializer();

serializer.serialize(objectsToSerialize, ExcelObject.class);
```

### Builder Options

The following options can be configured using the builder obtained from `ExcelConverterBuilderFactory.create()`.

| Option              | Required | Default                         | Description                                                                                   |
|---------------------|----------|---------------------------------|-----------------------------------------------------------------------------------------------|
| `excelFilePath`     | Required |                                 | Path to the Excel file (input for deserialization, output for serialization)                   |
| `sheetName`         | Required |                                 | Name of the target sheet                                                                      |
| `hasHeader`         | Optional | `true`                          | Indicates whether the table has a header                                                       |
| `headerStartCell`   | Optional | `A1`                            | Starting cell address of the header                                                            |
| `headerEndCell`     | Optional |                                 | Ending cell address of the header (specify if the header spans multiple rows/columns)         |
| `contentsStartCell` | Optional | Cell below/right of header      | Starting cell address of the content (auto-detected if omitted)                               |
| `linesOfUnit`       | Optional | Header height/width             | Number of rows/columns representing a single object in the content (for complex structures)   |

> **Note:** Most options have reasonable defaults. For multi-line headers or complex tables, specify `headerEndCell`, `contentsStartCell`, and `linesOfUnit` as needed.
