package at.er.sfmb.plugin.command;

import at.er.sfmb.plugin.BattlePlugin;
import at.er.sfmb.plugin.player.BattlePlayer;
import com.google.common.base.Preconditions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;

public class CommandManager implements CommandExecutor {

    private HashMap<String, CustomCommand> commandHandlers;

    public CommandManager() {
        this.commandHandlers = new HashMap<>();

        // this.addCommandHandler("help", new BattleCommandHelp());
    }

    public void addCommandHandler(String label, CustomCommand bc) {
        Preconditions.checkNotNull(label);
        Preconditions.checkNotNull(bc);
        if (this.commandHandlers.containsKey(label)) throw new IllegalArgumentException("commandLabel in use");
        this.commandHandlers.put(label, bc);
    }

    public void addCommandHandler(String[] labels, CustomCommand bc) {
        for (String s : labels) {
            addCommandHandler(s, bc);
        }
    }

    public void removeCommandHandler(String label) {
        Preconditions.checkNotNull(label);
        this.commandHandlers.remove(label);
    }

    public void removeCommandHandler(String[] labels) {
        for (String s : labels) {
            removeCommandHandler(s);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("battle") || label.equalsIgnoreCase("b")) {
            if (sender instanceof Player) {
                BattlePlayer player = BattlePlugin.game().getBattlePlayerManager().getBattlePlayer((Player) sender);

                if (args.length == 0) {
                    player.sendMessage(BattlePlugin.prefix() + "EXSolo's and Rene8888's Battle Plugin: For a command overview do /battle help");
                } else {
                    String sublabel = args[0];
                    CustomCommand abc = this.commandHandlers.get(sublabel);
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
