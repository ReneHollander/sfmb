package at.er.sfmb.test.command;

import at.er.sfmb.plugin.command.ArgType;
import junit.framework.Assert;
import org.junit.Test;

public class ArgTypeTest {

    @Test
    public void testArgTypeStringGetType() {
        Assert.assertEquals(ArgType.STRING.getType(), ArgType.Type.SINGLE);
    }

    @Test
    public void testArgTypeStringParser() {
        ArgType.SingleArgTypeParser parser = (ArgType.SingleArgTypeParser) ArgType.STRING.getArgTypeParser();
        Assert.assertEquals(parser.parse("hello world"), "hello world");
    }

    @Test
    public void testArgTypeUsernameGetType() {
        Assert.assertEquals(ArgType.USERNAME.getType(), ArgType.Type.SINGLE);
    }

    @Test
    public void testArgTypeIntegerGetType() {
        Assert.assertEquals(ArgType.INTEGER.getType(), ArgType.Type.SINGLE);
    }

    @Test
    public void testArgTypeIntegerParser() {
        ArgType.SingleArgTypeParser parser = (ArgType.SingleArgTypeParser) ArgType.INTEGER.getArgTypeParser();
        Assert.assertEquals(parser.parse("5"), 5);
    }

    @Test
    public void testArgTypeDoubleGetType() {
        Assert.assertEquals(ArgType.DOUBLE.getType(), ArgType.Type.SINGLE);
    }

    @Test
    public void testArgTypeDoubleParser() {
        ArgType.SingleArgTypeParser parser = (ArgType.SingleArgTypeParser) ArgType.DOUBLE.getArgTypeParser();
        Assert.assertEquals(parser.parse("5.0"), 5D);
    }

    @Test
    public void testArgTypeMultiStringGetType() {
        Assert.assertEquals(ArgType.MULTI_STRING.getType(), ArgType.Type.MULTI);
    }

    @Test
    public void testArgTypeMultiStringParser() {
        ArgType.MultiArgTypeParser parser = (ArgType.MultiArgTypeParser) ArgType.MULTI_STRING.getArgTypeParser();
        Assert.assertEquals(parser.parse(new String[]{"hello", "world"}), "hello world");
    }
}
