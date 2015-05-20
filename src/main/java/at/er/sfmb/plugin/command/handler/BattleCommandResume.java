package at.er.sfmb.plugin.command.handler;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.command.BattleCommand;
import at.er.sfmb.plugin.player.BattlePlayer;
import at.er.sfmb.plugin.timer.timeables.BattleResumeCountdown;
import at.er.sfmb.util.ConfigurationHelper;
import org.bukkit.Bukkit;

public class BattleCommandResume implements BattleCommand {

    @Override
    public boolean onCommand(String label, String[] args, BattlePlayer player) {
        if (BattlePlugin.configurationHelper().getConfigFile().getBoolean(ConfigurationHelper.MISC_STARTCOUNTDOWN_ENABLED_PATH)) {
            new BattleResumeCountdown(BattlePlugin.configurationHelper().getConfigFile().getInt(ConfigurationHelper.MISC_STARTCOUNTDOWN_DURATION_PATH));
        } else {
            BattlePlugin.instance().resumeGame();
            Bukkit.broadcastMessage(BattlePlugin.prefix() + "The game got resumed!");
        }
        return true;
    }
}
