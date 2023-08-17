package com.aliqornan.inv.match.parser.error;

import java.util.Map;

public class ApplicationException extends Exception {

    private final Map<String, String> metadata;

    public ApplicationException(String message, Map<String, String> metadata) {
        super(message + " with metadata: " + metadata);
        this.metadata = metadata;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }
}
