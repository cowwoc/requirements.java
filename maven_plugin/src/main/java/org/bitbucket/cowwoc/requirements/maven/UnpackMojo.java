/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.maven;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.bitbucket.cowwoc.requirements.natives.internal.util.OperatingSystem;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.twdata.maven.mojoexecutor.MojoExecutor;
import org.twdata.maven.mojoexecutor.MojoExecutor.Element;
import org.twdata.maven.mojoexecutor.MojoExecutor.ExecutionEnvironment;

import java.io.File;

/**
 * Unpacks native binaries used by this library.
 */
@Mojo(name = "unpack", defaultPhase = LifecyclePhase.GENERATE_RESOURCES, threadSafe = true)
public final class UnpackMojo extends AbstractMojo
{
	/**
	 * The directory to unpack the binaries into.
	 */
	@Parameter
	private File target;

	@Parameter(property = "project", required = true, readonly = true)
	private MavenProject project;

	@Parameter(property = "session", required = true, readonly = true)
	private MavenSession session;

	@Component
	private BuildPluginManager pluginManager;

	@Override
	public void execute() throws MojoExecutionException
	{
		OperatingSystem os = OperatingSystem.detected();
		String classifier = os.type.name().toLowerCase() + "-" + os.architecture.name().toLowerCase();
		File outputDirectory;
		if (target != null)
			outputDirectory = target;
		else
			outputDirectory = new File(project.getBuild().getDirectory());

		PluginDescriptor pluginDescriptor = (PluginDescriptor) getPluginContext().
			get("pluginDescriptor");
		String groupId = pluginDescriptor.getGroupId();
		String version = pluginDescriptor.getVersion();
		String binariesArtifact = "natives";
		Element groupIdElement = new Element("groupId", groupId);
		Element artifactIdElement = new Element("artifactId", binariesArtifact);
		Element classifierElement = new Element("classifier", classifier);
		Element versionElement = new Element("version", version);
		Element overwriteElement = new Element("overWrite", "false");
		Element includesElement = new Element("includes", "*.dll,*.pdb,*.so,*.dylib");
		Element outputDirectoryElement = new Element("outputDirectory",
			outputDirectory.getAbsolutePath());
		Element artifactItemElement = new Element("artifactItem", groupIdElement, artifactIdElement,
			classifierElement, versionElement, overwriteElement, includesElement, outputDirectoryElement);
		Element artifactItemsItem = new Element("artifactItems", artifactItemElement);
		Xpp3Dom configuration = MojoExecutor.configuration(artifactItemsItem);
		ExecutionEnvironment environment = MojoExecutor.executionEnvironment(project, session,
			pluginManager);
		Plugin dependencyPlugin = MojoExecutor.plugin("org.apache.maven.plugins",
			"maven-dependency-plugin", "3.0.0");
		MojoExecutor.executeMojo(dependencyPlugin, "unpack", configuration, environment);
	}
}
