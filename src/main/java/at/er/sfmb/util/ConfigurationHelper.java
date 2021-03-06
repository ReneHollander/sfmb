package at.er.sfmb.util;

import at.er.sfmb.plugin.BattlePlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationHelper {

    public static final String GAME_STARTERLIFES_PATH = "game.starterlifes";
    public static final String GAME_WOOL_REMOVERADIUS_PATH = "game.wool.removeradius";
    public static final String GAME_WOOL_MINPLACEHEIGHT_PATH = "game.wool.minplaceheight";
    public static final String GAME_WOOL_TIMETOPLACE_PATH = "game.wool.timetoplace";
    public static final String GAME_INVINCIBILITY_DURATION_PATH = "game.invincibility.duration";
    public static final String GAME_INVINCIBILITY_LOSEONITEMPICKUP = "game.invincibility.loseonitempickup";
    public static final String GAME_BASEBLOCK_ENABLED_PATH = "game.baseblock.enabled";
    public static final String GAME_BASEBLOCK_CONTENTS_PATH = "game.baseblock.contents";
    public static final String TIMER_REMINDER_ENABLED_PATH = "timer.reminder.enabled";
    public static final String MISC_AUTOREPSAWN_ENABLED_PATH = "misc.autorespawn.enabled";
    public static final String MISC_AUTOREPSAWN_DELAY_PATH = "misc.autorespawn.delay";
    public static final String MISC_STARTCOUNTDOWN_ENABLED_PATH = "misc.startcountdown.enabled";
    public static final String MISC_STARTCOUNTDOWN_DURATION_PATH = "misc.startcountdown.duration";

    private FileConfiguration configFile;

    public ConfigurationHelper() {
        this.configFile = BattlePlugin.instance().getConfig();

        this.setupDefaultConfig();
    }

    public void setupDefaultConfig() {
        this.configFile.options().header("YT-Battle Plugin by EXSolo and Rene8888");

        this.configFile.addDefault(GAME_STARTERLIFES_PATH, 10);

        this.configFile.addDefault(GAME_WOOL_REMOVERADIUS_PATH, 2);
        this.configFile.addDefault(GAME_WOOL_MINPLACEHEIGHT_PATH, 50);
        this.configFile.addDefault(GAME_WOOL_TIMETOPLACE_PATH, 15);

        this.configFile.addDefault(GAME_INVINCIBILITY_DURATION_PATH, 10);
        this.configFile.addDefault(GAME_INVINCIBILITY_LOSEONITEMPICKUP, true);

        this.configFile.addDefault(GAME_BASEBLOCK_ENABLED_PATH, true);
        List<String> defaultChestContent = new ArrayList<>();
        defaultChestContent.add("392:16");
        defaultChestContent.add("272");
        defaultChestContent.add("273");
        defaultChestContent.add("274");
        defaultChestContent.add("275");
        this.configFile.addDefault(GAME_BASEBLOCK_CONTENTS_PATH, defaultChestContent);

        this.configFile.addDefault(TIMER_REMINDER_ENABLED_PATH, true);

        this.configFile.addDefault(MISC_AUTOREPSAWN_ENABLED_PATH, false);
        this.configFile.addDefault(MISC_AUTOREPSAWN_DELAY_PATH, 0);
        this.configFile.addDefault(MISC_STARTCOUNTDOWN_ENABLED_PATH, true);
        this.configFile.addDefault(MISC_STARTCOUNTDOWN_DURATION_PATH, 5);

        this.configFile.options().copyDefaults(true);
        BattlePlugin.instance().saveConfig();
    }

    public int getWoolTimeToPlace() {
        return this.getConfigFile().getInt(GAME_WOOL_TIMETOPLACE_PATH) * 60;
    }

    public int getInvincibilityTimerDuration() {
        return this.getConfigFile().getInt(GAME_INVINCIBILITY_DURATION_PATH);
    }

    public void reloadConfiguration() {
        BattlePlugin.instance().reloadConfig();
    }

    public FileConfiguration getConfigFile() {
        return this.configFile;
    }

}
