package org.bitbucket.cowwoc.requirements.maven;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.bitbucket.cowwoc.requirements.ApiGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Generates the {@code Requirements}, {@code DefaultRequirements} endpoints classes that expose a different number of methods depending on
 * the plugins that are available at build-time.
 */
@Mojo(name = "generate-api", defaultPhase = LifecyclePhase.GENERATE_SOURCES, threadSafe = true)
public final class GenerateApiMojo extends AbstractMojo
{
	@Parameter(defaultValue = "${project.build.directory}", readonly = true)
	private File targetDirectory;

	/**
	 * The scope to export for. One of {@code [compile, test]}.
	 */
	@Parameter(property = "scope", defaultValue = "compile")
	private String scope;
	/**
	 * Indicates if the generated API should reference the guava plugin. If the guava plugin is present as a project dependency (of any
	 * scope) this value is true by default; otherwise, it is false by default.
	 */
	@Parameter(property = "guavaEnabled")
	private Boolean overrideGuavaEnabled;

	@Parameter(property = "project", required = true, readonly = true)
	private MavenProject project;

	@Parameter(defaultValue = "${plugin}", readonly = true)
	private PluginDescriptor plugin;

	@Override
	public void execute() throws MojoFailureException, MojoExecutionException
	{
		if (scope == null)
			throw new MojoExecutionException("scope may not be null");
		Path targetPath;
		switch (scope)
		{
			case "compile":
			{
				targetPath = targetDirectory.toPath().resolve("generated-sources/requirements");
				break;
			}
			case "test":
			{
				targetPath = targetDirectory.toPath().resolve("generated-test-sources/requirements");
				break;
			}
			default:
			{
				throw new MojoExecutionException("scope must be one of [compile, test].\n" +
					"Actual: " + scope);
			}
		}
		boolean guavaEnabled = false;
		for (Dependency dependency : project.getDependencies())
		{
			if (!dependency.getGroupId().equals(plugin.getGroupId()))
				continue;
			if (dependency.getArtifactId().equals("requirements-guava"))
				guavaEnabled = true;
		}
		if (overrideGuavaEnabled != null)
			guavaEnabled = overrideGuavaEnabled;
		ApiGenerator generator = new ApiGenerator();
		generator.setGuavaEnabled(guavaEnabled);
		try
		{
			Files.createDirectories(targetPath);
		}
		catch (IOException e)
		{
			getLog().error("Failed to create: " + targetPath.toAbsolutePath());
			throw new MojoExecutionException("", e);
		}
		try
		{
			generator.writeDefaultRequirements(targetPath);
			generator.writeRequirements(targetPath);
		}
		catch (IOException e)
		{
			throw new MojoExecutionException("", e);
		}
		switch (scope)
		{
			case "compile":
			{
				project.addCompileSourceRoot(targetPath.toString());
				break;
			}
			case "test":
			{
				project.addTestCompileSourceRoot(targetPath.toString());
				break;
			}
			default:
			{
				throw new MojoExecutionException("scope must be one of [compile, test].\n" +
					"Actual: " + scope);
			}
		}
	}
}
