package io.github.jeng832.model;

import io.github.jeng832.annotation.ExcelProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SetterTestClass {

    @ExcelProperty("number")
    private Integer number;

    @ExcelProperty("번호")
    private int num;

    @ExcelProperty("이름")
    private String name;

    @ExcelProperty("날짜")
    private LocalDate time;

    @ExcelProperty("formula")
    private Integer sum;

    @ExcelProperty("formula2")
    private String divStr;

    @ExcelProperty("formula2")
    private Float divFloat;

    public void setDivStr(String divStr) {
        this.divStr = divStr;
    }

    public void setDivFloat(Float divFloat) {
        this.divFloat = divFloat;
    }

    @ExcelProperty("date_time")
    private LocalDateTime dateTime;

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

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

    @Override
    public String toString() {
        return "SetterTestClass{" +
                "number=" + number +
                ", num=" + num +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", sum=" + sum +
                ", divStr='" + divStr + '\'' +
                ", divFloat='" + divFloat + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
