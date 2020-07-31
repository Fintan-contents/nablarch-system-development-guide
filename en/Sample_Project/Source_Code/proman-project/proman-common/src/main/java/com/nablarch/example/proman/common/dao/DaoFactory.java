package com.nablarch.example.proman.common.dao;

import nablarch.common.dao.BasicDaoContextFactory;
import nablarch.common.dao.DaoContext;
import nablarch.common.dao.DaoContextFactory;
import nablarch.core.repository.SystemRepository;

/**
 * Class for acquisition of {@link DaoContext}
 *
 * @author TIS
 *
 */
public class DaoFactory {

    /** Name for looking up {@link DaoContextFactory} from {@link SystemRepository} */
    private static final String DAO_CONTEXT_FACTORY = "daoContextFactory";

    /**
     * Masked constructor.
     */
    private DaoFactory() {
    }

    /**
     * Acquires {@link DaoContext}.
     *
     * When implementation of {@link DaoContextFactory} is registered on {@link SystemRepository} in {@link #DAO_CONTEXT_FACTORY}, the corresponding class is generated.
     * If it is not registered, {@link DaoContext} is generated using {@link BasicDaoContextFactory}.
     *
     * @return DaoContext
     */
    public static DaoContext create() {
        final DaoContextFactory factory = SystemRepository.get(DAO_CONTEXT_FACTORY);
        return factory != null ? factory.create() : new BasicDaoContextFactory().create();
    }
}
