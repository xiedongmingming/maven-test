package org.xiem.com.jackson.yc;


import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonObjectDeserializer extends JsonDeserializer<JSONObject> {


    @Override
    public JSONObject deserialize(JsonParser jsonParser,
            DeserializationContext context) throws IOException,
            JsonProcessingException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        JSONObject json = null;
        try {
            json = new JSONObject(jsonNode.toString());
        } catch (JSONException e) {

        }
        return json;
    }
}
