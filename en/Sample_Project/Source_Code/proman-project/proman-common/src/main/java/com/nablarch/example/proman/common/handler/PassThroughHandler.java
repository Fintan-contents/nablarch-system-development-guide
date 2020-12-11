package com.nablarch.example.proman.common.handler;

import nablarch.fw.ExecutionContext;
import nablarch.fw.Handler;

/**
 * do-nothing handler.
 * <p>
 * Use this when want to disable the handler in a specific environment (e.g., local development environment).
 * </p>
 */
public class PassThroughHandler implements Handler<Object, Object> {
    @Override
    public Object handle(Object data, ExecutionContext context) {
        return context.handleNext(data);
    }
}
