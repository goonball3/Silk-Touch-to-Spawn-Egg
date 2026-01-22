package goonball3.silktouchtospawnegg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class Config {
	private static final Path file = FabricLoader.getInstance().getConfigDir().resolve("silk-touch-to-egg.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Config instance;
	
	public boolean enabled = true;
	public int chance = 1;

	public void save() {
        try {
            Files.writeString(file, GSON.toJson(this));
        } catch (IOException e) {
            Main.LOGGER.error("Silk Touch To Spawn Egg could not save the config.");
            throw new RuntimeException(e);
        }
    }

	public static Config getInstance() {
        if (instance == null) {
            try {
                instance = GSON.fromJson(Files.readString(file), Config.class);
            } catch (IOException exception) {
                Main.LOGGER.warn("Silk Touch To Spawn Egg couldn't load the config, using defaults.");
                instance = new Config();
            }
        }

        return instance;
    }
	
}
