package com.komarov.webschool.utility;

public class StringUtility {

    public static String makeFirstNotNullCharUpperCase(String line) {
        if (line != null) {
            return line.toUpperCase().charAt(0) + line.substring(1);
        }
        return null;
    }

    public static String makeNotNullStringLowerCase(String line) {
        if (line != null) {
            return line.toLowerCase();
        }
        return null;
    }
}
