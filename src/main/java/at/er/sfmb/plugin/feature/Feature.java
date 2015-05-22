package at.er.sfmb.plugin.feature;

import org.bukkit.event.Listener;

public interface Feature extends Listener {

    public String getName();

    public String getDescription();

    public void enable();

    public void disable();

    public boolean isEnabled();

    public String[] getCommandLabels();
}
