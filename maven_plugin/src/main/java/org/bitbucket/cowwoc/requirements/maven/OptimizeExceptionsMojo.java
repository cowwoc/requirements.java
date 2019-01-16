/*
 * Copyright (c) 2018 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.bitbucket.cowwoc.requirements.generator.ExceptionOptimizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Optimizes the exceptions thrown by the library. Specifically, stack traces are stripped lazily.
 */
@Mojo(name = "optimize-exceptions", defaultPhase = LifecyclePhase.GENERATE_SOURCES, threadSafe = true)
@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public final class OptimizeExceptionsMojo extends AbstractMojo
{
	@Parameter(defaultValue = "${project.build.directory}", readonly = true)
	private File targetDirectory;

	/**
	 * The scope to export for. One of {@code [compile, test]}.
	 */
	@Parameter(property = "scope", defaultValue = "compile")
	private String scope;
	/**
	 * The list of exceptions to optimize. Each entry is expected to contain a fully-qualified class name.
	 */
	@Parameter(property = "exceptions")
	private List<String> exceptions;

	@Parameter(property = "project", required = true, readonly = true)
	private MavenProject project;

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
		ExceptionOptimizer optimizer = new ExceptionOptimizer();
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
			Logger log = LoggerFactory.getLogger(ExceptionOptimizer.class);
			for (String exceptionName : exceptions)
			{
				Class<?> exception;
				try
				{
					exception = Class.forName(exceptionName);
				}
				catch (ClassNotFoundException e)
				{
					log.error("Cannot find " + exceptionName);
					continue;
				}
				Path path = ExceptionOptimizer.getWrapperPath(targetPath, exceptionName);
				if (optimizer.writeWrapper(path, exception))
					log.info("{} was up-to-date", path);
				else
					log.info("Generated {}", path);
			}
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
