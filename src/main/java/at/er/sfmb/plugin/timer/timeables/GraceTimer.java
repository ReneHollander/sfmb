package at.er.sfmb.plugin.timer.timeables;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.player.BattlePlayer;
import at.er.sfmb.plugin.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;

public class GraceTimer implements Runnable {

    private int time;

    public GraceTimer(int timeSec) {
        this.time = timeSec;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(BattlePlugin.instance(), this, 0, 20);
    }

    @Override
    public void run() {
        if (time >= 600 && time % 600 == 0) {
            broadcastTime();
        }
        if (time <= 300 && time % 60 == 0 && time > 60) {
            broadcastTime();
        }

        if (time <= 60 && time % 15 == 0 && time > 0) {
            broadcastTime();
        }

        if (time <= 10 && time > 0) {
            broadcastTime();
            if (time <= 3)
                note();
        }

        if (time == 0) {
            for (Team t : BattlePlugin.game().getTeamManager().getTeams()) {
                for (BattlePlayer p : t.getPlayers()) {
                    if (p.hasPlayer()) {
                        p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 10, 1);
                    }
                }
            }
            for (World w : Bukkit.getServer().getWorlds()) {
                w.setPVP(true);
            }
            Bukkit.broadcastMessage(BattlePlugin.prefix() + "The grace period has ended!");
        }

        if (time >= 0) {
            time = time - 1;
        }
    }

    private void note() {
        for (Team t : BattlePlugin.game().getTeamManager().getTeams()) {
            for (BattlePlayer p : t.getPlayers()) {
                if (p.hasPlayer()) {
                    p.playSound(p.getLocation(), Sound.NOTE_SNARE_DRUM, 10, 1);
                }
            }
        }
    }

    private void broadcastTime() {
        if (time > 60) {
            Bukkit.broadcastMessage(BattlePlugin.prefix() + "The grace period will end in " + time / 60 + " Minutes");
        } else {
            Bukkit.broadcastMessage(BattlePlugin.prefix() + "The grace period will end in " + time + " Seconds");
        }
    }

    public int getTime() {
        return this.time;
    }

}
