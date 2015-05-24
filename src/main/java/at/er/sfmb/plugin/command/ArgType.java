package at.er.sfmb.plugin.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public enum ArgType {
    STRING(Type.SINGLE, new SingleArgTypeParser<String>() {
        @Override
        public String parse(String input) {
            return input;
        }
    }), USERNAME(Type.SINGLE, new SingleArgTypeParser<Player>() {
        @Override
        public Player parse(String input) {
            return Bukkit.getPlayer(input);
        }
    }), INTEGER(Type.SINGLE, new SingleArgTypeParser<Integer>() {
        @Override
        public Integer parse(String input) throws InvalidTypeException {
            try {
                return Integer.parseInt(input);
            } catch (Exception e) {
                throw new InvalidTypeException("Argument is not an Integer", e);
            }
        }
    }), DOUBLE(Type.SINGLE, new SingleArgTypeParser<Double>() {
        @Override
        public Double parse(String input) throws InvalidTypeException {
            try {
                return Double.parseDouble(input);
            } catch (Exception e) {
                throw new InvalidTypeException("Argument is not an Integer", e);
            }
        }
    }), MULTI_STRING(Type.MULTI, new MultiArgTypeParser<String>() {
        @Override
        public String parse(String[] input) throws InvalidTypeException {
            return String.join(" ", input);
        }
    });

    private Type type;
    private ArgTypeParser<?> argTypeParser;

    ArgType(Type type, ArgTypeParser<?> argTypeParser) {
        this.type = type;
        this.argTypeParser = argTypeParser;
    }

    public Type getType() {
        return type;
    }

    public ArgTypeParser<?> getArgTypeParser() {
        return argTypeParser;
    }

    public interface ArgTypeParser<T> {
    }

    public interface SingleArgTypeParser<T> extends ArgTypeParser {
        T parse(String input) throws InvalidTypeException;
    }

    public interface MultiArgTypeParser<T> extends ArgTypeParser {
        T parse(String[] input) throws InvalidTypeException;
    }

    public static class InvalidTypeException extends IllegalArgumentException {
        public InvalidTypeException(String s) {
            super(s);
        }

        public InvalidTypeException(String message, Throwable cause) {
            super(message, cause);
        }

        public InvalidTypeException(Throwable cause) {
            super(cause);
        }
    }

    public enum Type {
        SINGLE, MULTI
    }

}