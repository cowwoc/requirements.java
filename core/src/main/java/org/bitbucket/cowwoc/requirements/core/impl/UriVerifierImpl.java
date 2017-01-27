/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.UriVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Default implementation of UriVerifier.
 *
 * @author Gili Tzabari
 */
public final class UriVerifierImpl implements UriVerifier
{
	private final ApplicationScope scope;
	private final URI actual;
	private final String name;
	private final Configuration config;
	private final ObjectVerifier<URI> asObject;

	/**
	 * Creates new UriVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public UriVerifierImpl(ApplicationScope scope, URI actual, String name, Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectVerifierImpl<>(scope, this.actual, name, config);
	}

	@Override
	public UriVerifier isAbsolute()
	{
		if (actual.isAbsolute())
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be absolute.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public UriVerifier isNotNull()
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public UriVerifier isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public UriVerifier isIn(Collection<URI> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public UriVerifier isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public UriVerifier isNotEqualTo(URI value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public UriVerifier isNotEqualTo(URI value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public UriVerifier isEqualTo(URI value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public UriVerifier isEqualTo(URI value)
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
	public UriVerifier asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
		return this;
	}

	@Override
	public Optional<URI> getActualIfPresent()
	{
		return Optional.of(actual);
	}

	@Override
	public URI getActual()
	{
		return actual;
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public UriVerifierImpl configuration(Consumer<Configuration> consumer)
	{
		consumer.accept(config);
		return this;
	}
}
