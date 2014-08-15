package br.com.flsusp.html;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {

    private static final Pattern extensionExtractionPattern = Pattern.compile("(.*)(\\.\\w+)$");

    public static String replaceExtension(String fileName, String newExtension) {
        Matcher matcher = extensionExtractionPattern.matcher(fileName);
        if (matcher.matches()) {
            return matcher.group(1) + "." + newExtension;
        } else {
            return fileName;
        }
    }

    public static void copy(InputStream input, OutputStream output) {
        try {
            IOUtils.copy(input, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copy(InputStream input, Writer output) {
        try {
            IOUtils.copy(input, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getContentsAsString(InputStream input) {
        try (StringWriter sw = new StringWriter()) {
            copy(input, sw);
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
