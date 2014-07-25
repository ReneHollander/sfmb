package at.er.ytbattle.battle.command.handler;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.er.ytbattle.battle.Battle;
import at.er.ytbattle.battle.Team;
import at.er.ytbattle.battle.command.AbstractCommand;
import at.er.ytbattle.battle.player.BattlePlayer;
import at.er.ytbattle.battle.timer.timeables.GraceTimer;
import at.er.ytbattle.battle.timer.timeables.RemindTimer;
import at.er.ytbattle.util.SerializableLocation;

public class BattleCommandStart extends AbstractCommand {

    @Override
    public boolean onCommand(String label, String[] args, BattlePlayer player) {
        if (Battle.instance().getGame().isStarted() == false) {

            if (Battle.instance().getGame().getTeamManager().getTeamCount() > 0) {

                Bukkit.broadcastMessage(Battle.prefix() + "The Battle has been started! Let the games begin!");

                try {
                    int timer = Integer.parseInt(args[0]);
                    Battle.instance().getGame().getSpawn().getLocation().getWorld().setPVP(false);
                    new GraceTimer(timer * 60);
                } catch (Exception e) {
                }

                int startlifes = Battle.instance().getConfig().getInt("config.lifes-at-start");
                boolean reminder = Battle.instance().getConfig().getBoolean("config.enable-remind-scheduler");
                boolean baseItem = Battle.instance().getConfig().getBoolean("config.enable-base-block");

                if (reminder == true) {
                    new RemindTimer().startReminder();
                }

                if (Battle.instance().getGame().getSpawn() == null) {
                    Location spawn = player.getLocation();
                    Battle.instance().getGame().setSpawn(new SerializableLocation(spawn));
                    spawn.getWorld().setSpawnLocation((int) spawn.getX(), (int) spawn.getY(), (int) spawn.getZ());
                }

                Battle.instance().getGame().getSpawn().getLocation().getWorld().setTime(200);

                ItemStack base = new ItemStack(Material.QUARTZ_ORE);
                ItemMeta baseMeta = base.getItemMeta();
                baseMeta.setDisplayName(ChatColor.GRAY + "Base Block");
                baseMeta.setLore(Arrays.asList("Place me to create a base"));
                base.setItemMeta(baseMeta);

                for (Team t : Battle.instance().getGame().getTeamManager().getTeams()) {
                    if (t.getTeamSize() > 0) {
                        t.setLifes(startlifes);
                        t.setupInitialWool();

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
                }

                Battle.instance().getGame().setStarted(true);
                Battle.instance().setTags();
                Battle.instance().updateScoreboard();

                Location soundLocation = Battle.instance().getGame().getSpawn().getLocation();

                soundLocation.getWorld().playSound(soundLocation, Sound.AMBIENCE_THUNDER, 10, 1);
                soundLocation.getWorld().playSound(soundLocation, Sound.EXPLODE, 10F, 0.5F);

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
