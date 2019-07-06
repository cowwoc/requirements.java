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
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

import java.util.function.Consumer;

/**
 * A {@code StringVerifier} that does nothing.
 */
public final class StringVerifierNoOp
	extends AbstractObjectVerifierNoOp<StringVerifier, String>
	implements StringVerifier
{
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public StringVerifierNoOp(Configuration config)
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
		return new InetAddressVerifierNoOp(config);
	}

	@Override
	public StringVerifier asInetAddress(Consumer<InetAddressVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public UriVerifier asUri()
	{
		return new UriVerifierNoOp(config);
	}

	@Override
	public StringVerifier asUri(Consumer<UriVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public UrlVerifier asUrl()
	{
		return new UrlVerifierNoOp(config);
	}

	@Override
	public StringVerifier asUrl(Consumer<UrlVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
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
		return new SizeVerifierNoOp(config);
	}

	@Override
	public StringVerifier length(Consumer<SizeVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return this;
	}
}
