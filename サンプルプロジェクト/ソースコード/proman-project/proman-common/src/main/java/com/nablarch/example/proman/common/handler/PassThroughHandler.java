package com.nablarch.example.proman.common.handler;

import nablarch.fw.ExecutionContext;
import nablarch.fw.Handler;

/**
 * 何もしないハンドラ。
 * <p>
 * 特定の環境(例えば、ローカルの開発環境)でハンドラを無効化したいときに使用する。
 * </p>
 */
public class PassThroughHandler implements Handler<Object, Object> {
    @Override
    public Object handle(Object data, ExecutionContext context) {
        return context.handleNext(data);
    }
}
