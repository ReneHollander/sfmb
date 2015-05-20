package at.er.sfmb.plugin.timer.timeables;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.player.BattlePlayer;
import at.er.sfmb.plugin.timer.manager.InvincibilityTimerManager;
import at.er.sfmb.util.timer.Timeable;
import at.er.sfmb.util.timer.TimerManager.TimeScale;
import org.bukkit.event.Listener;

public class InvincibilityTimer extends Timeable implements Listener {

    private BattlePlayer player;

    public InvincibilityTimer(BattlePlayer player) {
        super(InvincibilityTimerManager.MANAGER_ID, TimeScale.MINUTE, 1, player);
        this.player = player;
    }

    @Override
    public void tick(long elapsedTime) {
        if (BattlePlugin.configurationHelper().getInvincibilityTimerDuration() == elapsedTime) {
            if (player.hasPlayer()) {
                player.sendMessage(BattlePlugin.prefix() + "Your invincibility ended!");
            }
            this.removeTimer();
        } else {
            if (player.hasPlayer()) {
                player.sendMessage(BattlePlugin.prefix() + "Your invincibility ends in " + (BattlePlugin.configurationHelper().getInvincibilityTimerDuration() - elapsedTime) + " minutes!");
            }
        }
    }
}
