package com.nablarch.example.proman.common.util;

import nablarch.core.util.StringUtil;

import java.util.regex.Pattern;

/**
 * Validate the relationship between large and small amounts.
 *
 * @author Goro Kumano
 */
public class MoneyRelationUtil {
    /** hidden constructor */
    private MoneyRelationUtil() {
    }

    /** Regular expression (half-width numbers)*/
    private static final String DIGITS = "^[0-9]+$";

    /**
     * If large is larger than small (including the same value), true is returned.
     * True is returned even if either of them does not show a number.
     *
     * @param small small amount of money
     * @param large large amount of money
     * @return small <= large
     */
    public static boolean isValid(String small, String large) {
        if (isNumber(small) && isNumber(large)) {
            return Integer.parseInt(small) <= Integer.parseInt(large);
        }
        return true;
    }

    /**
     * Determines if the string is a number.
     *
     * @param money monetary string
     * @return true, if it is a number.
     */
    private static boolean isNumber(String money) {
        if (StringUtil.isNullOrEmpty(money)) {
            return false;
        }
        return Pattern.matches(DIGITS, money);
    }
}
