package at.er.ytbattle.plugin.feature.explicit;

import at.er.ytbattle.plugin.BattlePlugin;
import at.er.ytbattle.plugin.feature.AbstractFeature;
import at.er.ytbattle.plugin.team.Team;
import at.er.ytbattle.util.BattleUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardFeature extends AbstractFeature {

    private Scoreboard battleScoreboard;
    private Objective battleStatsObjective;

    public ScoreboardFeature() {
        super("Scoreboard", "Displays the lives of the teams on the scoreboard");
    }

    @Override
    public void onEnable() {
        if (this.battleScoreboard == null) {
            this.battleScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.setScoreboard(this.battleScoreboard);
            }
        }

        if (battleStatsObjective == null) {
            battleStatsObjective = this.battleScoreboard.registerNewObjective("stats", "dummy");
            battleStatsObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
    }

    @Override
    public void onDisable() {
        this.battleScoreboard.clearSlot(DisplaySlot.SIDEBAR);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDeath(PlayerDeathEvent event) {
        updateScoreboard();
    }

    private void updateScoreboard() {
        Scoreboard scoreboard = this.battleScoreboard;
        Objective objective = this.battleStatsObjective;
        if (!BattlePlugin.game().isStarted()) {
            if (objective.getDisplayName().equals(ChatColor.BOLD + "Battle Teamstats")) {
                for (String entry : scoreboard.getEntries()) {
                    scoreboard.resetScores(entry);
                }
            }
            objective.setDisplayName(ChatColor.BOLD + "Battle Infos");
            objective.getScore(ChatColor.ITALIC + "Battle v" + BattleUtils.getShortVersion()).setScore(9);
            objective.getScore(ChatColor.ITALIC + "").setScore(8);
            objective.getScore(ChatColor.ITALIC + "by").setScore(7);
            objective.getScore(ChatColor.ITALIC + "EXSolo").setScore(6);
            objective.getScore(ChatColor.ITALIC + "Rene8888").setScore(5);
            objective.getScore(ChatColor.ITALIC + "").setScore(4);
            objective.getScore(ChatColor.ITALIC + "Download:").setScore(3);
            objective.getScore(ChatColor.ITALIC + "bit.ly").setScore(2);
            objective.getScore(ChatColor.ITALIC + "/battleplugin").setScore(1);
        } else {
            if (objective.getDisplayName().equals(ChatColor.BOLD + "Battle Infos")) {
                for (String entry : scoreboard.getEntries()) {
                    scoreboard.resetScores(entry);
                }
            }
            objective.setDisplayName(ChatColor.BOLD + "Battle Teamstats");
            for (Team t : BattlePlugin.game().getTeamManager().getTeams()) {
                if (t.getPlayers().size() > 0) {
                    objective.getScore(t.getTeamColor().getChatColor() + "Team " + t.getTeamColor().getLongName()).setScore(t.getLifes());
                } else {
                    scoreboard.resetScores(t.getTeamColor().getChatColor() + "Team " + t.getTeamColor().getLongName());
                }
            }
        }
    }
}
