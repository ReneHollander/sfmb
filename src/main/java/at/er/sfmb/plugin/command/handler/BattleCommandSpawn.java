package at.er.sfmb.plugin.command.handler;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.command.CustomCommand;
import at.er.sfmb.plugin.player.BattlePlayer;

public class BattleCommandSpawn implements CustomCommand {

    @Override
    public boolean onCommand(String label, String[] args, BattlePlayer player) {
        if (BattlePlugin.game().getSpawn() != null && !BattlePlugin.game().isStarted()) {
            player.teleport(BattlePlugin.game().getSpawn().getLocation());
        } else {
            player.sendMessage(BattlePlugin.prefix() + "Battlespawn hasn't been set yet!");
        }
        return true;
    }
}
