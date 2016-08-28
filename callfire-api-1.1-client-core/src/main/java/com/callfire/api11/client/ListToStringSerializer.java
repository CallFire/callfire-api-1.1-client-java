package com.callfire.api11.client;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

public class ListToStringSerializer extends JsonSerializer<List<Object>> {
    @Override
    public void serialize(List<Object> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeString(StringUtils.join(value, " "));
        }
    }
}
