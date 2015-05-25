package at.er.sfmb.plugin;

import at.er.sfmb.plugin.player.BattlePlayer;
import at.er.sfmb.plugin.team.TeamManager;
import at.er.sfmb.plugin.timer.manager.InvincibilityTimerManager;
import at.er.sfmb.util.SerializableLocation;
import at.er.sfmb.util.timer.TimerManager;
import feature.player.PlayerManager;

public class Game {

    private PlayerManager<BattlePlayer> battlePlayerManager;
    private TeamManager teamManager;

    private TimerManager timerManager;
    private InvincibilityTimerManager invincibilityTimerManager;

    private SerializableLocation spawn;

    private boolean started;
    private boolean paused;

    public Game() {
        this.spawn = null;
        this.started = false;
        this.paused = false;

        this.battlePlayerManager = new PlayerManager<BattlePlayer>(BattlePlayer.class);
        this.teamManager = new TeamManager();

        this.timerManager = new TimerManager();
        this.invincibilityTimerManager = new InvincibilityTimerManager();
    }

    public TimerManager getTimerManager() {
        return this.timerManager;
    }

    public PlayerManager<BattlePlayer> getPlayerManager() {
        return this.battlePlayerManager;
    }

    public TeamManager getTeamManager() {
        return this.teamManager;
    }

    public InvincibilityTimerManager getInvincibilityTimerManager() {
        return this.invincibilityTimerManager;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public SerializableLocation getSpawn() {
        return spawn;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setSpawn(SerializableLocation spawn) {
        this.spawn = spawn;
    }

}
