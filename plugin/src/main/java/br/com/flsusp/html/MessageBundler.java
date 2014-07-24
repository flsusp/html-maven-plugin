package br.com.flsusp.html;

import java.io.InputStream;
import java.io.OutputStream;

interface MessageBundler {
    
    void process(InputStream input, OutputStream output);
}
