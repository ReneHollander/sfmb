package at.er.sfmb.plugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AnnotationBasedCommandManager implements CommandExecutor {

    private final String[] labels;
    private final Map<String, AnnotatedCommand> commandMap;

    public AnnotationBasedCommandManager(String... labels) {
        this.labels = labels;
        this.commandMap = new HashMap<>();
    }

    public void register(Class<? extends Listener> clazz) {
        Method[] methods = getClass().getMethods();
        for (Method method : methods) {
            CommandAnnotation ca = method.getAnnotation(CommandAnnotation.class);
            if (ca != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 2 || parameterTypes[0] != CommandSender.class || parameterTypes[1] != Object[].class)
                    throw new IllegalArgumentException("method needs parameters CommandSender and Object[]");
                AnnotatedCommand ac = new AnnotatedCommand(ca.label(), ca.argTypes(), ca.argNames(), method);
                this.commandMap.put(ca.label(), ac);
            }
        }
    }

    private boolean hasLabel(String label) {
        for (String s : this.labels) {
            if (label.equalsIgnoreCase(s)) return true;
        }
        return false;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (this.hasLabel(s)) {
            AnnotatedCommand ac = this.commandMap.get(s);
            ac.parse(strings);
            return true;
        }
        return false;
    }
}
