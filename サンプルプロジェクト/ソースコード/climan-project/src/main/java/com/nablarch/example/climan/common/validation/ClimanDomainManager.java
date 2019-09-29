package com.nablarch.example.climan.common.validation;

import nablarch.core.validation.ee.DomainManager;

/**
 * {@link DomainManager}の実装クラス。
 */
public class ClimanDomainManager implements DomainManager<ClimanDomainType> {
    @Override
    public Class<ClimanDomainType> getDomainBean() {
        return ClimanDomainType.class;
    }
}
