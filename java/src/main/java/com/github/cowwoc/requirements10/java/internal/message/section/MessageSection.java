package com.github.cowwoc.requirements10.java.internal.message.section;

import java.util.List;

/**
 * A section of text that contains contextual information related to a validation failure.
 */
public sealed interface MessageSection permits ContextSection, StringSection
{
	/**
	 * @return if the section contains key-value pairs, returns the maximum length of all keys; otherwise
	 *   returns 0
	 */
	int getMaxKeyLength();

	/**
	 * @param maxKeyLength the maximum key length across all sections
	 * @return this section's lines
	 */
	List<String> getLines(int maxKeyLength);
}