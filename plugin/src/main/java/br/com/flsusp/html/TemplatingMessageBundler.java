package br.com.flsusp.html;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.commons.io.IOUtils;
import org.codehaus.plexus.interpolation.InterpolationException;
import org.codehaus.plexus.interpolation.PropertiesBasedValueSource;
import org.codehaus.plexus.interpolation.RegexBasedInterpolator;

class TemplatingMessageBundler implements MessageBundler {

    private final RegexBasedInterpolator interpolator;

    TemplatingMessageBundler(File baseDir, String bundle) {
        File file = new File(baseDir, "src/main/resources/" + bundle);
        try (FileReader reader = new FileReader(file)) {
            Properties properties = new Properties();
            properties.load(reader);

            interpolator = new RegexBasedInterpolator();
            interpolator.setCacheAnswers(false);
            interpolator.setReusePatterns(true);
            interpolator.addValueSource(new PropertiesBasedValueSource(properties));

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void process(InputStream input, OutputStream output) {
        try {
            final String content = readString(input);
            final String result = interpolator.interpolate(content);
        } catch (InterpolationException e) {
            throw new RuntimeException(e);
        }
    }
}
