package br.com.flsusp.html;

import org.junit.Assert;
import org.junit.Test;

public class FileUtilsTest {

    @Test
    public void testReplaceExtension() {
        Assert.assertEquals("test.css", FileUtils.replaceExtension("test.less", "css"));
    }
}
