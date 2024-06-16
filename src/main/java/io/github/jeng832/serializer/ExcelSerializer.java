package io.github.jeng832.serializer;

import io.github.jeng832.exception.ExcelConvertException;

import java.util.List;

public interface ExcelSerializer {

    <T> List<T> serialize(Class<T> clazz) throws ExcelConvertException;
}
