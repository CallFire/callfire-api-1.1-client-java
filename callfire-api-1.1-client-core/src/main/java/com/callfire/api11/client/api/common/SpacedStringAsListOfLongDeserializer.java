package com.callfire.api11.client.api.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpacedStringAsListOfLongDeserializer extends JsonDeserializer<List<Long>> {

    @Override
    public List<Long> deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        String stringToParse = jsonParser.getText();
        List<Long> items = new ArrayList<>();

        if (stringToParse == null || stringToParse == "")
            return items;

        for (String obj : stringToParse.split(" ")) {
            items.add(Long.parseLong(obj));
        }
        return items;
    }

}
