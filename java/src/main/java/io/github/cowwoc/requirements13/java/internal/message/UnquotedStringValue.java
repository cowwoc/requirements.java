/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.message;

/**
 * Wraps a String to prevent it from being quoted when used as a context value.
 */
public final class UnquotedStringValue
{
	private final String value;

	/**
	 * Creates a new instance.
	 *
	 * @param value the string
	 * @throws NullPointerException if {@code value} is null
	 */
	public UnquotedStringValue(String value)
	{
		if (value == null)
			throw new NullPointerException("value may not be null");
		this.value = value;
	}

	@Override
	public String toString()
	{
		return value;
	}
}