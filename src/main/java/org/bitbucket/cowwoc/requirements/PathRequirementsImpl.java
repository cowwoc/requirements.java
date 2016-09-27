/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.Configuration;
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

/**
 * Default implementation of PathRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class PathRequirementsImpl implements PathRequirements
{
	private final Path parameter;
	private final String name;
	private final Configuration config;
	private final ObjectRequirements<Path> asObject;

	/**
	 * Creates new PathRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PathRequirementsImpl(Path parameter, String name, Configuration config)
		throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectRequirementsImpl<>(parameter, name, config);
	}

	@Override
	public PathRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new PathRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public PathRequirements addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new PathRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public PathRequirements withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new PathRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public PathRequirements exists() throws IllegalArgumentException
	{
		if (Files.exists(parameter))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s refers to a non-existent path: %s", name, parameter.toAbsolutePath())).
			build();
	}

	@Override
	public PathRequirements isRegularFile(LinkOption... options)
		throws IllegalArgumentException, IOException
	{
		BasicFileAttributes attrs;
		try
		{
			attrs = Files.readAttributes(parameter, BasicFileAttributes.class, options);
		}
		catch (NoSuchFileException e)
		{
			throw config.exceptionBuilder(IllegalArgumentException.class,
				String.format("%s refers to a non-existent path: %s", name, parameter.toAbsolutePath()), e).
				build();
		}
		if (!attrs.isRegularFile())
		{
			throw config.exceptionBuilder(IllegalArgumentException.class,
				String.format("%s must refer to a file.", name)).
				addContext("Actual", parameter.toAbsolutePath()).
				build();
		}
		return this;
	}

	@Override
	public PathRequirements isDirectory(LinkOption... options)
		throws IllegalArgumentException, IOException
	{
		BasicFileAttributes attrs;
		try
		{
			attrs = Files.readAttributes(parameter, BasicFileAttributes.class, options);
		}
		catch (NoSuchFileException e)
		{
			throw config.exceptionBuilder(IllegalArgumentException.class,
				String.format("%s refers to a non-existent path: %s", name, parameter.toAbsolutePath()), e).
				build();
		}
		if (!attrs.isDirectory())
		{
			throw config.exceptionBuilder(IllegalArgumentException.class,
				String.format("%s must refer to a directory.", name)).
				addContext("Actual", parameter.toAbsolutePath()).
				build();
		}
		return this;
	}

	@Override
	public PathRequirements isRelative() throws IllegalArgumentException
	{
		if (!parameter.isAbsolute())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must refer to a relative path.", name)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public PathRequirements isAbsolute() throws IllegalArgumentException
	{
		if (parameter.isAbsolute())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must refer to an absolute path.", name)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public PathRequirements isNotNull() throws NullPointerException
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public PathRequirements isNull() throws IllegalArgumentException
	{
		asObject.isNull();
		return this;
	}

	@Override
	public PathRequirements isIn(Collection<Path> collection)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public PathRequirements isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public PathRequirements isNotEqualTo(Path value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public PathRequirements isNotEqualTo(Path value) throws IllegalArgumentException
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public PathRequirements isEqualTo(Path value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public PathRequirements isEqualTo(Path value) throws IllegalArgumentException
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
