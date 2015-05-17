package at.er.ytbattle.plugin.feature.explicit;

import at.er.ytbattle.plugin.BattlePlugin;
import at.er.ytbattle.plugin.feature.AbstractFeature;
import at.er.ytbattle.plugin.player.BattlePlayer;
import at.er.ytbattle.plugin.team.Team;
import at.er.ytbattle.util.BattleUtils;
import at.er.ytbattle.util.PlayerArmor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class ArmorFeature extends AbstractFeature {

    private Map<Player, PlayerArmor> playerArmor;

    public ArmorFeature() {
        super("PlayerArmor", "When a player respawns, he will wear the armor he had on death. Diamond armor will be downgraded");
        this.playerArmor = new HashMap<>();
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        BattlePlayer player = BattlePlugin.game().getBattlePlayerManager().getBattlePlayer(event.getPlayer());

        if (BattlePlugin.game().isStarted() && BattlePlugin.game().getTeamManager().isInTeam(player)) {
            Team t = BattlePlugin.game().getTeamManager().getTeamByPlayer(player);
            PlayerArmor armor = this.playerArmor.get(player);

            if (armor != null) {
                player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                player.getInventory().setHelmet(armor.getHelmet());
                player.getInventory().setChestplate(armor.getChestplate());
                player.getInventory().setLeggings(armor.getLeggings());
                player.getInventory().setBoots(armor.getBoots());
                this.playerArmor.remove(player);
            }

            BattlePlugin.game().getInvincibilityTimerManager().createTimer(player);
            event.setRespawnLocation(BattlePlugin.game().getSpawn().getLocation());
            player.getInventory().addItem(new Wool(t.getTeamColor().getDyeColor()).toItemStack(1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 2));
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {
        BattlePlayer player = BattlePlugin.game().getBattlePlayerManager().getBattlePlayer(event.getEntity());

        if (BattlePlugin.game().isStarted() && BattlePlugin.game().getTeamManager().isInTeam(player)) {
            Team t = BattlePlugin.game().getTeamManager().getTeamByPlayer(player);

            if (t.getLifes() > 0) {
                t.setLifes(t.getLifes() - 1);

                ItemStack helmet = player.getInventory().getHelmet();
                ItemStack chestplate = player.getInventory().getChestplate();
                ItemStack leggings = player.getInventory().getLeggings();
                ItemStack boots = player.getInventory().getBoots();

                if (helmet != null) {
                    helmet = helmet.clone();
                    if (helmet.getType() == Material.DIAMOND_HELMET)
                        helmet = new ItemStack(Material.IRON_HELMET);
                    helmet.setDurability((short) 0);
                    BattleUtils.removeEnchants(helmet);
                }

                if (chestplate != null) {
                    chestplate = chestplate.clone();
                    if (chestplate.getType() == Material.DIAMOND_CHESTPLATE)
                        chestplate = new ItemStack(Material.IRON_CHESTPLATE);
                    chestplate.setDurability((short) 0);
                    BattleUtils.removeEnchants(chestplate);
                }

                if (leggings != null) {
                    leggings = leggings.clone();
                    if (leggings.getType() == Material.DIAMOND_LEGGINGS)
                        leggings = new ItemStack(Material.IRON_LEGGINGS);
                    leggings.setDurability((short) 0);
                    BattleUtils.removeEnchants(leggings);
                }

                if (boots != null) {
                    boots = boots.clone();
                    if (boots.getType() == Material.DIAMOND_BOOTS)
                        boots = new ItemStack(Material.IRON_BOOTS);
                    boots.setDurability((short) 0);
                    BattleUtils.removeEnchants(boots);
                }

                PlayerArmor armor = new PlayerArmor(helmet, chestplate, leggings, boots);
                BattlePlugin.instance().playerArmor.put(player, armor);
            }
        }
    }
}
