package com.infinity.employee.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class DepartmentChangeDeserializer implements Deserializer<DepartmentChangeModel>{

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public DepartmentChangeModel deserialize(String s, byte[] data) {
        try {
            return objectMapper.readValue(new String(data), DepartmentChangeModel.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
