/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.java.SizeVerifier;
import org.bitbucket.cowwoc.requirements.java.StringVerifier;
import org.bitbucket.cowwoc.requirements.java.UriVerifier;
import org.bitbucket.cowwoc.requirements.java.UrlVerifier;

import java.util.function.Consumer;

/**
 * A {@code StringVerifier} that does nothing.
 */
public final class NoOpStringVerifier
	extends NoOpObjectCapabilities<StringVerifier, String>
	implements StringVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpStringVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected StringVerifier getThis()
	{
		return this;
	}

	@Override
	public StringVerifier doesNotEndWith(String suffix)
	{
		return this;
	}

	@Override
	public StringVerifier doesNotStartWith(String prefix)
	{
		return this;
	}

	@Override
	public StringVerifier endsWith(String suffix)
	{
		return this;
	}

	@Override
	public StringVerifier contains(String expected)
	{
		return this;
	}

	@Override
	public StringVerifier doesNotContain(String value)
	{
		return this;
	}

	@Override
	public InetAddressVerifier asInetAddress()
	{
		return new NoOpInetAddressVerifier(config);
	}

	@Override
	public StringVerifier asInetAddress(Consumer<InetAddressVerifier> consumer)
	{
		return this;
	}

	@Override
	public UriVerifier asUri()
	{
		return new NoOpUriVerifier(config);
	}

	@Override
	public StringVerifier asUri(Consumer<UriVerifier> consumer)
	{
		return this;
	}

	@Override
	public UrlVerifier asUrl()
	{
		return new NoOpUrlVerifier(config);
	}

	@Override
	public StringVerifier asUrl(Consumer<UrlVerifier> consumer)
	{
		return this;
	}

	@Override
	public StringVerifier isEmpty()
	{
		return this;
	}

	@Override
	public StringVerifier isNotEmpty()
	{
		return this;
	}

	@Override
	public StringVerifier startsWith(String prefix)
	{
		return this;
	}

	@Override
	public StringVerifier trim()
	{
		return this;
	}

	@Override
	public StringVerifier isTrimmed()
	{
		return this;
	}

	@Override
	public SizeVerifier length()
	{
		return new NoOpSizeVerifier(config);
	}

	@Override
	public StringVerifier length(Consumer<SizeVerifier> consumer)
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return this;
	}
}
