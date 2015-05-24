package at.er.sfmb.plugin.feature;

import at.er.sfmb.plugin.feature.event.FeatureRegisterEvent;
import at.er.sfmb.plugin.feature.event.FeatureUnregisterEvent;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class FeatureManager {

    private JavaPlugin plugin;

    private Set<Feature> registeredFeatures;

    public FeatureManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.registeredFeatures = new HashSet<>();
    }

    public void registerFeature(Feature f) {
        Preconditions.checkNotNull(f);

        FeatureRegisterEvent event = new FeatureRegisterEvent(f);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            this.plugin.getLogger().log(Level.INFO, "FeatureRegisterEvent for " + f.getName() + " was canceled");
        } else {
            this.registeredFeatures.add(f);
            this.plugin.getLogger().log(Level.INFO, "Feature " + f.getName() + " was loaded successfully");
        }
    }

    public void registerFeature(Class<? extends Feature> featureClass) {
        try {
            Constructor<? extends Feature> constructor = featureClass.getConstructor();
            Feature feature = constructor.newInstance();
            this.registerFeature(feature);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void unregisterFeature(Feature f) {
        Preconditions.checkNotNull(f);

        FeatureUnregisterEvent event = new FeatureUnregisterEvent(f);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            this.plugin.getLogger().log(Level.INFO, "FeatureUnregisterEvent for " + f.getName() + " was canceled");
        } else {
            this.registeredFeatures.remove(f);
            this.plugin.getLogger().log(Level.INFO, "Feature " + f.getName() + " was removed successfully");
        }
    }

    public Feature getFeature(String featureName) {
        for (Feature f : this.registeredFeatures) {
            if (f.getName().equals(featureName)) {
                return f;
            }
        }

        return null;
    }

    public Set<Feature> getRegisteredFeatures() {
        return this.registeredFeatures;
    }
}
