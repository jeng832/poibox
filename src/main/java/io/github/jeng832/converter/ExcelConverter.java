package io.github.jeng832.converter;

import java.util.List;

import io.github.jeng832.exception.ExcelConvertException;

/**
 * @deprecated This class is replaced by {@link io.github.jeng832.deserializer.ExcelDeserializer}
 */
@Deprecated
public interface ExcelConverter {

    /**
     * This method is convert Excel file to objects.
     * @deprecated This method is replaced by {@link io.github.jeng832.deserializer.ExcelDeserializer#deserialize(Class)}
     */
    @Deprecated
    <T> List<T> toObjects(Class<T> clazz) throws ExcelConvertException;
}
