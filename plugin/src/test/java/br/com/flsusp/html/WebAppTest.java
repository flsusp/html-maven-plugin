package br.com.flsusp.html;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import br.com.flsusp.html.parser.Html;

public class WebAppTest {

	@Test
	public void test() {
		final File outputDir = new File("target");

		WebApp webapp = new WebApp();
		webapp.load(new File(WebAppTest.class.getClassLoader().getResource("static").getPath()));
		webapp.outputFilesTo(outputDir);

		Html html = Html.parse(new File(outputDir, "static/index.html"));
		assertEquals(0, html.head().numberOfCssLinks());
		assertEquals(3, html.head().numberOfJsScripts());
		assertEquals(1, html.head().numberOfLessLinks());

//		File style = new File(outputDir, "style/style.css");
//		assertTrue(style.exists());
//		assertContains(style, ".test1 .test2 {\n\twidth: 100%");
	}

	private void assertContains(File file, String string) {
		StringWriter sw = new StringWriter();
		try {
			IOUtils.copy(new FileInputStream(file), sw);
			assertTrue(sw.toString().contains(string));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
