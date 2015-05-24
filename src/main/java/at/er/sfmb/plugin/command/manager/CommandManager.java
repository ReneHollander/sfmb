package at.er.sfmb.plugin.command.manager;

import at.er.sfmb.plugin.command.CustomCommand;
import org.bukkit.command.CommandExecutor;

import java.util.Map;

public interface CommandManager extends CommandExecutor {

    void addCommandHandler(String label, CustomCommand bc);

    void addCommandHandler(String[] labels, CustomCommand bc);

    void removeCommandHandler(String label);

    void removeCommandHandler(String[] labels);

    Map<String, CustomCommand> getCommandHandlers();
}
