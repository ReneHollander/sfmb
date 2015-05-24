package at.er.sfmb.plugin.command.handler;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.command.CustomCommand;
import at.er.sfmb.plugin.player.BattlePlayer;
import at.er.sfmb.plugin.team.Team;
import at.er.sfmb.plugin.team.TeamColor;
import org.bukkit.ChatColor;

public class BattleCommandStats implements CustomCommand {

    @Override
    public boolean onCommand(String label, String[] args, BattlePlayer player) {
        if (BattlePlugin.game().isStarted()) {
            String list = "";
            String winners;

            if (BattlePlugin.game().getTeamManager().getTeams().size() > 1) {
                if (args.length == 0) {
                    player.sendMessage(BattlePlugin.prefix() + "Please select a team: /battle stats <teamname>");
                } else {
                    Team t = BattlePlugin.game().getTeamManager().getTeam(TeamColor.getTeamByShortName(args[0].toLowerCase()));
                    if (t != null) {
                        for (BattlePlayer p : t.getPlayers()) {
                            list += p.getName() + " (" + p.getHealth() * 10.0 / 2.0 + "%), ";
                        }
                        if (list.length() > 0)
                            list = list.substring(0, list.lastIndexOf(','));
                        player.sendMessage(BattlePlugin.prefix() + t.getTeamColor().getChatColor() + t.getTeamColor().getLongName() + " Team:" + ChatColor.WHITE + "\n" + "Players: " + list + "\n" + "Lifes: " + t.getLifes() + "\n" + "Wools: " + t.getBlockPlaceTimerManager().getRemainingWoolCount());
                    } else {
                        player.sendMessage(BattlePlugin.prefix() + "Please select a team: /battle stats <teamname>");
                    }
                }
            } else {
                winners = BattlePlugin.game().getTeamManager().getLastTeam().getTeamColor().getLongName();
                player.sendMessage(BattlePlugin.prefix() + "The " + winners + " team has won the battle - Stats are disabled now!");
            }
        } else {
            player.sendMessage(BattlePlugin.prefix() + "Game is not running!");
        }
        return true;
    }

}
