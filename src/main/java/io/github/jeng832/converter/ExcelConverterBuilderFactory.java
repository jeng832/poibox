package io.github.jeng832.converter;

public class ExcelConverterBuilderFactory {

    private ExcelConverterBuilderFactory() {

    }

    public static ExcelConverterBuilder create() {
        return new ExcelConverterBuilderImpl();
    }
}
