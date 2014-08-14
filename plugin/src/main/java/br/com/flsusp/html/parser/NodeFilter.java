package br.com.flsusp.html.parser;

import org.jsoup.nodes.Node;

public interface NodeFilter {

    boolean filter(Node node);
}
