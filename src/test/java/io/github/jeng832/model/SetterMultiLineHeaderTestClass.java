package io.github.jeng832.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.github.jeng832.annotation.ExcelProperty;

public class SetterMultiLineHeaderTestClass {

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

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public void setDivStr(String divStr) {
        this.divStr = divStr;
    }

    public void setDivFloat(Float divFloat) {
        this.divFloat = divFloat;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

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
