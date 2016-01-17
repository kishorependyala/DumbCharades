package com.teabreaktechnology.dumcharades.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kishorekpendyala on 8/15/15.
 * <p/>
 * Utility class to hold all common util functions that are not available in java or apache commons
 */

public class StringUtil {


    /**
     * @param str       Sample "'string1,value','string2','string3'"
     * @param delimiter delimiter for example ,
     * @param encloseBy encloseBy example '
     * @return return string array {"string1,value","string2","string3"}
     */

    public static String[] split(String str, char delimiter, char encloseBy) {

        if (str == null || str.length() == 0) {
            return new String[]{};
        }
        boolean isWithinEncloseBy = false;
        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char selectedChar = str.charAt(i);
            if (selectedChar == delimiter && !isWithinEncloseBy
                    ) {
                tokens.add(token.toString());
                token = new StringBuilder();

            }

            /*
                Example when parsing this string "abc",12,9
                for first occurrence of quotes (") isWithinEncloseBy is set to true

             */
            else if (selectedChar == encloseBy) {
                if (!isWithinEncloseBy) {
                    isWithinEncloseBy = true;
                } else {
                    isWithinEncloseBy = false;
                    if (i == str.length() - 1) {
                        tokens.add(token.toString());
                    }
                }

            } else {

                token.append(selectedChar);
                if (i == str.length() - 1) {
                    tokens.add(token.toString());
                }
            }
        }
        String[] returnValue = new String[tokens.size()];
        tokens.toArray(returnValue);
        return returnValue;
    }

    /*Function to split a string*/
    public static String[] split(String str) {
        return split(str, ',', '"');
    }


    public static boolean isValidStringWithNonZeroLength(String str) {
        return str != null && !str.isEmpty();
    }

    public static String strArrayToStr(String[] arr) {
        StringBuilder sb = new StringBuilder();
        for (String str : arr
                ) {
            sb.append(str);
            sb.append(",");
        }
        if (sb.length() > 0)
            return sb.substring(0, sb.length() - 1);
        else
            return sb.toString();
    }
}
