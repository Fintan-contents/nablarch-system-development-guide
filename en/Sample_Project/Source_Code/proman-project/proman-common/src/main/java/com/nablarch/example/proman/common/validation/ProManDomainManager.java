package com.nablarch.example.proman.common.validation;

import nablarch.core.validation.ee.DomainManager;

/**
 * Implementation class of {@link DomainManager}.
 * <p/>
 * Returns bean class defining the domain.
 *
 * @author Masaya Seko
 */
public class ProManDomainManager implements DomainManager<ProManDomainType> {
    @Override
    public Class<ProManDomainType> getDomainBean() {
        // Returns class object for domain bean
        return ProManDomainType.class;
    }
}
