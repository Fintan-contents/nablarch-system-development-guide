package com.nablarch.example.climan.common.jaxrs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import nablarch.integration.jaxrs.jackson.Jackson2BodyConverter;

/**
 * {@link Jackson2BodyConverter}のカスタマイズを行うクラス。
 *
 * @author TIS
 */
public class ClimanJackson2BodyConverter  extends Jackson2BodyConverter {
    @Override
    protected void configure(ObjectMapper objectMapper) {
        super.configure(objectMapper);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }
}
