package at.er.sfmb.plugin.command;

import at.er.sfmb.plugin.player.CustomPlayer;

public interface CustomCommand {

    public abstract boolean onCommand(String label, String[] args, CustomPlayer player);

}
