package com.fplstats.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fplstats.common.dto.adapter.FplJsonObject;

public class AdapterUtility {

    //https://www.baeldung.com/jackson-object-mapper-tutorial
    public static FplJsonObject ParseJsonString(String json) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        FplJsonObject fplJsonObject = objectMapper.readValue(json, FplJsonObject.class);

        return fplJsonObject;
    }
}
