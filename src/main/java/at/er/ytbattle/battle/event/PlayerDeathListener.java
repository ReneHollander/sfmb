package at.er.ytbattle.battle.event;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import at.er.ytbattle.battle.Battle;
import at.er.ytbattle.battle.Team;
import at.er.ytbattle.battle.player.BattlePlayer;
import at.er.ytbattle.battle.player.BattlePlayerManager;
import at.er.ytbattle.util.PlayerArmor;

public class PlayerDeathListener implements Listener {

	public PlayerDeathListener() {
		Bukkit.getPluginManager().registerEvents(this, Battle.instance());
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeath(PlayerDeathEvent event) {
		BattlePlayer player = BattlePlayerManager.instance().getBattlePlayer(event.getEntity());

		if (Battle.instance().getGame().isStarted() && Battle.instance().getGame().getTeamManager().isInTeam(player)) {

			Location spawn = Battle.instance().getGame().getSpawn().getLocation();

			Team t = Battle.instance().getGame().getTeamManager().getTeamByPlayer(player);

			ItemStack helmet = player.getInventory().getHelmet();
			ItemStack chestplate = player.getInventory().getChestplate();
			ItemStack leggings = player.getInventory().getLeggings();
			ItemStack boots = player.getInventory().getBoots();

			if (helmet != null) {
				helmet = helmet.clone();
				if (helmet.getType() == Material.DIAMOND_HELMET)
					helmet = new ItemStack(Material.IRON_HELMET);
				helmet.setDurability((short) 0);
				Iterator<?> hit = helmet.getEnchantments().entrySet().iterator();
				while (hit.hasNext())
					helmet.removeEnchantment((Enchantment) ((Map.Entry<?, ?>) hit.next()).getKey());
			}

			if (chestplate != null) {
				chestplate = chestplate.clone();
				if (chestplate.getType() == Material.DIAMOND_CHESTPLATE)
					chestplate = new ItemStack(Material.IRON_CHESTPLATE);
				chestplate.setDurability((short) 0);
				Iterator<?> cit = chestplate.getEnchantments().entrySet().iterator();
				while (cit.hasNext())
					chestplate.removeEnchantment((Enchantment) ((Map.Entry<?, ?>) cit.next()).getKey());
			}

			if (leggings != null) {
				leggings = leggings.clone();
				if (leggings.getType() == Material.DIAMOND_LEGGINGS)
					leggings = new ItemStack(Material.IRON_LEGGINGS);
				leggings.setDurability((short) 0);
				Iterator<?> lit = leggings.getEnchantments().entrySet().iterator();
				while (lit.hasNext())
					leggings.removeEnchantment((Enchantment) ((Map.Entry<?, ?>) lit.next()).getKey());
			}

			if (boots != null) {
				boots = boots.clone();
				if (boots.getType() == Material.DIAMOND_BOOTS)
					boots = new ItemStack(Material.IRON_BOOTS);
				boots.setDurability((short) 0);
				Iterator<?> bit = boots.getEnchantments().entrySet().iterator();
				while (bit.hasNext())
					boots.removeEnchantment((Enchantment) ((Map.Entry<?, ?>) bit.next()).getKey());
			}

			PlayerArmor armor = new PlayerArmor(helmet, chestplate, leggings, boots);
			Battle.instance().playerArmor.put(player, armor);

			if (t.getLifes() > 0) {
				t.setLifes(t.getLifes() - 1);
			} else {
				player.teleport(spawn);
				player.setDisplayName(player.getName());
				t.removePlayer(player);
				Bukkit.broadcastMessage(Battle.prefix() + player.getName() + " from the " + t.getTeamColor().getShortName() + " team has lost!");
				if (t.getTeamSize() == 0) {
					t.setLost(true);
					Bukkit.broadcastMessage(Battle.prefix() + "Team " + t.getTeamColor().getShortName() + " has lost!");
				}
			}

			// if (Battle.instance().getGame().getTeamManager().isLastTeam(t)) {
			// Bukkit.broadcastMessage(Battle.prefix() + "Team " +
			// t.getTeamColor().getShortName() + " has won the Battle!");
			// for (String s : t.getPlayers()) {
			// Player p = Bukkit.getPlayer(s);
			// p.setDisplayName(ChatColor.GOLD + "[Winner]" + ChatColor.WHITE +
			// " - " + p.getName());
			// p.teleport(spawn);
			// p.setAllowFlight(true);
			// p.setFlying(true);
			// }
			// RemindTimer.getRT().stopTimer();
			// Battle.instance().getGame().setStarted(false);
			// FireworkTimer ft = new FireworkTimer();
			// int id =
			// Bukkit.getScheduler().scheduleSyncRepeatingTask(Battle.instance(),
			// ft, 0, 20L);
			// ft.setID(id);
			// Bukkit.broadcastMessage(Battle.prefix() +
			// "Thanks for playing! Battle Battle.instance() v" +
			// Battle.instance().getDescription().getVersion() +
			// " made by EXSolo and Rene8888.");
			// }
		}

		Battle.instance().updateScoreboard();

	}
}