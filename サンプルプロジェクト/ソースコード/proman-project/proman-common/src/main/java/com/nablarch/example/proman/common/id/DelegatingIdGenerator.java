package com.nablarch.example.proman.common.id;

import nablarch.common.idgenerator.IdFormatter;
import nablarch.common.idgenerator.IdGenerator;
import nablarch.core.repository.initialization.Initializable;

/**
 * ID採番処理を委譲する{@link IdGenerator}実装クラス。
 * {@link IdGenerator}と{@link Initializable}を実装しており、
 * どの実装クラスでも委譲先に設定可能である。
 *
 * @author Tsuyoshi Kawasaki
 */
public class DelegatingIdGenerator implements IdGenerator, Initializable {

    private IdGenerator delegate;

    @Override
    public String generateId(String id) {
        return delegate.generateId(id);
    }

    @Override
    public String generateId(String id, IdFormatter formatter) {
        return delegate.generateId(id, formatter);
    }

    @Override
    public void initialize() {
        if (delegate instanceof Initializable) {
            ((Initializable) delegate).initialize();
        }
    }

    /**
     * 実際の採番処理を行う委譲先を設定する。
     *
     * @param delegate 委譲先
     */
    public void setDelegate(IdGenerator delegate) {
        this.delegate = delegate;
    }
}
