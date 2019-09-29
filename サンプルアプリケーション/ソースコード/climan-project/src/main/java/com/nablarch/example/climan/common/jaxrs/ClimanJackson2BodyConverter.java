package com.nablarch.example.climan.common.jaxrs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import nablarch.integration.jaxrs.jackson.Jackson2BodyConverter;

import java.io.IOException;

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

    /**
     * オブジェクトをJSON文字列に書き出す。
     * @param value オブジェクト
     * @return JSON文字列
     */
    public String write(Object value) {
        try {
            return writeValueAsString(value);
        } catch (IOException e) {
            throw new IllegalArgumentException("failed to write response.", e);
        }
    }
}
