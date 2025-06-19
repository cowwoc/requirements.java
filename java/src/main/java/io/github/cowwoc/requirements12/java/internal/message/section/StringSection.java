/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */

package io.github.cowwoc.requirements12.java.internal.message.section;

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