package dev.gyongyosi.dbmapitest;

import dev.gyongyosi.dbm.api.event.EventHandler;
import dev.gyongyosi.dbm.api.event.MathOperationEnum;
import dev.gyongyosi.dbm.api.event.player.survivor.modifiableevent.GetItemRarityEvent;
import dev.gyongyosi.dbm.api.game.item.Item;
import dev.gyongyosi.dbm.api.game.item.ItemCore;
import dev.gyongyosi.dbm.api.reflect.Inject;

public class LuckyCharm implements ItemCore {

    @Inject
    private Item<LuckyCharm> item;

    @EventHandler
    public void onItemTier(GetItemRarityEvent event){
        item.getHolder().ifPresent(s -> {
            if (event.getDbmPlayer().equals(s) && s.isItemInHands()) {
                if (event.getRarity() == item.getRarity()) {
                    // Subtracting 1 from the threshold, making it guaranteed to get an Item with this rarity.
                    event.addOperation(MathOperationEnum.SUBTRACT, 1);
                    // LuckyCharm did what it was supposed to do, time to decrease its durability.
                    item.decreaseDurability(event);
                } else {
                    // Adding 1 to the threshold, making it impossible to get an Item with this rarity.
                    event.addOperation(MathOperationEnum.ADD, 1);
                }
            }
        });
    }
}
