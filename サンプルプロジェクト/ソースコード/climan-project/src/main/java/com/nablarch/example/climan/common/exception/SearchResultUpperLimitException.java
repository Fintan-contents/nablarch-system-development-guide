package com.nablarch.example.climan.common.exception;

/**
 * 検索結果上限エラーを表す例外。
 *
 * @author Masaya Seko
 */
public class SearchResultUpperLimitException extends RuntimeException {

    /** 検索結果上限件数 */
    private long limit;

    /**
     * 検索結果上限エラーを表す例外を生成する。
     * @param limit 検索結果上限件数
     */
    public SearchResultUpperLimitException(long limit) {
        this.limit = limit;
    }

    /**
     * 検索結果上限件数を返す。
     * @return 結果上限件数
     */
    public long getLimit() {
        return limit;
    }
}
