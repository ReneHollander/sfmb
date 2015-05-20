package at.er.sfmb.plugin.timer.timeables;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.player.BattlePlayer;
import at.er.sfmb.plugin.team.Team;
import at.er.sfmb.util.timer.Timeable;
import at.er.sfmb.util.timer.TimerManager.TimeScale;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WoolPlaceTimer extends Timeable {

    private Team team;

    public WoolPlaceTimer(Team team) {
        super(team, TimeScale.SECOND, 1);
        this.team = team;
    }

    @Override
    public void tick(long elapsedTime) {
        int duration = BattlePlugin.configurationHelper().getWoolTimeToPlace();
        if (elapsedTime == 0) {
            for (BattlePlayer player : team.getPlayers()) {
                if (player.hasPlayer()) {
                    player.sendMessage(BattlePlugin.prefix() + "You have " + ((duration - elapsedTime) / 60) + " minutes left to place a wool.");
                }
            }
        } else if (elapsedTime >= duration) {
            for (BattlePlayer player : team.getPlayers()) {
                if (player.hasPlayer()) {
                    player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 10, 0.5F);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 0));
                    player.sendMessage(BattlePlugin.prefix() + "Place a wool to disable the whiter effect!");
                }
            }
        } else {
            if (elapsedTime % 300 == 0) {
                for (BattlePlayer player : team.getPlayers()) {
                    if (player.hasPlayer()) {
                        player.sendMessage(BattlePlugin.prefix() + "You have " + ((duration - elapsedTime) / 60) + " minutes left to place a wool.");
                    }
                }
            } else if (elapsedTime % 60 == 0 && elapsedTime > duration - 300) {
                for (BattlePlayer player : team.getPlayers()) {
                    if (player.hasPlayer()) {
                        player.sendMessage(BattlePlugin.prefix() + "You have " + ((duration - elapsedTime) / 60) + " minutes left to place a wool.");
                    }
                }
            } else if (elapsedTime % 10 == 0 && elapsedTime > duration - 60) {
                for (BattlePlayer player : team.getPlayers()) {
                    if (player.hasPlayer()) {
                        player.sendMessage(BattlePlugin.prefix() + "You have " + (duration - elapsedTime) + " seconds left to place a wool.");
                    }
                }
            }
        }
    }
}
