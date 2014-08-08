package br.com.flsusp.html;

import br.com.flsusp.html.parser.Html;

public class LessProcessor {

	private final WebAppFolder root;

	public LessProcessor(WebAppFolder root) {
		this.root = root;
	}

	public void process() {
		root.process(new LoadHtmlFiles());
		compile();
		root.process(new RemoveLessFiles());
	}

	private void compile() {
		// TODO Auto-generated method stub
	}

	private void process(Html html) {
		// TODO
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
