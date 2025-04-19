# POI-Box 1.0.0 Release

POI-Box는 Apache POI를 사용하여 Excel 파일을 Java 객체로 쉽게 변환할 수 있는 Java 라이브러리입니다.

## 주요 기능

### Excel to Java Object 변환
- 단일/다중 행 헤더 지원
- 다중 행 콘텐츠 지원
- Setter/Constructor 기반 객체 생성 지원
- 사용자 정의 필드 매핑 지원

### 데이터 타입 지원
- 기본 데이터 타입 (int, long, double 등)
- Java 8 날짜/시간 타입 (LocalDate, LocalDateTime, ZonedDateTime)
- Excel 수식 결과값 변환
- 특수 문자 처리

### 유연한 설정
- 시작 행/열 지정
- 시트 이름 지정
- 헤더 행 수 설정
- 콘텐츠 행 수 설정

### 예외 처리
- 파일 누락/손상 검증
- 시트 존재 여부 검증
- 데이터 타입 불일치 검증
- 필수 값 검증

## 시스템 요구사항
- Java 8 이상
- Apache POI 5.2.5
- Log4j 2.23.1

## 사용 예시
```java
List<UserDto> users = ExcelConverter.builder(UserDto.class)
    .filePath("users.xlsx")
    .sheetName("Sheet1")
    .headerLineCount(2)
    .build()
    .deserialize();
```

## 문서
자세한 사용법과 예제는 [GitHub Wiki](https://github.com/jeng832/poibox/wiki)를 참조해주세요.