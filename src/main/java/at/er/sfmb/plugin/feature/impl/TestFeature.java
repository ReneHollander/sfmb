package at.er.sfmb.plugin.feature.impl;

import feature.AbstractFeature;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class TestFeature extends AbstractFeature {

    public TestFeature() {
        super("TestFeature", "This is a TestFeature");
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
