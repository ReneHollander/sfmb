package at.er.sfmb.plugin.command.handler;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.command.BattleCommand;
import at.er.sfmb.plugin.player.BattlePlayer;
import at.er.sfmb.plugin.team.Team;
import org.bukkit.ChatColor;

public class BattleCommandList implements BattleCommand {

    @Override
    public boolean onCommand(String label, String[] args, BattlePlayer player) {
        String list = "";
        for (Team t : BattlePlugin.game().getTeamManager().getTeams()) {
            for (BattlePlayer p : t.getPlayers()) {
                list = list + p.getName() + ", ";
            }
        }
        if (list.equals("")) {
            player.sendMessage(BattlePlugin.prefix() + "The Playerlist is empty!");
        } else {
            player.sendMessage(BattlePlugin.prefix() + "Battleplayers " + "[P] " + ChatColor.DARK_AQUA + "[S]" + ChatColor.GOLD + ": " + list.substring(0, list.lastIndexOf(',')));
        }
        return true;
    }

}
