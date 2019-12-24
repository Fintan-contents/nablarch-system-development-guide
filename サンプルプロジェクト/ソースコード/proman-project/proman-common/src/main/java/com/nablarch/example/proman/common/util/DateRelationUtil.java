package com.nablarch.example.proman.common.util;

import nablarch.core.util.DateUtil;
import nablarch.core.util.StringUtil;

/**
 * 開始日と終了日の関係が正しいかをバリデーションするクラス。
 *
 * @author Nabu Rakutaro
 */
public class DateRelationUtil {
    /** 隠蔽コンストラクタ */
    private DateRelationUtil() {
    }

    /**
     * 開始日より終了日が後（同日を含む）ならtrueを返す。
     * また、どちらかが日付形式でない場合もtrueを返す。
     *
     * @param start 開始日
     * @param end   終了日
     * @return 開始日 <= 終了日 なら true
     */
    public static boolean isValid(String start, String end) {
        if (isDate(start) && isDate(end)) {
            return DateUtil.getParsedDate(start, "yyyy/MM/dd")
                    .compareTo(DateUtil.getParsedDate(end, "yyyy/MM/dd")) <= 0;
        }
        // 日付以外はバリデーション対象外
        return true;
    }

    /**
     * 文字列が日付形式であるかをチェックする。
     *
     * @param date 日付文字列
     * @return 日付形式ならtrue
     */
    private static boolean isDate(final String date) {
        if (StringUtil.isNullOrEmpty(date)) {
            return false;
        }
        return DateUtil.isValid(date, "yyyy/MM/dd");
    }
}
