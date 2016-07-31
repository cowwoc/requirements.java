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
import java.util.Map;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

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
	public PathRequirements withContext(Map<String, Object> context)
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s refers to a non-existent path: %s", name, parameter.toAbsolutePath()));
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
			throw config.createException(IllegalArgumentException.class,
				String.format("%s refers to a non-existent path: %s", name, parameter.toAbsolutePath()),
				e);
		}
		if (!attrs.isRegularFile())
		{
			throw config.createException(IllegalArgumentException.class,
				String.format("%s must refer to a file.", name),
				"Actual", parameter.toAbsolutePath());
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
			throw config.createException(IllegalArgumentException.class,
				String.format("%s refers to a non-existent path: %s", name, parameter.toAbsolutePath()),
				e);
		}
		if (!attrs.isDirectory())
		{
			throw config.createException(IllegalArgumentException.class,
				String.format("%s must refer to a directory.", name),
				"Actual", parameter.toAbsolutePath());
		}
		return this;
	}

	@Override
	public PathRequirements isRelative() throws IllegalArgumentException
	{
		if (!parameter.isAbsolute())
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must refer to a relative path.", name),
			"Actual", parameter);
	}

	@Override
	public PathRequirements isAbsolute() throws IllegalArgumentException
	{
		if (parameter.isAbsolute())
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must refer to an absolute path.", name),
			"Actual", parameter);
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
