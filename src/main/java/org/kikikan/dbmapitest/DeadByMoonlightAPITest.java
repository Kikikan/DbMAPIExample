package org.kikikan.dbmapitest;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.kikikan.deadbymoonlight.DeadByMoonlightAPI;

public class DeadByMoonlightAPITest extends JavaPlugin {

    @Override
    public void onEnable() {
        DeadByMoonlightAPI.addPerk(new WhooshPerk(this, null));
        DeadByMoonlightAPI.addGameComponent(new Repair5Object(this, null));
        DeadByMoonlightAPI.addItem(new LuckyCharmItem(this, null));
    }

    public void whoosh(Player player){
        player.sendMessage("WHOOSH!");
    }
}
