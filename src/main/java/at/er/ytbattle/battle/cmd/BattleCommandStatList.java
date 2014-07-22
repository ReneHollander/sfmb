package at.er.ytbattle.battle.cmd;

import org.bukkit.ChatColor;

import at.er.ytbattle.battle.Battle;
import at.er.ytbattle.battle.Team;
import at.er.ytbattle.battle.TeamColor;
import at.er.ytbattle.battle.TeamManager;
import at.er.ytbattle.battle.player.BattlePlayer;

public class BattleCommandStatList {

	private TeamManager teamManager;

	public BattleCommandStatList() {
		this.teamManager = Battle.instance().getGame().getTeamManager();
	}

	public boolean onCmdStats(String[] args, BattlePlayer player) {
		if (Battle.instance().getGame().isStarted()) {
			String list = "";
			String winners = "";

			if (this.teamManager.getTeams().size() > 1) {
				if (args.length == 1) {
					player.sendMessage(Battle.prefix() + "Please select a team: /battle stats <teamname>");
					return true;
				}
				Team t = this.teamManager.getTeam(TeamColor.getTeamByShortName(args[1].toLowerCase()));
				if (t != null) {
					for (BattlePlayer p : t.getPlayers()) {
						list += p + " (" + p.getHealth() * 10.0 / 2.0 + "%), ";
					}
					if (list.length() > 0)
						list = list.substring(0, list.lastIndexOf(','));
					player.sendMessage(Battle.prefix() + ChatColor.DARK_RED + "Red Team:" + ChatColor.WHITE + "\n" + "Players: " + list + "\n" + "Lifes: " + t.getLifes() + "\n" + "Wools: " + t.getBlockPlaceTimerManager().getRemainingWoolCount());
				} else {
					player.sendMessage(Battle.prefix() + "Please select a team: /battle stats <teamname>");
				}
			} else {
				winners = this.teamManager.getLastTeam().getTeamColor().getLongName();
				player.sendMessage(Battle.prefix() + "The " + winners + " team has won the battle - Stats are disabled now!");
			}
			return true;
		}

		return false;
	}

	public boolean onCmdList(String[] args, BattlePlayer player) {
		String list = "";
		for (Team t : this.teamManager.getTeams()) {
			for (BattlePlayer p : t.getPlayers()) {
				list = list + p.getName() + ", ";
			}
		}
		if (list.equals("")) {
			player.sendMessage(Battle.prefix() + "The Playerlist is empty!");
		} else {
			player.sendMessage(Battle.prefix() + "Battleplayers " + "[P] " + ChatColor.DARK_AQUA + "[S]" + ChatColor.GOLD + ": " + list.substring(0, list.lastIndexOf(',')));
		}
		return true;
	}
}