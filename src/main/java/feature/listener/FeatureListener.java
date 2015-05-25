package feature.listener;

import at.er.sfmb.plugin.BattlePlugin;
import feature.event.FeatureDisableEvent;
import feature.event.FeatureEnabledEvent;
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
        // TODO BattlePlugin.instance().getCommandManager().addCommandHandler(event.getFeature().getCommandLabels(), event.getFeature());
    }

    @EventHandler
    public void onFeatureDisable(FeatureDisableEvent event) {
        HandlerList.unregisterAll(event.getFeature());
        BattlePlugin.instance().getCommandManager().removeCommandHandler(event.getFeature().getCommandLabels());
    }
}
