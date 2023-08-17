package com.aliqornan.inv.match.parser;

import com.aliqornan.inv.match.parser.common.FileContentReader;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MatchParserIntegrationTest {

    private static WireMockServer wireMockServer;

    @BeforeAll
    public static void setUpAll() {
        wireMockServer = new WireMockServer(8888);
        wireMockServer.start();
    }

    @AfterAll
    public static void tearDownAlll() {
        wireMockServer.stop();
    }

    @BeforeEach
    public void setUpEach() {
        wireMockServer.resetAll();
    }

    @Test
    public void givenUpstreamReturnNotFoundThenRaiseUpstreamFailedException() throws IOException {
        URL responseBodyFileURL = getClass().getResource("/responses/opendota/api/matches/get_404_error.json");
        String stubResponseBody = FileContentReader.read(responseBodyFileURL);
        wireMockServer.stubFor(get(urlEqualTo("/api/matches/foobar"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(HttpURLConnection.HTTP_NOT_FOUND)
                        .withBody(stubResponseBody)));

        assertThrows(RuntimeException.class,
                () -> App.main(new String[]{"foobar"}),
                "Failed when calling OpenDota with metadata: {http_status=404}");
    }

    @Test
    public void givenUpstreamIsUnavailableThenRaiseUpstreamFailedException() throws IOException {
        URL responseBodyFileURL = getClass().getResource("/responses/opendota/api/matches/get_404_error.json");
        String stubResponseBody = FileContentReader.read(responseBodyFileURL);
        wireMockServer.stubFor(get(urlEqualTo("/api/matches/foobar"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(HttpURLConnection.HTTP_UNAVAILABLE)
                        .withBody(stubResponseBody)));

        assertThrows(RuntimeException.class,
                () -> App.main(new String[]{"foobar"}),
                "Failed when calling OpenDota with metadata: {http_status=503}");
    }

    @Test
    public void givenUpstreamIsSuccessThenParseAndGenerateCsvReport() throws IOException, URISyntaxException {
        URL responseBodyFileURL = getClass().getResource("/responses/opendota/api/matches/get_success.json");
        String stubResponseBody = FileContentReader.read(responseBodyFileURL);
        wireMockServer.stubFor(get(urlEqualTo("/api/matches/foobar"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(HttpURLConnection.HTTP_OK)
                        .withBody(stubResponseBody)));

        assertDoesNotThrow(() -> App.main(new String[]{"foobar"}));

        File reportFile = new File("/tmp/playerstat.csv");
        String report = IOUtils.toString(Objects.requireNonNull(reportFile.toURI()), Charset.defaultCharset());
        assertEquals("Date,PLAYER NAME,GAME STATUS,Kill,Death,Assist\n" +
                "12/08/2023,Bima,L,6,7,35\n" +
                "12/08/2023,Agung,L,5,3,28\n" +
                "12/08/2023,Ali,L,14,7,21\n" +
                "12/08/2023,Opik,L,9,8,43\n" +
                "12/08/2023,Danu,L,25,4,16\n" +
                "12/08/2023,Kafin,L,3,17,15\n" +
                "12/08/2023,Fauzi,L,10,6,6\n" +
                "12/08/2023,Nidy,L,9,14,8\n" +
                "12/08/2023,Kemal,L,3,9,16\n" +
                "12/08/2023,rezca,L,3,15,17\n", report);
    }
}
