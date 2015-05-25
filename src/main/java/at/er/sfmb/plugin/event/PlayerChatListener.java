package at.er.sfmb.plugin.event;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.player.BattlePlayer;
import at.er.sfmb.plugin.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    public PlayerChatListener() {
        Bukkit.getPluginManager().registerEvents(this, BattlePlugin.instance());
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        BattlePlayer player = BattlePlugin.game().getPlayerManager().getPlayer(event.getPlayer());
        if (BattlePlugin.game().isStarted()) {
            if (BattlePlugin.game().getTeamManager().isInTeam(player)) {
                Team t = BattlePlugin.game().getTeamManager().getTeamByPlayer(player);
                if (BattlePlugin.game().getTeamManager().isLastTeam(t)) {
                    event.setFormat(ChatColor.GOLD + "[Winner]" + ChatColor.WHITE + " - " + "%1$s: " + ChatColor.RESET + "%2$s");
                } else {
                    event.setFormat(t.getTeamColor().getChatColor() + "[Battle]" + ChatColor.WHITE + " - " + "%1$s: " + ChatColor.RESET + "%2$s");
                }
            }
        }
    }

}
