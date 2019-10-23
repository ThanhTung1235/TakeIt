package com.takeIt.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeUtil {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public static String formatDateFromLong(long time) {
        if (time == 0) {
            return "";
        }
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTimeInMillis(time);
        return simpleDateFormat.format(tempCalendar.getTime());
    }
}
