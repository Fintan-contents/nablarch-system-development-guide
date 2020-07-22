package com.nablarch.example.proman.test;

import nablarch.test.core.batch.BatchRequestTestSupport;

/**
 * 本プロジェクト専用の{@link BatchRequestTestSupport}継承クラス。<br/>
 * プロジェクト固有の共通処理を提供する。
 * 本プロジェクトにてバッチ処理方式のリクエスト単体テストを作成する場合、
 * {@link BatchRequestTestSupport}を直接継承するのではなく、
 * 本クラスを継承すること。
 */
public abstract class BatchRequestTestBase extends BatchRequestTestSupport {

    /** コンストラクタ。 */
    protected BatchRequestTestBase() {
        super();
    }

    /**
     * コンストラクタ。
     *
     * @param testClass テストクラス
     */
    public BatchRequestTestBase(Class<?> testClass) {
        super(testClass);
    }
}
