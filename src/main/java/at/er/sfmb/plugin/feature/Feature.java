package at.er.sfmb.plugin.feature;

import org.bukkit.event.Listener;

public interface Feature extends Listener {

    String getName();

    String getDescription();

    void enable();

    void disable();

    boolean isEnabled();

    String[] getCommandLabels();
}
