package com.list.todo.util;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static final NumberFormat f = new DecimalFormat("00");

    public static Date stringToDate(String dateString) throws ParseException {
        return spf.parse(dateString);
    }

    public static String dateToString(Date date) {
        return spf.format(date);
    }

    public static String getDateString(int day, int month, int year) {
        return f.format(day) + "/" + f.format(month + 1) + "/" + year + " 23:59:59";
    }

    public static Date nowDate() {
        /*
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
         */
        return new Date();
    }
}