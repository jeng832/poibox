[![Maven Central](https://img.shields.io/maven-central/v/io.github.jeng832/poibox.svg)](https://search.maven.org/artifact/io.github.jeng832/poibox)

# POI-Box
POI-Box는 Apache POI를 이용하여 Excel 파일을 Java 객체로 손쉽게 변환하거나 Java 객체를 Excel 파일로 손쉽게 변환해주는 Java 라이브러리이다. POI-Box는 Java 1.8 이상이 필요하다.

## 요구사항

- Java 1.8 이상

## 의존성 추가

`pom.xml`에 다음을 추가하세요:
```xml
<dependency>
  <groupId>io.github.jeng832</groupId>
  <artifactId>poibox</artifactId>
  <version>1.0.2</version>
</dependency>
```
> **참고:** Apache POI 등 필요한 라이브러리는 poibox에 포함되어 있으므로 별도로 추가할 필요가 없습니다.

## 빌드 방법
```shell
mvn package
```

## 사용 방법

### Excel 파일을 Java 객체로 변환 (역직렬화)

Excel 파일의 "sheet1" 시트의 A1 ~ G1 셀 사이에 있는 헤더를 이용하는 표를 객체화 하기 위한 예시이다.
Excel 파일의 시트에 있는 표를 저장할 객체의 필드에 어떤 열의 값을 저장할지 지정한다. 값의 지정은 `@ExcelProperty` 어노테이션을 이용한다.
`@ExcelProperty`의 값은 Excel 파일 표의 헤더 이름을 사용한다.

아래와 같은 표를 객체로 변경한다고 가정하자

| number | 번호 | 이름      | 날짜           | formula | formula2 | date_time             |
|--------|----|---------|--------------|---------|----------|-----------------------|
| 1      | 11 | 이순신     | 2022. 10. 19 | 12      | #DIV/0!  | 2021. 01. 01 13:00:24 |
| 2      | 12 | James   | 2022. 10. 20 | 14      | 14.000   | 1983. 02. 24 1:00:24  |
| 3      | 13 | 타이거JK   | 2022. 10. 21 | 16      | 8.000    | 2021. 1. 21 0:00:00   |
| 4      | 14 | 특_수_문_자 | 2022. 10. 22 | 18      | 6.000    | 2021. 01. 04 13:00:24 |

먼저 `@ExcelProperty`를 이용하여 필드가 저장할 표의 헤더를 지정한다.
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

`ExcelDeserializer`를 통해 객체의 목록으로 변환할 수 있다. `ExcelConverterBuilderFactory`를 통해서 빌더를 얻고, 빌더를 통해서 파일의 위치, 시트 정보, 헤더 관련 정보를 지정한다.

```java
String filePath = ... /* Excel 파일 경로 */;

ExcelDeserializer deserializer = ExcelConverterBuilderFactory.create()
        .excelFilePath(filePath)
        .sheetName("sheet1")
        .hasHeader(true) // 기본값은 true
        .headerStartCell("A1") // 기본값은 A1
        .headerEndCell("G1") // 헤더가 한 줄이면 생략 가능
        .buildDeserializer();

List<ExcelObject> objects = deserializer.deserialize(ExcelObject.class);
```

### Java 객체를 Excel 파일로 변환 (직렬화)

Java 객체 리스트를 Excel 파일로 변환하는 예시이다. 객체의 필드에 `@ExcelProperty` 어노테이션을 사용하여 Excel 헤더를 지정한다.

```java
List<ExcelObject> objectsToSerialize = ... /* 직렬화할 객체 리스트 */;
String outputFilePath = ... /* 출력할 Excel 파일 경로 */;

ExcelSerializer serializer = ExcelConverterBuilderFactory.create()
        .excelFilePath(outputFilePath)
        .sheetName("Sheet1") // 기본값은 Sheet0
        .hasHeader(true) // 기본값은 true
        .headerStartCell("A1") // 기본값은 A1
        .buildSerializer();

serializer.serialize(objectsToSerialize, ExcelObject.class);
```

### 빌더 옵션

`ExcelConverterBuilderFactory.create()`를 통해 얻은 빌더에서 설정 가능한 옵션은 다음과 같다.

| 옵션                | 필수 여부 | 기본값                            | 설명                                                                                           |
|---------------------|-----------|-----------------------------------|----------------------------------------------------------------------------------------------|
| `excelFilePath`     | 필수      |                                   | Excel 파일 경로 (역직렬화 시 입력 파일, 직렬화 시 출력 파일)                                                 |
| `sheetName`         | 필수      |                                   | 대상 시트 이름                                                                                 |
| `hasHeader`         | 선택      | `true`                            | 표에 헤더 존재 여부                                                                            |
| `headerStartCell`   | 선택      | `A1`                              | 헤더 시작 셀 주소                                                                              |
| `headerEndCell`     | 선택      |                                   | 헤더 종료 셀 주소 (헤더가 여러 줄/열일 경우 지정)                                                  |
| `contentsStartCell` | 선택      | 헤더 바로 아래/오른쪽 셀             | 내용 시작 셀 주소 (헤더와 내용 사이에 빈 줄/열이 있을 경우 지정, 생략 시 자동 감지)                | 
| `linesOfUnit`       | 선택      | 헤더 높이/너비 (헤더 방향에 따라 다름) | 내용에서 단일 객체를 나타내는 줄/열의 수 (복잡한 구조일 때 지정)                                 |

> **참고:** 대부분의 옵션은 기본값이 있어 단순 표에서는 최소한의 설정만으로 동작합니다. 멀티라인 헤더/복잡한 표는 옵션을 명시적으로 지정하세요.
