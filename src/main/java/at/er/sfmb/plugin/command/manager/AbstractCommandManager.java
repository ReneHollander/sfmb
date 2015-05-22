package at.er.sfmb.plugin.command.manager;

import at.er.sfmb.plugin.command.CustomCommand;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCommandManager implements CommandManager {

    private HashMap<String, CustomCommand> commandHandlers;

    public AbstractCommandManager() {
        this.commandHandlers = new HashMap<>();
    }

    public void addCommandHandler(String subLabel, CustomCommand bc) {
        Preconditions.checkNotNull(subLabel);
        Preconditions.checkNotNull(bc);
        if (this.commandHandlers.containsKey(subLabel)) throw new IllegalArgumentException("commandLabel in use");
        this.commandHandlers.put(subLabel, bc);
    }

    public void addCommandHandler(String[] subLabels, CustomCommand bc) {
        for (String s : subLabels) {
            addCommandHandler(s, bc);
        }
    }

    public void removeCommandHandler(String subLabel) {
        Preconditions.checkNotNull(subLabel);
        this.commandHandlers.remove(subLabel);
    }

    public void removeCommandHandler(String[] subLabels) {
        for (String s : subLabels) {
            removeCommandHandler(s);
        }
    }

    public Map<String, CustomCommand> getCommandHandlers() {
        return commandHandlers;
    }
}
