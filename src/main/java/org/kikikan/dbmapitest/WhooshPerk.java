package org.kikikan.dbmapitest;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kikikan.deadbymoonlight.LanguageManager;
import org.kikikan.deadbymoonlight.cooldowns.CustomCooldown;
import org.kikikan.deadbymoonlight.events.player.both.VaultEvent;
import org.kikikan.deadbymoonlight.events.player.survivor.modifiableevents.GetScratchMarkDurationEvent;
import org.kikikan.deadbymoonlight.game.PerkUser;
import org.kikikan.deadbymoonlight.game.Survivor;
import org.kikikan.deadbymoonlight.perks.CooldownPerk;
import org.kikikan.deadbymoonlight.util.PointCategory;

@SuppressWarnings({"unused"})
public final class WhooshPerk extends CooldownPerk { // No need to implement CooldownController, as CooldownPerk has already implemented it.

    private final int ticks;
    private final CustomCooldown scratchMarkTimer;

    public WhooshPerk(JavaPlugin plugin, PerkUser p) {
        super(plugin, p, false);
        ticks = (int)getValueFromConfig("ticks", 100); // Custom setting that can be changed in the perks.yml; If the id "time" is not in the file yet, it will write the default value (100) into the file and will return it.
        scratchMarkTimer = new CustomCooldown(getPlugin(), this, ticks);
    }

    @Override
    public boolean isKiller() {
        return true;
    }

    @Override
    public boolean isSurvivor() {
        return true;
    }

    @Override
    public int amountOfTotemsRequired() {return 0;}

    @Override
    public String getName() {
        return "Whoosh";
    }

    public void onFastVault(VaultEvent event){
        if (!isTurnedOn() && event.player.equals(getPerkUser())){
            on(); // Turns on the Cooldown.
            DeadByMoonlightAPITest pl = (DeadByMoonlightAPITest)getPlugin();
            pl.whoosh(getPerkUser().getPlayer()); // Uses a method from the main class.
            getPerkUser().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, ticks, 0));
            getPerkUser().addPoint(PointCategory.DEVIOUSNESS, 100, LanguageManager.getLanguageFile(getPlugin()).getString("perks.whoosh.whooshPoint"), true); // We don't know if the PerkUser is a Survivor or Killer, but it does not matter. Deviousness is the second category for the Killer, so if the PerkUser is a Survivor, the Survivor will get points in the SURVIVAL category. (Which is the second category for the Survivors)
            if (getPerkUser() instanceof Survivor){
                Survivor s = (Survivor)getPerkUser();
                s.resetScratchMarkDuration(); // Forces GetScratchMarkDurationEvent to be called.
                scratchMarkTimer.on(); // Turns on the scratch mark timer, which is set to be turned off after 100 ticks = 5 seconds.
            }
        }
    }

    @Override
    protected void onCooldownOff(CustomCooldown cooldown) {
        if (cooldown == scratchMarkTimer && getPerkUser() instanceof Survivor){
            Survivor survivor = (Survivor)getPerkUser();
            survivor.resetScratchMarkDuration(); // Forces GetScratchMarkDurationEvent to be called.
        }
    }

    @Override
    protected int getCooldownTime() {
        return 1200;
    }

    public void onScratchMarkEvent(GetScratchMarkDurationEvent event){
        if (scratchMarkTimer.isRunning()){
            event.setValue(0);
        }
    }
}
