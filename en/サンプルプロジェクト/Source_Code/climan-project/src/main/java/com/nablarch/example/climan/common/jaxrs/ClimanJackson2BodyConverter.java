package com.nablarch.example.climan.common.jaxrs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import nablarch.integration.jaxrs.jackson.Jackson2BodyConverter;

import java.io.IOException;

/**
 * Class for customizing {@link Jackson2BodyConverter}.
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
     * The object is written in the JSON character string.
     * @param value: Object
     * @return: JSON character string
     */
    public String write(Object value) {
        try {
            return writeValueAsString(value);
        } catch (IOException e) {
            throw new IllegalArgumentException("failed to write response.", e);
        }
    }
}
