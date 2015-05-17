package at.er.ytbattle.plugin.feature.explicit;

import at.er.ytbattle.plugin.feature.AbstractFeature;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * Created by Stefan on 16.05.2015.
 */
public class CreeperDamageFeature extends AbstractFeature {

    public CreeperDamageFeature() {
        super("NoCreeperBlockDamage", "If enabled creepers won't do block damage");
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.CREEPER)
            event.blockList().clear();
    }
}
