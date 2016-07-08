package com.callfire.api11.client;

import com.callfire.api11.client.api.common.QueryParamIgnore;
import com.callfire.api11.client.api.common.QueryParamName;
import com.callfire.api11.client.api.common.QueryParamObject;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.callfire.api11.client.api.common.model.ToNumber;
import com.callfire.api11.client.api.contacts.model.Contact;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.callfire.api11.client.ClientConstants.TIMESTAMP_FORMAT_PATTERN;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE;

/**
 * Utility class
 *
 * @since 1.0
 */
public final class ClientUtils {
    private ClientUtils() {
    }

    /**
     * Add query param to name-value query list if it's value not null
     *
     * @param name        parameter name
     * @param value       parameter value
     * @param queryParams parameters list
     */
    public static void addQueryParamIfSet(String name, Object value, List<NameValuePair> queryParams) {
        if (name != null && value != null && queryParams != null) {
            if (value instanceof Date) {
                Date date = (Date) value;
                queryParams.add(new BasicNameValuePair(name, String.valueOf(date.getTime())));
            } else if (value instanceof Collection) {
                for (Object item : (Collection) value) {
                    if (item instanceof Contact) {
                        readObject(item, queryParams);
                    } else {
                        queryParams.add(new BasicNameValuePair(name, Objects.toString(item)));
                    }
                }
            } else {
                queryParams.add(new BasicNameValuePair(name, Objects.toString(value)));
            }
        }
    }

    /**
     * Makes list of query parameter pairs
     *
     * @param name  parameter name
     * @param value parameter value
     * @return list of name-value pairs
     */
    public static List<NameValuePair> asParams(String name, Object value) {
        List<NameValuePair> params = new ArrayList<>();
        addQueryParamIfSet(name, value, params);
        return params;
    }

    /**
     * Makes list of query parameter pairs
     *
     * @param name1  first parameter name
     * @param value1 first parameter value
     * @param name2  second parameter name
     * @param value2 second parameter value
     * @return list of name-value pairs
     */
    public static List<NameValuePair> asParams(String name1, Object value1, String name2, Object value2) {
        List<NameValuePair> params = asParams(name1, value1);
        addQueryParamIfSet(name2, value2, params);
        return params;
    }

    /**
     * Method traverses request object using reflection and build {@link List} of {@link NameValuePair} from it
     *
     * @param request request
     * @param <T>     type of request
     * @return list contains query parameters
     * @throws CfApi11ClientException in case IllegalAccessException occurred
     */
    public static <T extends CfApi11Model> List<NameValuePair> buildQueryParams(T request)
        throws CfApi11ClientException {
        List<NameValuePair> params = new ArrayList<>();
        readObject(request, params);
        return params;
    }

    private static void readField(Object request, List<NameValuePair> params, Field field)
        throws IllegalAccessException {
        field.setAccessible(true);
        if (field.isAnnotationPresent(QueryParamIgnore.class)) {
            return;
        }
        Object value = field.get(request);
        if (field.isAnnotationPresent(QueryParamObject.class) && value != null) {
            readObject(value, params);
            return;
        }
        if (value != null) {
            String name = getParamName(field);
            if (value instanceof Iterable) {
                // object index
                int i = 0;
                for (Object o : (Iterable) value) {
                    if (o instanceof ToNumber) {
                        params.add(new BasicNameValuePair(name, ((ToNumber) o).toQueryString()));
                    } else if (o instanceof Contact) {
                        params.addAll(((Contact) o).serializeToMap(i));
                    } else {
                        params.add(new BasicNameValuePair(name, o.toString()));
                    }
                    i++;
                }
                return;
            }
            if (value instanceof Date) {
                if (field.isAnnotationPresent(JsonFormat.class)) {
                    JsonFormat ann = field.getAnnotation(JsonFormat.class);
                    value = new SimpleDateFormat(ann.pattern()).format(value);
                } else {
                    value = new SimpleDateFormat(TIMESTAMP_FORMAT_PATTERN).format(value);
                }
            }
            params.add(new BasicNameValuePair(name, value.toString()));
        }
    }

    private static void readObject(Object request, List<NameValuePair> params) {
        Class<?> superclass = request.getClass().getSuperclass();
        while (superclass != null) {
            readFields(request, params, superclass);
            superclass = superclass.getSuperclass();
        }
        readFields(request, params, request.getClass());
    }

    private static void readFields(Object request, List<NameValuePair> params, Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            try {
                readField(request, params, field);
            } catch (IllegalAccessException e) {
                throw new CfApi11ClientException(e);
            }
        }
    }

    private static String getParamName(Field field) {
        String paramName = field.getName();
        QueryParamName ann = field.getAnnotation(QueryParamName.class);
        if (ann != null) {
            paramName = ann.value();
        } else {
            // PascalCase properties by default
            paramName = PASCAL_CASE_TO_CAMEL_CASE.nameForField(null, null, paramName);
        }
        return paramName;
    }

    public static <T extends Enum> List<T> deserializeEnumString(String input, Class<T> type) {
        List<T> result = new ArrayList<>();
        for (String value : StringUtils.split(input, " ")) {
            result.add((T) Enum.valueOf(type, value));
        }

        return result;
    }
}
