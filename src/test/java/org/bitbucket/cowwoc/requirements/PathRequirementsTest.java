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
		Path actual = Paths.get("/");
		Requirements.requireThat(actual, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Path actual = Paths.get("/");
		Requirements.requireThat(actual, "");
	}

	@Test
	public void existsTrue() throws IOException
	{
		Path actual = Files.createTempFile(null, null);
		try
		{
			Requirements.requireThat(actual, "actual").exists();
		}
		finally
		{
			Files.delete(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void exists_False() throws IOException
	{
		Path actual = Files.createTempFile(null, null);
		Files.delete(actual);
		Requirements.requireThat(actual, "actual").exists();
	}

	@Test
	public void isDirectory() throws IOException
	{
		Path actual = Files.createTempDirectory(null);
		try
		{
			Requirements.requireThat(actual, "actual").isDirectory();
		}
		finally
		{
			Files.delete(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isDirectory_actualIsMissing() throws IOException
	{
		Path actual = Files.createTempDirectory(null);
		Files.delete(actual);
		Requirements.requireThat(actual, "actual").isDirectory();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isDirectory_actualIsFile() throws IOException
	{
		Path actual = Files.createTempFile(null, null);
		try
		{
			Requirements.requireThat(actual, "actual").isDirectory();
		}
		finally
		{
			Files.delete(actual);
		}
	}

	@Test
	public void isRegularFile() throws IOException
	{
		Path actual = Files.createTempFile(null, null);
		try
		{
			Requirements.requireThat(actual, "actual").isRegularFile();
		}
		finally
		{
			Files.delete(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRegularFile_actualIsMissing() throws IOException
	{
		Path actual = Files.createTempFile(null, null);
		Files.delete(actual);
		Requirements.requireThat(actual, "actual").isRegularFile();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRegularFile_actualIsDirectory() throws IOException
	{
		Path actual = Files.createTempDirectory(null);
		try
		{
			Requirements.requireThat(actual, "actual").isRegularFile();
		}
		finally
		{
			Files.delete(actual);
		}
	}

	@Test
	public void isRelative() throws IOException
	{
		Path actual = Paths.get("path1/path2");
		Requirements.requireThat(actual, "actual").isRelative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRelative_False() throws IOException
	{
		Path actual = Paths.get(new File("/paths1/path2").toURI());
		Requirements.requireThat(actual, "actual").isRelative();
	}

	@Test
	public void isAbsolute() throws IOException
	{
		Path actual = Paths.get(new File("/paths1/path2").toURI());
		Requirements.requireThat(actual, "actual").isAbsolute();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isAbsolute_False() throws IOException
	{
		Path actual = Paths.get("path1/path2");
		Requirements.requireThat(actual, "actual").isAbsolute();
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Path actual = null;
		new AssertionVerifier(false).requireThat(actual, "actual").isNotNull();
	}
}
