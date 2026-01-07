/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.test.java;

import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.test.TestValidators;
import io.github.cowwoc.requirements13.test.TestValidatorsImpl;
import io.github.cowwoc.requirements13.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.util.List;
import java.util.Set;

import static io.github.cowwoc.requirements13.java.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class PathTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Paths.get("/");
			validators.requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Paths.get("/");
			validators.requireThat(actual, "");
		}
	}

	@Test
	public void existsTrue() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Files.createTempFile(null, null);
			try
			{
				validators.requireThat(actual, "actual").exists();
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
			TestValidators validators = TestValidators.of(scope);

			Path actual = Files.createTempFile(null, null);
			Files.delete(actual);
			validators.requireThat(actual, "actual").exists();
		}
	}

	@Test
	public void isDirectory() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Files.createTempDirectory(null);
			try
			{
				validators.requireThat(actual, "actual").isDirectory();
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
			TestValidators validators = TestValidators.of(scope);

			Path actual = Files.createTempFile(null, null);
			validators.requireThat(actual, "actual").isDirectory((LinkOption[]) null);
		}
	}

	@Test(expectedExceptions = IOException.class)
	public void isDirectory_actualIsMissing() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Files.createTempDirectory(null);
			Files.delete(actual);
			validators.requireThat(actual, "actual").isDirectory();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isDirectory_actualIsFile() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Files.createTempFile(null, null);
			try
			{
				validators.requireThat(actual, "actual").isDirectory();
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
			TestValidators validators = TestValidators.of(scope);

			Path actual = Files.createTempFile(null, null);
			try
			{
				validators.requireThat(actual, "actual").isRegularFile();
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
			TestValidators validators = TestValidators.of(scope);

			Path actual = Files.createTempFile(null, null);
			validators.requireThat(actual, "actual").isRegularFile((LinkOption[]) null);
		}
	}

	@Test(expectedExceptions = IOException.class)
	public void isRegularFile_actualIsMissing() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Files.createTempFile(null, null);
			Files.delete(actual);
			validators.requireThat(actual, "actual").isRegularFile();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRegularFile_actualIsDirectory() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Files.createTempDirectory(null);
			try
			{
				validators.requireThat(actual, "actual").isRegularFile();
			}
			finally
			{
				Files.delete(actual);
			}
		}
	}

	@Test
	public void isExecutable() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Files.createTempFile(null, null);
			PosixFileAttributeView view = Files.getFileAttributeView(actual, PosixFileAttributeView.class);
			if (view != null)
				view.setPermissions(Set.of(PosixFilePermission.OWNER_EXECUTE));
			try
			{
				validators.requireThat(actual, "actual").isExecutable();
			}
			finally
			{
				Files.delete(actual);
			}
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isExecutable_actualIsMissing() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Files.createTempFile(null, null);
			Files.delete(actual);
			validators.requireThat(actual, "actual").isExecutable();
		}
	}

	@Test
	public void isRelative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Paths.get("path1/path2");
			validators.requireThat(actual, "actual").isRelative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isRelative_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Paths.get(new File("/paths1/path2").toURI());
			validators.requireThat(actual, "actual").isRelative();
		}
	}

	@Test
	public void isAbsolute()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Paths.get(new File("/paths1/path2").toURI());
			validators.requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isAbsolute_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Paths.get("path1/path2");
			validators.requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test
	public void isEmpty() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			try
			{
				validators.requireThat(parent, "parent").isEmpty();
			}
			finally
			{
				Files.delete(parent);
			}
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			Path child = parent.resolve("child");
			Files.createDirectory(child);
			try
			{
				validators.requireThat(parent, "parent").isEmpty();
			}
			finally
			{
				Files.delete(child);
				Files.delete(parent);
			}
		}
	}

	@Test
	public void isNotEmpty() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			Path child = parent.resolve("child");
			Files.createDirectory(child);
			try
			{
				validators.requireThat(parent, "parent").isNotEmpty();
			}
			finally
			{
				Files.delete(child);
				Files.delete(parent);
			}
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			try
			{
				validators.requireThat(parent, "parent").isNotEmpty();
			}
			finally
			{
				Files.delete(parent);
			}
		}
	}

	@Test
	public void contains() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			Path child = parent.resolve("child");
			Files.createDirectory(child);
			try
			{
				validators.requireThat(parent, "parent").contains(child);
			}
			finally
			{
				Files.delete(child);
				Files.delete(parent);
			}
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			Path child1 = Files.createDirectory(parent.resolve("child1"));
			Path child2 = Files.createDirectory(parent.resolve("child2"));
			try
			{
				validators.requireThat(child1, "child1").contains(child2);
			}
			finally
			{
				Files.delete(child2);
				Files.delete(child1);
				Files.delete(parent);
			}
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_descendants() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);
			Path ancestor = Files.createTempDirectory("");
			Path child = Files.createDirectory(ancestor.resolve("child"));
			Path descendant = Files.createDirectory(child.resolve("descendant"));
			try
			{
				validators.requireThat(ancestor, "ancestor").contains(descendant);
			}
			finally
			{
				Files.delete(descendant);
				Files.delete(child);
				Files.delete(ancestor);
			}
		}
	}

	@Test
	public void currentDirectoryContainsSubDirectory() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Path.of("");
			Path child = actual.resolve("child");
			Files.createDirectory(child);
			try
			{
				validators.requireThat(actual, "actual").contains(child);
			}
			finally
			{
				Files.delete(child);
			}
		}
	}

	@Test
	public void doesNotContain() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			try
			{
				Path child = parent.resolve("child");
				validators.requireThat(parent, "parent").doesNotContain(child);
			}
			finally
			{
				Files.delete(parent);
			}
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_False() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			Path child = parent.resolve("child");
			Files.createDirectory(child);
			try
			{
				validators.requireThat(parent, "parent").doesNotContain(child);
			}
			finally
			{
				Files.delete(child);
				Files.delete(parent);
			}
		}
	}

	@Test
	public void containsExactly() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			Path child = parent.resolve("child");
			Files.createFile(child);
			try
			{
				validators.requireThat(parent, "parent").containsExactly(List.of(child));
			}
			finally
			{
				Files.delete(child);
				Files.delete(parent);
			}
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsExactly_False() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			Path child1 = parent.resolve("child1");
			Path child2 = parent.resolve("child2");
			Files.createFile(child1);
			Files.createFile(child2);
			try
			{
				validators.requireThat(parent, "parent").containsExactly(List.of(child1));
			}
			finally
			{
				Files.delete(child1);
				Files.delete(child2);
				Files.delete(parent);
			}
		}
	}

	@Test
	public void doesNotContainExactly() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			Path child1 = parent.resolve("child1");
			Path child2 = parent.resolve("child2");
			Files.createFile(child1);
			Files.createFile(child2);
			try
			{
				validators.requireThat(parent, "parent").doesNotContainExactly(List.of(child1));
			}
			finally
			{
				Files.delete(child1);
				Files.delete(child2);
				Files.delete(parent);
			}
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainExactly_False() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			Path child = parent.resolve("child");
			Files.createFile(child);
			try
			{
				validators.requireThat(parent, "parent").doesNotContainExactly(List.of(child));
			}
			finally
			{
				Files.delete(child);
				Files.delete(parent);
			}
		}
	}

	@Test
	public void containsAny() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			Path child1 = parent.resolve("child1");
			Path child2 = parent.resolve("child2");
			Files.createDirectory(child1);
			try
			{
				Set<Path> children = Set.of(child1, child2);
				validators.requireThat(parent, "parent").containsAny(children);
			}
			finally
			{
				Files.delete(child1);
				Files.delete(parent);
			}
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAny_False() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			try
			{
				Path child1 = parent.resolve("child1");
				Path child2 = parent.resolve("child2");

				Set<Path> children = Set.of(child1, child2);
				validators.requireThat(parent, "parent").containsAny(children);
			}
			finally
			{
				Files.delete(parent);
			}
		}
	}

	@Test
	public void doesNotContainAny() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			try
			{
				Path child1 = parent.resolve("child1");
				Path child2 = parent.resolve("child2");

				Set<Path> children = Set.of(child1, child2);
				validators.requireThat(parent, "parent").doesNotContainAny(children);
			}
			finally
			{
				Files.delete(parent);
			}
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAny_False() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			Path child1 = parent.resolve("child1");
			Path child2 = parent.resolve("child2");
			Files.createDirectory(child1);
			try
			{
				Set<Path> children = Set.of(child1, child2);
				validators.requireThat(parent, "parent").doesNotContainAny(children);
			}
			finally
			{
				Files.delete(child1);
				Files.delete(parent);
			}
		}
	}

	@Test
	public void containsAll() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			Path child1 = parent.resolve("child1");
			Path child2 = parent.resolve("child2");
			Files.createDirectory(child1);
			Files.createDirectory(child2);
			try
			{
				Set<Path> children = Set.of(child1, child2);
				validators.requireThat(parent, "parent").containsAll(children);
			}
			finally
			{
				Files.delete(child1);
				Files.delete(child2);
				Files.delete(parent);
			}
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsAll_False() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			Path child1 = parent.resolve("child1");
			Path child2 = parent.resolve("child2");
			Files.createDirectory(child1);
			try
			{
				Set<Path> children = Set.of(child1, child2);
				validators.requireThat(parent, "parent").containsAll(children);
			}
			finally
			{
				Files.delete(child1);
				Files.delete(parent);
			}
		}
	}

	@Test
	public void doesNotContainAll() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			Path child1 = parent.resolve("child1");
			Path child2 = parent.resolve("child2");
			Files.createDirectory(child1);
			try
			{
				Set<Path> children = Set.of(child1, child2);
				validators.requireThat(parent, "parent").doesNotContainAll(children);
			}
			finally
			{
				Files.delete(child1);
				Files.delete(parent);
			}
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainAll_False() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path parent = Files.createTempDirectory("");
			Path child1 = parent.resolve("child1");
			Path child2 = parent.resolve("child2");
			Files.createDirectory(child1);
			Files.createDirectory(child2);
			try
			{
				Set<Path> children = Set.of(child1, child2);
				validators.requireThat(parent, "parent").doesNotContainAll(children);
			}
			finally
			{
				Files.delete(child1);
				Files.delete(child2);
				Files.delete(parent);
			}
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresIsDirectoryNullOptions() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Files.createTempFile(null, null);
			validators.requireThat(actual, "actual").
				isDirectory((LinkOption[]) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresIsRegularFileNullOptions() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = Files.createTempFile(null, null);
			validators.requireThat(actual, "actual").
				isRegularFile((LinkOption[]) null);
		}
	}

	@Test
	public void multipleFailuresNullExists()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must exist", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				exists().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").
				isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsAbsolute()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must reference an absolute path", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isAbsolute().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").
				isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsDirectory() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must reference an existing directory", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isDirectory().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsRegularFile() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must reference an existing file", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isRegularFile().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").
				isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsRelative()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Path actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must reference a relative path", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isRelative().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").
				isEqualTo(expectedMessages);
		}
	}
}
