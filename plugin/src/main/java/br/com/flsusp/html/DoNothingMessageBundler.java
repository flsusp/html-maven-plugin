package br.com.flsusp.html;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

class DoNothingMessageBundler implements MessageBundler {

    @Override
    public void process(InputStream input, OutputStream output) {
        try {
            IOUtils.copy(input, output);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
