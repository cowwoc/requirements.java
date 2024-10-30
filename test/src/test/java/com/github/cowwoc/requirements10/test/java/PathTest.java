/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java;

import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class PathTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get("/");
			new TestValidatorsImpl(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get("/");
			new TestValidatorsImpl(scope).requireThat(actual, "");
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
				new TestValidatorsImpl(scope).requireThat(actual, "actual").exists();
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
			new TestValidatorsImpl(scope).requireThat(actual, "actual").exists();
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
				new TestValidatorsImpl(scope).requireThat(actual, "actual").isDirectory();
			}
			finally
			{
				Files.delete(actual);
			}
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isDirectory_nullOptions() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isDirectory((LinkOption[]) null);
		}
	}

	@Test(expectedExceptions = IOException.class)
	public void isDirectory_actualIsMissing() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempDirectory(null);
			Files.delete(actual);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isDirectory();
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
				new TestValidatorsImpl(scope).requireThat(actual, "actual").isDirectory();
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
				new TestValidatorsImpl(scope).requireThat(actual, "actual").isRegularFile();
			}
			finally
			{
				Files.delete(actual);
			}
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isRegularFile_nullOptions() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isRegularFile((LinkOption[]) null);
		}
	}

	@Test(expectedExceptions = IOException.class)
	public void isRegularFile_actualIsMissing() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			Files.delete(actual);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isRegularFile();
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
				new TestValidatorsImpl(scope).requireThat(actual, "actual").isRegularFile();
			}
			finally
			{
				Files.delete(actual);
			}
		}
	}

	@Test
	public void isRelative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get("path1/path2");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isRelative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRelative_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get(new File("/paths1/path2").toURI());
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isRelative();
		}
	}

	@Test
	public void isAbsolute()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get(new File("/paths1/path2").toURI());
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isAbsolute_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get("path1/path2");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresIsDirectoryNullOptions() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isDirectory((LinkOption[]) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresIsRegularFileNullOptions() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isRegularFile((LinkOption[]) null);
		}
	}

	@Test
	public void multipleFailuresNullExists()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must exist", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				exists().isEqualTo(5).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").
				isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsAbsolute()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must reference an absolute path", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isAbsolute().isEqualTo(5).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").
				isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsDirectory() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must reference an existing directory", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isDirectory().isEqualTo(5).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsRegularFile() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must reference an existing file", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isRegularFile().isEqualTo(5).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").
				isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsRelative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must reference a relative path", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isRelative().isEqualTo(5).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").
				isEqualTo(expectedMessages);
		}
	}
}
