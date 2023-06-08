/*
 * Copyright (c) 2018 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.maven;

import com.github.cowwoc.requirements.generator.ApiGenerator;
import com.github.cowwoc.requirements.generator.internal.secret.SharedSecrets;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Generates the {@code Requirements}, {@code DefaultRequirements} endpoints. The contents of these classes
 * depend on which plugins are enabled.
 */
@Mojo(name = "generate-api", defaultPhase = LifecyclePhase.GENERATE_SOURCES, threadSafe = true,
	requiresDependencyResolution = ResolutionScope.TEST)
public final class GenerateApiMojo extends AbstractGeneratorMojo
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
	 * is present as a project dependency (of any scope), this value is true by default; otherwise, it is
	 * false by default.
	 */
	@Parameter(property = "guavaEnabled")
	private Boolean overrideGuavaEnabled;

	@Parameter(property = "project", required = true, readonly = true)
	private MavenProject project;

	@Parameter(defaultValue = "${plugin}", readonly = true)
	private PluginDescriptor plugin;

	/**
	 * Creates a new instance of this plugin.
	 */
	public GenerateApiMojo()
	{
	}

	@Override
	public void execute() throws MojoExecutionException
	{
		// Create a ClassLoader containing the enclosing project's dependencies
		// https://stackoverflow.com/a/883219/14731
		URL[] runtimeUrls;
		try
		{
			List<String> runtimeClasspathElements = project.getRuntimeClasspathElements();
			List<String> testClasspathElements = project.getTestClasspathElements();
			int runtimeSize = runtimeClasspathElements.size();
			int testSize = testClasspathElements.size();
			runtimeUrls = new URL[runtimeSize + testSize];
			for (int i = 0; i < runtimeSize; ++i)
			{
				String element = runtimeClasspathElements.get(i);
				runtimeUrls[i] = new File(element).toURI().toURL();
			}
			for (int i = 0; i < testSize; ++i)
			{
				String element = testClasspathElements.get(i);
				runtimeUrls[runtimeSize + i] = new File(element).toURI().toURL();
			}
		}
		catch (DependencyResolutionRequiredException | MalformedURLException e)
		{
			throw new MojoExecutionException(e);
		}
		try (URLClassLoader projectClassLoader = new URLClassLoader(runtimeUrls,
			Thread.currentThread().getContextClassLoader()))
		{
			boolean guavaEnabled;
			if (overrideGuavaEnabled == null)
			{
				guavaEnabled = false;
				try
				{
					projectClassLoader.loadClass("com.github.cowwoc.requirements.guava.GuavaRequirements");
					guavaEnabled = true;
				}
				catch (ClassNotFoundException ignored)
				{
				}
			}
			else
				guavaEnabled = overrideGuavaEnabled;
			Path generatedSources = getGeneratedSourcesPath(scope, targetDirectory.toPath());
			ApiGenerator generator = new ApiGenerator(projectClassLoader);
			generator.setGuavaEnabled(guavaEnabled);
			if (project.getGroupId().equals(pluginGroupId) && (project.getArtifactId().equals("test")))
				SharedSecrets.INSTANCE.secretApiGenerator.exportScope(generator);
			try
			{
				Files.createDirectories(generatedSources);
			}
			catch (IOException e)
			{
				getLog().error("Failed to create: " + generatedSources.toAbsolutePath());
				throw new MojoExecutionException("", e);
			}
			try
			{
				generator.writeTo(generatedSources);
			}
			catch (IOException e)
			{
				throw new MojoExecutionException("", e);
			}
			addFilesToSources(project, scope, generatedSources);
		}
		catch (IOException e)
		{
			throw new MojoExecutionException(e);
		}
	}
}