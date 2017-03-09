package org.xiem.com.jackson;


import java.io.IOException;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonObjectSerializer extends JsonSerializer<JSONObject> {

    public JsonObjectSerializer() {
    }

    @Override
	public void serialize(JSONObject value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        String jsonString = value.toString();
        jgen.writeRaw(jsonString.substring(1, jsonString.length() - 1));
        jgen.writeEndObject();
    }
}
