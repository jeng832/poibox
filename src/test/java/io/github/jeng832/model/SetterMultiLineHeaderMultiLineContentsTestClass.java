package io.github.jeng832.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import io.github.jeng832.annotation.ExcelProperty;

public class SetterMultiLineHeaderMultiLineContentsTestClass {

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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFormulaStr(String formulaStr) {
        this.formulaStr = formulaStr;
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

    public void setMinTimeAsLocalDate(LocalDate minTimeAsLocalDate) {
        this.minTimeAsLocalDate = minTimeAsLocalDate;
    }

    public void setMinTimeAsLocalDateTime(LocalDateTime minTimeAsLocalDateTime) {
        this.minTimeAsLocalDateTime = minTimeAsLocalDateTime;
    }

    public void setMinTimeAsZonedDateTime(ZonedDateTime minTimeAsZonedDateTime) {
        this.minTimeAsZonedDateTime = minTimeAsZonedDateTime;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

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
