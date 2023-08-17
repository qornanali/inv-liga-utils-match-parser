package com.aliqornan.inv.match.parser.common;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Objects;

public class FileContentReader {
    public static String read(URL url) throws IOException {
        return IOUtils.toString(Objects.requireNonNull(url), Charset.defaultCharset());
    }
}
