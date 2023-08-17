package com.aliqornan.inv.match.parser.common.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDatetimeTypeAdapter implements JsonDeserializer<OffsetDateTime> {

    @Override
    public OffsetDateTime deserialize(JsonElement json,
                                      Type typeOfT,
                                      JsonDeserializationContext context)
            throws JsonParseException {
        return Instant.ofEpochSecond(json.getAsLong()).atOffset(ZoneOffset.UTC);
    }
}
