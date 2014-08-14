package br.com.flsusp.html;

import br.com.flsusp.html.parser.Html;
import com.asual.lesscss.LessEngine;

public class LessProcessor {

    private final WebAppFolder root;
    private final LessEngine engine = new LessEngine();

    public LessProcessor(WebAppFolder root) {
        this.root = root;
    }

    public void process() {
        root.process(new LoadHtmlFiles());
        compile();
        root.process(new RemoveLessFiles());
    }

    private void compile() {
        // TODO
        // Execute compilation in each substitution
        // Execute substitutions

//		String text = engine.compile("div { width: 1 + 1 }");
//		String url = engine.compile(getClass().getClassLoader().getResource("META-INF/test.css"));
//		engine.compile(new File("/Users/User/Projects/styles.less"),
//		               new File("/Users/User/Projects/styles.css"));
    }

    private void process(Html html) {
        html.head().forEach((node) -> "link".equalsIgnoreCase(node.nodeName())
                && "text/less".equalsIgnoreCase(node.attr("type")), (node) -> {
            String href = node.attr("href");
            WebAppFile lessFile = root.search(href);
//            this.substitutions.put(href, new LessSubstitution(html, href, lessFile));
        });
    }

    private class LoadHtmlFiles implements WebAppItemProcessor {

        @Override
        public void process(WebAppFile file) {
            if (file.isHtml()) {
                LessProcessor.this.process(file.getHtml());
            }
        }
    }

    private class RemoveLessFiles implements WebAppItemProcessor {

        @Override
        public void process(WebAppFile file) {
            if (file.isLess()) {
                file.remove();
            }
        }
    }
}
