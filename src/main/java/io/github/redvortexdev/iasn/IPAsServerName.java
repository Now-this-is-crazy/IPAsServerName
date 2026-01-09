package io.github.redvortexdev.iasn;

import com.google.gson.JsonElement;
import net.fabricmc.api.ModInitializer;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class IPAsServerName implements ModInitializer {

    public static final String MOD_ID = "iasn";
    public static final String MOD_NAME = "IPAsServerName";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final Component DEFAULT_SERVER_NAME = Component.translatable("selectServer.defaultName");
    public static final ServerMappings SERVER_MAPPINGS = new ServerMappings();
    private static final String SERVER_LIST_CDN = "https://servermappings.lunarclientcdn.com/servers.json";

    @Override
    public void onInitialize() {
        CompletableFuture.runAsync(() -> {
            try {
                JsonElement servers = JsonFetcher.fetch(SERVER_LIST_CDN);
                SERVER_MAPPINGS.populate(servers);
            } catch (IOException | InterruptedException e) {
                LOGGER.error("Failed to fetch server list", e);
            }

            LOGGER.info("Fetched servers!");
        }).exceptionally(e -> {
            LOGGER.error("Failed to fetch/parse server list", e);
            return null;
        });

        LOGGER.info("Initialized");
    }

}
