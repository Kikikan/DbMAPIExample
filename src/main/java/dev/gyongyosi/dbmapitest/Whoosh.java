package dev.gyongyosi.dbmapitest;

import dev.gyongyosi.dbm.api.event.EventHandler;
import dev.gyongyosi.dbm.api.event.global.TrialStartedEvent;
import dev.gyongyosi.dbm.api.event.player.both.VaultEvent;
import dev.gyongyosi.dbm.api.event.player.survivor.modifiableevent.GetScratchMarkDurationEvent;
import dev.gyongyosi.dbm.api.game.perk.Perk;
import dev.gyongyosi.dbm.api.game.perk.PerkCore;
import dev.gyongyosi.dbm.api.game.perk.PerkStatusEnum;
import dev.gyongyosi.dbm.api.game.player.Killer;
import dev.gyongyosi.dbm.api.game.player.PointCategoryEnum;
import dev.gyongyosi.dbm.api.game.player.Survivor;
import dev.gyongyosi.dbm.api.game.toggleable.Toggleable;
import dev.gyongyosi.dbm.api.game.toggleable.ToggleableEventTypeEnum;
import dev.gyongyosi.dbm.api.language.LanguageManager;
import dev.gyongyosi.dbm.api.reflect.Inject;
import dev.gyongyosi.dbmapitest.language.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SuppressWarnings({"unused"})
public class Whoosh implements PerkCore {

    @Inject
    private Perk<Whoosh> perk;

    private long ticks = 100;
    private Toggleable scratchMarkTimer;

    @EventHandler
    public void onStart(TrialStartedEvent e) {
        // Overriding the default value with the value inside the configuration file.
        ticks = ((Number)perk.getConfig().get("ticks")).longValue();
        // Creating a toggleable for implementing the cooldown part of the Perk.
        scratchMarkTimer = e.getTrial().createToggleable(ticks);
        scratchMarkTimer.addRunnable(ToggleableEventTypeEnum.OFF, this::onCooldownOff);
        perk.getPerkItem().setStatus(PerkStatusEnum.DISABLED);
    }

    @EventHandler
    public void onFastVault(VaultEvent event){
        if (!scratchMarkTimer.isOn() && event.getDbmPlayer().equals(perk.getDbmPlayer())){
            // Turns on the Toggleable.
            // The Toggleable will automatically turn off after the specified amount of ticks has passed.
            scratchMarkTimer.on();
            // Getting a String value from LanguageManager
            event.getDbmPlayer().sendMessage(LanguageManager.getInstance().getString(Text.WHOOSH_PERK_NAME));
            // Getting the Bukkit Player object from the DbmPlayer object.
            Player player = Bukkit.getPlayer(event.getDbmPlayer().getId());
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, (int)ticks, 0));
            // Giving the player points.
            event.getDbmPlayer().addPoints(event.getDbmPlayer() instanceof Killer ? PointCategoryEnum.DEVIOUSNESS : PointCategoryEnum.SURVIVAL, 100, LanguageManager.getInstance().getString("perks.whoosh.whooshPoint", "Whoosh!"), true);
            if (event.getDbmPlayer() instanceof Survivor s){
                s.calculateScratchMarkDuration(); // Forces Scratch Mark duration recalculation.
                s.getStatusEffectOfType(Blessed.class).ifPresent(
                        b -> b.on(ticks)
                );
            }
            perk.getPerkItem().setStatus(PerkStatusEnum.ENABLED);
        }
    }

    private void onCooldownOff() {
        if (perk.getDbmPlayer() instanceof Survivor survivor){
            survivor.calculateScratchMarkDuration(); // Forces Scratch Mark duration recalculation.
        }
        perk.getPerkItem().setStatus(PerkStatusEnum.DISABLED);
    }

    @EventHandler
    public void onScratchMarkEvent(GetScratchMarkDurationEvent event){
        if (scratchMarkTimer.isOn()){
            // Cancelling the event makes the value inside a Modifiable Event 0.
            event.setCancelled(true);
        }
    }
}
