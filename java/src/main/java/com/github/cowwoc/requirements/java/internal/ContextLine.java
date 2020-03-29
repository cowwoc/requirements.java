/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

/**
 * A line item in an exception context.
 */
public final class ContextLine
{
	private final String key;
	private final Object value;

	/**
	 * Creates a new line.
	 *
	 * @param key   an optional value (empty string if absent)
	 * @param value a value
	 * @throws AssertionError if the key is null
	 */
	public ContextLine(String key, Object value)
	{
		assert (key != null);
		this.key = key;
		this.value = value;
	}

	/**
	 * Creates a new line.
	 *
	 * @param value the value of the line
	 */
	public ContextLine(Object value)
	{
		this.key = "";
		this.value = value;
	}

	/**
	 * @return the key associated with the value (empty string is absent)
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * @return a value
	 */
	public Object getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		if (!key.isEmpty())
			result.append(key + ":");
		result.append(value);
		return result.toString();
	}
}
