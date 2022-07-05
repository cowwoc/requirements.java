/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import com.github.cowwoc.requirements.java.internal.util.Strings;

/**
 * A line item in an exception context.
 */
public final class ContextLine
{
	private final String name;
	private final String value;

	/**
	 * Creates a new line.
	 *
	 * @param name  the key associated with the value (empty string if absent)
	 * @param value a value
	 * @throws AssertionError if the key is null, blank or contains a colon
	 */
	public ContextLine(String name, String value)
	{
		assert (name != null);
		assert (!name.contains(":")) : "name may not contain a colon.\n" +
			"Actual: " + Strings.asJavaString(name);
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the key associated with the value (empty string is absent)
	 */
	public String getName()
	{
		return name;
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
		if (!name.isBlank())
			result.append(name + ": ");
		result.append(value);
		return result.toString();
	}
}
