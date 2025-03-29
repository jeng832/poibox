package io.github.jeng832.serializer;

import io.github.jeng832.exception.ExcelConvertException;

import java.util.List;

public interface ExcelSerializer {

    /**
     * Java 객체를 Excel 파일로 변환합니다.
     * @param objects 변환할 Java 객체 목록
     * @param clazz 객체의 클래스 타입
     * @param <T> 객체의 타입
     * @throws ExcelConvertException 변환 중 오류가 발생한 경우
     */
    <T> void serialize(List<T> objects, Class<T> clazz) throws ExcelConvertException;
}
