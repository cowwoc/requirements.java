/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff.test;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.internal.diff.TextOnly;
import com.github.cowwoc.requirements.java.internal.diff.Writer16Colors;
import com.github.cowwoc.requirements.java.internal.diff.Writer16MillionColors;
import com.github.cowwoc.requirements.java.internal.diff.Writer256Colors;
import com.github.cowwoc.requirements.java.internal.diff.Writer8Colors;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import com.github.cowwoc.requirements.java.test.SameToStringDifferentHashCode;
import org.testng.annotations.Test;

import java.util.List;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_DELETE;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_EQUAL;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_INSERT;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.EOS_MARKER;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.NEWLINE_MARKER;
import static com.github.cowwoc.requirements.java.internal.diff.TextOnly.DIFF_PADDING;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.RGB_888_COLORS;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.XTERM_16_COLORS;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.XTERM_256_COLORS;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.XTERM_8_COLORS;

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
				"\n****************\nActual:\n" + actualMessage;
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
				"\n****************\nActual:\n" + actualMessage;
		}
	}

	/**
	 * Ensure that XTERM_16_COLORS diffs generate the expected value.
	 */
	@Test
	public void diffArraySize_8Colors()
	{
		try (ApplicationScope scope = new TestApplicationScope(XTERM_8_COLORS))
		{
			String actual = "int[6]";
			String expected = "int[5]";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			Writer8Colors scheme = new Writer8Colors();

			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : " + scheme.decorateUnchangedText("int[") +
				scheme.decorateDeletedText("6") + scheme.decoratePadding(1) +
				scheme.decorateUnchangedText("]") + EOS_MARKER + scheme.stopDecoration() + "\n" +
				"Expected: " + scheme.decorateUnchangedText("int[") + scheme.decoratePadding(1) +
				scheme.decorateInsertedText("5") + scheme.decorateUnchangedText("]") + EOS_MARKER +
				scheme.stopDecoration();
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n****************\nActual:\n" + actualMessage;
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
				"\n****************\nActual:\n" + actualMessage;
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
				"\n****************\nActual:\n" + actualMessage;
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
				"\n****************\nActual:\n" + actualMessage;
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
				"\n****************\nActual:\n" + actualMessage;
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
			String expectedMessage = "Actual@0  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected  : " + DIFF_PADDING.repeat(NEWLINE_MARKER.length()) + "\n" +
				"\n" +
				"Actual@1  : actual" + DIFF_PADDING.repeat("expected".length()) +
				EOS_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat("actual".length()) +
				DIFF_INSERT.repeat("expected".length()) +
				DIFF_EQUAL.repeat(EOS_MARKER.length()) + "\n" +
				"Expected@0: " + DIFF_PADDING.repeat("actual".length()) + "expected" +
				EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n****************\nActual:\n" + actualMessage;
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
			String expectedMessage = "Actual@0  : actual" + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat("actual".length() + NEWLINE_MARKER.length()) +
				"\n" +
				"Expected  : " + DIFF_PADDING.repeat("actual".length() + NEWLINE_MARKER.length()) + "\n" +
				"\n" +
				"Actual@1  : " + DIFF_PADDING.repeat("expected".length()) + EOS_MARKER +
				"\n" +
				"Diff      : " + DIFF_INSERT.repeat("expected".length()) + DIFF_EQUAL.repeat(EOS_MARKER.length()) +
				"\n" +
				"Expected@0: expected" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n****************\nActual:\n" + actualMessage;
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
			String expectedMessage = "Actual@0  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected  : " + DIFF_PADDING.repeat(NEWLINE_MARKER.length()) + "\n" +
				"\n" +
				"Actual@1  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected  : " + DIFF_PADDING.repeat(NEWLINE_MARKER.length()) + "\n" +
				"\n" +
				"Actual@2  : value" + EOS_MARKER + "\n" +
				"Expected@0: value" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n****************\nActual:\n" + actualMessage;
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
			String expectedMessage = "Actual@0  : 1" + NEWLINE_MARKER + "\n" +
				"Expected@0: 1" + NEWLINE_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual@2  : 3" + DIFF_PADDING + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE + DIFF_INSERT +
				DIFF_EQUAL.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected@2: " + DIFF_PADDING + "9" + NEWLINE_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual@4  : 5" + EOS_MARKER + "\n" +
				"Expected@4: 5" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\n****************\nActual:\n" + actualMessage;
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
				"\n****************\nActual:\n" + actualMessage;
		}
	}

	@Test
	public void smallChangeBeforeWord()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "you like me?";
			String expected = "Don't you like me?";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : " + DIFF_PADDING.repeat("Don't ".length()) + "you like me?" +
				EOS_MARKER + "\n" +
				"Diff    : " + DIFF_INSERT.repeat("Don't ".length()) +
				DIFF_EQUAL.repeat("you like me?".length() + EOS_MARKER.length()) + "\n" +
				"Expected: Don't you like me?" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n****************\nActual:\n" + actualMessage;
		}
	}

	@Test
	public void smallChangeInMiddle()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "I lice dogs";
			String expected = "I like dogs";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : I lic" + DIFF_PADDING + "e dogs" + EOS_MARKER + "\n" +
				"Diff    : " + DIFF_EQUAL.repeat("I li".length()) + DIFF_DELETE + DIFF_INSERT +
				DIFF_EQUAL.repeat("e dogs".length() + EOS_MARKER.length()) + "\n" +
				"Expected: I li" + DIFF_PADDING + "ke dogs" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n****************\nActual:\n" + actualMessage;
		}
	}

	@Test
	public void smallChangeAfterWord()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "I like dogs";
			String expected = "I like dogs";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : I like dog" + DIFF_PADDING + EOS_MARKER + "\n" +
				"Diff    : " + DIFF_EQUAL.repeat("I like dog".length()) + DIFF_INSERT +
				DIFF_EQUAL.repeat(EOS_MARKER.length()) + "\n" +
				"Expected: I like dogs" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n****************\nActual:\n" + actualMessage;
		}
	}

	@Test
	public void largeChangeInMiddle()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "I lices dogs";
			String expected = "I like dogs";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : I lices" + DIFF_PADDING.repeat("like".length()) + " dogs" +
				EOS_MARKER + "\n" +
				"Diff    : " + DIFF_EQUAL.repeat("I ".length()) + DIFF_DELETE.repeat("lices".length()) +
				DIFF_INSERT.repeat("like".length()) + DIFF_EQUAL.repeat(" dogs".length() + EOS_MARKER.length()) +
				"\n" +
				"Expected: I " + DIFF_PADDING.repeat("lices".length()) + "like dogs" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n****************\nActual:\n" + actualMessage;
		}
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

	/**
	 * Ensure that DIFF notices that non-terminal lines are different when they only contain whitespace.
	 */
	@Test
	public void diffMiddleWhitespace()
	{
		String actual = "one\n" +
			"\n" +
			"three";
		String expected = "one\n" +
			"   \n" +
			"three";
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "actual must be equal to " + expected + ".\n" +
				"\n" +
				"Actual@0  : one" + NEWLINE_MARKER + "\n" +
				"Expected@0: one" + NEWLINE_MARKER + "\n" +
				"\n" +
				"Actual@1  : " + DIFF_PADDING.repeat(3) + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_INSERT.repeat(3) + DIFF_PADDING.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected@1: " + DIFF_PADDING.repeat(3) + NEWLINE_MARKER + "\n" +
				"\n" +
				"Actual@2  : three" + EOS_MARKER + "\n" +
				"Expected@2: three" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n****************\nActual:\n" + actualMessage;
		}
	}

	/**
	 * Diffing of {@code List<Integer>}.
	 */
	@Test
	public void diffListOfInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			List<Integer> actual = List.of(1, 2, 3, 4, 5);
			List<Integer> expected = List.of(1, 2, 9, 4, 5);
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual[0]  : 1" + EOS_MARKER + "\n" +
				"Expected[0]: 1" + EOS_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual[2]  : " + "3" + DIFF_PADDING.repeat(1) + EOS_MARKER + "\n" +
				"Diff       : " + DIFF_DELETE + DIFF_INSERT + DIFF_EQUAL.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected[2]: " + DIFF_PADDING + "9" + EOS_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual[4]  : 5" + EOS_MARKER + "\n" +
				"Expected[4]: 5" + EOS_MARKER;
			;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\n****************\nActual:\n" + actualMessage;
		}
	}

	/**
	 * Diffing of {@code List<String>}.
	 */
	@Test
	public void diffListOfString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			List<String> actual = List.of("1", "2", "3.1\n3.2", "4", "5");
			List<String> expected = List.of("1", "2", "9.1\n9.2", "4", "5");
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual[0]    : 1" + EOS_MARKER + "\n" +
				"Expected[0]  : 1" + EOS_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual[2]@0  : " + "3" + DIFF_PADDING.repeat(1) + ".1" + NEWLINE_MARKER + "\n" +
				"Diff         : " + DIFF_DELETE + DIFF_INSERT + DIFF_EQUAL.repeat((".1" + NEWLINE_MARKER).length()) +
				"\n" +
				"Expected[2]@0: " + DIFF_PADDING + "9.1" + NEWLINE_MARKER + "\n" +
				"\n" +
				"Actual[2]@1  : " + "3" + DIFF_PADDING.repeat(1) + ".2" + EOS_MARKER + "\n" +
				"Diff         : " + DIFF_DELETE + DIFF_INSERT + DIFF_EQUAL.repeat((".2" + EOS_MARKER).length()) +
				"\n" +
				"Expected[2]@1: " + DIFF_PADDING + "9.2" + EOS_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual[4]    : 5" + EOS_MARKER + "\n" +
				"Expected[4]  : 5" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\n****************\nActual:\n" + actualMessage;
		}
	}
}
