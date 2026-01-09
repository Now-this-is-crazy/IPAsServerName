package io.github.redvortexdev.iasn;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class JsonFetcher {

    private JsonFetcher() {
    }

    public static JsonElement fetch(final String url) throws IOException, InterruptedException {
        try (final HttpClient httpClient = HttpClient.newHttpClient()) {

            final HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

            final HttpResponse<String> httpResponse = httpClient.send(
                httpRequest,
                HttpResponse.BodyHandlers.ofString()
            );

            return JsonParser.parseString(httpResponse.body());
        }
    }

}
