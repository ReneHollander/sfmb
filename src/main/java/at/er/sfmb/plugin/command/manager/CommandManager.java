package at.er.sfmb.plugin.command.manager;

import at.er.sfmb.plugin.command.CustomCommand;
import org.bukkit.command.CommandExecutor;

import java.util.Map;

public interface CommandManager extends CommandExecutor {

    public void addCommandHandler(String label, CustomCommand bc);

    public void addCommandHandler(String[] labels, CustomCommand bc);

    public void removeCommandHandler(String label);

    public void removeCommandHandler(String[] labels);

    public Map<String, CustomCommand> getCommandHandlers();
}
