package com.list.todo.util;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilsTest {

    @Test
    public void stringToDate() {
        String dateString = "12/02/2010 23:59:59";

        try {
            assertEquals(1266011999000L, DateUtils.stringToDate(dateString).getTime());
            assertNotEquals(1266011999001L, DateUtils.stringToDate(dateString).getTime());
            assertNotEquals(1266011998999L, DateUtils.stringToDate(dateString).getTime());

            assertNotEquals(1266011999000L, DateUtils.stringToDate("12/02/2010 23:59:58").getTime());
            assertNotEquals(1266011999000L, DateUtils.stringToDate("13/02/2010 00:00:00").getTime());
            assertNotEquals(1266011999000L, DateUtils.stringToDate("12/02/2010 23:58:58").getTime());
            assertNotEquals(1266011999000L, DateUtils.stringToDate("12/02/2010 22:59:58").getTime());
            assertNotEquals(1266011999000L, DateUtils.stringToDate("12/02/2010 00:00:00").getTime());

            assertNotEquals(1266011999000L, DateUtils.stringToDate("11/02/2010 23:59:59").getTime());
            assertNotEquals(1266011999000L, DateUtils.stringToDate("12/01/2010 23:59:59").getTime());
            assertNotEquals(1266011999000L, DateUtils.stringToDate("12/03/2010 23:59:59").getTime());
            assertNotEquals(1266011999000L, DateUtils.stringToDate("12/02/2011 23:59:59").getTime());
            assertNotEquals(1266011999000L, DateUtils.stringToDate("12/02/2009 23:59:59").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dateToString() {
        Date date = new Date(1266011999000L);
        assertEquals(DateUtils.dateToString(date),"12/02/2010 23:59:59");
        assertNotEquals(DateUtils.dateToString(date),"12/2/2010 23:59:59");
        assertNotEquals(DateUtils.dateToString(date),"12/2/2010 23:59:59");
        assertNotEquals(DateUtils.dateToString(date),"12/2/2010");
        assertNotEquals(DateUtils.dateToString(date),"12/02/2010");
    }

    @Test
    public void getDateString() {
        assertNotEquals(DateUtils.getDateString(7,1,2010),"07/02/2010");

        assertEquals(DateUtils.getDateString(12,1,2010),"12/02/2010 23:59:59");
        assertNotEquals(DateUtils.getDateString(12,1,2010),"12/01/2010 23:59:59");
        assertNotEquals(DateUtils.getDateString(12,1,2010),"12/2/2010 23:59:59");

        assertEquals(DateUtils.getDateString(7,1,2010),"07/02/2010 23:59:59");
        assertNotEquals(DateUtils.getDateString(7,1,2010),"7/02/2010 23:59:59");
        assertNotEquals(DateUtils.getDateString(7,1,2010),"7/2/2010 23:59:59");

        assertNotEquals(DateUtils.getDateString(7,1,201),"07/02/201");
        assertEquals(DateUtils.getDateString(7,1,201),"07/02/201 23:59:59");

    }
}