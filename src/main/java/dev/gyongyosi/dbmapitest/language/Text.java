package dev.gyongyosi.dbmapitest.language;

import dev.gyongyosi.dbm.api.language.LanguageRecord;

public enum Text implements LanguageRecord {
    WHOOSH_PERK_NAME("perks.whoosh.displayName", "Whoosh!"),
    WHOOSH_PERK_DESCRIPTION("perks.whoosh.description", """
            §fYour experiences with social anxiety
            §fgave you the ability to become
            §finvisible to all creatures.
            
            §fAfter Vaulting, you leave no Scratch Marks,
            §fwhile also gaining the Invisible Potion Effect
            §ffor §e{ticks/20} §fseconds. You also become
            §fBlessed for the same duration.
            
            §fIf you are the Killer, you simply become
            §finvisible for the same duration.
            
            §fWhoosh has a cooldown of §e{ticks/20} §fseconds."""),

    REPAIR_5_NAME("gamecomponents.repair5.displayName", "5x Repair Speed"),
    REPAIR_5_DESCRIPTION("gamecomponents.repair5.description", """
            §fThis GameComponent increases
            §fthe repair speed by 5x."""),

    WORKBENCH_NAME("worldobjects.workbench.displayName", "Workbench"),
    WORKBENCH_DESCRIPTION("worldobjects.workbench.description", """
            §fDoesn't really
            §fdo anything..."""),

    LUCKY_CHARM_NAME("items.luckyCharm.displayName", "Lucky Charm"),
    LUCKY_CHARM_DESCRIPTION("items.luckyCharm.description", """
            §fOpening Chests while holding this
            §fguarantees the Chest will contain
            §fan Item with the same rarity level."""),

    LUCKY_CHARM_COMMON_NAME("items.luckyCharm.common.name", "§r§6Bone Charm"),
    LUCKY_CHARM_COMMON_DESCRIPTION("items.luckyCharm.common.description", """
            §fSomeone's bone.
            §fBrings you bad luck."""),

    LUCKY_CHARM_UNCOMMON_NAME("items.luckyCharm.uncommon.name", "§r§eCopper Charm"),
    LUCKY_CHARM_UNCOMMON_DESCRIPTION("items.luckyCharm.uncommon.description", """
            §fA dirty copper nugget.
            §fGives you a miniscule amount of luck."""),

    LUCKY_CHARM_RARE_NAME("items.luckyCharm.rare.name", "§r§2Gold Charm"),
    LUCKY_CHARM_RARE_DESCRIPTION("items.luckyCharm.rare.description", """
            §fA shiny gold nugget.
            §fBrings you good luck."""),

    LUCKY_CHARM_VERY_RARE_NAME("items.luckyCharm.very_rare.name", "§r§5Diamond Charm"),
    LUCKY_CHARM_VERY_RARE_DESCRIPTION("items.luckyCharm.very_rare.description", """
            §fA diamond!
            §fFortune favors you."""),

    LUCKY_CHARM_ULTRA_RARE_NAME("items.luckyCharm.ultra_rare.name", "§r§cStar From the Skies"),
    LUCKY_CHARM_ULTRA_RARE_DESCRIPTION("items.luckyCharm.ultra_rare.description", """
            §fIt is said to belong
            §fto the Goddess of Luck."""),

    BLESS_NAME("statuseffects.blessed.name", "Blessed"),
    BLESS_DESCRIPTION("statuseffects.blessed.description", """
            §fSurvivors with the Blessed Status Effect
            §fheal other Survivors significantly faster."""),

    BLESS_NOTIFICATION_ON("statuseffects.blessed.notification.on", "§aThe Gods' have Blessed you!"),
    BLESS_NOTIFICATION_OFF("statuseffects.blessed.notification.off", "§cYou are no longer Blessed!"),

    ;
    private final String path;
    private final String defaultText;

    Text(String path, String defaultText) {
        this.path = path;
        this.defaultText = defaultText;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getDefaultText() {
        return defaultText;
    }
}
