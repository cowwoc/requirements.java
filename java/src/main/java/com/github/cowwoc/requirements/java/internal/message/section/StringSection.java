package com.github.cowwoc.requirements.java.internal.message.section;

/**
 * A string that is added to the exception context.
 */
public record StringSection(String value) implements MessageSection
{
	public StringSection
	{
		if (value == null)
			throw new NullPointerException("value may not be null");
	}
}