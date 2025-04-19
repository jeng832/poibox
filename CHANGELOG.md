# Changelog

## [1.0.0] - 2025-04-19

### Added
- Excel to Java Object 변환 기능
  - 단일/다중 행 헤더 지원
  - 다중 행 콘텐츠 지원
  - Setter/Constructor 기반 객체 생성 지원
  - 사용자 정의 필드 매핑 지원

### Supported
- 데이터 타입 지원
  - 기본 데이터 타입 (int, long, double 등)
  - Java 8 날짜/시간 타입 (LocalDate, LocalDateTime, ZonedDateTime)
  - Excel 수식 결과값 변환
  - 특수 문자 처리

### Features
- 유연한 설정
  - 시작 행/열 지정
  - 시트 이름 지정
  - 헤더 행 수 설정
  - 콘텐츠 행 수 설정

### Security
- 예외 처리
  - 파일 누락/손상 검증
  - 시트 존재 여부 검증
  - 데이터 타입 불일치 검증
  - 필수 값 검증