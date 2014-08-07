package br.com.flsusp.html;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "html", requiresProject = true)
public class BuildMojo extends AbstractMojo {

	@Parameter(property = "project", required = true, readonly = true)
	private MavenProject project;

	@Parameter(property = "bundle", required = false, readonly = true)
	private String bundle = "messages.properties";

	@Parameter(property = "source", required = false, readonly = true)
	private String source = "static";

	@Parameter(property = "release", required = false, readonly = true)
	private String target = "release";

	public void execute() throws MojoExecutionException, MojoFailureException {
		final File src = getSrcDir();
		final File output = getOutputDir();

		WebApp webapp = new WebApp();
		webapp.useBundleMessages(project.getBasedir(), bundle);

		webapp.load(src);
		webapp.outputFilesTo(output);
	}

	private File getOutputDir() throws MojoExecutionException {
		File outputDir = new File(project.getBasedir(), "src/main/webapp/" + target);
		getLog().info("Output directory used: " + outputDir.getAbsolutePath());
		outputDir.mkdirs();
		if (!outputDir.exists()) {
			throw new MojoExecutionException("Output directory does not exists: " + outputDir.getAbsolutePath());
		}
		return outputDir;
	}

	private File getSrcDir() throws MojoExecutionException {
		File srcDir = new File(project.getBasedir(), "src/main/webapp/" + source);
		getLog().info("Source directory used: " + srcDir.getAbsolutePath());
		if (!srcDir.exists()) {
			throw new MojoExecutionException("Source directory does not exists: " + srcDir.getAbsolutePath());
		}
		return srcDir;
	}

	public void setProject(MavenProject project) {
		this.project = project;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}
