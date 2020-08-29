package org.kikikan.dbmapitest;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kikikan.deadbymoonlight.events.player.both.VaultEvent;
import org.kikikan.deadbymoonlight.game.PerkUser;
import org.kikikan.deadbymoonlight.game.Survivor;
import org.kikikan.deadbymoonlight.perks.CooldownPerk;

@SuppressWarnings({"unused"})
public final class WhooshPerk extends CooldownPerk {

    private int originalScratchMarkLength = 0;

    public WhooshPerk(JavaPlugin plugin, PerkUser p) {
        super(plugin, p, 1200, false);
    }

    public boolean isKiller() {
        return true;
    }

    public boolean isSurvivor() {
        return true;
    }

    public boolean needsObsession() {
        return false;
    }

    public String getName() {
        return "Whoosh";
    }

    public String[] getPerkDescription() {
        return new String[]{
                ChatColor.WHITE + "Your experiences with social anxiety",
                ChatColor.WHITE + "gave you the ability to become",
                ChatColor.WHITE + "invisible to all creatures.",
                "",
                ChatColor.WHITE + "After Vaulting, you leave no Scratch Marks,",
                ChatColor.WHITE + "while also gaining the Invisible Potion Effect",
                ChatColor.WHITE + "for " + ChatColor.YELLOW + "5 seconds" + ChatColor.WHITE + ".",
                "",
                ChatColor.WHITE + "Whoosh has a cooldown of 60 seconds."
        };
    }

    public void onFastVault(VaultEvent event){
        if (!isTurnedOn() && event.player.equals(getPerkUser())){
            on();
            DeadByMoonlightAPITest pl = (DeadByMoonlightAPITest)getPlugin();
            getPerkUser().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0));
            if (getPerkUser() instanceof Survivor){
                Survivor survivor = (Survivor)getPerkUser();
                originalScratchMarkLength = survivor.scratchMarkDuration;
                survivor.scratchMarkDuration = 0;
            }
        }
    }

    @Override
    protected void onCooldownOff() {
        if (getPerkUser() instanceof Survivor){
            Survivor survivor = (Survivor)getPerkUser();
            survivor.scratchMarkDuration = originalScratchMarkLength;
            DeadByMoonlightAPITest pl = (DeadByMoonlightAPITest)getPlugin();
            pl.whoosh(survivor.getPlayer());
        }
    }
}
