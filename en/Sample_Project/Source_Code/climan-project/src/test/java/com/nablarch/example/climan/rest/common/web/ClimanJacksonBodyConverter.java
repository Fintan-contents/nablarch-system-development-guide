package com.nablarch.example.climan.rest.common.web;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import nablarch.fw.web.JacksonBodyConverter;

public class ClimanJacksonBodyConverter extends JacksonBodyConverter {
    public ClimanJacksonBodyConverter() {
        super(new SnakeCaseObjectMapperFactory());
    }

    private static class SnakeCaseObjectMapperFactory implements ObjectMapperFactory {
        @Override
        public ObjectMapper create() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.getFactory().configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true);
            return objectMapper;
        }
    }
}
