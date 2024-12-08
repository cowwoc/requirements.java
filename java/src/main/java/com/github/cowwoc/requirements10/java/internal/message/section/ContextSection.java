package com.github.cowwoc.requirements10.java.internal.message.section;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
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

	@Override
	public int getMaxKeyLength()
	{
		int maxKeyLength = 0;
		for (String key : value.keySet())
			maxKeyLength = Math.max(maxKeyLength, key.length());
		return maxKeyLength;
	}

	@Override
	public List<String> getLinesWithPaddedKeys(int maxKeyLength)
	{
		// Align the colons vertically
		List<String> lines = new ArrayList<>();
		for (Entry<String, String> entry : value.entrySet())
			lines.add(alignLeft(entry.getKey(), maxKeyLength) + ": " + entry.getValue());
		return lines;
	}

	/**
	 * @param text      the {@code String} to align
	 * @param minLength the minimum length of {@code text}
	 * @return {@code text} padded on the right with spaces until its length is greater than or equal to
	 * {@code minLength}
	 */
	private static String alignLeft(String text, int minLength)
	{
		int actualLength = text.length();
		if (actualLength > minLength)
			return text;
		return text + " ".repeat(minLength - actualLength);
	}
}