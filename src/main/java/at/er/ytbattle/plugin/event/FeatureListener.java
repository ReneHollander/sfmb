package at.er.ytbattle.plugin.event;

import at.er.ytbattle.plugin.BattlePlugin;
import at.er.ytbattle.plugin.feature.event.FeatureDisableEvent;
import at.er.ytbattle.plugin.feature.event.FeatureEnabledEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class FeatureListener implements Listener {

    public FeatureListener() {
        Bukkit.getPluginManager().registerEvents(this, BattlePlugin.instance());
    }

    @EventHandler
    public void onFeatureEnable(FeatureEnabledEvent event) {
        Bukkit.getPluginManager().registerEvents(event.getFeature(), BattlePlugin.instance());
    }

    @EventHandler
    public void onFeatureDisable(FeatureDisableEvent event) {
        HandlerList.unregisterAll(event.getFeature());
    }

}
