/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.diff.test;

import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.internal.diff.TextOnly;
import org.bitbucket.cowwoc.requirements.java.internal.diff.Writer16Colors;
import org.bitbucket.cowwoc.requirements.java.internal.diff.Writer16MillionColors;
import org.bitbucket.cowwoc.requirements.java.internal.diff.Writer256Colors;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.Strings;
import org.bitbucket.cowwoc.requirements.java.test.SameToStringDifferentHashCode;
import org.testng.annotations.Test;

import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_DELETE;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_EQUAL;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_INSERT;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.EOS_MARKER;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.NEWLINE_MARKER;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.TextOnly.DIFF_PADDING;
import static org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;
import static org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding.RGB_888_COLORS;
import static org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding.XTERM_16_COLORS;
import static org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding.XTERM_256_COLORS;

public final class DiffTest
{
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
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			TextOnly scheme = new TextOnly();

			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : int[6" + scheme.decoratePadding(1) + "]" + EOS_MARKER + "\n" +
				"Diff    : " + DIFF_EQUAL.repeat(4) + DIFF_DELETE + DIFF_INSERT +
				DIFF_EQUAL.repeat(1 + EOS_MARKER.length()) + "\n" +
				"Expected: int[" + scheme.decoratePadding(1) + "5]" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\nActual:\n" + actualMessage;
		}
	}

	/**
	 * Ensure that XTERM_16_COLORS diffs generate the expected value.
	 */
	@Test
	public void diffArraySize_16Colors()
	{
		try (ApplicationScope scope = new TestApplicationScope(XTERM_16_COLORS))
		{
			String actual = "int[6]";
			String expected = "int[5]";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			Writer16Colors scheme = new Writer16Colors();

			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : " + scheme.decorateUnchangedText("int[") +
				scheme.decorateDeletedText("6") + scheme.decoratePadding(1) +
				scheme.decorateUnchangedText("]") + EOS_MARKER + scheme.stopDecoration() + "\n" +
				"Expected: " + scheme.decorateUnchangedText("int[") + scheme.decoratePadding(1) +
				scheme.decorateInsertedText("5") + scheme.decorateUnchangedText("]") + EOS_MARKER +
				scheme.stopDecoration();
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\nActual:\n" + actualMessage;
		}
	}

	/**
	 * Ensure that XTERM_256_COLORS diffs generate the expected value.
	 */
	@Test
	public void diffArraySize_256Colors()
	{
		try (ApplicationScope scope = new TestApplicationScope(XTERM_256_COLORS))
		{
			String actual = "int[6]";
			String expected = "int[5]";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			Writer256Colors scheme = new Writer256Colors();

			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : " + scheme.decorateUnchangedText("int[") +
				scheme.decorateDeletedText("6") + scheme.decoratePadding(1) + scheme.decorateUnchangedText("]") +
				EOS_MARKER + scheme.stopDecoration() + "\n" +
				"Expected: " + scheme.decorateUnchangedText("int[") + scheme.decoratePadding(1) +
				scheme.decorateInsertedText("5") + scheme.decorateUnchangedText("]") + EOS_MARKER +
				scheme.stopDecoration();
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\nActual:\n" + actualMessage;
		}
	}

	/**
	 * Ensure that RGB_888_COLORS diffs generate the expected value.
	 */
	@Test
	public void diffArraySize_16MillionColors()
	{
		try (ApplicationScope scope = new TestApplicationScope(RGB_888_COLORS))
		{
			String actual = "int[6]";
			String expected = "int[5]";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			Writer16MillionColors scheme = new Writer16MillionColors();

			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : " + scheme.decorateUnchangedText("int[") +
				scheme.decorateDeletedText("6") + scheme.decoratePadding(1) + scheme.decorateUnchangedText("]") +
				EOS_MARKER + scheme.stopDecoration() + "\n" +
				"Expected: " + scheme.decorateUnchangedText("int[") + scheme.decoratePadding(1) +
				scheme.decorateInsertedText("5") + scheme.decorateUnchangedText("]") + EOS_MARKER +
				scheme.stopDecoration();
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\nActual:\n" + actualMessage;
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
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : actual" +
				DIFF_PADDING.repeat("expected".length()) + EOS_MARKER + "\n" +
				"Diff    : " + DIFF_DELETE.repeat("actual".length()) +
				DIFF_INSERT.repeat("expected".length()) + DIFF_EQUAL.repeat(EOS_MARKER.length()) + "\n" +
				"Expected: " + " ".repeat("actual".length()) + "expected" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\nActual:\n" + actualMessage;
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
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : \"key\": \"value \"" + EOS_MARKER + "\n" +
				"Diff    : " + DIFF_EQUAL.repeat(13) + DIFF_DELETE +
				DIFF_EQUAL.repeat(1 + EOS_MARKER.length()) + "\n" +
				"Expected: \"key\": \"value \"" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\nActual:\n" + actualMessage;
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
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@1  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected  : " + DIFF_PADDING.repeat(NEWLINE_MARKER.length()) + "\n" +
				"\n" +
				"Actual@2  : actual" + DIFF_PADDING.repeat("expected".length()) +
				EOS_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat("actual".length()) +
				DIFF_INSERT.repeat("expected".length()) +
				DIFF_EQUAL.repeat(EOS_MARKER.length()) + "\n" +
				"Expected@1: " + DIFF_PADDING.repeat("actual".length()) + "expected" +
				EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\nActual:\n" + actualMessage;
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
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@1  : actual" + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat("actual".length() + NEWLINE_MARKER.length()) +
				"\n" +
				"Expected  : " + DIFF_PADDING.repeat("actual".length() + NEWLINE_MARKER.length()) + "\n" +
				"\n" +
				"Actual@2  : " + DIFF_PADDING.repeat("expected".length()) + EOS_MARKER +
				"\n" +
				"Diff      : " + DIFF_INSERT.repeat("expected".length()) + DIFF_EQUAL.repeat(EOS_MARKER.length()) +
				"\n" +
				"Expected@1: expected" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\nActual:\n" + actualMessage;
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
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@1  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected  : " + DIFF_PADDING.repeat(NEWLINE_MARKER.length()) + "\n" +
				"\n" +
				"Actual@2  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected  : " + DIFF_PADDING.repeat(NEWLINE_MARKER.length()) + "\n" +
				"\n" +
				"Actual@3  : value" + EOS_MARKER + "\n" +
				"Expected@1: value" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\nActual:\n" + actualMessage;
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
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@1  : 1" + NEWLINE_MARKER + "\n" +
				"Expected@1: 1" + NEWLINE_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual@3  : 3" + DIFF_PADDING + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE + DIFF_INSERT +
				DIFF_EQUAL.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected@3: " + DIFF_PADDING + "9" + NEWLINE_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual@5  : 5\\0\n" +
				"Expected@5: 5\\0";
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\nActual:\n" + actualMessage;
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
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : The dog" +
				DIFF_PADDING.repeat("fox".length()) + " is br" + DIFF_PADDING + "own" + EOS_MARKER + "\n" +
				"Diff    : " + DIFF_EQUAL.repeat(4) + DIFF_DELETE.repeat(3) + DIFF_INSERT.repeat(3) +
				DIFF_EQUAL.repeat(4) + DIFF_DELETE.repeat(2) + DIFF_INSERT +
				DIFF_EQUAL.repeat(3 + EOS_MARKER.length()) + "\n" +
				"Expected: The " + DIFF_PADDING.repeat("dog".length()) + "fox is " +
				DIFF_PADDING.repeat("br".length()) + "down" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\nActual:\n" + actualMessage;
		}
	}

	@Test
	public void lastConsecutiveIndexOf()
	{
		int result = Strings.lastConsecutiveIndexOf("textex", " ");
		assert (result == -1) : result;

		result = Strings.lastConsecutiveIndexOf("  text", " ");
		assert (result == -1) : result;

		result = Strings.lastConsecutiveIndexOf("text  ", " ");
		assert (result == 4) : result;

		result = Strings.lastConsecutiveIndexOf("      ", " ");
		assert (result == 0) : result;
	}

	/**
	 * BUG: If the length of target is less than or equal to the return value, the method would
	 * return zero (which is incorrect).
	 */
	@Test
	public void lastConsecutiveIndexOf_resultLessThanOrEqualToLengthOfTarget()
	{
		int result = Strings.lastConsecutiveIndexOf("1 ", " ");
		assert (result == 1) : result;
	}

	/**
	 * BUG: If actual != expected but their string value is identical, then actual's string value
	 * should be included in the output, but is not.
	 */
	@Test
	public void stringValueIsEqual()
	{
		SameToStringDifferentHashCode actual = new SameToStringDifferentHashCode();
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new Requirements(scope).requireThat(actual, "actual").
				isEqualTo(new SameToStringDifferentHashCode());
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert (actualMessage.contains(actual.toString())) :
				"Was expecting output to contain actual value, but did not.\n" +
					"\nActual:\n" + actualMessage;
		}
	}
}
