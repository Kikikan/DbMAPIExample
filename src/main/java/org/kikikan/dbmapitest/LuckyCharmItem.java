package org.kikikan.dbmapitest;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.kikikan.deadbymoonlight.events.player.survivor.GetItemTierEvent;
import org.kikikan.deadbymoonlight.game.Item;
import org.kikikan.deadbymoonlight.game.Survivor;

import java.util.Random;

public class LuckyCharmItem extends Item {
    public LuckyCharmItem(JavaPlugin plugin, Survivor generatorSurvivor) {
        super(plugin, generatorSurvivor);
    }

    @Override
    public String getItemName() {
        switch (getTier()){
            case COMMON:
                return "Dirty Cobblestone";
            case UNCOMMON:
                return "Some Coal";
            case RARE:
                return "Heavy Iron Ingot";
            case VERY_RARE:
                return "Shiny Gold Bar";
            case ULTRA_RARE:
                return "AN EPIC DIAMOND";
            default:
                return "";
        }
    }

    @Override
    public String getDescription() {
        return "Increases your chances of opening ultra rare Items from Chests.";
    }

    @Override
    public String getName() {
        return "Lucky Charm";
    }

    @Override
    protected void calculateStats() {
        float minModifier = 0;
        float maxModifier = 0;
        int dur = 0;
        switch (getTier()){
            case COMMON:
                minModifier = 3.0f;
                maxModifier = 3.5f;
                dur = 1;
                break;
            case UNCOMMON:
                minModifier = 3.8f;
                maxModifier = 4.5f;
                dur = 1;
                break;
            case RARE:
                minModifier = 6f;
                maxModifier = 6.7f;
                dur = 2;
                break;
            case VERY_RARE:
                minModifier = 7f;
                maxModifier = 7.4f;
                dur = 2;
                break;
            case ULTRA_RARE:
                minModifier = 10f;
                maxModifier = 12f;
                dur = 3;
                break;
        }
        setDurability(dur);
        setModifier(new Random().nextFloat() * (maxModifier - minModifier) + minModifier);
    }

    @Override
    protected Material getMaterial() {
        switch (getTier()){
            case COMMON:
                return Material.COBBLESTONE;
            case UNCOMMON:
                return Material.COAL;
            case RARE:
                return Material.IRON_INGOT;
            case VERY_RARE:
                return Material.GOLD_INGOT;
            case ULTRA_RARE:
                return Material.DIAMOND;
            default:
                return null;
        }
    }

    public void onItemTier(GetItemTierEvent event){
        event.multiplyUltraRareThreshold(getModifier());
        removeDurability();
    }
}
