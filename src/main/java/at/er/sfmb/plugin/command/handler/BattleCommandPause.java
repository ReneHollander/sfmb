package at.er.sfmb.plugin.command.handler;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.command.CustomCommand;
import at.er.sfmb.plugin.player.BattlePlayer;
import org.bukkit.Bukkit;

public class BattleCommandPause implements CustomCommand {

    @Override
    public boolean onCommand(String label, String[] args, BattlePlayer player) {
        BattlePlugin.instance().pauseGame();
        Bukkit.broadcastMessage(BattlePlugin.prefix() + "The game got paused!");
        return true;
    }
}
