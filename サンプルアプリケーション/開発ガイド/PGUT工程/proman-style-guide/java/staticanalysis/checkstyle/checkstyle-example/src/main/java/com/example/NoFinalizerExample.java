/*
 * Header example
 */
package com.example;


/**
 * NoFinalizerのコード例です。
 *
 * @author example
 * @since 1.0.0
 */
public class NoFinalizerExample {

    // finalizeメソッドをオーバライドしています（NG）。
    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        // （リソース解放処理）
    }
}

