package com.nablarch.example.climan.common.validation;

import nablarch.core.validation.ee.DomainManager;

/**
 * Implementation class of {@link DomainManager}.
 */
public class ClimanDomainManager implements DomainManager<ClimanDomainType> {
    @Override
    public Class<ClimanDomainType> getDomainBean() {
        return ClimanDomainType.class;
    }
}
