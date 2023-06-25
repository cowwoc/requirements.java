/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

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
				new TestValidatorsImpl(scope).requireThat(actual, "Actual").exists();
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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").exists();
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
				new TestValidatorsImpl(scope).requireThat(actual, "Actual").isDirectory();
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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isDirectory((LinkOption[]) null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isDirectory_actualIsMissing() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempDirectory(null);
			Files.delete(actual);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isDirectory();
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
				new TestValidatorsImpl(scope).requireThat(actual, "Actual").isDirectory();
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
				new TestValidatorsImpl(scope).requireThat(actual, "Actual").isRegularFile();
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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isRegularFile((LinkOption[]) null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRegularFile_actualIsMissing() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			Files.delete(actual);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isRegularFile();
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
				new TestValidatorsImpl(scope).requireThat(actual, "Actual").isRegularFile();
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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isRelative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRelative_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get(new File("/paths1/path2").toURI());
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isRelative();
		}
	}

	@Test
	public void isAbsolute()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get(new File("/paths1/path2").toURI());
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isAbsolute();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isAbsolute_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get("path1/path2");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isAbsolute();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresIsDirectoryNullOptions() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				isDirectory((LinkOption[]) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresIsRegularFileNullOptions() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				isRegularFile((LinkOption[]) null);
		}
	}

	@Test
	public void multipleFailuresNullExists()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				exists().isEqualTo(5).elseGetMessages();
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
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isAbsolute().isEqualTo(5).elseGetMessages();
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
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isDirectory().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsRegularFile() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isRegularFile().isEqualTo(5).elseGetMessages();
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
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isRelative().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").
				isEqualTo(expectedMessages);
		}
	}
}
