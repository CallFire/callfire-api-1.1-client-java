package com.callfire.api11.client.api.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpacedStringAsListDeserializer extends JsonDeserializer<List<String>> {

    @Override
    public List<String> deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        String stringToParse = jsonParser.getText();
        List<String> items = new ArrayList<>();

        if (stringToParse == null || stringToParse == "")
            return items;

        return Arrays.asList(stringToParse.split(" "));
    }

}
