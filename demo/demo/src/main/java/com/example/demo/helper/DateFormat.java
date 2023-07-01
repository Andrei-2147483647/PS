package com.example.demo.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    public static boolean isDateInRange(String givenDate, String startDate, String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date date = dateFormat.parse(givenDate);
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);

            return date.compareTo(start) >= 0 && date.compareTo(end) <= 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
