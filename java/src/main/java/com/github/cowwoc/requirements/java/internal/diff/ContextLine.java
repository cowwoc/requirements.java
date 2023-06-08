/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.internal.util.Strings;

/**
 * A line item in an exception context.
 */
public final class ContextLine
{
	private final String name;
	private final Object value;
	private final boolean convertedToString;

	/**
	 * Creates a new line.
	 *
	 * @param name              the key associated with the value (empty string if absent)
	 * @param value             a value
	 * @param convertedToString true if the value was already converted to a String using
	 *                          {@link Configuration#toString(Object)}
	 * @throws AssertionError if the key is null, blank or contains a colon
	 */
	public ContextLine(String name, Object value, boolean convertedToString)
	{
		assert (name != null);
		assert (!name.contains(":")) : "name may not contain a colon.\n" +
			"Actual: " + Strings.asJavaString(name);
		this.name = name;
		this.value = value;
		this.convertedToString = convertedToString;
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

	/**
	 * @return true if the value was converted to a String
	 */
	public boolean wasConvertedToString()
	{
		return convertedToString;
	}

	@Override
	public String toString()
	{
		StringBuilder result = new StringBuilder(23);
		if (!name.isBlank())
			result.append(name + ": ");
		result.append(value).
			append("\nconvertedToString: ").
			append(convertedToString);
		return result.toString();
	}
}