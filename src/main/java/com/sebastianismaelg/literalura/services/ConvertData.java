package com.sebastianismaelg.literalura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class  ConvertData  {
    private final ObjectMapper objectMapper = new ObjectMapper();

    //@Override
    public <T> T obtainData(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json,tClass);

        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);
        }
    }
}
