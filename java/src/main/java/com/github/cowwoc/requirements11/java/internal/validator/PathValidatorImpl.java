/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements11.java.internal.validator;

import com.github.cowwoc.pouch.core.WrappedCheckedException;
import com.github.cowwoc.requirements11.java.ValidationFailure;
import com.github.cowwoc.requirements11.java.internal.Configuration;
import com.github.cowwoc.requirements11.java.internal.message.PathMessages;
import com.github.cowwoc.requirements11.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements11.java.internal.util.Difference;
import com.github.cowwoc.requirements11.java.internal.util.ValidationTarget;
import com.github.cowwoc.requirements11.java.validator.PathValidator;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

public final class PathValidatorImpl extends AbstractObjectValidator<PathValidator, Path>
	implements PathValidator
{
	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         the value being validated
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public PathValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<Path> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public PathValidator exists()
	{
		if (value.validationFailed(Files::exists))
		{
			failOnNull();
			addIllegalArgumentException(
				PathMessages.exists(this).toString());
		}
		return this;
	}

	@Override
	public PathValidator isRegularFile(LinkOption... options) throws IOException
	{
		isType("file", BasicFileAttributes::isRegularFile, options);
		return this;
	}

	/**
	 * Validates the type of a path.
	 *
	 * @param type                       the expected type of the path
	 * @param attributesMatchExpectation returns {@code true} if the path matches the expected type
	 * @param options                    determines how symbolic links are handled
	 * @throws NullPointerException     if the value or {@code options} are null
	 * @throws IllegalArgumentException if the path does not exist or is not a file
	 * @throws IOException              if an I/O error occurs while reading the file attributes (e.g., the file
	 *                                  does not exist or the user lacks the required permissions)
	 */
	private void isType(String type, Function<BasicFileAttributes, Boolean> attributesMatchExpectation,
		LinkOption... options) throws IOException
	{
		scope.getInternalValidators().requireThat(options, "options").isNotNull();
		try
		{
			if (value.validationFailed(v -> isType(v, attributesMatchExpectation, options)))
			{
				failOnNull();
				addIllegalArgumentException(
					PathMessages.exists(this, type, options).toString());
			}
		}
		catch (WrappedCheckedException e)
		{
			Throwable cause = e.getCause();
			if (!(cause instanceof IOException ioe))
				throw e;
			addIOException(
				PathMessages.readAttributes(this, type, options, cause).toString(), ioe);
		}
	}

	/**
	 * Validates the type of a path.
	 *
	 * @param path                       the path
	 * @param attributesMatchExpectation returns {@code true} if the path matches the expected type
	 * @param options                    determines how symbolic links are handled
	 * @return {@code true} if the path matched the expected type
	 * @throws NullPointerException     if the value or {@code options} are null
	 * @throws IllegalArgumentException if the path does not exist or is not a file
	 */
	private boolean isType(Path path, Function<BasicFileAttributes, Boolean> attributesMatchExpectation,
		LinkOption... options)
	{
		try
		{
			BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class, options);
			return attributesMatchExpectation.apply(attrs);
		}
		catch (IOException e)
		{
			throw WrappedCheckedException.wrap(e);
		}
	}

	@Override
	public PathValidator isDirectory(LinkOption... options) throws IOException
	{
		isType("directory", BasicFileAttributes::isDirectory, options);
		return this;
	}

	@Override
	public PathValidator isExecutable()
	{
		if (value.validationFailed(Files::isExecutable))
		{
			failOnNull();
			addIllegalArgumentException(
				PathMessages.isExecutable(this).toString());
		}
		return this;
	}

	@Override
	public PathValidator isRelative()
	{
		if (value.validationFailed(v -> !v.isAbsolute()))
		{
			failOnNull();
			addIllegalArgumentException(
				PathMessages.isRelative(this).toString());
		}
		return this;
	}

	@Override
	public PathValidator isAbsolute()
	{
		if (value.validationFailed(Path::isAbsolute))
		{
			failOnNull();
			addIllegalArgumentException(
				PathMessages.isAbsolute(this).toString());
		}
		return this;
	}

	@Override
	public PathValidator isEmpty()
	{
		if (value.validationFailed(v ->
		{
			try (Stream<Path> files = Files.walk(v))
			{
				// Skip the parent directory
				return files.skip(1).findAny().isEmpty();
			}
			catch (IOException e)
			{
				throw WrappedCheckedException.wrap(e);
			}
		}))
		{
			failOnNull();
			addIllegalArgumentException(
				PathMessages.isEmptyFailed(this).toString());
		}
		return this;
	}

	@Override
	public PathValidator isNotEmpty()
	{
		if (value.validationFailed(v ->
		{
			try (Stream<Path> files = Files.walk(v))
			{
				// Skip the parent directory
				return files.skip(1).findAny().isPresent();
			}
			catch (IOException e)
			{
				throw WrappedCheckedException.wrap(e);
			}
		}))
		{
			failOnNull();
			addIllegalArgumentException(
				PathMessages.isNotEmptyFailed(this).toString());
		}
		return this;
	}

	@Override
	public PathValidator contains(Path expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsImpl(expected, null);
	}

	@Override
	public PathValidator contains(Path expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, "expected").isNotNull();
		return containsImpl(expected, name);
	}

	private PathValidator containsImpl(Path expected, String name)
	{
		if (value.validationFailed(v ->
		{
			Path parent = v.toAbsolutePath().normalize();
			return isImmediateChild(parent, expected) && Files.exists(expected);
		}))
		{
			failOnNull();
			addIllegalArgumentException(
				PathMessages.contains(this, name, expected).toString());
		}
		return this;
	}

	/**
	 * Indicates if one path is an immediate child of another.
	 * <p>
	 * The parent path must be absolute and normalized prior to invoking this method.
	 *
	 * @param parent a path
	 * @param child  another path
	 * @return {@code true} if {@code child} is an immediate child of {@code parent}
	 */
	private boolean isImmediateChild(Path parent, Path child)
	{
		// If we don't invoke toAbsolutePath() first then Path.of("") will not contain Path.of("subDirectory")
		assert parent.toAbsolutePath().normalize().equals(parent) : parent;
		child = child.toAbsolutePath().normalize();
		return child.getNameCount() == parent.getNameCount() + 1 && child.startsWith(parent);
	}

	@Override
	public PathValidator doesNotContain(Path unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainImpl(unwanted, null);
	}

	@Override
	public PathValidator doesNotContain(Path unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainImpl(unwanted, name);
	}

	private PathValidator doesNotContainImpl(Path unwanted, String name)
	{
		if (value.validationFailed(v ->
		{
			Path parent = v.toAbsolutePath().normalize();
			return !isImmediateChild(parent, unwanted) || Files.notExists(unwanted);
		}))
		{
			failOnNull();
			addIllegalArgumentException(
				PathMessages.doesNotContain(this, name, unwanted).toString());
		}
		return this;
	}

	@Override
	public PathValidator containsExactly(Collection<Path> expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsExactlyImpl(expected, null);
	}

	@Override
	public PathValidator containsExactly(Collection<Path> expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, "expected").isNotNull();
		return containsExactlyImpl(expected, name);
	}

	private PathValidator containsExactlyImpl(Collection<Path> expected, String name)
	{
		Difference<Path> difference = value.nullToInvalid().map(v ->
		{
			try
			{
				return Difference.actualVsOther(getChildren(v), expected);
			}
			catch (IOException e)
			{
				throw WrappedCheckedException.wrap(e);
			}
		}).or(null);
		if (difference == null || !difference.areTheSame())
		{
			failOnNull();
			addIllegalArgumentException(
				PathMessages.containsExactlyFailed(this, difference, name, expected).
					toString());
		}
		return this;
	}

	/**
	 * Returns the children of a Path.
	 *
	 * @param parent the parent path
	 * @return the children
	 */
	private List<Path> getChildren(Path parent) throws IOException
	{
		List<Path> children = new ArrayList<>();
		AtomicInteger depth = new AtomicInteger();
		Files.walkFileTree(parent, new FileVisitor<>()
		{
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			{
				if (depth.get() == 0)
					return FileVisitResult.CONTINUE;
				depth.incrementAndGet();
				return FileVisitResult.SKIP_SUBTREE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			{
				children.add(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException e) throws IOException
			{
				if (e != null)
					throw e;
				children.add(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException
			{
				if (e != null)
					throw e;
				return FileVisitResult.SKIP_SUBTREE;
			}
		});
		return children;
	}

	@Override
	public PathValidator doesNotContainExactly(Collection<Path> unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainExactlyImpl(unwanted, null);
	}

	@Override
	public PathValidator doesNotContainExactly(Collection<Path> unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, name).isNotNull();
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainExactlyImpl(unwanted, name);
	}

	private PathValidator doesNotContainExactlyImpl(Collection<Path> unwanted, String name)
	{
		Difference<Path> difference = value.nullToInvalid().map(v ->
		{
			try
			{
				return Difference.actualVsOther(getChildren(v), unwanted);
			}
			catch (IOException e)
			{
				throw WrappedCheckedException.wrap(e);
			}
		}).or(null);
		if (difference == null || !difference.areDifferent())
		{
			failOnNull();
			addIllegalArgumentException(
				PathMessages.doesNotContainExactlyFailed(this, name, unwanted).toString());
		}
		return this;
	}

	@Override
	public PathValidator containsAny(Collection<Path> expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsAnyImpl(expected, null);
	}

	@Override
	public PathValidator containsAny(Collection<Path> expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, "expected").isNotNull();
		return containsAnyImpl(expected, name);
	}

	private PathValidator containsAnyImpl(Collection<Path> expected, String name)
	{
		if (value.validationFailed(v ->
		{
			Path parent = v.toAbsolutePath().normalize();
			for (Path child : expected)
			{
				if (isImmediateChild(parent, child) && Files.exists(child))
					return true;
			}
			return false;
		}))
		{
			failOnNull();
			addIllegalArgumentException(
				PathMessages.containsAnyFailed(this, name, expected).toString());
		}
		return this;
	}

	@Override
	public PathValidator doesNotContainAny(Collection<Path> unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAnyImpl(unwanted, null);
	}

	@Override
	public PathValidator doesNotContainAny(Collection<Path> unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAnyImpl(unwanted, name);
	}

	private PathValidator doesNotContainAnyImpl(Collection<Path> unwanted, String name)
	{
		Set<Path> unwantedMatches = value.nullToInvalid().map(v ->
		{
			Path parent = v.toAbsolutePath().normalize();
			Set<Path> matches = new HashSet<>();
			for (Path child : unwanted)
			{
				if (isImmediateChild(parent, child) && Files.exists(child))
					matches.add(child);
			}
			return matches;
		}).or(null);
		if (unwantedMatches == null || !unwantedMatches.isEmpty())
		{
			failOnNull();
			addIllegalArgumentException(
				PathMessages.doesNotContainAnyFailed(this, unwantedMatches, name, unwanted).
					toString());
		}
		return this;
	}

	@Override
	public PathValidator containsAll(Collection<Path> expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		return containsAllImpl(expected, null);
	}

	@Override
	public PathValidator containsAll(Collection<Path> expected, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(expected, "expected").isNotNull();
		return containsAllImpl(expected, name);
	}

	private PathValidator containsAllImpl(Collection<Path> expected, String name)
	{
		Set<Path> missingMatches = value.nullToInvalid().map(v ->
		{
			Path parent = v.toAbsolutePath().normalize();
			Set<Path> missing = new HashSet<>();
			for (Path child : expected)
			{
				if (!isImmediateChild(parent, child) || Files.notExists(child))
					missing.add(child);
			}
			return missing;
		}).or(null);
		if (missingMatches == null || !missingMatches.isEmpty())
		{
			failOnNull();
			addIllegalArgumentException(
				PathMessages.containsAllFailed(this, missingMatches, name, expected).
					toString());
		}
		return this;
	}

	@Override
	public PathValidator doesNotContainAll(Collection<Path> unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAllImpl(unwanted, null);
	}

	@Override
	public PathValidator doesNotContainAll(Collection<Path> unwanted, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(unwanted, "unwanted").isNotNull();
		return doesNotContainAllImpl(unwanted, name);
	}

	private PathValidator doesNotContainAllImpl(Collection<Path> unwanted, String name)
	{
		if (value.validationFailed(v ->
		{
			Path parent = v.toAbsolutePath().normalize();
			for (Path child : unwanted)
			{
				if (!isImmediateChild(parent, child) || Files.notExists(child))
					return true;
			}
			return false;
		}))
		{
			failOnNull();
			addIllegalArgumentException(
				PathMessages.doesNotContainAllFailed(this, name, unwanted).toString());
		}
		return this;
	}
}