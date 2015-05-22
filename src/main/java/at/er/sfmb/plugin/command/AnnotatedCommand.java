package at.er.sfmb.plugin.command;

import java.lang.reflect.Method;
import java.util.Arrays;

public class AnnotatedCommand {

    private String label;
    private ArgType[] argTypes;
    private String[] argNames;
    private Method method;

    public AnnotatedCommand(String label, ArgType[] argTypes, String[] argNames, Method method) {
        this.label = label;
        this.method = method;
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

    public Method getMethod() {
        return method;
    }


    public Object[] parse(String[] strings) {
        return null;
    }

    public enum ArgType {
        STRING, USERNAME, INTEGER, FLOAT, MULTI_STRING;
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

}
