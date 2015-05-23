package at.er.sfmb.test.command;

import at.er.sfmb.plugin.command.AnnotatedCommand;
import at.er.sfmb.plugin.command.ArgType;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

public class AnnotedCommandTest {

    @Test
    public void testSimpleArgs() throws NoSuchMethodException, AnnotatedCommand.ParseException {
        TestListener tl = new TestListener();
        AnnotatedCommand ac = new AnnotatedCommand("test", new ArgType[]{ArgType.STRING, ArgType.DOUBLE}, new String[]{"msg", "displaytime"}, tl, tl.method());
        Object[] args = ac.parse(new String[]{"this_is_a_message", "5"});
        Assert.assertArrayEquals(args, new Object[]{"this_is_a_message", 5D});
    }

    @Test
    public void testMultiArg() throws NoSuchMethodException, AnnotatedCommand.ParseException {
        TestListener tl = new TestListener();
        AnnotatedCommand ac = new AnnotatedCommand("test", new ArgType[]{ArgType.INTEGER, ArgType.MULTI_STRING}, new String[]{"time", "text"}, tl, tl.method());
        Object[] args = ac.parse(new String[]{"10", "this", "is", "a", "test", "string"});
        Assert.assertArrayEquals(args, new Object[]{10, "this is a test string"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiArgWrongPosition() throws NoSuchMethodException {
        TestListener tl = new TestListener();
        AnnotatedCommand ac = new AnnotatedCommand("test", new ArgType[]{ArgType.INTEGER, ArgType.MULTI_STRING, ArgType.INTEGER}, new String[]{"time", "text", "i am a integer"}, tl, tl.method());
    }

    @Test
    public void testMultiArgOnly() throws NoSuchMethodException, AnnotatedCommand.ParseException {
        TestListener tl = new TestListener();
        AnnotatedCommand ac = new AnnotatedCommand("test", new ArgType[]{ArgType.MULTI_STRING}, new String[]{"test"}, tl, tl.method());
        Object[] args = ac.parse(new String[]{"this", "is", "a", "test", "string"});
        Assert.assertArrayEquals(args, new Object[]{"this is a test string"});
    }

    @Test
    public void testExecute() throws NoSuchMethodException, AnnotatedCommand.ParseException {
        TestListener tl = new TestListener();
        AnnotatedCommand ac = new AnnotatedCommand("test", new ArgType[]{ArgType.MULTI_STRING}, new String[]{"test"}, tl, tl.method());
        Object[] args = ac.parse(new String[]{"this", "is", "a", "test", "string"});
        ac.execute(null, args);
    }

    public class TestListener implements Listener {
        public void testMethod(CommandSender sender, Object[] args) {
            Assert.assertEquals(sender, null);
            Assert.assertEquals(args, new Object[]{"this is a test string"});
        }

        public Method method() throws NoSuchMethodException {
            return this.getClass().getMethod("testMethod", CommandSender.class, Object[].class);
        }
    }

}
