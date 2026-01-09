package io.github.redvortexdev.iasn;

import com.google.common.net.InternetDomainName;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ServerMappings {

    private final Map<String, String> serverMap = new HashMap<>();

    public static Optional<String> clean(final String serverAddress) {

        if (serverAddress == null || serverAddress.isBlank()) {
            return Optional.empty();
        }

        final String host = serverAddress.split(":", 2)[0];

        try {
            final InternetDomainName domainName = InternetDomainName.from(host);

            if (domainName.isUnderPublicSuffix()) {
                return Optional.of(domainName.topPrivateDomain().toString());
            }

            return Optional.of(host);
        } catch (final IllegalArgumentException exception) {
            return Optional.empty();
        }
    }

    public void populate(final JsonElement servers) {
        servers.getAsJsonArray().forEach(element -> {
            final JsonObject server = element.getAsJsonObject();
            final String name = server.get("name").getAsString();
            final String address = server.get("primaryAddress").getAsString();

            Optional<String> cleanAddress = clean(address);
            if (cleanAddress.isEmpty()) {
                return;
            }

            serverMap.put(cleanAddress.get(), name);
        });
    }

    public Optional<String> getServerName(final String serverAddress) {
        return clean(serverAddress).map(serverMap::get);
    }

}
