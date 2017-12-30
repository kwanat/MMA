package com.example.kwanat.mma_mobilemanagementassistant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kamil on 28.12.2017.
 */

public class DateDiff {

    public long getDaysFromNow(String userDate)
    {
        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date = sdf.parse(userDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal1.setTime(date);

        Calendar cal2 = Calendar.getInstance();

        // Get the represented date in milliseconds
        long millis1 = cal1.getTimeInMillis();
        long millis2 = cal2.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = millis2 - millis1;

        // Calculate difference in days
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;
    }
    public String getDate()
    {
       // Calendar cal2 = Calendar.getInstance();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        String date=sdf.format(new Date());
        return date;
    }
}
