package at.er.sfmb.plugin.command.handler;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.command.BattleCommand;
import at.er.sfmb.plugin.player.BattlePlayer;

public class BattleCommandLeave implements BattleCommand {

    @Override
    public boolean onCommand(String label, String[] args, BattlePlayer player) {
        if (BattlePlugin.game().getTeamManager().isInTeam(player)) {
            player.setDisplayName(player.getName());
            player.setPlayerListName(player.getName());
            BattlePlugin.game().getTeamManager().removePlayerFromTeam(player);
            player.sendMessage(BattlePlugin.prefix() + "You have left the Battle");
            return true;
        } else {
            player.sendMessage(BattlePlugin.prefix() + "You havn't joined the Battle before");
            return true;
        }
    }
}
