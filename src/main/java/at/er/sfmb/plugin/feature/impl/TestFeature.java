package at.er.sfmb.plugin.feature.impl;

import at.er.sfmb.plugin.command.ArgType;
import at.er.sfmb.plugin.command.CommandAnnotation;
import at.er.sfmb.plugin.feature.AbstractFeature;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Arrays;

public class TestFeature extends AbstractFeature {

    public TestFeature() {
        super("TestFeature", "This is a TestFeature");
    }

    @CommandAnnotation(label = "test", argTypes = {ArgType.STRING}, argNames = {"msg"})
    public void testCommand(CommandSender sender, Object[] args) {
        sender.sendMessage(Arrays.toString(args));
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
