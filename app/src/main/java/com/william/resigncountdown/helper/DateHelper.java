package com.william.resigncountdown.helper;

import com.william.resigncountdown.model.DateDiff;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by willi on 21-Apr-16.
 */
public class DateHelper {

    public static DateDiff getDiff(Date targetDate) {
        long duration = targetDate.getTime() - Calendar.getInstance().getTimeInMillis();
        return new DateDiff(duration);
    }

}
