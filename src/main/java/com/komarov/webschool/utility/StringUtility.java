package com.komarov.webschool.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtility {

    public static String makeFirstNotNullCharUpperCase(String line) {
        return line != null ? line.toUpperCase().charAt(0) + line.substring(1) : null;
    }

    public static String makeNotNullStringLowerCase(String line) {
        return line != null ? line.toLowerCase() : null;
    }
}
