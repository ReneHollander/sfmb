package at.er.sfmb.plugin.command;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.command.manager.AbstractCommandManager;
import at.er.sfmb.plugin.player.BattlePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BattleCommandManager extends AbstractCommandManager {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("battle") || label.equalsIgnoreCase("b")) {
            if (sender instanceof Player) {
                BattlePlayer player = BattlePlugin.game().getBattlePlayerManager().getBattlePlayer((Player) sender);

                if (args.length == 0) {
                    player.sendMessage(BattlePlugin.prefix() + "EXSolo's and Rene8888's Battle Plugin: For a command overview do /battle help");
                } else {
                    String sublabel = args[0];
                    CustomCommand abc = this.getCommandHandlers().get(sublabel);
                    if (abc != null) {
                        String[] subargs = Arrays.copyOfRange(args, 1, args.length);
                        boolean ret = abc.onCommand(sublabel, subargs, player);
                        if (!ret) {
                            player.sendMessage(BattlePlugin.prefix() + "Something went wrong! :(");
                        }
                    } else {
                        player.sendMessage(BattlePlugin.prefix() + "No command with label " + sublabel + " found!");
                    }
                }
            } else {
                sender.sendMessage(BattlePlugin.prefix() + "The Battle Commands are only available for users!");
            }
            return true;
        } else {
            return false;
        }
    }
}
