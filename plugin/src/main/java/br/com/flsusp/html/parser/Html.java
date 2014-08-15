package br.com.flsusp.html.parser;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Html {

	private final Document document;

	public Html(Document document) {
		this.document = document;
	}

	public static Html parse(File file) {
		try {
			Document document = Jsoup.parse(file, "UTF-8");
			return new Html(document);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Head head() {
		return new Head(document.head());
	}

	public static class Head {

		private final Element element;

		public Head(Element element) {
			this.element = element;
		}

		public int numberOfCssLinks() {
			return (int) element
					.childNodes()
					.stream()
					.filter((node) -> "link".equalsIgnoreCase(node.nodeName())
							&& "text/css".equalsIgnoreCase(node.attr("type"))).count();
		}

		public int numberOfJsScripts() {
			return (int) element.childNodes().stream().filter((node) -> "script".equalsIgnoreCase(node.nodeName()))
					.count();
		}

		public int numberOfLessLinks() {
			return (int) element
					.childNodes()
					.stream()
					.filter((node) -> "link".equalsIgnoreCase(node.nodeName())
							&& "text/less".equalsIgnoreCase(node.attr("type"))).count();
		}

        public void forEach(NodeFilter filter, ForEachNode action) {
            element.childNodes().parallelStream().filter((node) -> filter.filter(node)).forEach((node) -> action.each(node));
        }
    }
}
