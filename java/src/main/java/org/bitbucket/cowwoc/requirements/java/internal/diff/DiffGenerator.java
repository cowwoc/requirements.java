/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.diff;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff;
import org.bitbucket.cowwoc.requirements.java.GlobalRequirements;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
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
	private final ApplicationScope scope;

	/**
	 * @param scope the application configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	public DiffGenerator(ApplicationScope scope)
	{
		assert (scope != null) : "scope may not be null";
		this.scope = scope;
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
		if (NEWLINE_PATTERN.matcher(actual).find() || NEWLINE_PATTERN.matcher(expected).find())
		{
			// If the input contains multiple lines, add the end of string character
			actualWithEos = actual + EOS_MARKER;
			expectedWithEos = expected + EOS_MARKER;
		}
		else
		{
			actualWithEos = actual;
			expectedWithEos = expected;
		}

		LinkedList<Diff> components = diffEngine.diffMain(actualWithEos, expectedWithEos);
		diffEngine.diffCleanupSemantic(components);

		DiffWriter writer = createDiffWriter(scope.getGlobalConfiguration().getTerminalEncoding());
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
		return new DiffResult(writer.getActualLines(), writer.getMiddleLines(), writer.getExpectedLines(),
			writer.getPaddingMarker());
	}

	/**
	 * @param encoding a terminal encoding
	 * @return a writer for the specified encoding
	 */
	private DiffWriter createDiffWriter(TerminalEncoding encoding)
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
}
