package com.nablarch.example.proman.common.validation;

import nablarch.core.validation.ee.DomainManager;

/**
 * {@link DomainManager} の実装クラス。
 * <p/>
 * ドメインを定義したBeanクラスを返却する。
 *
 * @author Masaya Seko
 */
public class ProManDomainManager implements DomainManager<ProManDomainType> {
    @Override
    public Class<ProManDomainType> getDomainBean() {
        // ドメインBeanのClassオブジェクトを返す
        return ProManDomainType.class;
    }
}
