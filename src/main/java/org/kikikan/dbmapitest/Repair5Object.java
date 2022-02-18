package org.kikikan.dbmapitest;

import org.bukkit.plugin.java.JavaPlugin;
import org.kikikan.deadbymoonlight.GameComponent;
import org.kikikan.deadbymoonlight.events.EventHandler;
import org.kikikan.deadbymoonlight.events.player.survivor.modifiableevents.GetRepairSpeedEvent;
import org.kikikan.deadbymoonlight.game.Game;

@SuppressWarnings({"unused"})
public class Repair5Object extends GameComponent {
    public Repair5Object(JavaPlugin plugin, Game game) {
        super(plugin, game);
    }

    @Override
    public String getName() {
        return "Repair 5 Times";
    }

    @Override
    public String getDescription() {
        return "Multiplies the Repair Speed of Survivors by 5.";
    }

    @EventHandler
    public void repair5(GetRepairSpeedEvent event){
        event.setValue(event.getValue() * 5);
    }
}
