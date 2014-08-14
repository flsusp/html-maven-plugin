package br.com.flsusp.html;

import br.com.flsusp.html.parser.Html;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

interface WebAppItemProcessor {

    default void process(WebAppFolder folder) {
    }

    default void process(WebAppFile file) {
    }
}

interface WebAppItem {

    void outputTo(File file, MessageBundler bundle);

    void process(WebAppItemProcessor processor);

    String getName();

    WebAppFile search(String[] path);
}

class WebApp {

    private String bundle;
    private File bundleBaseDir;
    private WebAppFolder root;

    void load(File baseDir) {
        root = new WebAppFolder(baseDir);
    }

    void process() {
        processLess();
    }

    private void processLess() {
        new LessProcessor(root).process();
    }

    void outputFilesTo(File outputDir) {
        MessageBundler bundler = new DoNothingMessageBundler();
        root.outputTo(outputDir, bundler);
    }

    void outputFilesTo(File outputDir, Locale locale) {
        MessageBundler bundler = new TemplatingMessageBundler(bundleBaseDir, bundle);
        root.outputTo(outputDir, bundler);
    }

    void useBundleMessages(File bundleBaseDir, String bundle) {
        this.bundleBaseDir = bundleBaseDir;
        this.bundle = bundle;
    }
}

class WebAppFolder implements WebAppItem {

    private final File dir;
    private final List<WebAppItem> children = Collections.synchronizedList(new ArrayList<>());

    public WebAppFolder(File dir) {
        this.dir = dir;
        Arrays.asList(dir.listFiles()).parallelStream().forEach((file) -> {
            if (file.isDirectory()) {
                children.add(new WebAppFolder(file));
            } else {
                children.add(new WebAppFile(file));
            }
        });
    }

    @Override
    public void outputTo(File dir, MessageBundler bundler) {
        final File newDir = new File(dir, this.dir.getName());
        newDir.mkdir();
        children.parallelStream().forEach((item) -> item.outputTo(newDir, bundler));
    }

    @Override
    public void process(WebAppItemProcessor processor) {
        processor.process(this);
        children.parallelStream().forEach((item) -> item.process(processor));
    }

    public WebAppFile search(String href) {
        String[] path = href.split("[" + File.separator + "]");
        return search(path);
    }

    public WebAppFile search(String[] path) {
        if (path.length == 0) return null;

        Optional<WebAppItem> item = children.parallelStream().filter((child) -> path[0].equalsIgnoreCase(child.getName())).findFirst();
        if (item.isPresent()) {
            return item.get().search(Arrays.copyOfRange(path, 1, path.length));
        } else {
            return null;
        }
    }

    public String getName() {
        return dir.getName();
    }
}

class WebAppFile implements WebAppItem {

    private final File file;
    private boolean removed;

    public WebAppFile(File file) {
        this.file = file;
    }

    @Override
    public void outputTo(File file, MessageBundler bundle) {
        if (removed)
            return;

        final File destination = new File(file, this.file.getName());
        try (FileInputStream input = new FileInputStream(this.file);
             FileOutputStream output = new FileOutputStream(destination)) {
            IOUtils.copy(input, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void process(WebAppItemProcessor processor) {
        processor.process(this);
    }

    public boolean isHtml() {
        final String name = file.getName().toLowerCase();
        return name.endsWith(".html") || name.endsWith(".htm") || name.endsWith(".xhtml");
    }

    public Html getHtml() {
        return Html.parse(file);
    }

    public boolean isLess() {
        final String name = file.getName().toLowerCase();
        return name.endsWith(".less");
    }

    public void remove() {
        this.removed = true;
    }

    public String getName() {
        return file.getName().toLowerCase();
    }

    @Override
    public WebAppFile search(String[] path) {
        return this;
    }
}
