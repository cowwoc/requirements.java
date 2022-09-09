/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.natives.internal.util.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class PathTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get("/");
			new Requirements(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get("/");
			new Requirements(scope).requireThat(actual, "");
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
				new Requirements(scope).requireThat(actual, "actual").exists();
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
			new Requirements(scope).requireThat(actual, "actual").exists();
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
				new Requirements(scope).requireThat(actual, "actual").isDirectory();
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
			new Requirements(scope).requireThat(actual, "actual").isDirectory((LinkOption[]) null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isDirectory_actualIsMissing() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempDirectory(null);
			Files.delete(actual);
			new Requirements(scope).requireThat(actual, "actual").isDirectory();
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
				new Requirements(scope).requireThat(actual, "actual").isDirectory();
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
				new Requirements(scope).requireThat(actual, "actual").isRegularFile();
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
			new Requirements(scope).requireThat(actual, "actual").isRegularFile((LinkOption[]) null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRegularFile_actualIsMissing() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			Files.delete(actual);
			new Requirements(scope).requireThat(actual, "actual").isRegularFile();
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
				new Requirements(scope).requireThat(actual, "actual").isRegularFile();
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
			new Requirements(scope).requireThat(actual, "actual").isRelative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRelative_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get(new File("/paths1/path2").toURI());
			new Requirements(scope).requireThat(actual, "actual").isRelative();
		}
	}

	@Test
	public void isAbsolute()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get(new File("/paths1/path2").toURI());
			new Requirements(scope).requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isAbsolute_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get("path1/path2");
			new Requirements(scope).requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Path actual = null;
			new Requirements(scope).withAssertionsDisabled().assertThat(r ->
				r.requireThat(actual, "actual").isNotNull());
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatIsDirectoryNullOptions() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			new Requirements(scope).validateThat(actual, "actual").isDirectory((LinkOption[]) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatIsRegularFileNullOptions() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Files.createTempFile(null, null);
			new Requirements(scope).validateThat(actual, "actual").isRegularFile((LinkOption[]) null);
		}
	}

	@Test
	public void validateThatNullExists()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				exists().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsAbsolute()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isAbsolute().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsDirectory()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isDirectory().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsRegularFile()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isRegularFile().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsRelative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isRelative().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
