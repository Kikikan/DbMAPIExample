package dev.gyongyosi.dbmapitest;

import dev.gyongyosi.dbm.api.ApiHolder;
import dev.gyongyosi.dbm.api.config.*;
import dev.gyongyosi.dbm.api.extension.ExtensionBuilder;
import dev.gyongyosi.dbm.api.game.player.DbmPlayerTypeEnum;
import dev.gyongyosi.dbm.api.language.MapLanguageFile;
import dev.gyongyosi.dbm.api.util.NestedLinkedHashMap;
import dev.gyongyosi.dbmapitest.language.Text;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DeadByMoonlightAPITest extends JavaPlugin {

    @Override
    public void onLoad() {
        // Creating the ExtensionBuilder.
        var extensionBuilder = new ExtensionBuilder(getLogger());

        // Creating the Language map.
        var textMap = new NestedLinkedHashMap();
        for (Text value : Text.values()) {
            textMap.put(value.getPath(), value.getDefaultText());
        }

        // Adding the LanguageFile.
        extensionBuilder.addLanguageFile(new MapLanguageFile(Locale.ENGLISH, textMap));

        // Creating PerkConfig.
        var perkConfig = createPerkConfig();
        // Setting the Extension's PerkConfig to our newly created config.
        extensionBuilder.setConfig(PerkConfig.class, perkConfig);

        var statusEffectConfig = createStatusEffectConfig();
        extensionBuilder.setConfig(StatusEffectConfig.class, statusEffectConfig);

        var gameComponentConfig = createGameComponentConfig();
        extensionBuilder.setConfig(GameComponentConfig.class, gameComponentConfig);

        var itemConfig = createItemConfig();
        extensionBuilder.setConfig(ItemConfig.class, itemConfig);

        var worldObjectConfig = createWorldObjectConfig();
        extensionBuilder.setConfig(WorldObjectConfig.class, worldObjectConfig);

        // Registering the Extension.
        ApiHolder.getApi().registerExtension(extensionBuilder.build());
    }

    private Map<String, Object> createPerkConfig() {
        var perkConfig = new HashMap<String, Object>();
        // Setting the required fields.
        perkConfig.put("whoosh.classpath", "dev.gyongyosi.dbmapitest.Whoosh");
        perkConfig.put("whoosh.playerTypes", List.of(DbmPlayerTypeEnum.KILLER.toString(), DbmPlayerTypeEnum.SURVIVOR.toString()));
        // Creating a custom field. You could fill this value from a configuration file which the server administrators can edit.
        // Custom fields like this are automatically inserted into the description of the Component.
        perkConfig.put("whoosh.ticks", 100L);
        return perkConfig;
    }

    private Map<String, Object> createStatusEffectConfig() {
        var statusEffectConfig = new HashMap<String, Object>();
        statusEffectConfig.put("blessed.classpath", "dev.gyongyosi.dbmapitest.Blessed");
        statusEffectConfig.put("blessed.playerTypes", List.of(DbmPlayerTypeEnum.SURVIVOR.toString()));
        return statusEffectConfig;
    }

    private Map<String, Object> createGameComponentConfig() {
        var gameComponentConfig = new HashMap<String, Object>();
        // Setting the required fields.
        gameComponentConfig.put("repair5.classpath", "dev.gyongyosi.dbmapitest.Repair5");
        return gameComponentConfig;
    }

    private Map<String, Object> createItemConfig() {
        var itemConfig = new HashMap<String, Object>();

        itemConfig.put("luckyCharm.classpath", "dev.gyongyosi.dbmapitest.LuckyCharm");
        itemConfig.put("luckyCharm.rarity.COMMON.material", "BONE");
        itemConfig.put("luckyCharm.rarity.COMMON.durability", 5);
        itemConfig.put("luckyCharm.rarity.UNCOMMON.material", "COPPER_NUGGET");
        itemConfig.put("luckyCharm.rarity.UNCOMMON.durability", 3);
        itemConfig.put("luckyCharm.rarity.RARE.material", "GOLD_NUGGET");
        itemConfig.put("luckyCharm.rarity.RARE.durability", 2);
        itemConfig.put("luckyCharm.rarity.VERY_RARE.material", "DIAMOND");
        itemConfig.put("luckyCharm.rarity.VERY_RARE.durability", 1);
        itemConfig.put("luckyCharm.rarity.ULTRA_RARE.material", "NETHER_STAR");
        itemConfig.put("luckyCharm.rarity.ULTRA_RARE.durability", 1);

        return itemConfig;
    }

    private Map<String, Object> createWorldObjectConfig() {
        var worldObjectConfig = new HashMap<String, Object>();
        // Setting the required fields.
        worldObjectConfig.put("workbench.classpath", "dev.gyongyosi.dbmapitest.Workbench");
        worldObjectConfig.put("workbench.material", "CRAFTING_TABLE");
        // ArenaGlobalConfig properties are global, meaning every "possible position"
        // of this WorldObject will have the same value
        // 'defaultArenaGlobalConfigs.spawn.amount' is a required property, but
        // you can create other custom properties.
        worldObjectConfig.put("workbench.defaultArenaGlobalConfigs.spawn.amount", 1);
        // ArenaObjectConfig properties can be modified from placement to placement,
        // meaning every "possible position" of this WorldObject can have
        // different values.
        // There are no required object properties, but you can create custom properties.
        worldObjectConfig.put("workbench.defaultArenaObjectConfigs.notify", true);
        return worldObjectConfig;
    }
}
