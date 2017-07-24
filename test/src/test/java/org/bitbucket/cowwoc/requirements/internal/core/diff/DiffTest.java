/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.diff;

import org.bitbucket.cowwoc.requirements.core.Verifiers;
import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.RGB_888COLOR;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.XTERM_16COLOR;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.XTERM_256COLOR;
import static org.bitbucket.cowwoc.requirements.internal.core.diff.DiffConstants.DIFF_DELETE;
import static org.bitbucket.cowwoc.requirements.internal.core.diff.DiffConstants.DIFF_EQUAL;
import static org.bitbucket.cowwoc.requirements.internal.core.diff.DiffConstants.DIFF_INSERT;
import static org.bitbucket.cowwoc.requirements.internal.core.diff.DiffConstants.EOS_MARKER;
import static org.bitbucket.cowwoc.requirements.internal.core.diff.DiffConstants.NEWLINE_MARKER;
import static org.bitbucket.cowwoc.requirements.internal.core.diff.TextOnly.PADDING_MARKER;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.util.Strings;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class DiffTest
{
	/**
	 * A class whose instances have the same toString() but are never equal.
	 */
	private static final class SameToStringDifferentHashCode
	{
		@Override
		public String toString()
		{
			return "SameToStringDifferentHashCode";
		}
	}

	/**
	 * Ensure that text-mode diffs generate the expected value.
	 */
	@Test
	public void diffArraySize()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "int[6]";
			String expected = "int[5]";
			new Verifiers(scope).requireThat("actual", actual).isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : int[6 ]\n" +
				"Diff    : " + Strings.repeat(DIFF_EQUAL, 4) + DIFF_DELETE + DIFF_INSERT +
				DIFF_EQUAL + "\n" +
				"Expected: int[ 5]";
			assert (actualMessage.contains(expectedMessage)): "expected:\n" + expectedMessage +
				"\nactual:\n" + actualMessage;
		}
	}

	/**
	 * Ensure that XTERM_16COLOR diffs generate the expected value.
	 */
	@Test
	public void diffArraySize_XTerm_16Color()
	{
		try (ApplicationScope scope = new TestApplicationScope(XTERM_16COLOR))
		{
			String actual = "int[6]";
			String expected = "int[5]";
			new Verifiers(scope).requireThat("actual", actual).isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			Xterm16Color scheme = new Xterm16Color("actual", "expected");

			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : int[" + scheme.deleteColor + "6" +
				scheme.paddingColor + scheme.getPaddingMarker() + scheme.resetColor + "]\n" +
				"Expected: int[" + scheme.paddingColor + scheme.getPaddingMarker() +
				scheme.insertColor + "5" + scheme.resetColor + "]";
			assert (actualMessage.contains(expectedMessage)): "expected:\n" + expectedMessage +
				"\nactual:\n" + actualMessage;
		}
	}

	/**
	 * Ensure that XTERM_256COLOR diffs generate the expected value.
	 */
	@Test
	public void diffArraySize_XTerm_256Color()
	{
		try (ApplicationScope scope = new TestApplicationScope(XTERM_256COLOR))
		{
			String actual = "int[6]";
			String expected = "int[5]";
			new Verifiers(scope).requireThat("actual", actual).isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			Xterm256Color scheme = new Xterm256Color("actual", "expected");

			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : int[" + scheme.deleteColor + "6" +
				scheme.paddingColor + scheme.getPaddingMarker() + scheme.resetColor + "]\n" +
				"Expected: int[" + scheme.paddingColor + scheme.getPaddingMarker() +
				scheme.insertColor + "5" + scheme.resetColor + "]";
			assert (actualMessage.contains(expectedMessage)): "expected:\n" + expectedMessage +
				"\nactual:\n" + actualMessage;
		}
	}

	/**
	 * Ensure that RGB_888COLOR diffs generate the expected value.
	 */
	@Test
	public void diffArraySize_Rgb888Color()
	{
		try (ApplicationScope scope = new TestApplicationScope(RGB_888COLOR))
		{
			String actual = "int[6]";
			String expected = "int[5]";
			new Verifiers(scope).requireThat("actual", actual).isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			Rgb888Color scheme = new Rgb888Color("actual", "expected");

			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : int[" + scheme.deleteColor + "6" +
				scheme.paddingColor + scheme.getPaddingMarker() + scheme.resetColor + "]\n" +
				"Expected: int[" + scheme.paddingColor + scheme.getPaddingMarker() +
				scheme.insertColor + "5" + scheme.resetColor + "]";
			assert (actualMessage.contains(expectedMessage)): "expected:\n" + expectedMessage +
				"\nactual:\n" + actualMessage;
		}
	}

	/**
	 * Ensure that diffs delete before inserting.
	 */
	@Test
	public void diffDeleteThenInsert()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "actual";
			String expected = "expected";
			new Verifiers(scope).requireThat("actual", actual).isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : actual" +
				Strings.repeat(PADDING_MARKER, "expected".length()) + "\n" +
				"Diff    : " + Strings.repeat(DIFF_DELETE, "actual".length()) +
				Strings.repeat(DIFF_INSERT, "expected".length()) + "\n" +
				"Expected: " + Strings.repeat(" ", "actual".length()) + "expected";
			assert (actualMessage.contains(expectedMessage)): "expected:\n" + expectedMessage +
				"\nactual:\n" + actualMessage;
		}
	}

	/**
	 * Ensure that whitespace differences are easy to understand in text-mode diffs.
	 */
	@Test
	public void diffMissingWhitespace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "\"key\": \"value \"";
			String expected = "\"key\": \"value\"";
			new Verifiers(scope).requireThat("actual", actual).isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : \"key\": \"value \"\n" +
				"Diff    : " + Strings.repeat(DIFF_EQUAL, 13) + DIFF_DELETE + DIFF_EQUAL + "\n" +
				"Expected: \"key\": \"value \"";
			assert (actualMessage.contains(expectedMessage)): "expected:\n" + expectedMessage +
				"\nactual:\n" + actualMessage;
		}
	}

	/**
	 * Test multi-line text-mode diffs where actual contains a leading newline character.
	 */
	@Test
	public void diffNewlinePrefix()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "\nactual";
			String expected = "expected";
			new Verifiers(scope).requireThat("actual", actual).isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@1  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_DELETE, NEWLINE_MARKER.length()) + "\n" +
				"Expected  : " + Strings.repeat(PADDING_MARKER, NEWLINE_MARKER.length()) + "\n" +
				"\n" +
				"Actual@2  : actual" + Strings.repeat(PADDING_MARKER, "expected".length()) +
				EOS_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_DELETE, "actual".length()) +
				Strings.repeat(DIFF_INSERT, "expected".length()) +
				Strings.repeat(DIFF_EQUAL, EOS_MARKER.length()) + "\n" +
				"Expected@1: " + Strings.repeat(PADDING_MARKER, "actual".length()) + "expected" +
				EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)): "expected:\n" + expectedMessage +
				"\nactual:\n" + actualMessage;
		}
	}

	/**
	 * Test multi-line text-mode diffs where actual contains a trailing newline character.
	 */
	@Test
	public void diffNewlinePostfix()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "actual\n";
			String expected = "expected";
			new Verifiers(scope).requireThat("actual", actual).isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@1  : actual" + NEWLINE_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_DELETE, "actual".length() + NEWLINE_MARKER.length()) +
				"\n" +
				"Expected  : " + Strings.repeat(PADDING_MARKER,
					"actual".length() + NEWLINE_MARKER.length()) + "\n" +
				"\n" +
				"Actual@2  : " + Strings.repeat(PADDING_MARKER, "expected".length()) + EOS_MARKER +
				"\n" +
				"Diff      : " + Strings.repeat(DIFF_INSERT, "expected".length()) +
				Strings.repeat(DIFF_EQUAL, EOS_MARKER.length()) + "\n" +
				"Expected@1: expected" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)): "expected:\n" + expectedMessage +
				"\nactual:\n" + actualMessage;
		}
	}

	/**
	 * Test multi-line text-mode diffs containing matching text across different lines.
	 */
	@Test
	public void matchAcrossLines()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "\n\nvalue";
			String expected = "value";
			new Verifiers(scope).requireThat("actual", actual).isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@1  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_DELETE, NEWLINE_MARKER.length()) + "\n" +
				"Expected  : " + Strings.repeat(PADDING_MARKER, NEWLINE_MARKER.length()) + "\n" +
				"\n" +
				"Actual@2  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_DELETE, NEWLINE_MARKER.length()) + "\n" +
				"Expected  : " + Strings.repeat(PADDING_MARKER, NEWLINE_MARKER.length()) + "\n" +
				"\n" +
				"Actual@3  : value" + EOS_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_EQUAL, "value".length() + EOS_MARKER.length()) + "\n" +
				"Expected@1: value" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)): "expected:\n" + expectedMessage +
				"\nactual:\n" + actualMessage;
		}
	}

	/**
	 * Ensures that duplicate lines in the middle of a diff are omitted.
	 */
	@Test
	public void skipDuplicateLinesTest()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1\n2\n3\n4\n5";
			String expected = "1\n2\n9\n4\n5";
			new Verifiers(scope).requireThat("actual", actual).isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@1  : 1" + NEWLINE_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_EQUAL, "1".length() + NEWLINE_MARKER.length()) + "\n" +
				"Expected@1: 1" + NEWLINE_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual@3  : 3" + PADDING_MARKER + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE + DIFF_INSERT +
				Strings.repeat(DIFF_EQUAL, NEWLINE_MARKER.length()) + "\n" +
				"Expected@3: " + PADDING_MARKER + "9" + NEWLINE_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual@5  : 5\\0\n" +
				"Diff      : " + Strings.repeat(DIFF_EQUAL, "5".length() + EOS_MARKER.length()) + "\n" +
				"Expected@5: 5\\0";
			assert (actualMessage.contains(expectedMessage)): "expected:\n" + expectedMessage +
				"\nactual:\n" + actualMessage;
		}
	}

	/**
	 * A test suggested by Charles Drolet.
	 */
	@Test
	public void charlesTest()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "The dog is brown";
			String expected = "The fox is down";
			new Verifiers(scope).requireThat("actual", actual).isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : The dog" +
				Strings.repeat(PADDING_MARKER, "fox".length()) + " is br" + PADDING_MARKER + "own\n" +
				"Diff    : ====   ^^^====  ^===\n" +
				"Expected: The " + Strings.repeat(PADDING_MARKER, "dog".length()) + "fox is " +
				Strings.repeat(DIFF_DELETE, "br".length()) + "down";
			assert (actualMessage.contains(expectedMessage)): "expected:\n" + expectedMessage +
				"\nactual:\n" + actualMessage;
		}
	}

	@Test
	public void lastConsecutiveIndexOf()
	{
		int result = Strings.lastConsecutiveIndexOf("textex", " ");
		assert (result == -1): result;

		result = Strings.lastConsecutiveIndexOf("  text", " ");
		assert (result == -1): result;

		result = Strings.lastConsecutiveIndexOf("text  ", " ");
		assert (result == 4): result;

		result = Strings.lastConsecutiveIndexOf("      ", " ");
		assert (result == 0): result;
	}

	/**
	 * BUG: If the length of target is less than or equal to the return value, the method would
	 * return zero (which is incorrect).
	 */
	@Test
	public void lastConsecutiveIndexOf_resultLessThanOrEqualToLengthOfTarget()
	{
		int result = Strings.lastConsecutiveIndexOf("1 ", " ");
		assert (result == 1): result;
	}

	/**
	 * BUG: If actual != expected but their string value is identical, then actual's string value
	 * should be included in the output, but is not.
	 */
	@Test
	public void stringValueIsEqual()
	{
		SameToStringDifferentHashCode actual =
			new SameToStringDifferentHashCode();
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new Verifiers(scope).requireThat("actual", actual).isEqualTo(
				new SameToStringDifferentHashCode());
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert (actualMessage.contains(actual.toString())):
				"Was expecting output to contain actual value, but did not.\n" +
				"\nActual:\n" + actualMessage;
		}
	}
}
