package br.com.flsusp.html;

import java.io.File;

class WebApp {

    private MessageBundler bundler = new DoNothingMessageBundler();

    void parseHTML(File file) {
        
    }

    void outputFilesTo(File output) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void useBundleMessages(File baseDir, String bundle) {
        this.bundler = new TemplatingMessageBundler(baseDir, bundle);
    }
}
