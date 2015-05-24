package at.er.sfmb.plugin.command.handler;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.command.CustomCommand;
import at.er.sfmb.plugin.player.BattlePlayer;
import at.er.sfmb.plugin.team.Team;
import at.er.sfmb.util.BattleUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Random;

public class BattleCommandRandomTeams implements CustomCommand {

    @Override
    public boolean onCommand(String label, String[] args, BattlePlayer bp) {
        if (!BattlePlugin.game().isStarted()) {
            List<Team> teams = BattlePlugin.game().getTeamManager().getTeams();
            Random r = new Random();
            for (BattlePlayer player : BattlePlugin.game().getBattlePlayerManager().getAllBattlePlayers()) {
                Team t = teams.get(r.nextInt(teams.size()));
                BattlePlugin.game().getTeamManager().removePlayerFromTeam(player);
                t.addPlayer(player);
                BattleUtils.setDisplayAndListName(player);
                Bukkit.broadcastMessage(BattlePlugin.prefix() + "Player " + player.getName() + " joined the " + t.getTeamColor().getChatColor() + t.getTeamColor().getLongName() + ChatColor.RESET + " Team!");
            }
            return true;
        } else {
            bp.sendMessage(BattlePlugin.prefix() + "Battle has already started!");
            return true;
        }
    }
}
