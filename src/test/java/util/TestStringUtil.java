package util;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class TestStringUtil {
    @Test
    public void testBytesToHex() {
        byte[] b = {15, 127};
        String hex = StringUtil.bytesToHex(b);
        Assert.assertEquals("0f7f", hex);
    }

    @Test
    public void TestSha256() throws NoSuchAlgorithmException {
        byte[] b = StringUtil.sha256("test");
        Assert.assertEquals(b.length, 32);

        String s = StringUtil.bytesToHex(b);
        Assert.assertEquals("9F86D081884C7D659A2FEAA0C55AD015A3BF4F1B2B0B822CD15D6C15B0F00A08", s);
    }
}
