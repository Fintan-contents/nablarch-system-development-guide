package com.nablarch.example.proman.common.util;

import nablarch.core.util.StringUtil;

import java.util.regex.Pattern;

/**
 * 金額の大小関係をバリデーションする
 *
 * @author Nabu Rakutaro
 */
public class MoneyRelationUtil {
    /** 隠蔽コンストラクタ */
    private MoneyRelationUtil() {
    }

    /** 正規表現(半角数字) */
    private static final String DIGITS = "^[0-9]+$";

    /**
     * smallよりlargeが大きい(同値を含む)ならtrueを返す。
     * どちらかが数字出ない場合もtrueを返す。
     *
     * @param small 小さい金額
     * @param large 大きい金額
     * @return small <= large
     */
    public static boolean isValid(String small, String large) {
        if (isNumber(small) && isNumber(large)) {
            return Integer.parseInt(small) <= Integer.parseInt(large);
        }
        return true;
    }

    /**
     * 文字列が数値かどうかを判定する。
     *
     * @param money 金額文字列
     * @return 数値ならtrue
     */
    private static boolean isNumber(String money) {
        if (StringUtil.isNullOrEmpty(money)) {
            return false;
        }
        return Pattern.matches(DIGITS, money);
    }
}
