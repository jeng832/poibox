package com.github.jeng832.converter;

import com.github.jeng832.exception.ExcelConvertException;

import java.util.List;

public interface ExcelConverter {

    <T> List<T> toObjects(Class<T> clazz) throws ExcelConvertException;
}
