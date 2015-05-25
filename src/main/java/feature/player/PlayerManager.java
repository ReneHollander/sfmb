package feature.player;


import at.er.sfmb.plugin.BattlePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Constructor;
import java.util.*;

public class PlayerManager<P extends CustomPlayer> implements Listener {

    private Class<? extends P> playerClass;
    private Map<UUID, P> players;

    private Constructor<? extends P> playerConstructor;

    public PlayerManager(Class<? extends P> playerClass) {
        this.playerClass = playerClass;
        this.players = new HashMap<>();
        try {
            this.playerConstructor = playerClass.getConstructor(this.playerClass);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("playerClass doesn't have constructor with arguments Class<? extends CustomPlayer>", e);
        }


        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!this.players.containsKey(p.getUniqueId())) {
                this.addPlayer(p);
            }
        }

        BattlePlugin.instance().getServer().getPluginManager().registerEvents(this, BattlePlugin.instance());
    }

    private Object readResolve() {
        BattlePlugin.instance().getServer().getPluginManager().registerEvents(this, BattlePlugin.instance());
        return this;
    }

    public P getPlayer(UUID uuid) {
        return this.players.get(uuid);
    }

    public P getPlayer(Player player) {
        return this.getPlayer(player.getUniqueId());
    }

    public Collection<P> getPlayers() {
        return Collections.unmodifiableCollection(this.players.values());
    }

    public int getPlayerCount() {
        return this.players.size();
    }

    private void addPlayer(Player p) {
        try {
            P player = this.playerConstructor.newInstance(p);
            this.players.put(player.getUniqueId(), player);
        } catch (Exception e) {
            throw new RuntimeException("An Error occured instanciating player " + p.getName() + " with uuid " + p.getUniqueId(), e);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (!this.players.containsKey(player.getUniqueId())) {
            this.addPlayer(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.getPlayer(event.getPlayer()).store();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerKick(PlayerKickEvent event) {
        this.getPlayer(event.getPlayer()).store();
    }
}
