package com.nablarch.example.proman.common.util;

import nablarch.core.util.DateUtil;
import nablarch.core.util.StringUtil;

/**
 * A class that validates that the relationship between start and end dates is correct.
 *
 * @author Goro Kumano
 */
public class DateRelationUtil {
    /**  hidden constructor */
    private DateRelationUtil() {
    }

    /**
     * If the end date is later than the start date (including the same day), true is returned.
     * It also returns true if either of them is not in date format.
     *
     * @param start start date
     * @param end   start date
     * @return true, if start date <= start date.
     */
    public static boolean isValid(String start, String end) {
        if (isDate(start) && isDate(end)) {
            return DateUtil.getParsedDate(start, "yyyy/MM/dd")
                    .compareTo(DateUtil.getParsedDate(end, "yyyy/MM/dd")) <= 0;
        }
        // Except for dates, validation is not applicable.
        return true;
    }

    /**
     * Checks whether a string is in date format.
     *
     * @param date date string
     * @return true, if it is in date format
     */
    private static boolean isDate(final String date) {
        if (StringUtil.isNullOrEmpty(date)) {
            return false;
        }
        return DateUtil.isValid(date, "yyyy/MM/dd");
    }
}
