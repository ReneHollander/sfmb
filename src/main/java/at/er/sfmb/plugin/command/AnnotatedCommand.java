package at.er.sfmb.plugin.command;

import com.google.common.base.Preconditions;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnotatedCommand {

    private String label;
    private ArgType[] argTypes;
    private String[] argNames;
    private Object object;
    private Method method;

    public AnnotatedCommand(String label, ArgType[] argTypes, String[] argNames, Object object, Method method) {
        Preconditions.checkNotNull(label);
        Preconditions.checkNotNull(argTypes);
        Preconditions.checkNotNull(argNames);
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(method);

        this.label = label;
        this.argTypes = argTypes;
        this.argNames = argNames;
        this.object = object;
        this.method = method;

        validateArgTypes(this.argTypes, this.argNames);
    }

    public String getLabel() {
        return label;
    }

    public ArgType[] getArgTypes() {
        return argTypes;
    }

    public String[] getArgNames() {
        return argNames;
    }

    public Object getObject() {
        return object;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] parse(String[] strings) throws ArgType.InvalidTypeException, ParseException {
        if (strings == null) return null;
        List<Object> ret = new ArrayList<>();
        for (int i = 0; i < this.getArgTypes().length; i++) {
            ArgType argType = this.getArgTypes()[i];
            String argName = this.getArgNames()[i];

            if (argType.getType() == ArgType.Type.SINGLE) {
                ArgType.SingleArgTypeParser parser = (ArgType.SingleArgTypeParser) argType.getArgTypeParser();
                String s = strings[i];
                Object res = null;
                try {
                    res = parser.parse(s);
                } catch (Exception e) {
                    throw new ParseException(argName, argType, null, null);
                }
                ret.add(parser.parse(s));
            } else if (argType.getType() == ArgType.Type.MULTI) {
                ArgType.MultiArgTypeParser parser = (ArgType.MultiArgTypeParser) argType.getArgTypeParser();
                String[] data = Arrays.copyOfRange(strings, i, strings.length);
                Object res = null;
                try {
                    res = parser.parse(data);
                } catch (Exception e) {
                    throw new ParseException(argName, argType, null, null);
                }
                ret.add(parser.parse(data));
            } else {
                throw new IllegalStateException("Invalid ArgType Type");
            }
        }
        return ret.toArray();
    }

    public void execute(CommandSender sender, Object[] args) {
        try {
            this.getMethod().invoke(this.getObject(), sender, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "AnnotatedCommand{" +
                "label='" + label + '\'' +
                ", argTypes=" + Arrays.toString(argTypes) +
                ", argNames=" + Arrays.toString(argNames) +
                ", method=" + method +
                '}';
    }

    private static void validateArgTypes(ArgType[] argTypes, String[] argNames) {
        if (argTypes.length != argNames.length)
            throw new IllegalArgumentException("Every ArgType needs a name");
        for (int i = 0; i < argTypes.length; i++) {
            ArgType at = argTypes[i];
            if (at.getType() == ArgType.Type.MULTI && (i != argTypes.length - 1))
                throw new IllegalArgumentException("A MultiArgType can only be at the last position");
        }
    }

    public class ParseException extends Exception {
        private String argName;
        private ArgType argType;

        public ParseException(String argName, ArgType argType, String msg, Throwable cause) {
            super(msg, cause);
            this.argName = argName;
            this.argType = argType;
        }

        public String getArgName() {
            return argName;
        }

        public ArgType getArgType() {
            return argType;
        }
    }

}
