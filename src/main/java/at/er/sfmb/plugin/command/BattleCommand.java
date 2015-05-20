package at.er.sfmb.plugin.command;

import at.er.sfmb.plugin.player.BattlePlayer;

public interface BattleCommand {

    public abstract boolean onCommand(String label, String[] args, BattlePlayer player);

}
