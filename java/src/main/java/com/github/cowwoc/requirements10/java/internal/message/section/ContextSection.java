package com.github.cowwoc.requirements10.java.internal.message.section;

import java.util.SequencedMap;

/**
 * A section of key-pair pairs that contain contextual information related to a validation failure.
 */
public record ContextSection(SequencedMap<String, String> value) implements MessageSection
{
	public ContextSection
	{
		if (value == null)
			throw new NullPointerException("value may not be null");
	}
}