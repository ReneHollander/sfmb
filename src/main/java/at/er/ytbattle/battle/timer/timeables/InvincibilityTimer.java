package at.er.ytbattle.battle.timer.timeables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import at.er.ytbattle.battle.Battle;
import at.er.ytbattle.battle.player.BattlePlayer;
import at.er.ytbattle.battle.player.PlayerUtil;

public class InvincibilityTimer implements Runnable, Listener {

    private BattlePlayer player;
    private int time;

    private transient int handleID;

    public InvincibilityTimer(BattlePlayer player, int time) {
        this.player = player;
        this.time = time;
        this.handleID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Battle.instance(), this, 0L, 1200L);
        Battle.instance().getServer().getPluginManager().registerEvents(this, Battle.instance());
    }

    private Object readResolve() {
        this.handleID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Battle.instance(), this, 0L, 1200L);
        Battle.instance().getServer().getPluginManager().registerEvents(this, Battle.instance());
        return this;
    }

    @Override
    public void run() {
        if (time == 0) {
            if (player.hasPlayer()) {
                player.sendMessage(Battle.prefix() + "Your invincibility ended!");
            }
            HandlerList.unregisterAll(this);
            time--;
        }

        if (time > 0) {
            if (player.hasPlayer()) {
                player.sendMessage(Battle.prefix() + "Your invincibility ends in " + time + " minutes!");
            }
            time--;
        }
    }

    public void stopTimer() {
        HandlerList.unregisterAll(this);
        Bukkit.getScheduler().cancelTask(handleID);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (PlayerUtil.areEqual(event.getEntity(), player)) {
            HandlerList.unregisterAll(this);
            Bukkit.getScheduler().cancelTask(handleID);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if ((event.getEntity() instanceof Player) && (event.getDamager() instanceof Player)) {
            Player victim = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            if (PlayerUtil.areEqual(victim, player)) {
                damager.sendMessage(Battle.prefix() + victim.getName() + " died shortly before. He is invincible!");
                event.setCancelled(true);
            }
            if (PlayerUtil.areEqual(damager, player)) {
                damager.sendMessage(Battle.prefix() + "You damaged " + victim.getName() + ". You have lost your invincibility!");
                HandlerList.unregisterAll(this);
                Bukkit.getScheduler().cancelTask(handleID);
            }
        } else if ((event.getDamager() instanceof Projectile) && (event.getEntity() instanceof Player)) {
            Projectile projectile = (Projectile) event.getDamager();
            if (projectile.getShooter() instanceof Player) {
                Player victim = (Player) event.getEntity();
                Player damager = (Player) projectile.getShooter();
                if (PlayerUtil.areEqual(victim, player)) {
                    damager.sendMessage(Battle.prefix() + victim.getName() + " died shortly before. He is invincible!");
                    event.setCancelled(true);
                }
                if (PlayerUtil.areEqual(damager, player)) {
                    damager.sendMessage(Battle.prefix() + "You damaged " + victim.getName() + ". You have lost your invincibility!");
                    HandlerList.unregisterAll(this);
                    Bukkit.getScheduler().cancelTask(handleID);
                }
            }
        }
    }
}