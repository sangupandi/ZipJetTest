package com.test.zipjettest.Network;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.test.zipjettest.PlaceItem;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kavya, 17-04-2015.
 */
public class JsonParser {
    private ObjectMapper mapper = new ObjectMapper();

    public JsonParser() {
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    }

    public List<PlaceItem> parseStreamList(InputStream responseBody)
            throws IOException, JSONException {
        List<PlaceItem> placeList = new ArrayList<>();

        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
        JsonNode node = mapper.readTree(responseBody);

        Iterator iterator = node.elements();
        while (iterator.hasNext()) {
            JsonNode node1 = (JsonNode) iterator.next();
            JsonNode node2 = node1.get("geo_position");

            PlaceItem stream = new PlaceItem(node1.get("country").asText(),
                    node1.get("name").asText(),
                    node2.get("latitude").asDouble(),
                    node2.get("longitude").asDouble());
            placeList.add(stream);
        }
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

        return placeList;
    }
}