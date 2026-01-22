package goonball3.silktouchtospawnegg;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Silk Touch To Spawn Egg");

    @Override
    public void onInitialize() {
        // Initialize config instance
        Config.getInstance();
        LOGGER.info("Silk Touch To Spawn Egg, made by goonball3");
    }
}