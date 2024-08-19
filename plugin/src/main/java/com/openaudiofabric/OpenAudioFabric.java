package com.openaudiofabric;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenAudioFabric implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("open-audio-fabric");

	public static final String modID = "";

	private static OpenAudioFabric instance;

	public MinecraftServer server;

	public static OpenAudioFabric getInstance()
	{
		return instance;
	}

	public void setServer(MinecraftServer server)
	{
		this.server = server;
	}

	public MinecraftServer getServer()
	{
		return server;
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.



		LOGGER.info("Hello Fabric world!");
	}
}