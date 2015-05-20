package at.er.sfmb.plugin.feature.event;

import at.er.sfmb.plugin.feature.Feature;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FeatureEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private Feature feature;
    private boolean cancelled;

    /**
     * Generic feature event
     *
     * @param feature feature event is running for
     */
    protected FeatureEvent(Feature feature) {
        this.feature = feature;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    /**
     * @return all the handlers
     */
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * @return the feature involved in this event
     */
    public Feature getFeature() {
        return feature;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

