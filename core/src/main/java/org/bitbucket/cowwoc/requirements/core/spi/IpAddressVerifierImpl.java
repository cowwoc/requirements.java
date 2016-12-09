/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.net.InetAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.IpAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;

/**
 * Default implementation of IpAddressVerifier.
 *
 * @author Gili Tzabari
 */
public final class IpAddressVerifierImpl implements IpAddressVerifier
{
	private final SingletonScope scope;
	private final InetAddress actual;
	private final String name;
	private final Configuration config;
	private final ObjectVerifier<InetAddress> asObject;

	/**
	 * Creates new IpAddressVerifierImpl.
	 *
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	public IpAddressVerifierImpl(SingletonScope scope, InetAddress actual, String name,
		Configuration config)
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
	public IpAddressVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new IpAddressVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public IpAddressVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new IpAddressVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public IpAddressVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new IpAddressVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public IpAddressVerifier isEqualTo(InetAddress value)
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public IpAddressVerifier isEqualTo(InetAddress value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public IpAddressVerifier isNotEqualTo(InetAddress value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public IpAddressVerifier isNotEqualTo(InetAddress value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public IpAddressVerifier isIn(Collection<InetAddress> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public IpAddressVerifier isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public IpAddressVerifier isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public IpAddressVerifier isNotNull()
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(scope, actual.toString(), name, config);
	}

	@Override
	public IpAddressVerifier isolate(Consumer<IpAddressVerifier> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
