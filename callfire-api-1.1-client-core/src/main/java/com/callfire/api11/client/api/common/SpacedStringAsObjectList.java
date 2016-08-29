package com.callfire.api11.client.api.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpacedStringAsObjectList extends JsonDeserializer<List<?>> implements ContextualDeserializer {

    private JavaType valueType;

    @Override
    public JsonDeserializer<List<?>> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        JavaType wrapperType = property.getType();
        JavaType valueType = wrapperType.containedType(0);
        SpacedStringAsObjectList deserializer = new SpacedStringAsObjectList();
        deserializer.valueType = valueType;
        return deserializer;
    }

    @Override
    public List<Object> deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        String stringToParse = jsonParser.getText();
        List<Object> items = new ArrayList<>();

        if (stringToParse == null || stringToParse == "")
            return items;

        Class paramClass = valueType.getRawClass();

        if (paramClass.equals(Long.class)) {
            for (String obj : stringToParse.split(" ")) {
                items.add(Long.parseLong(obj));
            }
            return items;
        } else {
            return Arrays.asList((Object[]) stringToParse.split(" "));
        }
    }

}
