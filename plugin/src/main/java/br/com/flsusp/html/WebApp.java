package br.com.flsusp.html;

import java.io.File;
import java.util.Arrays;
import java.util.Locale;

class WebApp {

	private String bundle;
	private File bundleBaseDir;

    void load(File baseDir) {
        final File[] files = baseDir.listFiles();
        // TODO
    }

    void outputFilesTo(File outputDir) {
    	MessageBundler bundler = new DoNothingMessageBundler();
    	// TODO
    }

    void outputFilesTo(File outputDir, Locale locale) {
    	MessageBundler bundler = new TemplatingMessageBundler(bundleBaseDir, bundle);
    	// TODO
    }

    void useBundleMessages(File bundleBaseDir, String bundle) {
        this.bundleBaseDir = bundleBaseDir;
		this.bundle = bundle;
    }
}
