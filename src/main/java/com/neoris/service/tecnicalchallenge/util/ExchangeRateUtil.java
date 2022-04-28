package com.neoris.service.tecnicalchallenge.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExchangeRateUtil {

    public static String FORMAT_DATE = "dd/MM/yyyy";
    public static String ENG_FORMAT_DATE = "yyyy-MM-dd";
    public static String HRS_FORMAT_DATE = "dd/MM/yyyy HH:mm:ss";
    public static String DB_FORMAT_DATE = "ddMMyyyy";

    public static String dateToString(Date date, String format) {
        assert format != null;
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date stringToDate(String date, String format) {
        assert format != null;
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

}
