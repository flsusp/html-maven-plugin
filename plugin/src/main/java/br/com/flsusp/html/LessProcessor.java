package br.com.flsusp.html;

import br.com.flsusp.html.parser.Html;
import com.asual.lesscss.LessEngine;
import com.asual.lesscss.LessException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class LessProcessor {

    private final WebAppFolder root;
    private final LessEngine engine = new LessEngine();

    public LessProcessor(WebAppFolder root) {
        this.root = root;
    }

    public void process() {
        root.process(new LoadHtmlFiles());
        root.process(new RemoveLessFiles());
    }

    private void process(Html html) {
        html.head().forEach((node) -> "link".equalsIgnoreCase(node.nodeName())
                && "text/less".equalsIgnoreCase(node.attr("type")), (node) -> {
            String href = node.attr("href");
            WebAppFile lessFile = root.search(href);
            lessFile.process(new LessSubstitution(engine, html, href, lessFile));
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

class LessSubstitution implements WebAppItemProcessor {

    private final LessEngine engine;
    private final Html html;
    private final String href;
    private final WebAppFile lessFile;

    public LessSubstitution(LessEngine engine, Html html, String href, WebAppFile lessFile) {
        this.engine = engine;
        this.html = html;
        this.href = href;
        this.lessFile = lessFile;
    }

    @Override
    public void process(WebAppFile file) {
        file.replace(FileUtils.replaceExtension(file.getName(), "css"), (input, output) -> {
            try {
                String result = engine.compile(FileUtils.getContentsAsString(input));
                try (OutputStreamWriter writer = new OutputStreamWriter(output);
                     PrintWriter pwriter = new PrintWriter(writer)) {
                    pwriter.write(result);
                }
            } catch (LessException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
