package io.github.redvortexdev.iasn;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPAsServerName implements ModInitializer {
	public static final String MOD_ID = "iasn";
	public static final String MOD_NAME = "IPAsServerName";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	@Override
	public void onInitialize() {
		LOGGER.info(MOD_NAME + " Initialized!");
	}
}