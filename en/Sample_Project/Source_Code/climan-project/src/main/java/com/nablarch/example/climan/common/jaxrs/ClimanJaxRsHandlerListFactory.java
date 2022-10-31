package com.nablarch.example.climan.common.jaxrs;

import nablarch.fw.Handler;
import nablarch.fw.jaxrs.*;
import nablarch.fw.web.HttpRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for customizing {@link JaxRsHandlerListFactory}.
 *
 * Changes the setting of Jackson2.x.
 *
 * @author Kiyohito Itoh
 */
public class ClimanJaxRsHandlerListFactory implements JaxRsHandlerListFactory {

    /** List for {@link Handler} */
    private List<Handler<HttpRequest, ?>> handlerList;

    /**
     * Set {@link BodyConverter}.
     * @param bodyConverter List for {@link BodyConverter}
     */
    public void setBodyConverter(BodyConverter bodyConverter) {

        final List<Handler<HttpRequest, ?>> list = new ArrayList<>();

        final BodyConvertHandler bodyConvertHandler = new BodyConvertHandler();
        bodyConvertHandler.addBodyConverter(bodyConverter);
        list.add(bodyConvertHandler);

        list.add(new JaxRsBeanValidationHandler());

        handlerList = Collections.unmodifiableList(list);
    }

    @Override
    public List<Handler<HttpRequest, ?>> createObject() {
        return handlerList;
    }
}
