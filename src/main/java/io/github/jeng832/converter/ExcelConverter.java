package io.github.jeng832.converter;

import io.github.jeng832.exception.ExcelConvertException;

import java.util.List;

@Deprecated
public interface ExcelConverter {

    @Deprecated
    <T> List<T> toObjects(Class<T> clazz) throws ExcelConvertException;
}
