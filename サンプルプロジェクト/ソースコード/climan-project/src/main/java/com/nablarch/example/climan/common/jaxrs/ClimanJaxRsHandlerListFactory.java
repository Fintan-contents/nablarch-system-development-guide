package com.nablarch.example.climan.common.jaxrs;

import nablarch.fw.Handler;
import nablarch.fw.jaxrs.*;
import nablarch.fw.web.HttpRequest;

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
