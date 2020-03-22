/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.diff;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff;
import org.bitbucket.cowwoc.requirements.java.GlobalRequirements;
import org.bitbucket.cowwoc.requirements.java.internal.util.Strings;
import org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding;

import java.util.LinkedList;

import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.EOS_MARKER;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.NEWLINE_MARKER;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.NEWLINE_PATTERN;

/**
 * Generates a diff of two Strings.
 */
public final class DiffGenerator
{
	private final TerminalEncoding encoding;
	private final String paddingMarker;

	/**
	 * @param encoding the terminal encoding
	 * @throws AssertionError if {@code encoding} is null
	 */
	public DiffGenerator(TerminalEncoding encoding)
	{
		assert (encoding != null) : "encoding may not be null";
		this.encoding = encoding;
		this.paddingMarker = getPaddingMarker();
	}

	/**
	 * @return the padding character used to align values vertically
	 */
	private String getPaddingMarker()
	{
		switch (encoding)
		{
			case NONE:
				return TextOnly.DIFF_PADDING;
			case XTERM_8_COLORS:
				return Writer8Colors.DIFF_PADDING;
			case XTERM_16_COLORS:
				return Writer16Colors.DIFF_PADDING;
			case XTERM_256_COLORS:
				return Writer256Colors.DIFF_PADDING;
			case RGB_888_COLORS:
				return Writer16MillionColors.DIFF_PADDING;
			default:
				throw new AssertionError(encoding.name());
		}
	}

	/**
	 * Generates the diff of two strings.
	 * <p>
	 * <b>NOTE</b>: Colors may be disabled when stdin or stdout are redirected. To override this
	 * behavior, use {@link GlobalRequirements#withTerminalEncoding(TerminalEncoding)}.
	 *
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @return the calculated diff
	 * @throws NullPointerException if any of the arguments are null
	 */
	public DiffResult diff(String actual, String expected)
	{
		DiffMatchPatch diffEngine = new DiffMatchPatch();
		String actualWithEos;
		String expectedWithEos;

		// Mark the end of the string to guard against cases that end with whitespace
		actualWithEos = actual + EOS_MARKER;
		expectedWithEos = expected + EOS_MARKER;

		LinkedList<Diff> components = diffEngine.diffMain(actualWithEos, expectedWithEos);
		diffEngine.diffCleanupSemantic(components);

		DiffWriter writer = createDiffWriter();
		for (Diff component : components)
		{
			String[] lines = NEWLINE_PATTERN.split(component.text, -1);
			for (int i = 0, size = lines.length; i < size; ++i)
			{
				String text = lines[i];
				if (i < size - 1)
					text += NEWLINE_MARKER;
				if (!text.isEmpty())
				{
					switch (component.operation)
					{
						case EQUAL:
						{
							writer.writeUnchanged(text);
							break;
						}
						case INSERT:
						{
							writer.writeInserted(text);
							break;
						}
						case DELETE:
						{
							writer.writeDeleted(text);
							break;
						}
						default:
							throw new AssertionError(component.operation.name());
					}
				}

				if (i < size - 1)
				{
					// (i == size - 1) does not necessarily indicate the end of a line
					writer.writeNewline();
				}
			}
		}
		writer.close();
		return new DiffResult(writer.getActualLines(), writer.getMiddleLines(), writer.getExpectedLines());
	}

	/**
	 * @return a new writer
	 */
	private DiffWriter createDiffWriter()
	{
		switch (encoding)
		{
			case NONE:
				return new TextOnly();
			case XTERM_8_COLORS:
				return new Writer8Colors();
			case XTERM_16_COLORS:
				return new Writer16Colors();
			case XTERM_256_COLORS:
				return new Writer256Colors();
			case RGB_888_COLORS:
				return new Writer16MillionColors();
			default:
				throw new AssertionError(encoding.name());
		}
	}

	/**
	 * @param line a line
	 * @return true if {@code line} only contains padding characters
	 */
	public boolean isEmpty(String line)
	{
		return Strings.containsOnly(line, paddingMarker);
	}
}
