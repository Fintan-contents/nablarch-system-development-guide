package com.nablarch.example.proman.web.common.validation;

import nablarch.test.support.web.servlet.MockServletRequest;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class PromanMockServletRequest extends MockServletRequest {

    @Override
    public Enumeration getParameterNames() {
        final Iterator iterator = getParameterMap().keySet().iterator();
        return new Enumeration() {
            @Override public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            @Override public Object nextElement() {
                return iterator.next();
            }
        };
    }


    public void setParam(String key, String[] values) {
        getParameterMap().put(key, values);
    }

    public void setParam(String key, String value) {
        setParam(key, new String[] { value } );
    }
}
