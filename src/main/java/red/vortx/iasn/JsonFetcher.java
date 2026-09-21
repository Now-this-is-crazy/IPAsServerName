package red.vortx.iasn;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
//? if >=1.18 {
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
//?} else {
/*import java.net.HttpURLConnection;
 *///?}
import java.nio.charset.StandardCharsets;

public final class JsonFetcher {

    //? if >=1.18 {
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    //?}

    private JsonFetcher() {
    }

    public static JsonElement fetch(final String url) throws IOException, InterruptedException {
        //? if >=1.18 {
        final HttpRequest httpRequest = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        final HttpResponse<String> httpResponse = HTTP_CLIENT.send(
            httpRequest,
            HttpResponse.BodyHandlers.ofString()
        );

        return JsonParser.parseString(httpResponse.body());
        //?} else {
        /*final HttpURLConnection connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "");

        try (InputStreamReader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)) {
            return new JsonParser().parse(reader);
        } finally {
            connection.disconnect();
        }
        *///?}
    }

}
