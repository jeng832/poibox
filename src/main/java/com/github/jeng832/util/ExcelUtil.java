package com.github.jeng832.util;

import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

public class ExcelUtil {

    public static List<String> getAddressCodes(String upperLeft, String lowerRight) {
        CellAddress upperLeftAddress = new CellAddress(upperLeft);
        CellAddress lowerRightAddress = new CellAddress(lowerRight);
        CellRangeAddress range = new CellRangeAddress(upperLeftAddress.getRow(), lowerRightAddress.getRow(), upperLeftAddress.getColumn(), lowerRightAddress.getColumn());
        return null;


    }
}
