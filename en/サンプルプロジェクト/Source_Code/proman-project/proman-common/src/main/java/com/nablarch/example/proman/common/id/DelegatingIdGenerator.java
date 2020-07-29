package com.nablarch.example.proman.common.id;

import nablarch.common.idgenerator.IdFormatter;
import nablarch.common.idgenerator.IdGenerator;
import nablarch.core.repository.initialization.Initializable;

/**
 * Class for implementation of {@link IdGenerator} for delegation of ID numbering process.
 * {@link IdGenerator} and {@link Initializable} are implemented
 * and any implementation class can be set as the delegate.
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
     * Set the delegate for the actual numbering process.
     *
     * @param delegate: Delegate
     */
    public void setDelegate(IdGenerator delegate) {
        this.delegate = delegate;
    }
}
