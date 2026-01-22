package goonball3.silktouchtospawnegg;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

public class ModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            Config config = Config.getInstance();

            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.of("Config"))
                    .setSavingRunnable(config::save);

            ConfigCategory behavior = builder.getOrCreateCategory(Text.of("Behavior"));
            ConfigEntryBuilder cfgent = builder.entryBuilder();


            behavior.addEntry(cfgent.startBooleanToggle(Text.of("Enabled"), config.enabled)
                    .setDefaultValue(true)
                    .setTooltip(Text.of("Enable "))
                    .setSaveConsumer(newValue -> config.enabled = newValue)
                    .build());

            behavior.addEntry(cfgent.startIntField(Text.of("Chance"), config.chance)
                    .setMin(0)
                    .setMax(100)
                    .setDefaultValue(1)
                    .setTooltip(Text.of("The chance of getting a spawn egg when killing a mob with silk touch"))
                    .setSaveConsumer(newValue -> config.chance = newValue)
                    .build());

            return builder.build();
        };
    }
}
