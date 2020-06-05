package com.nablarch.example.climan.rest.common.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import nablarch.fw.web.JacksonHttpBodyWriter;

public class ClimanJacksonHttpBodyWriter extends JacksonHttpBodyWriter {
    @Override
    protected void configure(ObjectMapper objectMapper) {
        super.configure(objectMapper);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }
}
