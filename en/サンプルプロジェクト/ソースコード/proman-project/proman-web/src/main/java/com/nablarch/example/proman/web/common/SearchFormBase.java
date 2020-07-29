package com.nablarch.example.proman.web.common;

import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;

/**
 * Basic form class.
 *
 * @author Nabu Rakutaro
 */
public abstract class SearchFormBase {

    /**
     * Page number
     */
    @Required
    @Domain("pageNumber")
    private String pageNumber;

    /**
     * Acquires page number.
     *
     * @return: Page number
     */
    public String getPageNumber() {
        return pageNumber;
    }

    /**
     * Set the page number.
     *
     * @param pageNumber: Page number to be set
     */
    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }
}
