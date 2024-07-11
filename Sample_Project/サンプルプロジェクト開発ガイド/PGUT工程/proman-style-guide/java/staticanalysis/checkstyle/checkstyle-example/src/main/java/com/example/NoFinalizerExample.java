/*
 * Header example
 */
package com.example;


/**
 * NoFinalizerのコード例です。
 */
public class NoFinalizerExample {

    // finalizeメソッドをオーバライドしています（NG）。
    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        // （リソース解放処理）
    }
}
