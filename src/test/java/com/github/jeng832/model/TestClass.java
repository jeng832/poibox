package com.github.jeng832.model;

import com.github.jeng832.annotation.ExcelProperty;

import java.time.LocalDate;

public class TestClass {

    @ExcelProperty("number")
    private Integer number;

    @ExcelProperty("번호")
    private int num;

    @ExcelProperty("이름")
    private String name;

    @ExcelProperty("날짜")
    private LocalDate time;
}
