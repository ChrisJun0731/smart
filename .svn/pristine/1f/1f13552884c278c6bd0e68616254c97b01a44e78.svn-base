package com.sumridge.smart.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by liu on 16/9/9.
 */
public class DateUtil {

    private static String[] pattern = { "yyyy-MM-dd","MM/yyyy","yyyyMM" };

    public static Date parseDate(String text, String format) {

        if(StringUtils.isEmpty(text)) {
            return null;
        } else {

            try {
                return  DateUtils.parseDate(text, format);
            } catch (ParseException e) {
                return null;
            }

        }
    }

    public static Date getDate(String date) {

        if (StringUtils.isNotEmpty(date)) {
            try {
                Date dt = DateUtils.parseDate(date, pattern);
                return dt;
            } catch (ParseException e) {
                return null;
            }
        } else {
            return null;
        }
    }
}
