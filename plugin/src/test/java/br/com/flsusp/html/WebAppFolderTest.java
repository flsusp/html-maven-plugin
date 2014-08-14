package br.com.flsusp.html;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class WebAppFolderTest {

    @Test
    public void testSearch() {
        WebAppFolder folder = new WebAppFolder(new File(WebAppFolderTest.class.getClassLoader().getResource("lessprocessor").getPath()));
        WebAppFile file = folder.search("style/style.less");
        Assert.assertNotNull(file);
        Assert.assertEquals("style.less", file.getName());
    }
}
