package dev.gyongyosi.dbmapitest;

import dev.gyongyosi.dbm.api.game.player.DbmPlayer;
import dev.gyongyosi.dbm.api.game.player.InteractionType;
import dev.gyongyosi.dbm.api.game.worldobject.WorldObject;
import dev.gyongyosi.dbm.api.game.worldobject.WorldObjectCore;
import dev.gyongyosi.dbm.api.reflect.Inject;

public class Workbench implements WorldObjectCore {

    @Inject
    private WorldObject<Workbench> worldObject;

    @Override
    public void startInteraction(DbmPlayer player, InteractionType interactionType) {
        // The notify property can be changed by Arena Creators.
        if (Boolean.TRUE.equals(worldObject.getObjectConfigMap().get("notify")))
            player.sendMessage("You have started interacting with the workbench!");
    }
}
