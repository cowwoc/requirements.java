/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class PathRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Path parameter = Paths.get("/");
		Requirements.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Path parameter = Paths.get("/");
		Requirements.requireThat(parameter, "");
	}

	@Test
	public void existsTrue() throws IOException
	{
		Path parameter = Files.createTempFile(null, null);
		try
		{
			Requirements.requireThat(parameter, "parameter").exists();
		}
		finally
		{
			Files.delete(parameter);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void existsFalse() throws IOException
	{
		Path parameter = Files.createTempFile(null, null);
		Files.delete(parameter);
		Requirements.requireThat(parameter, "parameter").exists();
	}

	@Test
	public void isDirectoryTrue() throws IOException
	{
		Path parameter = Files.createTempDirectory(null);
		try
		{
			Requirements.requireThat(parameter, "parameter").isDirectory();
		}
		finally
		{
			Files.delete(parameter);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isDirectoryFalseMissing() throws IOException
	{
		Path parameter = Files.createTempDirectory(null);
		Files.delete(parameter);
		Requirements.requireThat(parameter, "parameter").isDirectory();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isDirectoryFalseFile() throws IOException
	{
		Path parameter = Files.createTempFile(null, null);
		try
		{
			Requirements.requireThat(parameter, "parameter").isDirectory();
		}
		finally
		{
			Files.delete(parameter);
		}
	}

	@Test
	public void isRegularFileTrue() throws IOException
	{
		Path parameter = Files.createTempFile(null, null);
		try
		{
			Requirements.requireThat(parameter, "parameter").isRegularFile();
		}
		finally
		{
			Files.delete(parameter);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRegularFileFalseMissing() throws IOException
	{
		Path parameter = Files.createTempFile(null, null);
		Files.delete(parameter);
		Requirements.requireThat(parameter, "parameter").isRegularFile();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRegularFileFalseDirectory() throws IOException
	{
		Path parameter = Files.createTempDirectory(null);
		try
		{
			Requirements.requireThat(parameter, "parameter").isRegularFile();
		}
		finally
		{
			Files.delete(parameter);
		}
	}

	@Test
	public void isRelativeTrue() throws IOException
	{
		Path parameter = Paths.get("path1/path2");
		Requirements.requireThat(parameter, "parameter").isRelative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRelativeFalse() throws IOException
	{
		Path parameter = Paths.get(new File("/paths1/path2").toURI());
		Requirements.requireThat(parameter, "parameter").isRelative();
	}

	@Test
	public void isAbsoluteTrue() throws IOException
	{
		Path parameter = Paths.get(new File("/paths1/path2").toURI());
		Requirements.requireThat(parameter, "parameter").isAbsolute();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isAbsoluteFalse() throws IOException
	{
		Path parameter = Paths.get("path1/path2");
		Requirements.requireThat(parameter, "parameter").isAbsolute();
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Path parameter = null;
		new AssertionVerifier(false).requireThat(parameter, "parameter").isNotNull();
	}
}
