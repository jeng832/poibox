package com.github.jeng832.model;

import com.github.jeng832.annotation.ExcelProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MultiLineHeaderTestClass {

    @ExcelProperty("number")
    private Integer number;

    @ExcelProperty("번호")
    private int num;

    @ExcelProperty("name")
    private String name;

    @ExcelProperty("날짜")
    private LocalDate time;

    @ExcelProperty("formula")
    private Integer sum;

    @ExcelProperty("formula2")
    private String divStr;

    @ExcelProperty("formula2")
    private Float divFloat;

    @ExcelProperty("일시")
    private LocalDateTime dateTime;

    @Override
    public String toString() {
        return "MultiLineHeaderTestClass{" +
                "number=" + number +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", sum=" + sum +
                ", divStr='" + divStr + '\'' +
                ", divFloat=" + divFloat +
                ", dateTime=" + dateTime +
                '}';
    }
}
