package com.usargis.usargisapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestUtils {
    public static JsonPatch stringToJsonPatch(String string) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final InputStream in = new ByteArrayInputStream(string.getBytes());
        return mapper.readValue(in, JsonPatch.class);
    }
}
