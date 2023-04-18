/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.nio.file.Path;

/**
 * Base class for all Mojos.
 */
abstract class AbstractGeneratorMojo extends AbstractMojo
{
	/**
	 * @param scope           the scope to export for
	 * @param targetDirectory the value of {@code ${project.build.directory}}
	 * @return the path to generate files into
	 * @throws MojoExecutionException if {@code scope} is not one of {@code [compile, test]}
	 */
	protected Path getGeneratedSourcesPath(String scope, Path targetDirectory) throws MojoExecutionException
	{
		if (scope == null)
			throw new MojoExecutionException("scope may not be null");
		switch (scope)
		{
			case "compile":
				return targetDirectory.resolve("generated-sources/requirements");
			case "test":
				return targetDirectory.resolve("generated-test-sources/requirements");
			default:
			{
				throw new MojoExecutionException("scope must be one of [compile, test].\n" +
					"Actual: " + scope);
			}
		}
	}

	/**
	 * Adds generated files to the Maven project.
	 *
	 * @param project the project
	 * @param scope   the scope that the files were generated into
	 * @param path    the path that files were generated into
	 * @throws NullPointerException   if any of the arguments are null
	 * @throws MojoExecutionException if {@code scope} is not one of {@code [compile, test]}
	 */
	protected void addFilesToSources(MavenProject project, String scope,
	                                 Path path) throws MojoExecutionException
	{
		if (project == null)
			throw new NullPointerException("project may not be null");
		if (scope == null)
			throw new NullPointerException("scope may not be null");
		if (path == null)
			throw new NullPointerException("path may not be null");
		switch (scope)
		{
			case "compile":
			{
				project.addCompileSourceRoot(path.toString());
				break;
			}
			case "test":
			{
				project.addTestCompileSourceRoot(path.toString());
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
