/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.diff;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff;
import org.bitbucket.cowwoc.requirements.java.GlobalConfigurable;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding;

import java.util.LinkedList;

import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.EOS_MARKER;
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
	 * behavior, use {@link GlobalConfigurable#withTerminalEncoding(TerminalEncoding)}.
	 *
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @return a writer
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

		DiffWriter writer = createDiffWriter(scope.getGlobalConfiguration().getTerminalEncoding(), actual,
			expected);
		for (Diff component : components)
		{
			switch (component.operation)
			{
				case EQUAL:
				{
					writer.keep(component.text);
					break;
				}
				case INSERT:
				{
					writer.insert(component.text);
					break;
				}
				case DELETE:
				{
					writer.delete(component.text);
					break;
				}
				default:
					throw new AssertionError(component.operation.name());
			}
		}
		writer.close();
		return new DiffResult(writer.getActual(), writer.getMiddle(), writer.getExpected(),
			writer.getPaddingMarker());
	}

	/**
	 * @param encoding a terminal encoding
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @return a writer that generates the diff of {@code actual} and {@code expected}
	 */
	private DiffWriter createDiffWriter(TerminalEncoding encoding, String actual, String expected)
	{
		switch (encoding)
		{
			case NONE:
				return new TextOnly(actual, expected);
			case XTERM_8COLOR:
				return new Xterm8Color(actual, expected);
			case XTERM_16COLOR:
				return new Xterm16Color(actual, expected);
			case XTERM_256COLOR:
				return new Xterm256Color(actual, expected);
			case RGB_888COLOR:
				return new Rgb888Color(actual, expected);
			default:
				throw new AssertionError(encoding.name());
		}
	}
}
