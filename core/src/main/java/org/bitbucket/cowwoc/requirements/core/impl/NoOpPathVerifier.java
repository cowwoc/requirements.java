/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PathVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of PathVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpPathVerifier implements PathVerifier
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPathVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	public PathVerifier exists()
	{
		return this;
	}

	@Override
	public PathVerifier isAbsolute()
	{
		return this;
	}

	@Override
	public PathVerifier isDirectory(LinkOption... options)
	{
		return this;
	}

	@Override
	public PathVerifier isRegularFile(LinkOption... options)
	{
		return this;
	}

	@Override
	public PathVerifier isRelative()
	{
		return this;
	}

	@Override
	public PathVerifier isEqualTo(Path value)
	{
		return this;
	}

	@Override
	public PathVerifier isEqualTo(Path value, String name)
	{
		return this;
	}

	@Override
	public PathVerifier isNotEqualTo(Path value)
	{
		return this;
	}

	@Override
	public PathVerifier isNotEqualTo(Path value, String name)
	{
		return this;
	}

	@Override
	public PathVerifier isIn(Collection<Path> collection)
	{
		return this;
	}

	@Override
	public PathVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public PathVerifier isNull()
	{
		return this;
	}

	@Override
	public PathVerifier isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public PathVerifier asString(Consumer<StringVerifier> consumer)
	{
		return this;
	}

	@Override
	public Optional<Path> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public Path getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public PathVerifier configuration(Consumer<Configuration> consumer)
	{
		return this;
	}
}
