package io.github.jeng832.deserializer;

import io.github.jeng832.converter.ExcelConverterBuilder;
import io.github.jeng832.exception.ExcelConvertException;

import java.util.Collections;
import java.util.List;

public class ExcelDeserializerImpl implements ExcelDeserializer {

    public ExcelDeserializerImpl(ExcelConverterBuilder excelConverterBuilder) {
        // TODO
    }

    @Override
    public <T> List<T> deserialize(Class<T> clazz) throws ExcelConvertException {
        return Collections.emptyList();
    }
}
