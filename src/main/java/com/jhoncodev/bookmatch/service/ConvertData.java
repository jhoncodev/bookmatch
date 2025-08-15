package com.jhoncodev.bookmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> myClass) {
        try {
            return objectMapper.readValue(json, myClass);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
