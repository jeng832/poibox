package io.github.jeng832.converter;

import io.github.jeng832.exception.ExcelConvertException;

import java.util.List;

/**
 * @deprecated This class is replaced by {@link io.github.jeng832.serializer.ExcelSerializer}
 */
@Deprecated
public interface ExcelConverter {

    /**
     * This method is convert Excel file to objects.
     * @deprecated This method is replaced by {@link io.github.jeng832.serializer.ExcelSerializer#serialize(Class)}
     */
    @Deprecated
    <T> List<T> toObjects(Class<T> clazz) throws ExcelConvertException;
}
