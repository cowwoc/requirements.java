/*
 * Copyright (c) 2018 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.maven;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.bitbucket.cowwoc.requirements.generator.ApiGenerator;
import org.bitbucket.cowwoc.requirements.generator.internal.secrets.SharedSecrets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Generates the {@code Requirements}, {@code DefaultRequirements} endpoints. The contents of these classes
 * depend on which plugins are enabled.
 */
@Mojo(name = "generate-api", defaultPhase = LifecyclePhase.GENERATE_SOURCES, threadSafe = true)
public final class GenerateApiMojo extends AbstractMojo
{
	@Parameter(defaultValue = "${project.build.directory}", readonly = true)
	private File targetDirectory;

	@Parameter(defaultValue = "${project.groupId}", readonly = true)
	private String pluginGroupId;

	/**
	 * The scope to export for. One of {@code [compile, test]}.
	 */
	@Parameter(property = "scope", defaultValue = "compile")
	private String scope;
	/**
	 * Indicates if the generated class should assume that the guava plugin is enabled. If the guava plugin
	 * is present as a project dependency (of any scope) this value is true by default; otherwise, it is
	 * false by default.
	 */
	@Parameter(property = "guavaEnabled")
	private Boolean overrideGuavaEnabled;

	@Parameter(property = "project", required = true, readonly = true)
	private MavenProject project;

	@Parameter(defaultValue = "${plugin}", readonly = true)
	private PluginDescriptor plugin;

	@Override
	public void execute() throws MojoExecutionException
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
			if (dependency.getArtifactId().equals("guava"))
				guavaEnabled = true;
		}
		if (overrideGuavaEnabled != null)
			guavaEnabled = overrideGuavaEnabled;
		ApiGenerator generator = new ApiGenerator();
		generator.setGuavaEnabled(guavaEnabled);
		if (project.getGroupId().equals(pluginGroupId) && project.getArtifactId().equals("test"))
			SharedSecrets.INSTANCE.secretApiGenerator.exportScope(generator);
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
			Logger log = LoggerFactory.getLogger(ApiGenerator.class);
			Path defaultRequirements = generator.getDefaultRequirementsPath(targetPath);
			if (generator.writeDefaultRequirements(defaultRequirements))
				log.info("{} was up-to-date", defaultRequirements);
			else
				log.info("Generated {}", defaultRequirements);

			Path requirements = generator.getRequirementsPath(targetPath);
			if (generator.writeRequirements(requirements))
				log.info("{} was up-to-date", requirements);
			else
				log.info("Generated {}", requirements);
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