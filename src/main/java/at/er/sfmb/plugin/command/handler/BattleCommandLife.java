package at.er.sfmb.plugin.command.handler;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.command.BattleCommand;
import at.er.sfmb.plugin.player.BattlePlayer;
import at.er.sfmb.plugin.team.Team;
import at.er.sfmb.plugin.team.TeamManager;
import at.er.sfmb.util.BattleUtils;
import org.bukkit.material.Wool;

public class BattleCommandLife implements BattleCommand {

    private TeamManager teamManager;

    public BattleCommandLife() {
        this.teamManager = BattlePlugin.game().getTeamManager();
    }

    @Override
    public boolean onCommand(String label, String[] args, BattlePlayer player) {
        if (BattlePlugin.game().isStarted()) {
            if (this.teamManager.isInTeam(player)) {
                Team t = this.teamManager.getTeamByPlayer(player);
                if (t.getLifes() > 0) {
                    player.getInventory().addItem(new Wool(t.getTeamColor().getDyeColor()).toItemStack(1));
                    t.setLifes(t.getLifes() - 1);
                    player.sendMessage(BattlePlugin.prefix() + "You recieved a new wool!");
                } else {
                    player.sendMessage(BattlePlugin.prefix() + "Your team hasn't enough lifes left!");
                }
                BattleUtils.updateScoreboard();
            } else {
                player.sendMessage(BattlePlugin.prefix() + "You aren't in a Team anymore!");
            }
        } else {
            player.sendMessage(BattlePlugin.prefix() + "The Game hasn't started yet");
        }
        return true;
    }
}
