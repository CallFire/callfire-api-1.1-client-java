package com.callfire.api11.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * JSON serializer/deserializer
 *
 * @since 1.0
 */
public class JsonConverter {
    private ObjectMapper mapper;

    public JsonConverter() {
        mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
        // FIXME uncomment before release
        //        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.ANY);
        mapper.setDateFormat(new SimpleDateFormat(ClientConstants.TIMESTAMP_FORMAT_PATTERN));
    }

    /**
     * Serialize the given Java object into JSON string.
     *
     * @param obj object to serialize
     * @return representation in JSON string
     * @throws CfApi11ClientException in case object cannot be serialized
     */
    public String serialize(Object obj) throws CfApi11ClientException {
        try {
            return obj == null ? null : mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new CfApi11ClientException(e);
        }
    }

    /**
     * Deserialize the given JSON string to Java object.
     *
     * @param body The JSON string
     * @param type The type of deserialized entity
     * @param <T>  type of deserialized entity
     * @return deserialized Java object
     * @throws CfApi11ClientException in case body cannot be deserialized
     */
    public <T> T deserialize(String body, TypeReference<T> type) throws CfApi11ClientException {
        try {
            return mapper.readValue(body, type);
        } catch (IOException e) {
            throw new CfApi11ClientException(e);
        }
    }

    /**
     * Get Jackson's {@link ObjectMapper}
     *
     * @return object mapper
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * Set Jackson's {@link ObjectMapper}
     *
     * @param mapper object mapper
     */
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Converts single string to array of enum values, e.g. "VAL1, VAL2, ..."
     */
    public static class ArrayToStringSerializer<T extends List<T>> extends JsonSerializer<List<T>> {
        @Override
        public void serialize(List<T> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            String asString = StringUtils.join(value, " ");
            gen.writeString(asString);
        }
    }
}
