package at.er.sfmb.plugin.feature;

import at.er.sfmb.plugin.command.CustomCommand;
import org.bukkit.event.Listener;

public interface Feature extends Listener, CustomCommand {

    public String getName();

    public String getDescription();

    public void enable();

    public void disable();

    public boolean isEnabled();

    public String[] getCommandLabels();
}
