package at.er.ytbattle.plugin.feature.explicit;

import at.er.ytbattle.plugin.BattlePlugin;
import at.er.ytbattle.plugin.feature.AbstractFeature;
import at.er.ytbattle.plugin.player.BattlePlayer;
import at.er.ytbattle.util.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BaseBlockFeature extends AbstractFeature {

    public BaseBlockFeature() {
        super("BaseBlock", "A chest with starter items defined in the config file will be spawned once the block was placed");
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        BattlePlayer player = BattlePlugin.game().getBattlePlayerManager().getBattlePlayer(event.getPlayer());

        if (event.getBlock().getType() == Material.QUARTZ_ORE) {
            Location l = event.getBlock().getLocation();
            BlockUtils.buildBase(l);
            if (player.getItemInHand().getAmount() > 1) {
                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
            } else {
                player.getInventory().setItemInHand(new ItemStack(Material.AIR));
            }
        }
    }
}
