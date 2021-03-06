package at.er.sfmb.plugin.event;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.player.BattlePlayer;
import at.er.sfmb.plugin.timer.timeables.InvincibilityTimer;
import at.er.sfmb.util.ConfigurationHelper;
import gnu.trove.set.hash.TIntHashSet;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class InvincibilityListener implements Listener {

    public InvincibilityListener() {
        Bukkit.getPluginManager().registerEvents(this, BattlePlugin.instance());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemDespawn(ItemDespawnEvent event) {
        if (BattlePlugin.configurationHelper().getConfigFile().getBoolean(ConfigurationHelper.GAME_INVINCIBILITY_LOSEONITEMPICKUP)) {
            ItemStack itemStack = event.getEntity().getItemStack();
            BattlePlugin.instance().deadPlayersItems.remove(itemStack.hashCode());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemCombust(EntityCombustEvent event) {
        if (BattlePlugin.configurationHelper().getConfigFile().getBoolean(ConfigurationHelper.GAME_INVINCIBILITY_LOSEONITEMPICKUP)) {
            if (event.getEntityType() == EntityType.DROPPED_ITEM) {
                ItemStack itemStack = ((Item) event.getEntity()).getItemStack();
                BattlePlugin.instance().deadPlayersItems.remove(itemStack.hashCode());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemPickup(PlayerPickupItemEvent event) {
        if (BattlePlugin.configurationHelper().getConfigFile().getBoolean(ConfigurationHelper.GAME_INVINCIBILITY_LOSEONITEMPICKUP)) {
            BattlePlayer player = BattlePlugin.game().getPlayerManager().getPlayer(event.getPlayer());
            InvincibilityTimer invincibilityTimer = BattlePlugin.game().getInvincibilityTimerManager().getTimerByPlayer(player);
            ItemStack itemStack = event.getItem().getItemStack();
            if (BattlePlugin.instance().deadPlayersItems.contains(itemStack.hashCode()) && invincibilityTimer != null) {
                player.sendMessage(BattlePlugin.prefix() + "You have picked up the loot from a dead player! You have lost your invincibility!");
                invincibilityTimer.removeTimer();
            }
            BattlePlugin.instance().deadPlayersItems.remove(itemStack.hashCode());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        BattlePlayer victim = BattlePlugin.game().getPlayerManager().getPlayer(event.getEntity());
        InvincibilityTimer victimTimer = BattlePlugin.game().getInvincibilityTimerManager().getTimerByPlayer(victim);
        if (victimTimer != null) {
            victimTimer.removeTimer();
        }
        if (BattlePlugin.configurationHelper().getConfigFile().getBoolean(ConfigurationHelper.GAME_INVINCIBILITY_LOSEONITEMPICKUP)) {
            TIntHashSet deadPlayersItems = BattlePlugin.instance().deadPlayersItems;
            for (ItemStack itemStack : event.getDrops()) {
                deadPlayersItems.add(itemStack.hashCode());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if ((event.getEntity() instanceof Player) && (event.getDamager() instanceof Player)) {
            BattlePlayer victim = BattlePlugin.game().getPlayerManager().getPlayer((Player) event.getEntity());
            BattlePlayer damager = BattlePlugin.game().getPlayerManager().getPlayer((Player) event.getDamager());
            InvincibilityTimer victimTimer = BattlePlugin.game().getInvincibilityTimerManager().getTimerByPlayer(victim);
            InvincibilityTimer damagerTimer = BattlePlugin.game().getInvincibilityTimerManager().getTimerByPlayer(damager);

            if (victimTimer != null && damagerTimer != null) {
                damager.sendMessage(BattlePlugin.prefix() + victim.getName() + " died shortly before. He is invincible!");
                event.setCancelled(true);
            } else if (victimTimer != null) {
                damager.sendMessage(BattlePlugin.prefix() + victim.getName() + " died shortly before. He is invincible!");
                event.setCancelled(true);
            } else if (damagerTimer != null) {
                damager.sendMessage(BattlePlugin.prefix() + "You damaged " + victim.getName() + ". You have lost your invincibility!");
                damagerTimer.removeTimer();
            }
        } else if ((event.getDamager() instanceof Projectile) && (event.getEntity() instanceof Player)) {
            Projectile projectile = (Projectile) event.getDamager();
            if (projectile.getShooter() instanceof Player) {
                BattlePlayer victim = BattlePlugin.game().getPlayerManager().getPlayer((Player) event.getEntity());
                BattlePlayer damager = BattlePlugin.game().getPlayerManager().getPlayer((Player) projectile.getShooter());
                InvincibilityTimer victimTimer = BattlePlugin.game().getInvincibilityTimerManager().getTimerByPlayer(victim);
                InvincibilityTimer damagerTimer = BattlePlugin.game().getInvincibilityTimerManager().getTimerByPlayer(damager);

                if (victimTimer != null && damagerTimer != null) {
                    damager.sendMessage(BattlePlugin.prefix() + victim.getName() + " died shortly before. He is invincible!");
                    event.setCancelled(true);
                } else if (victimTimer != null) {
                    damager.sendMessage(BattlePlugin.prefix() + victim.getName() + " died shortly before. He is invincible!");
                    event.setCancelled(true);
                } else if (damagerTimer != null) {
                    damager.sendMessage(BattlePlugin.prefix() + "You damaged " + victim.getName() + ". You have lost your invincibility!");
                    damagerTimer.removeTimer();
                }
            }
        }
    }
}
