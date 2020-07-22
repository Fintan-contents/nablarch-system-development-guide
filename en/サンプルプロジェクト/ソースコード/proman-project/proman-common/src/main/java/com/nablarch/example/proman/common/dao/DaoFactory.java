package com.nablarch.example.proman.common.dao;

import nablarch.common.dao.BasicDaoContextFactory;
import nablarch.common.dao.DaoContext;
import nablarch.common.dao.DaoContextFactory;
import nablarch.core.repository.SystemRepository;

/**
 * {@link DaoContext}取得用クラス
 *
 * @author TIS
 *
 */
public class DaoFactory {

    /** {@link DaoContextFactory}を{@link SystemRepository}からルックアップする際の名前 */
    private static final String DAO_CONTEXT_FACTORY = "daoContextFactory";

    /**
     * 隠蔽コンストラクタ。
     */
    private DaoFactory() {
    }

    /**
     * {@link DaoContext}を取得する。
     *
     * {@link #DAO_CONTEXT_FACTORY}で{@link SystemRepository}上に{@link DaoContextFactory}実装が登録されている場合はそのクラスを、
     * 登録されていない場合には{@link BasicDaoContextFactory}を用いて{@link DaoContext}を生成する。
     *
     * @return DaoContext
     */
    public static DaoContext create() {
        final DaoContextFactory factory = SystemRepository.get(DAO_CONTEXT_FACTORY);
        return factory != null ? factory.create() : new BasicDaoContextFactory().create();
    }
}
