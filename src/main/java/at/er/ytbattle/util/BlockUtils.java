package at.er.ytbattle.util;

import at.er.ytbattle.plugin.BattlePlugin;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

public class BlockUtils {

    public static boolean placeWool(Location l) {

        int minHeight = BattlePlugin.configurationHelper().getConfigFile().getInt(ConfigurationHelper.GAME_WOOL_MINPLACEHEIGHT_PATH);

        if (l.getBlockY() < minHeight) {
            return false;
        } else {
            for (int i = l.getBlockY() + 1; i <= 255; i++) {
                Location now = l.clone();
                now.setY(i);
                Block b = now.getWorld().getBlockAt(now);
                if (b != null) {
                    if (b.getType() != Material.AIR) {
                        return false;
                    }
                }
            }
        }

        World w = l.getWorld();

        int rad = BattlePlugin.configurationHelper().getConfigFile().getInt(ConfigurationHelper.GAME_WOOL_REMOVERADIUS_PATH);

        for (int x = -rad; x <= rad; x++) {
            for (int y = -rad; y <= rad; y++) {
                for (int z = -rad; z <= rad; z++) {
                    Block b = w.getBlockAt((int) l.getX() + x, (int) l.getY() + y, (int) l.getZ() + z);
                    if ((b.getType() != Material.WOOL) && (b.getType() != Material.BEDROCK) && (b.getType() != Material.GLASS)) {
                        w.getBlockAt((int) l.getX() + x, (int) l.getY() + y, (int) l.getZ() + z).setType(Material.AIR);
                        w.playEffect(new Location(l.getWorld(), (int) l.getX() + x, (int) l.getY() + y, (int) l.getZ() + z), Effect.MOBSPAWNER_FLAMES, 0);
                    }
                }
            }
        }
        return true;
    }

    public static void buildBase(Location l) {
        World w = l.getWorld();

        for (int x = -2; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    Material type = w.getBlockAt((int) l.getX() + x, (int) l.getY() + y, (int) l.getZ() + z).getType();
                    if (type != Material.AIR && type != Material.IRON_ORE && type != Material.GOLD_ORE && type != Material.DIAMOND_ORE && type != Material.GLASS && type != Material.WOOL) {
                        w.getBlockAt((int) l.getX() + x, (int) l.getY() + y, (int) l.getZ() + z).setType(Material.AIR);
                        w.playEffect(new Location(l.getWorld(), (int) l.getX() + x, (int) l.getY() + y, (int) l.getZ() + z), Effect.MOBSPAWNER_FLAMES, 0);
                    }
                }
            }
        }

        Location tmp = l;

        tmp.setZ(tmp.getZ() - 2);
        tmp.setX(tmp.getX() - 2);

        w.getBlockAt(tmp).setType(Material.CHEST);

        tmp.setZ(tmp.getZ() + 1);

        w.getBlockAt(tmp).setType(Material.CHEST);

        Chest c = (Chest) w.getBlockAt(tmp).getState();
        c.getInventory().setContents(BattleUtils.getStarterChestContents());
    }
}
