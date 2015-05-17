package at.er.ytbattle.plugin.feature.explicit;

import at.er.ytbattle.plugin.BattlePlugin;
import at.er.ytbattle.plugin.feature.AbstractFeature;
import at.er.ytbattle.plugin.player.BattlePlayer;
import at.er.ytbattle.plugin.team.Team;
import at.er.ytbattle.util.BlockUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class WoolFeature extends AbstractFeature {

    public WoolFeature() {
        super("WoolLives", "The players will be given wool representing their health. One wool per player has to be placed in the world");
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerShear(PlayerShearEntityEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.SHEEP)
            event.getDrops().clear();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        BattlePlayer player = BattlePlugin.game().getBattlePlayerManager().getBattlePlayer(event.getPlayer());

        if (event.getBlock().getType() == Material.GLASS && player.getWorld() == BattlePlugin.game().getSpawn().getLocation().getWorld() && BattlePlugin.game().isStarted()) {
            event.setCancelled(true);
            player.sendMessage(BattlePlugin.prefix() + "You are unable to place a Block of the Bordermaterial.");
        }

        if (event.getBlock().getType() == Material.WOOL && BattlePlugin.game().isStarted() && BattlePlugin.game().getTeamManager().isInTeam(player)) {
            DyeColor color = ((Wool) event.getBlock().getState().getData()).getColor();
            Team t = BattlePlugin.game().getTeamManager().getTeamByPlayer(player);

            if (t.getTeamColor().getDyeColor().equals(color)) {
                boolean placed = BlockUtils.placeWool(event.getBlock().getLocation());

                if (!placed) {
                    player.sendMessage(BattlePlugin.prefix() + "Invalid Wool Location!");
                    event.setCancelled(true);
                } else {
                    t.getBlockPlaceTimerManager().woolPlace();
                }
            } else {
                player.sendMessage(BattlePlugin.prefix() + "You can't place other team's wool!");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        BattlePlayer player = BattlePlugin.game().getBattlePlayerManager().getBattlePlayer(event.getPlayer());

        if (event.getBlock().getType() == Material.WOOL && BattlePlugin.game().isStarted() && BattlePlugin.game().getTeamManager().isInTeam(player)) {
            DyeColor color = ((Wool) event.getBlock().getState().getData()).getColor();
            Team t = BattlePlugin.game().getTeamManager().getTeamByPlayer(player);

            Team victim = BattlePlugin.game().getTeamManager().getTeamByDyeColor(color);
            if (t.getTeamColor().getDyeColor().equals(color)) {
                player.sendMessage(BattlePlugin.prefix() + "You can't break your own team's wool!");
                event.setCancelled(true);
            } else {
                victim.getBlockPlaceTimerManager().woolBreak();

                if (BattlePlugin.game().getInvincibilityTimerManager().timerRunning(player)) {
                    BattlePlugin.game().getInvincibilityTimerManager().stopTimer(player);
                    player.sendMessage(BattlePlugin.prefix() + "You have lost your invincibility!");
                }

                Bukkit.broadcastMessage(BattlePlugin.prefix() + player.getName() + " destroyed a " + victim.getTeamColor().getChatColor() + victim.getTeamColor().getLongName() + ChatColor.RESET + " wool!");
            }
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        if (event.getInventory().getResult().getType() == Material.WOOL) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityExplode(EntityExplodeEvent event) {
        for (Block block : event.blockList()) {
            if (block.getType() == Material.WOOL) {
                event.blockList().remove(block);
            }
        }
    }
}
