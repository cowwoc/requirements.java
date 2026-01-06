/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.message.section;

import java.util.List;

/**
 * A section of text that contains contextual information related to a validation failure.
 */
public sealed interface MessageSection permits ContextSection, StringSection
{
	/**
	 * @return if the section contains key-value pairs, returns the maximum length of all keys; otherwise
	 * returns 0
	 */
	int getMaxKeyLength();

	/**
	 * @param maxKeyLength the maximum key length across all sections
	 * @return this section's lines, with the keys padded to fill {@code maxKeyLength} characters
	 */
	List<String> getLinesWithPaddedKeys(int maxKeyLength);
}