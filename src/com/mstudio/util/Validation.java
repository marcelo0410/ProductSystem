package com.mstudio.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    /**
     * Check value is matched with the regular expression
     * @param ex regular expression
     * @param input user's input
     * @return matched value
     */
    public static boolean checkString(String ex, String input){
        Pattern pattern = Pattern.compile(ex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

}
