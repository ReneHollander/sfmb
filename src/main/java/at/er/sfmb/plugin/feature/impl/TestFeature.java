package at.er.sfmb.plugin.feature.impl;

import at.er.sfmb.plugin.feature.AbstractFeature;
import at.er.sfmb.plugin.player.BattlePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class TestFeature extends AbstractFeature {

    public TestFeature() {
        super("TestFeature", "This is a TestFeature");
    }

    @Override
    public boolean onCommand(String label, String[] args, BattlePlayer player) {
        if (label.equalsIgnoreCase("test")) {
            player.sendMessage("It works!");
            return true;
        }

        return false;
    }

    @Override
    public String[] getCommandLabels() {
        return new String[]{"test"};
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.getPlayer().sendMessage("It works");
    }
}
