package dev.gyongyosi.dbmapitest;

import dev.gyongyosi.dbm.api.event.EventHandler;
import dev.gyongyosi.dbm.api.event.MathOperationEnum;
import dev.gyongyosi.dbm.api.event.player.survivor.modifiableevent.GetHealProgressEvent;
import dev.gyongyosi.dbm.api.game.statuseffect.StatusEffect;
import dev.gyongyosi.dbm.api.game.statuseffect.StatusEffectCore;
import dev.gyongyosi.dbm.api.reflect.Inject;

public class Blessed implements StatusEffectCore {

    @Inject
    private StatusEffect<Blessed> statusEffect;

    @EventHandler
    public void onHeal(GetHealProgressEvent e) {
        if (e.getDbmPlayer().equals(statusEffect.getDbmPlayer()) && !e.isSelfHeal() && statusEffect.isActive()) {
            e.addOperation(MathOperationEnum.MULTIPLY, 5);
        }
    }
}
