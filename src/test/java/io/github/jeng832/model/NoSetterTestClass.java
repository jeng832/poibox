package io.github.jeng832.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.github.jeng832.annotation.ExcelProperty;

public class NoSetterTestClass {

    @ExcelProperty("number")
    private Integer number;

    @ExcelProperty("번호")
    private int num;

    @ExcelProperty("이름")
    private String name;

    @ExcelProperty(value = "날짜")
    private LocalDate time;

    @ExcelProperty("formula")
    private Integer sum;

    @ExcelProperty("formula2")
    private String divStr;

    @ExcelProperty("formula2")
    private Float divFloat;

    @ExcelProperty("date_time")
    private LocalDateTime dateTime;

    @Override
    public String toString() {
        return "NoSetterTestClass{" +
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
