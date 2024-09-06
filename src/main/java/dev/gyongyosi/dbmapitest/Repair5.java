package dev.gyongyosi.dbmapitest;

import dev.gyongyosi.dbm.api.event.EventHandler;
import dev.gyongyosi.dbm.api.event.MathOperationEnum;
import dev.gyongyosi.dbm.api.event.player.survivor.modifiableevent.GetRepairProgressEvent;
import dev.gyongyosi.dbm.api.game.gamecomponent.GameComponentCore;
import dev.gyongyosi.dbm.api.reflect.Inject;

import java.util.logging.Logger;

@SuppressWarnings({"unused"})
public class Repair5 implements GameComponentCore {
    @Inject
    private Logger logger;

    @EventHandler
    public void repair5(GetRepairProgressEvent event){
        event.addOperation(MathOperationEnum.MULTIPLY, 5);
        // Logging messages into the console.
        logger.info("Repair5 GameComponent has modified a GetRepairProgressEvent.");
    }
}
