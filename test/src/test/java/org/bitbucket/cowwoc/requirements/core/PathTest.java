/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class PathTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get("/");
			new Verifiers(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get("/");
			new Verifiers(scope).requireThat(actual, "");
		}
	}

	@Test
	public void existsTrue() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			try
			{
				new Verifiers(scope).requireThat(actual, "actual").exists();
			}
			finally
			{
				Files.delete(actual);
			}
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void exists_False() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			Files.delete(actual);
			new Verifiers(scope).requireThat(actual, "actual").exists();
		}
	}

	@Test
	public void isDirectory() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempDirectory(null);
			try
			{
				new Verifiers(scope).requireThat(actual, "actual").isDirectory();
			}
			finally
			{
				Files.delete(actual);
			}
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isDirectory_actualIsMissing() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempDirectory(null);
			Files.delete(actual);
			new Verifiers(scope).requireThat(actual, "actual").isDirectory();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isDirectory_actualIsFile() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			try
			{
				new Verifiers(scope).requireThat(actual, "actual").isDirectory();
			}
			finally
			{
				Files.delete(actual);
			}
		}
	}

	@Test
	public void isRegularFile() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			try
			{
				new Verifiers(scope).requireThat(actual, "actual").isRegularFile();
			}
			finally
			{
				Files.delete(actual);
			}
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRegularFile_actualIsMissing() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			Files.delete(actual);
			new Verifiers(scope).requireThat(actual, "actual").isRegularFile();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRegularFile_actualIsDirectory() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempDirectory(null);
			try
			{
				new Verifiers(scope).requireThat(actual, "actual").isRegularFile();
			}
			finally
			{
				Files.delete(actual);
			}
		}
	}

	@Test
	public void isRelative() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get("path1/path2");
			new Verifiers(scope).requireThat(actual, "actual").isRelative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRelative_False() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get(new File("/paths1/path2").toURI());
			new Verifiers(scope).requireThat(actual, "actual").isRelative();
		}
	}

	@Test
	public void isAbsolute() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get(new File("/paths1/path2").toURI());
			new Verifiers(scope).requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isAbsolute_False() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get("path1/path2");
			new Verifiers(scope).requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Path actual = null;
			new Verifiers(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}
}
