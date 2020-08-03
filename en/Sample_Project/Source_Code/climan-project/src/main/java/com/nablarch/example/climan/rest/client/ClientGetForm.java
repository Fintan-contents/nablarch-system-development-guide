package com.nablarch.example.climan.rest.client;

import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;

import java.io.Serializable;

/**
 * Form class for acquiring client details.
 */
public class ClientGetForm implements Serializable {

    /** Client ID */
    @Required
    @Domain("clientId")
    private String clientId;

    /**
     * Returns client ID.
     * @return Client ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Set client ID.
     * @param clientId Client ID
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
