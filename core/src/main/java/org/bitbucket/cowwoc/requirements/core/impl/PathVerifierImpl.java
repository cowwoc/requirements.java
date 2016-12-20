/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.PathVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.util.Configuration;

/**
 * Default implementation of PathVerifier.
 *
 * @author Gili Tzabari
 */
public final class PathVerifierImpl implements PathVerifier
{
	private final SingletonScope scope;
	private final Path actual;
	private final String name;
	private final Configuration config;
	private final ObjectVerifier<Path> asObject;

	/**
	 * Creates new PathVerifierImpl.
	 *
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public PathVerifierImpl(SingletonScope scope, Path actual, String name, Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public PathVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new PathVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public PathVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new PathVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public PathVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new PathVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public PathVerifier exists()
	{
		if (Files.exists(actual))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s refers to a non-existent path: %s", name, actual.toAbsolutePath())).
			build();
	}

	@Override
	public PathVerifier isRegularFile(LinkOption... options) throws IOException
	{
		BasicFileAttributes attrs;
		try
		{
			attrs = Files.readAttributes(actual, BasicFileAttributes.class, options);
		}
		catch (NoSuchFileException e)
		{
			throw config.exceptionBuilder(IllegalArgumentException.class,
				String.format("%s refers to a non-existent path: %s", name, actual.toAbsolutePath()), e).
				build();
		}
		if (!attrs.isRegularFile())
		{
			throw config.exceptionBuilder(IllegalArgumentException.class,
				String.format("%s must refer to a file.", name)).
				addContext("Actual", actual.toAbsolutePath()).
				build();
		}
		return this;
	}

	@Override
	public PathVerifier isDirectory(LinkOption... options) throws IOException
	{
		BasicFileAttributes attrs;
		try
		{
			attrs = Files.readAttributes(actual, BasicFileAttributes.class, options);
		}
		catch (NoSuchFileException e)
		{
			throw config.exceptionBuilder(IllegalArgumentException.class,
				String.format("%s refers to a non-existent path: %s", name, actual.toAbsolutePath()), e).
				build();
		}
		if (!attrs.isDirectory())
		{
			throw config.exceptionBuilder(IllegalArgumentException.class,
				String.format("%s must refer to a directory.", name)).
				addContext("Actual", actual.toAbsolutePath()).
				build();
		}
		return this;
	}

	@Override
	public PathVerifier isRelative()
	{
		if (!actual.isAbsolute())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must refer to a relative path.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public PathVerifier isAbsolute()
	{
		if (actual.isAbsolute())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must refer to an absolute path.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public PathVerifier isNotNull()
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public PathVerifier isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public PathVerifier isIn(Collection<Path> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public PathVerifier isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public PathVerifier isNotEqualTo(Path value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public PathVerifier isNotEqualTo(Path value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public PathVerifier isEqualTo(Path value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public PathVerifier isEqualTo(Path value)
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(scope, actual.toString(), name, config);
	}

	@Override
	public PathVerifier asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
		return this;
	}

	@Override
	public Optional<Path> getActualIfPresent()
	{
		return Optional.of(actual);
	}

	@Override
	public Path getActual()
	{
		return actual;
	}
}
