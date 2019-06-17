package com.example.myapplication;

import android.location.Location;

import java.util.regex.Pattern;


public class ValidationUtility {

    public static boolean isString(String string) {
        String stringRegularExpression = "^[a-zA-Z]+$";
        return string.matches(stringRegularExpression);
    }

    public static boolean isLocationValid(Location location) {
        return location == null;
    }

    public static boolean isTypeRight(String type) {
        return !type.isEmpty() && type.equals("Rain") || type.equals("Clouds") || type.equals("Sun") && isString(type);
    }

    public static boolean isCorrectTime(String time) {
        final Pattern TimePattern;
        TimePattern = Pattern.compile("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");
        return TimePattern.matcher(time).matches();
    }

    public static boolean isTempCorrect(int temp) {
        int minus = -100;
        int plus = 100;
        return temp > minus && temp < plus;
    }

}