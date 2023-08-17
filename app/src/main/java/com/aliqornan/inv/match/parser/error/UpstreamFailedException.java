package com.aliqornan.inv.match.parser.error;

import java.util.Map;

public class UpstreamFailedException extends ApplicationException {
    public UpstreamFailedException(Map<String, String> metadata) {
        super("Failed when calling OpenDota", metadata);
    }
}
