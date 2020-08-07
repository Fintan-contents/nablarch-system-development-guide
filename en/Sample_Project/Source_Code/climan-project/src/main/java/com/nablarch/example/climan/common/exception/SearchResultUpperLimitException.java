package com.nablarch.example.climan.common.exception;

/**
 * Exception indicating maximum search result error.
 *
 * @author Masaya Seko
 */
public class SearchResultUpperLimitException extends RuntimeException {

    /** Maximum number of search results */
    private long limit;

    /**
     * An exception indicating a maximum search result error is generated.
     * @param limit Maximum number of search results
     */
    public SearchResultUpperLimitException(long limit) {
        this.limit = limit;
    }

    /**
     * Returns maximum number of search results.
     * @return Maximum number of results
     */
    public long getLimit() {
        return limit;
    }
}
