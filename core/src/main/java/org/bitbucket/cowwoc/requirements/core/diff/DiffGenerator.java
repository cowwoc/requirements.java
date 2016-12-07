/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.diff;

import java.util.LinkedList;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff;
import static org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Operation.DELETE;
import static org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Operation.EQUAL;
import static org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Operation.INSERT;
import static org.bitbucket.cowwoc.requirements.core.diff.DiffConstants.EOS_MARKER;
import static org.bitbucket.cowwoc.requirements.core.diff.DiffConstants.NEWLINE_PATTERN;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;

/**
 * Generates a diff of two Strings.
 *
 * @author Gili Tzabari
 */
public final class DiffGenerator
{
	/**
	 * The terminal type.
	 */
	private final TerminalType terminal;

	/**
	 * @param scope the system configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	public DiffGenerator(SingletonScope scope)
	{
		assert (scope != null): "scope may not be null";
		this.terminal = scope.getTerminal().getType();
	}

	/**
	 * Generates the diff of two strings.
	 * <p>
	 * NOTE: Colors are disabled when stdin or stdout are redirected. To override this behavior, set
	 * the system property "org.bitbucket.cowwoc.requirements.terminal" to any value found in
	 * {@link TerminalType} (e.g. XTERM).
	 * NOTE: the system property is only read once. Changing its value will not affect the terminal
	 * type used by subsequent invocations.
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

		DiffWriter writer = terminal.diff(actual, expected);
		for (Diff component: components)
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
		return new DiffResult(writer.getActual(), writer.getMiddle(), writer.getExpected());
	}
}
