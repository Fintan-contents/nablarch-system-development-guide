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
    private final List<Handler<HttpRequest, ?>> handlerList;

    /**
     * {@link JaxRsHandlerListFactory}を生成する。
     */
    public ClimanJaxRsHandlerListFactory() {

        final List<Handler<HttpRequest, ?>> list = new ArrayList<Handler<HttpRequest, ?>>();

        final BodyConvertHandler bodyConvertHandler = new BodyConvertHandler();
        bodyConvertHandler.addBodyConverter(new ClimanJackson2BodyConverter());
        bodyConvertHandler.addBodyConverter(new JaxbBodyConverter());
        bodyConvertHandler.addBodyConverter(new FormUrlEncodedConverter());
        list.add(bodyConvertHandler);

        list.add(new JaxRsBeanValidationHandler());

        handlerList = Collections.unmodifiableList(list);
    }

    /**
     * {@link Jackson2BodyConverter}のカスタマイズを行うクラス。
     *
     * @author TIS
     */
    public static final class ClimanJackson2BodyConverter extends Jackson2BodyConverter {
        @Override
        protected void configure(ObjectMapper objectMapper) {
            super.configure(objectMapper);
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        }
    }

    @Override
    public List<Handler<HttpRequest, ?>> createObject() {
        return handlerList;
    }
}
