/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of PathRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class PathRequirementsImpl implements PathRequirements
{
	private final SingletonScope scope;
	private final Path actual;
	private final String name;
	private final Configuration config;
	private final ObjectRequirements<Path> asObject;

	/**
	 * Creates new PathRequirementsImpl.
	 * <p>
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	PathRequirementsImpl(SingletonScope scope, Path actual, String name, Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectRequirementsImpl<>(scope, actual, name, config);
	}

	@Override
	public PathRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new PathRequirementsImpl(scope, actual, name, newConfig);
	}

	@Override
	public PathRequirements addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new PathRequirementsImpl(scope, actual, name, newConfig);
	}

	@Override
	public PathRequirements withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new PathRequirementsImpl(scope, actual, name, newConfig);
	}

	@Override
	public PathRequirements exists()
	{
		if (Files.exists(actual))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s refers to a non-existent path: %s", name, actual.toAbsolutePath())).
			build();
	}

	@Override
	public PathRequirements isRegularFile(LinkOption... options) throws IOException
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
	public PathRequirements isDirectory(LinkOption... options) throws IOException
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
	public PathRequirements isRelative()
	{
		if (!actual.isAbsolute())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must refer to a relative path.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public PathRequirements isAbsolute()
	{
		if (actual.isAbsolute())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must refer to an absolute path.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public PathRequirements isNotNull()
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public PathRequirements isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public PathRequirements isIn(Collection<Path> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public PathRequirements isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public PathRequirements isNotEqualTo(Path value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public PathRequirements isNotEqualTo(Path value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public PathRequirements isEqualTo(Path value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public PathRequirements isEqualTo(Path value)
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public PathRequirements isolate(Consumer<PathRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
