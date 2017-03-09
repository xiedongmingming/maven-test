package org.xiem.com.baidureport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonProcessor {

    private static final JsonProcessor INSTANCE = new JsonProcessor(false);
    private final JsonFactory factory = new JsonFactory();

    public static JsonProcessor get() {
        return INSTANCE;
    }

    private JsonProcessor(final boolean includeDefaultValues) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);
        if (!includeDefaultValues) {
            objectMapper.setSerializationInclusion(Include.NON_DEFAULT);
        }
        factory.setCodec(objectMapper);
        factory.disable(JsonFactory.Feature.INTERN_FIELD_NAMES);
        factory.enable(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS);
    }

    /*
     * Read JSON object from input stream based on given class
     */
    public <T> T readJsonToObject(InputStream inputStream, Class<T> objectType)
            throws IOException, JsonException
    {
        try {
            JsonParser parser = factory.createParser(inputStream);
            return parser.readValueAs(objectType);
        } catch (JsonParseException e) {
            throw new JsonException(e);
        }
    }

    public <T> T readJsonToObject(String input, Class<T> objectType)
            throws IOException, JsonException
    {
        try {
            JsonParser parser = factory.createParser(input);
            return parser.readValueAs(objectType);
        } catch (JsonParseException e) {
            throw new JsonException(e);
        }

    }

    public <T> T readJsonToObject(String input, TypeReference<T> typeReference) throws IOException {
        JsonParser parser = factory.createParser(input);
        return parser.readValueAs(typeReference);
    }

    public <T> T readJsonToObject(InputStream inputStream,
            TypeReference<T> typeReference) throws IOException {
        JsonParser parser = factory.createParser(inputStream);
        return parser.readValueAs(typeReference);
    }

    protected void generateJsonFromObject(JsonGenerator generator,
            Object jsonObject)
            throws JsonProcessingException, IOException {
        if (jsonObject != null) {
            generator.writeObject(jsonObject);
        }
    }

    public String writeJsonFromObject(Object jsonObject)
            throws IOException, JsonException {
        return getJsonObjectString(jsonObject);
    }

    public String toStringWithDefault(final Object jsonObject,
            final String defaultString) {
        String str = StringUtils.EMPTY;
        try {
            str = writeJsonFromObject(jsonObject);
        } catch (Exception e) {
        }
        if (StringUtils.isBlank(str)) {
            str = defaultString;
        }
        return str;
    }

    public byte[] getJsonObjectBytes(Object jsonObject) throws IOException,
            JsonException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writeJsonFromObject(baos, jsonObject);
        return baos.toByteArray();
    }

    public String getJsonObjectString(Object jsonObject) throws IOException, JsonException {
        StringWriter sw = new StringWriter();
        writeJsonFromObject(sw, jsonObject);
        return sw.toString();
    }

    public void writeJsonFromObject(OutputStream outputStream, Object jsonObject)
            throws IOException, JsonException
    {
        JsonGenerator generator =
                factory.createGenerator(outputStream, JsonEncoding.UTF8);
        try {
            generateJsonFromObject(generator, jsonObject);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }

    public void writeJsonFromObject(Writer writer, Object jsonObject) throws IOException, JsonException {
        JsonGenerator generator = factory.createGenerator(writer);
        try {
            generateJsonFromObject(generator, jsonObject);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }

    /**
     * To wrap JsonProcessingException so that it won't be subclass of
     * IOException
     *
     */
    public static class JsonException extends Exception {
        private static final long serialVersionUID = -8326077007255099042L;
        final JsonProcessingException exception;

        public JsonException(final JsonProcessingException exception) {
            this.exception = exception;
        }

        @Override
        public String getMessage() {
            return exception.getMessage();
        }

        @Override
        public Throwable getCause() {
            return exception.getCause();
        }

        @Override
        public String toString() {
            return exception.toString();
        }

        @Override
        public void printStackTrace() {
            exception.printStackTrace();
        }

    }
}
