package at.er.sfmb.plugin.timer.timeables;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.util.timer.Timeable;
import at.er.sfmb.util.timer.TimerManager.TimeScale;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class RemindTimer extends Timeable {

    private static final int MANAGER_ID = 461462;

    public RemindTimer() {
        super(MANAGER_ID, TimeScale.MINUTE, 15);
    }

    @Override
    public void tick(long elapsedTime) {
        if (elapsedTime != 0) {
            Bukkit.broadcastMessage(BattlePlugin.prefix() + ChatColor.DARK_RED + "The battle is going on for " + elapsedTime + " minutes!");
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 1);
            }
        }
    }

    public void startReminder() {
        BattlePlugin.game().getTimerManager().registerTimer(this);
        this.startTimer();
    }

}