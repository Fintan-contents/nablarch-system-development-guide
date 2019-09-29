package com.nablarch.example.climan.common.jaxrs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import nablarch.fw.Handler;
import nablarch.fw.jaxrs.*;
import nablarch.fw.web.HttpRequest;
import nablarch.integration.jaxrs.jackson.Jackson2BodyConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@link JaxRsHandlerListFactory}のカスタマイズを行うクラス。
 *
 * Jackson2.xの設定を変更する。
 *
 * @author TIS
 */
public class ClimanJaxRsHandlerListFactory implements JaxRsHandlerListFactory {

    /** {@link Handler}のリスト */
    private List<Handler<HttpRequest, ?>> handlerList;

    /**
     * {@link BodyConverter}を設定する。
     * @param bodyConverter {@link BodyConverter}のリスト
     */
    public void setBodyConverter(BodyConverter bodyConverter) {

        final List<Handler<HttpRequest, ?>> list = new ArrayList<Handler<HttpRequest, ?>>();

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
