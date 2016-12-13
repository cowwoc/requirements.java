/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.PathVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of PathVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public enum NoOpPathVerifier implements PathVerifier
{
	INSTANCE;

	@Override
	public PathVerifier withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public PathVerifier addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public PathVerifier withContext(List<Entry<String, Object>> context)
	{
		return this;
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
		return NoOpStringVerifier.INSTANCE;
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
	public PathVerifier isolate(Consumer<PathVerifier> consumer)
	{
		return this;
	}
}