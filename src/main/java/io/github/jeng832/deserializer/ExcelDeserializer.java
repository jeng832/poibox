package io.github.jeng832.deserializer;

import io.github.jeng832.exception.ExcelConvertException;

import java.util.List;

public interface ExcelDeserializer {

    <T> List<T> deserialize(Class<T> clazz) throws ExcelConvertException;
}
