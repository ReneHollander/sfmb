package at.er.ytbattle.battle.cmd;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.er.ytbattle.battle.Battle;
import at.er.ytbattle.battle.Team;
import at.er.ytbattle.battle.TeamManager;
import at.er.ytbattle.battle.player.BattlePlayer;
import at.er.ytbattle.battle.timer.GraceTimer;
import at.er.ytbattle.battle.timer.RemindTimer;
import at.er.ytbattle.util.SerializableLocation;

public class BattleCommandStart {

	private TeamManager teamManager;

	public BattleCommandStart() {
		this.teamManager = Battle.instance().getGame().getTeamManager();
	}

	public boolean onCmdStart(String[] args, BattlePlayer player) {
		if (Battle.instance().getGame().isStarted() == false) {

			int teamcounter = 0;

			for (Team t : this.teamManager.getTeams()) {
				if (t.getTeamSize() > 0) {
					teamcounter++;
				}
			}

			if (teamcounter > 1) {

				int timer = 0;

				int startlifes = Battle.instance().getConfig().getInt("config.lifes-at-start");
				boolean reminder = Battle.instance().getConfig().getBoolean("config.enable-remind-scheduler");

				if (Battle.instance().getGame().getSpawn() == null) {
					Battle.instance().getGame().setSpawn(new SerializableLocation(player.getLocation()));
					Battle.instance().getGame().getSpawn().getLocation().getWorld()
							.setSpawnLocation((int) Battle.instance().getGame().getSpawn().getLocation().getX(), (int) Battle.instance().getGame().getSpawn().getLocation().getY(), (int) Battle.instance().getGame().getSpawn().getLocation().getZ());
					Battle.instance().getConfig().set("saves.spawn.world", player.getLocation().getWorld().getName());
					Battle.instance().getConfig().set("saves.spawn.x", player.getLocation().getX());
					Battle.instance().getConfig().set("saves.spawn.y", player.getLocation().getY());
					Battle.instance().getConfig().set("saves.spawn.z", player.getLocation().getZ());
					Battle.instance().saveConfig();
					player.sendMessage(Battle.prefix() + "Battlespawn has been set to your current location!");
				}

				Battle.instance().getGame().getSpawn().getLocation().getWorld().setTime(200);

				ItemStack base = new ItemStack(Material.QUARTZ_ORE);
				ItemMeta baseMeta = base.getItemMeta();
				baseMeta.setDisplayName(ChatColor.GRAY + "Base Block");
				baseMeta.setLore(Arrays.asList("Place me to create a base"));
				base.setItemMeta(baseMeta);

				boolean baseItem = Battle.instance().getConfig().getBoolean("config.enable-base-block");

				for (Team t : this.teamManager.getTeams()) {

					if (t.getTeamSize() > 0) {
						t.setLifes(startlifes);
					}

					for (BattlePlayer p : t.getPlayers()) {
						p.teleport(Battle.instance().getGame().getSpawn().getLocation());
						p.setGameMode(GameMode.SURVIVAL);
						p.setAllowFlight(false);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1));
						p.closeInventory();
						p.getInventory().clear();
						p.getInventory().setHelmet(new ItemStack(Material.AIR));
						p.getInventory().setChestplate(new ItemStack(Material.AIR));
						p.getInventory().setLeggings(new ItemStack(Material.AIR));
						p.getInventory().setBoots(new ItemStack(Material.AIR));
						p.getInventory().addItem(new Wool(t.getTeamColor().getDyeColor()).toItemStack(2));
						if (baseItem) {
							p.getInventory().addItem(base);
						}
						p.setHealth(20);
						p.setFoodLevel(20);
						p.setSaturation(20);
					}
				}

				try {
					timer = Integer.parseInt(args[1]);
				} catch (Exception e) {
				}
				if (timer > 0) {
					Battle.instance().getGame().getSpawn().getLocation().getWorld().setPVP(false);

					Battle.instance().getGame().setGraceTimer(new GraceTimer(timer * 60));
					Bukkit.getScheduler().scheduleSyncRepeatingTask(Battle.instance(), Battle.instance().getGame().getGraceTimer(), 0, 20);
				}
				if (reminder == true) {
					Bukkit.broadcastMessage(Battle.prefix() + "Starting battle reminder. Scheduling every 15 minutes!");
					new RemindTimer();
				}
				Bukkit.broadcastMessage(Battle.prefix() + "The game will warn you to place your wools in time! Remind to place ALL the wools before reloading or restarting!");
				for (Team t : this.teamManager.getTeams()) {
					t.setupInitialWool();
				}
				Battle.instance().getGame().setStarted(true);
				Battle.instance().setTags();
				Battle.instance().updateScoreboard();
				return true;
			} else {
				player.sendMessage(Battle.prefix() + "There have to be at least two teams with one or more Player(s) before the battle can be launched!");
			}
		} else {
			player.sendMessage(Battle.prefix() + "Battle has already been started!");
		}
		return true;
	}
}
