package at.er.sfmb.plugin.player;

import at.er.sfmb.plugin.BattlePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class BattlePlayerManager implements Listener {

    private Map<UUID, BattlePlayer> players;

    public BattlePlayerManager() {
        this.players = new HashMap<UUID, BattlePlayer>();

        BattlePlugin.instance().getServer().getPluginManager().registerEvents(this, BattlePlugin.instance());

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!this.players.containsKey(p.getUniqueId())) {
                BattlePlayer battlePlayer = new BattlePlayer(p);
                this.players.put(UUID.fromString(p.getUniqueId().toString()), battlePlayer);
            }
        }
    }

    private Object readResolve() {
        BattlePlugin.instance().getServer().getPluginManager().registerEvents(this, BattlePlugin.instance());
        return this;
    }

    public BattlePlayer getBattlePlayer(UUID uuid) {
        return this.players.get(uuid);
    }

    public BattlePlayer getBattlePlayer(Player player) {
        return this.getBattlePlayer(player.getUniqueId());
    }

    public List<BattlePlayer> getAllBattlePlayers() {
        return new ArrayList<BattlePlayer>(this.players.values());
    }

    public int getBattlePlayerCount() {
        return this.players.size();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (!this.players.containsKey(player.getUniqueId())) {
            BattlePlayer battlePlayer = new BattlePlayer(player);
            this.players.put(UUID.fromString(player.getUniqueId().toString()), battlePlayer);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.getBattlePlayer(event.getPlayer()).store();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerKick(PlayerKickEvent event) {
        this.getBattlePlayer(event.getPlayer()).store();
    }
}
