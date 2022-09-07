package com.api.utils;

public class StringUtils {
    public static String htmlElement(String tag, String text) {
        return "<" + tag + ">" + text + "</" + tag + ">";
    }
}
