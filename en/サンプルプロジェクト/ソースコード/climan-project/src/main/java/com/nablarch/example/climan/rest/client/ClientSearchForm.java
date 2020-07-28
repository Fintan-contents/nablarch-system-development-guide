package com.nablarch.example.climan.rest.client;

import nablarch.core.validation.ee.Domain;

import java.io.Serializable;

/**
 * Form class for searching for clients.
 */
public class ClientSearchForm implements Serializable {

    /** Client name */
    @Domain("clientName")
    private String clientName;

    /** Industry code */
    @Domain("industryCode")
    private String industryCode;

    /**
     * Returns client names.
     * @return: Client names
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Set client names.
     * @param clientName: Client names
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Returns industry type code.
     * @return: Industry type code
     */
    public String getIndustryCode() {
        return industryCode;
    }

    /**
     * Set industry code.
     * @param industryCode: Industry code
     */
    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }
}
