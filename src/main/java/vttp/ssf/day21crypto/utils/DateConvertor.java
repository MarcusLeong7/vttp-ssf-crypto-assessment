package vttp.ssf.day21crypto.utils;

import jakarta.json.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvertor {


    // Common SimpleDateFormats:
   /* Format            Output      Description
    yyyy-MM-dd	        2024-12-12	ISO 8601 date format (year-month-day).
    MM/dd/yyyy	        12/12/2024	US-style date format (month/day/year).
    dd/MM/yyyy	        12/12/2024	European-style date format (day/month/year).
    dd MMM yyyy	        12 Dec 2024	Day, abbreviated month, and year.
    EEE, MMM dd, yyyy	Thu, Dec 12, 2024	Day of the week, abbreviated month, day, and year.
    EEEE, MMMM dd, yyyy	Thursday, December 12, 2024	Full day name, full month name, day, and year.
    yyyy.MM.dd G 'at' HH:mm:ss z	2024.12.12 AD at 14:55:02 UTC	Full date and time with era and timezone.*/

   /* public static Date stringToDate(String jsonDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MM/dd/yyyy");
            return sdf.parse(jsonDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    // converting Unix TimeStamp
    // 1734002417
    public static Date stringToDate(String input) throws ParseException {
        try {
            // Check if the input is a Unix timestamp
            long unixSeconds = Long.parseLong(input);

            // Convert seconds to milliseconds and return the Date
            return new Date(unixSeconds * 1000);
        } catch (NumberFormatException e) {
            throw new ParseException("Unparseable timestamp: " + input, 0);
        }
    }

    public static long dateToLong(Date date) {
        try{
            return date.getTime();
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static Date longToDate(JsonObject jsonObject, String dateFormat) {
        long longDate = jsonObject.getJsonNumber(dateFormat).longValue();
        return new Date(longDate);
    }
}
