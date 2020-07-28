package com.nablarch.example.proman.common.validation;

import nablarch.core.util.DateUtil;
import nablarch.core.util.StringUtil;

/**
 * Class for validating whether the relationship between the start date and end date is correct.
 *
 * @author Nabu Rakutaro
 */
public class DateRangeValidator {
    /** Start date */
    private final String start;
    /** End date */
    private final String end;

    /**
     * Constructor for setting start date and end date.
     *
     * @param start: Start date
     * @param end: End date
     */
    public DateRangeValidator(final String start, final String end) {
        this.start = start;
        this.end = end;
    }

    /**
     * True is returned if the end date is later than (or the same date as) the start date.
     * True is also returned if one of the values is not in date format.
     *
     * @return: True if start date <= end date
     */
    public boolean isValid() {
        if (isValidDate(start) && isValidDate(end)) {
            return DateUtil.getParsedDate(start, "yyyy/MM/dd").compareTo(DateUtil.getParsedDate(end, "yyyy/MM/dd")) <= 0;
        }
        // Values other than dates are not subject to validation
        return true;
    }

    /**
     * Checks that the character string is in date format.
     *
     * @param date: Date string
     * @return: True if in date format
     */
    private boolean isValidDate(final String date) {
        if (StringUtil.isNullOrEmpty(date)) {
            return false;
        }
        return DateUtil.isValid(date, "yyyy/MM/dd");
    }
}
