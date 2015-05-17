package at.er.ytbattle.plugin.feature;

import at.er.ytbattle.plugin.feature.event.FeatureDisableEvent;
import at.er.ytbattle.plugin.feature.event.FeatureEnabledEvent;
import org.bukkit.Bukkit;

public abstract class AbstractFeature implements Feature {

    private String name;
    private String description;

    private boolean enabled;

    public AbstractFeature(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void enable() {

        FeatureEnabledEvent event = new FeatureEnabledEvent(this);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            onEnable();
            this.enabled = true;
        }
    }

    @Override
    public void disable() {
        FeatureDisableEvent event = new FeatureDisableEvent(this);
        Bukkit.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            onDisable();
            this.enabled = false;
        }
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public abstract void onEnable();

    public abstract void onDisable();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractFeature)) return false;

        AbstractFeature that = (AbstractFeature) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
