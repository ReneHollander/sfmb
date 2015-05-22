package at.er.sfmb.plugin.command.handler;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.command.CustomCommand;
import at.er.sfmb.plugin.player.BattlePlayer;
import at.er.sfmb.util.SerializableLocation;
import org.bukkit.Location;

public class BattleCommandSetspawn implements CustomCommand {

    @Override
    public boolean onCommand(String label, String[] args, BattlePlayer player) {
        Location spawn = player.getLocation();
        BattlePlugin.game().setSpawn(new SerializableLocation(spawn));
        spawn.getWorld().setSpawnLocation((int) spawn.getX(), (int) spawn.getY(), (int) spawn.getZ());
        player.sendMessage(BattlePlugin.prefix() + "Battlespawn has been set to your current location!");
        return true;
    }
}
