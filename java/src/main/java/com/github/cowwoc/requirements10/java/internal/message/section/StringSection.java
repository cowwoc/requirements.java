package com.github.cowwoc.requirements10.java.internal.message.section;

import java.util.List;

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

	@Override
	public int getMaxKeyLength()
	{
		return 0;
	}

	@Override
	public List<String> getLinesWithPaddedKeys(int maxKeyLength)
	{
		return List.of(value);
	}
}