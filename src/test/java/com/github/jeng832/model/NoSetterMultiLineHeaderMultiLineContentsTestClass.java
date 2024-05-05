package com.github.jeng832.model;

import com.github.jeng832.annotation.ExcelProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class NoSetterMultiLineHeaderMultiLineContentsTestClass {

    @ExcelProperty("숫자")
    private Integer id;

    @ExcelProperty("number")
    private Integer number;

    @ExcelProperty("번호")
    private int num;

    @ExcelProperty("별명")
    private String nick;

    @ExcelProperty("name")
    private String name;

    @ExcelProperty("공식")
    private String formulaStr;

    @ExcelProperty("formula")
    private Integer sum;

    @ExcelProperty("formula2")
    private String divStr;

    @ExcelProperty("formula2")
    private Float divFloat;

    @ExcelProperty("시간")
    private LocalDate minTimeAsLocalDate;

    @ExcelProperty("시간")
    private LocalDateTime minTimeAsLocalDateTime;

    @ExcelProperty("시간")
    private ZonedDateTime minTimeAsZonedDateTime;

    @ExcelProperty("날짜")
    private LocalDate time;

    @ExcelProperty("일시")
    private LocalDateTime dateTime;

    @Override
    public String toString() {
        return "MultiLineHeaderMultiLineContentsTestClass{" +
                "id=" + id +
                ", number=" + number +
                ", num=" + num +
                ", nick='" + nick + '\'' +
                ", name='" + name + '\'' +
                ", formulaStr='" + formulaStr + '\'' +
                ", sum=" + sum +
                ", divStr='" + divStr + '\'' +
                ", divFloat=" + divFloat +
                ", minTimeAsLocalDate=" + minTimeAsLocalDate +
                ", minTimeAsLocalDateTime=" + minTimeAsLocalDateTime +
                ", minTimeAsZonedDateTime=" + minTimeAsZonedDateTime +
                ", time=" + time +
                ", dateTime=" + dateTime +
                '}';
    }
}
