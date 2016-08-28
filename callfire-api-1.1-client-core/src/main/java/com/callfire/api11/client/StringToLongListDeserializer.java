package com.callfire.api11.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StringToLongListDeserializer extends JsonDeserializer<List<Long>> {

    @Override
    public List<Long> deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        String string = jsonParser.getText();
        List<Long> result = new ArrayList<>();
        if (StringUtils.isEmpty(string)) {
            return result;
        }

        for (String s : string.split(" ")) {
            result.add(NumberUtils.toLong(s));
        }
        return result;
    }
}
