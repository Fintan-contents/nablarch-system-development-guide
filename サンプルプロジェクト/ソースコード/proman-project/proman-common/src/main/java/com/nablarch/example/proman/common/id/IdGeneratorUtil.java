package com.nablarch.example.proman.common.id;

import nablarch.common.idgenerator.IdFormatter;
import nablarch.common.idgenerator.IdGenerator;
import nablarch.core.repository.SystemRepository;

import java.util.Objects;

/**
 * ID採番を行うユーティリティクラス。
 * {@link SystemRepository}に登録されたID採番機能を使用して採番を行う。
 *
 * @author Tsuyoshi Kawasaki
 * @since 1.0
 */
class IdGeneratorUtil {

    /** プライベートコンストラクタ. */
    private IdGeneratorUtil() {
    }

    /** デフォルトの{@link IdGenerator}を取得するためのキー */
    private static final String ID_GENERATOR_KEY = "idGenerator";

    /**
     * 汎用の採番処理を行う。
     *
     * @param id 採番対象を識別するID
     * @return 採番された番号
     */
    static String generateId(String id) {
        return getDefaultIdGenerator().generateId(id);
    }

    /**
     * 汎用の採番処理を行う。
     *
     * @param id 採番対象を識別するID
     * @param idFormatter 採番したIDをフォーマットするIdFormatter
     * @return 採番された番号
     */
    static String generateId(String id, IdFormatter idFormatter) {
        return getDefaultIdGenerator().generateId(id, idFormatter);
    }

    /**
     * デフォルトの{@link IdGenerator}を取得する。
     *
     * @return {@link IdGenerator}実装
     */
    private static IdGenerator getDefaultIdGenerator() {
        return Objects.requireNonNull(SystemRepository.get(ID_GENERATOR_KEY));
    }

}
