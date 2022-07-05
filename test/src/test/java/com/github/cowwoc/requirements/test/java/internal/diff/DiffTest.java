/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java.internal.diff;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.internal.diff.TextOnly;
import com.github.cowwoc.requirements.java.internal.diff.Writer16Colors;
import com.github.cowwoc.requirements.java.internal.diff.Writer16MillionColors;
import com.github.cowwoc.requirements.java.internal.diff.Writer256Colors;
import com.github.cowwoc.requirements.java.internal.diff.Writer8Colors;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.java.SameToStringAndHashCodeDifferentIdentity;
import com.github.cowwoc.requirements.test.java.SameToStringDifferentHashCode;
import com.github.cowwoc.requirements.test.natives.internal.util.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.List;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_DELETE;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_EQUAL;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_INSERT;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.EOS_MARKER;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.NEWLINE_MARKER;
import static com.github.cowwoc.requirements.java.internal.diff.TextOnly.DIFF_PADDING;
import static com.github.cowwoc.requirements.java.internal.util.Strings.TEXT_BLOCK_DELIMITER;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.RGB_888_COLORS;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.XTERM_16_COLORS;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.XTERM_256_COLORS;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.XTERM_8_COLORS;
import static org.testng.Assert.fail;

public final class DiffTest
{
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
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : \"actual\"" + DIFF_PADDING.repeat("\"expected\"".length()) +
				EOS_MARKER + "\n" +
				"Diff    : " + DIFF_DELETE.repeat("\"actual\"".length()) +
				DIFF_INSERT.repeat("\"expected\"".length()) +
				DIFF_EQUAL.repeat(EOS_MARKER.length()) + "\n" +
				"Expected: " + DIFF_PADDING.repeat("\"actual\"".length()) + "\"expected\"" +
				EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
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
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : \"\\\"key\\\": \\\"value \\\"\"" + EOS_MARKER + "\n" +
				"Diff    : " + DIFF_EQUAL.repeat("\"\\\"key\\\": \\\"value".length()) + DIFF_DELETE +
				DIFF_EQUAL.repeat(("\"\\\"").length() + EOS_MARKER.length()) + "\n" +
				"Expected: \"\\\"key\\\": \\\"value \\\"\"" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
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
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@0  : " + TEXT_BLOCK_DELIMITER + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat((TEXT_BLOCK_DELIMITER + NEWLINE_MARKER).length()) + "\n" +
				"Expected  : " + DIFF_PADDING.repeat((TEXT_BLOCK_DELIMITER + NEWLINE_MARKER).length()) + "\n" +
				"\n" +
				"Actual@1  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected  : " + DIFF_PADDING.repeat(NEWLINE_MARKER.length()) + "\n" +
				"\n" +
				"Actual@2  : actual" + TEXT_BLOCK_DELIMITER + DIFF_PADDING.repeat("\"expected\"".length()) +
				EOS_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat(("actual" + TEXT_BLOCK_DELIMITER).length()) +
				DIFF_INSERT.repeat("\"expected\"".length()) + DIFF_EQUAL.repeat(EOS_MARKER.length()) + "\n" +
				"Expected@0: " + DIFF_PADDING.repeat(("actual" + TEXT_BLOCK_DELIMITER).length()) + "\"expected\"" +
				EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
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
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@0  : " + TEXT_BLOCK_DELIMITER + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat((TEXT_BLOCK_DELIMITER + NEWLINE_MARKER).length()) + "\n" +
				"Expected  : " + DIFF_PADDING.repeat((TEXT_BLOCK_DELIMITER + NEWLINE_MARKER).length()) + "\n" +
				"\n" +
				"Actual@1  : actual" + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat(("actual" + NEWLINE_MARKER).length()) + "\n" +
				"Expected  : " + DIFF_PADDING.repeat(("actual" + NEWLINE_MARKER).length()) + "\n" +
				"\n" +
				"Actual@2  : " + TEXT_BLOCK_DELIMITER + DIFF_PADDING.repeat(("\"expected\"").length()) +
				EOS_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat(TEXT_BLOCK_DELIMITER.length()) +
				DIFF_INSERT.repeat("\"expected\"".length()) + DIFF_PADDING.repeat(EOS_MARKER.length()) + "\n" +
				"Expected@0: " + DIFF_PADDING.repeat((TEXT_BLOCK_DELIMITER).length()) +
				"\"expected\"" + EOS_MARKER;

			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
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
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@0  : " + TEXT_BLOCK_DELIMITER + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_EQUAL.repeat("\"".length()) +
				DIFF_DELETE.repeat(("\"\"" + NEWLINE_MARKER).length()) + "\n" +
				"Expected@0: " + "\"" + DIFF_PADDING.repeat(("\"\"" + NEWLINE_MARKER).length()) + "\n" +
				"\n" +
				"Actual@1  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected  : " + DIFF_PADDING.repeat(NEWLINE_MARKER.length()) + "\n" +
				"\n" +
				"Actual@2  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected  : " + DIFF_PADDING.repeat(NEWLINE_MARKER.length()) + "\n" +
				"\n" +
				"Actual@3  : value" + TEXT_BLOCK_DELIMITER + EOS_MARKER + "\n" +
				"Diff      : " + DIFF_EQUAL.repeat(("value\"").length()) + DIFF_DELETE.repeat("\"\"".length()) +
				DIFF_EQUAL.repeat(EOS_MARKER.length()) + "\n" +
				"Expected@0: value\"" + DIFF_PADDING.repeat("\"\"".length()) + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
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
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@0  : " + TEXT_BLOCK_DELIMITER + NEWLINE_MARKER + "\n" +
				"Expected@0: " + TEXT_BLOCK_DELIMITER + NEWLINE_MARKER + "\n" +
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
				"Actual@5  : 5" + TEXT_BLOCK_DELIMITER + EOS_MARKER + "\n" +
				"Expected@5: 5" + TEXT_BLOCK_DELIMITER + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\n**************** Actual:\n" + actualMessage;
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
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : \"The dog" +
				DIFF_PADDING.repeat("fox".length()) + " is br" +
				DIFF_PADDING.repeat("d".length()) + "own\"" +
				EOS_MARKER + "\n" +
				"Diff    : " + DIFF_EQUAL.repeat("\"The ".length()) +
				DIFF_DELETE.repeat("dog".length()) +
				DIFF_INSERT.repeat("fox".length()) + DIFF_EQUAL.repeat(" is ".length()) +
				DIFF_DELETE.repeat("br".length()) + DIFF_INSERT.repeat("d".length()) +
				DIFF_EQUAL.repeat("own\"".length() + EOS_MARKER.length()) + "\n" +
				"Expected: \"The " + DIFF_PADDING.repeat("dog".length()) + "fox is " +
				DIFF_PADDING.repeat("br".length()) + "down\"" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
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
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : \"" + DIFF_PADDING.repeat("Don't ".length()) + "you like me?\"" +
				EOS_MARKER + "\n" +
				"Diff    : " + DIFF_EQUAL.repeat("\"".length()) + DIFF_INSERT.repeat("Don't ".length()) +
				DIFF_EQUAL.repeat("you like me?\"".length() + EOS_MARKER.length()) + "\n" +
				"Expected: \"Don't you like me?\"" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
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
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : \"I lic" + DIFF_PADDING + "e dogs\"" + EOS_MARKER + "\n" +
				"Diff    : " + DIFF_EQUAL.repeat("\"I li".length()) + DIFF_DELETE + DIFF_INSERT +
				DIFF_EQUAL.repeat("e dogs\"".length() + EOS_MARKER.length()) + "\n" +
				"Expected: \"I li" + DIFF_PADDING + "ke dogs\"" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
		}
	}

	@Test
	public void smallChangeAfterWord()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "I like dog";
			String expected = "I like dogs";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : \"I like dog" + DIFF_PADDING.repeat(" ".length()) +
				"\"" + EOS_MARKER + "\n" +
				"Diff    : " + DIFF_EQUAL.repeat("\"I like dog".length()) + DIFF_INSERT.repeat("s".length()) +
				DIFF_EQUAL.repeat(("\"" + EOS_MARKER).length()) + "\n" +
				"Expected: \"I like dogs\"" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
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
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : \"I lices" + DIFF_PADDING.repeat("like".length()) + " dogs\"" +
				EOS_MARKER + "\n" +
				"Diff    : " + DIFF_EQUAL.repeat("\"I ".length()) +
				DIFF_DELETE.repeat("lices".length()) +
				DIFF_INSERT.repeat("like".length()) +
				DIFF_EQUAL.repeat(" dogs\"".length() + EOS_MARKER.length()) +
				"\n" +
				"Expected: \"I " + DIFF_PADDING.repeat("lices".length()) + "like dogs\"" +
				EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
		}
	}

	/**
	 * Avoid short diffs in short words.
	 */
	@Test
	public void combineShortDiffs()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "2017-05-13T17:55:01-04:00[America/Montreal]";
			String expected = "2017-05-13T17:56:03-04:00[America/Montreal]";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : \"2017-05-13T17:55" + DIFF_PADDING.repeat("56".length()) +
				":01" + DIFF_PADDING.repeat("03".length()) + "-04:00[America/Montreal]\"" +
				EOS_MARKER + "\n" +
				"Diff    : " + DIFF_EQUAL.repeat("\"2017-05-13T17:".length()) +
				DIFF_DELETE.repeat("55".length()) + DIFF_INSERT.repeat("56".length()) + DIFF_EQUAL +
				DIFF_DELETE.repeat("01".length()) + DIFF_INSERT.repeat("03".length()) +
				DIFF_EQUAL.repeat("-04:00[America/Montreal]\"".length() + EOS_MARKER.length()) + "\n" +
				"Expected: \"2017-05-13T17:" + DIFF_PADDING.repeat("55".length()) +
				"56:" + DIFF_PADDING.repeat("01".length()) + "03-04:00[America/Montreal]\"" +
				EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
		}
	}

	/**
	 * If actual != expected but their string value is identical, make sure that the hashCode()
	 * is returned.
	 */
	@Test
	public void stringValueIsEqualDifferentHashCode()
	{
		SameToStringDifferentHashCode actual = new SameToStringDifferentHashCode();
		SameToStringDifferentHashCode expected = new SameToStringDifferentHashCode();
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new Requirements(scope).requireThat(actual, "actual").
				isEqualTo(expected);
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert (actualMessage.contains(actual.toString())) :
				"Was expecting output to contain actual value, but did not.\n" +
					" Actual:\n" + actualMessage;
			assert (actualMessage.contains("Actual.hashCode")) :
				"Was expecting output to contain Actual.hashCode, but did not.\n" +
					" Actual:\n" + actualMessage;
			assert (actualMessage.contains("Expected.hashCode")) :
				"Was expecting output to contain Expected.hashCode, but did not.\n" +
					" Actual:\n" + actualMessage;
		}
	}

	/**
	 * If actual != expected but their string value and hashCode() are identical, make sure
	 * that the identity hashCode is returned.
	 */
	@Test
	public void stringValueIsEqualDifferentIdentityHashCode()
	{
		SameToStringAndHashCodeDifferentIdentity actual = new SameToStringAndHashCodeDifferentIdentity();
		SameToStringAndHashCodeDifferentIdentity expected = new SameToStringAndHashCodeDifferentIdentity();
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new Requirements(scope).requireThat(actual, "actual").
				isEqualTo(expected);
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert (actualMessage.contains(actual.toString())) :
				"Was expecting output to contain actual value, but did not.\n" +
					" Actual:\n" + actualMessage;
			assert (actualMessage.contains("Actual.identityHashCode")) :
				"Was expecting output to contain Actual.identityHashCode, but did not.\n" +
					" Actual:\n" + actualMessage;
			assert (actualMessage.contains("Expected.identityHashCode")) :
				"Was expecting output to contain Expected.identityHashCode, but did not.\n" +
					" Actual:\n" + actualMessage;
		}
	}

	/**
	 * Ensure that DIFF notices that non-terminal lines are different when they only contain whitespace.
	 */
	@Test
	public void diffMiddleWhitespace()
	{
		String actual = """
			one

			three
			""";
		String expected = """
			one
			  \s
			three
			""";
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "actual must be equal to " + TEXT_BLOCK_DELIMITER + "\n" +
				expected + TEXT_BLOCK_DELIMITER + "." +
				"\n" +
				"\n" +
				"Actual@0  : " + TEXT_BLOCK_DELIMITER + NEWLINE_MARKER + "\n" +
				"Expected@0: " + TEXT_BLOCK_DELIMITER + NEWLINE_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual@2  : " + DIFF_PADDING.repeat(3) + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_INSERT.repeat(3) +
				DIFF_PADDING.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected@2: " + DIFF_PADDING.repeat(3) + NEWLINE_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual@4  : " + TEXT_BLOCK_DELIMITER + EOS_MARKER + "\n" +
				"Expected@4: " + TEXT_BLOCK_DELIMITER + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
		}
	}

	@Test
	public void listOfIntegers()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			List<Integer> actual = List.of(1, 2, 3, 4, 5);
			List<Integer> expected = List.of(1, 2, 9, 4, 5);
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual[0]  : 1" + EOS_MARKER + "\n" +
				"Expected[0]: 1" + EOS_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual[2]  : 3" + DIFF_PADDING.repeat(1) + EOS_MARKER + "\n" +
				"Diff       : " + DIFF_DELETE + DIFF_INSERT +
				DIFF_EQUAL.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected[2]: " + DIFF_PADDING + "9" + EOS_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual[4]  : 5" + EOS_MARKER + "\n" +
				"Expected[4]: 5" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\n**************** Actual:\n" + actualMessage;
		}
	}

	@Test
	public void listOfStrings()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			List<String> actual = List.of("1", "foo\nbar", "3");
			List<String> expected = List.of("1", "bar\nfoo", "3");
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual[0]    : \"1\"" + EOS_MARKER + "\n" +
				"Expected[0]  : \"1\"" + EOS_MARKER + "\n" +
				"\n" +
				"Actual[1]@0  : " + TEXT_BLOCK_DELIMITER + NEWLINE_MARKER + "\n" +
				"Expected[1]@0: " + TEXT_BLOCK_DELIMITER + NEWLINE_MARKER + "\n" +
				"\n" +
				"Actual[1]@1  : foo" + NEWLINE_MARKER + "\n" +
				"Diff         : " + DIFF_DELETE.repeat(("foo" + NEWLINE_MARKER).length()) + "\n" +
				"Expected[1]  : " + DIFF_PADDING.repeat(("foo" + NEWLINE_MARKER).length()) + "\n" +
				"\n" +
				"Actual[1]@2  : bar" + DIFF_PADDING.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Diff         : " + DIFF_EQUAL.repeat("bar".length()) + DIFF_INSERT.repeat(NEWLINE_MARKER.length()) +
				"\n" +
				"Expected[1]@1: bar" + NEWLINE_MARKER + "\n" +
				"\n" +
				"Actual[1]@2  : " + DIFF_PADDING.repeat("foo".length()) + TEXT_BLOCK_DELIMITER + EOS_MARKER + "\n" +
				"Diff         : " + DIFF_INSERT.repeat("foo".length()) +
				DIFF_EQUAL.repeat((TEXT_BLOCK_DELIMITER + EOS_MARKER).length()) + "\n" +
				"Expected[1]@2: foo" + TEXT_BLOCK_DELIMITER + EOS_MARKER + "\n" +
				"\n" +
				"Actual[2]    : \"3\"" + EOS_MARKER + "\n" +
				"Expected[2]  : \"3\"" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\n**************** Actual:\n" + actualMessage;
		}
	}

	@Test
	public void elementOfListLooksLikeMultipleElements()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			List<String> actual = List.of("1", "2, 3, 4", "5");
			List<String> expected = List.of("1", "2", "3", "4", "5");
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual[0]  : \"1\"" + EOS_MARKER + "\n" +
				"Expected[0]: \"1\"" + EOS_MARKER + "\n" +
				"\n" +
				"Actual[1]  : \"2, 3, 4\"" + EOS_MARKER + "\n" +
				"Diff       : " + DIFF_EQUAL.repeat("\"2".length()) + DIFF_DELETE.repeat(", 3, 4".length()) +
				DIFF_EQUAL.repeat(("\"" + EOS_MARKER).length()) + "\n" +
				"Expected[1]: \"2" + DIFF_PADDING.repeat(", 3, 4".length()) + "\"" + EOS_MARKER + "\n" +
				"\n" +
				"Actual[2]  : \"5\"" + DIFF_PADDING.repeat("\"3\"".length()) + EOS_MARKER + "\n" +
				"Diff       : " + DIFF_DELETE.repeat("\"5\"".length()) + DIFF_INSERT.repeat("\"3\"".length()) +
				DIFF_EQUAL.repeat(EOS_MARKER.length()) + "\n" +
				"Expected[2]: " + DIFF_PADDING.repeat("\"5\"".length()) + "\"3\"" + EOS_MARKER + "\n" +
				"\n" +
				"Actual     : " + DIFF_PADDING.repeat("\"4\"".length()) + EOS_MARKER + "\n" +
				"Diff       : " + DIFF_INSERT.repeat("\"4\"".length()) + DIFF_EQUAL.repeat(EOS_MARKER.length()) +
				"\n" +
				"Expected[3]: \"4\"" + EOS_MARKER + "\n" +
				"\n" +
				"Actual     : " + DIFF_PADDING.repeat("\"5\"".length()) + EOS_MARKER + "\n" +
				"Diff       : " + DIFF_INSERT.repeat("\"5\"".length()) + DIFF_EQUAL.repeat(EOS_MARKER.length()) +
				"\n" +
				"Expected[4]: \"5\"" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n\n**************** Actual:\n" + actualMessage;
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
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			TextOnly scheme = new TextOnly();
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : \"int[6" + scheme.getPaddingMarker() + "]\"" + EOS_MARKER + "\n" +
				"Diff    : " + DIFF_EQUAL.repeat("\"int[".length()) + DIFF_DELETE + DIFF_INSERT +
				DIFF_EQUAL.repeat("]\"".length() + EOS_MARKER.length()) + "\n" +
				"Expected: \"int[" + scheme.getPaddingMarker() + "5]\"" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
		}
	}

	/**
	 * Ensures that DiffGenerator.ReduceDeltasPerWord does not modify EQUAL deltas between matches. Meaning,
	 * it should not collapse "-same-" into the [DELETE, INSERT] pair associated with "different"/"maybe".
	 */
	@Test
	public void equalDeltaAfterReduceDeltasPerWord()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "different-same-different";
			String expected = "maybe-same-maybe";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : \"different" + DIFF_PADDING.repeat("\"maybe".length()) +
				"-same-different\"" + DIFF_PADDING.repeat("maybe\"".length()) + EOS_MARKER +
				"\n" +
				"Diff    : " + DIFF_DELETE.repeat("\"different".length()) +
				DIFF_INSERT.repeat("\"maybe".length()) +
				DIFF_EQUAL.repeat("-same-".length()) +
				DIFF_DELETE.repeat("different\"".length()) +
				DIFF_INSERT.repeat("maybe\"".length()) +
				DIFF_EQUAL.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected: " + DIFF_PADDING.repeat("\"different".length()) + "\"maybe-same-" +
				DIFF_PADDING.repeat("different\"".length()) + "maybe\"" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
		}
	}

	/**
	 * When processing DELETE "same\nactual" followed by INSERT "same\nexpected", ensure that actual and
	 * expected keep track of different "diff" line numbers. Otherwise, the DELETE advances to the next line
	 * and INSERT updates the diff of the wrong line number. We end up with:
	 *
	 * <pre><code>
	 * Actual@1  : same\n
	 * Diff      : ------
	 * Expected  :
	 *
	 * Actual@2  : actual
	 * Diff      : ------++++++
	 * Expected@1:       same\n
	 * </code></pre>
	 * <p>
	 * instead of:
	 *
	 * <pre><code>
	 * Actual    : same\n
	 * Expected  : same\n
	 * </code></pre>
	 */
	@Test
	public void independentDiffLineNumbers()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "actual\nsame\nactual actual";
			String expected = "expected\nsame\nexpected expected";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@0  : " + TEXT_BLOCK_DELIMITER + NEWLINE_MARKER + "\n" +
				"Expected@0: " + TEXT_BLOCK_DELIMITER + NEWLINE_MARKER + "\n" +
				"\n" +
				"Actual@1  : actual" + DIFF_PADDING.repeat("expected".length()) + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat("actual".length()) +
				DIFF_INSERT.repeat("expected".length()) + DIFF_EQUAL.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected@1: " + DIFF_PADDING.repeat("actual".length()) + "expected" + NEWLINE_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual@3  : actual" + DIFF_PADDING.repeat("expected".length()) + DIFF_EQUAL + "actual" +
				TEXT_BLOCK_DELIMITER + DIFF_PADDING.repeat(("expected" + TEXT_BLOCK_DELIMITER).length()) +
				EOS_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE.repeat("actual".length()) + DIFF_INSERT.repeat("expected".length()) +
				DIFF_EQUAL + DIFF_DELETE.repeat(("actual" + TEXT_BLOCK_DELIMITER).length()) +
				DIFF_INSERT.repeat(("expected" + TEXT_BLOCK_DELIMITER).length()) +
				DIFF_EQUAL.repeat(NEWLINE_MARKER.length()) + "\n" +
				"Expected@3: " + DIFF_PADDING.repeat("actual".length()) + "expected" + DIFF_PADDING +
				DIFF_PADDING.repeat(("actual" + TEXT_BLOCK_DELIMITER).length()) + "expected" +
				TEXT_BLOCK_DELIMITER + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
		}
	}

	/**
	 * Ensures that "expected" is included in the error message when it is shorter than the terminal width.
	 */
	@Test
	public void expectedShorterThanTerminalWidth()
	{
		String expected = "expected";
		try (ApplicationScope scope = new TestApplicationScope(NONE,
			"actual must be equal to \"expected\".".length() + 1))
		{
			String actual = "actual";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert (actualMessage.contains("must be equal to \"" + expected + "\"")) :
				"Actual:\n" + actualMessage;
		}
	}

	/**
	 * Ensures that "expected" is excluded from the error message when it is equal to the terminal width.
	 */
	@Test
	public void expectedEqualToTerminalWidth()
	{
		String expected = "expected";
		try (ApplicationScope scope = new TestApplicationScope(NONE,
			"actual must be equal to expected.".length()))
		{
			String actual = "actual";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert (!actualMessage.contains("must be equal to " + expected)) : "Actual:\n" + actualMessage;
		}
	}

	/**
	 * Ensures that "expected" is excluded from the error message when it is equal to the terminal width.
	 */
	@Test
	public void expectedLongerThanTerminalWidth()
	{
		String expected = "expected";
		try (ApplicationScope scope = new TestApplicationScope(NONE,
			"actual must be equal to expected.".length() - 1))
		{
			String actual = "actual";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert (!actualMessage.contains("must be equal to " + expected)) : "Actual:\n" + actualMessage;
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
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			Writer16Colors scheme = new Writer16Colors();
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : " + scheme.decorateEqualText("\"int[") +
				scheme.decorateDeletedText("6") + scheme.decoratePadding(scheme.getPaddingMarker()) +
				scheme.decorateEqualText("]\"") + EOS_MARKER + scheme.stopDecoration() + "\n" +
				"Expected: " + scheme.decorateEqualText("\"int[") +
				scheme.decoratePadding(scheme.getPaddingMarker()) +
				scheme.decorateInsertedText("5") + scheme.decorateEqualText("]\"") + EOS_MARKER +
				scheme.stopDecoration();
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
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
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			Writer8Colors scheme = new Writer8Colors();
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : " + scheme.decorateEqualText("\"int[") +
				scheme.decorateDeletedText("6") + scheme.decoratePadding(scheme.getPaddingMarker()) +
				scheme.decorateEqualText("]\"") + EOS_MARKER + scheme.stopDecoration() + "\n" +
				"Expected: " + scheme.decorateEqualText("\"int[") +
				scheme.decoratePadding(scheme.getPaddingMarker()) + scheme.decorateInsertedText("5") +
				scheme.decorateEqualText("]\"") + EOS_MARKER + scheme.stopDecoration();
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
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
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			Writer256Colors scheme = new Writer256Colors();
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : " + scheme.decorateEqualText("\"int[") +
				scheme.decorateDeletedText("6") + scheme.decoratePadding(scheme.getPaddingMarker()) +
				scheme.decorateEqualText("]\"") + EOS_MARKER + scheme.stopDecoration() + "\n" +
				"Expected: " + scheme.decorateEqualText("\"int[") +
				scheme.decoratePadding(scheme.getPaddingMarker()) + scheme.decorateInsertedText("5") +
				scheme.decorateEqualText("]\"") + EOS_MARKER + scheme.stopDecoration();
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
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
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			Writer16MillionColors scheme = new Writer16MillionColors();
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : " + scheme.decorateEqualText("\"int[") +
				scheme.decorateDeletedText("6") + scheme.decoratePadding(scheme.getPaddingMarker()) +
				scheme.decorateEqualText("]\"") + EOS_MARKER + scheme.stopDecoration() + "\n" +
				"Expected: " + scheme.decorateEqualText("\"int[") +
				scheme.decoratePadding(scheme.getPaddingMarker()) +
				scheme.decorateInsertedText("5") + scheme.decorateEqualText("]\"") + EOS_MARKER +
				scheme.stopDecoration();
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
		}
	}

	/**
	 * Make sure that we skip duplicate lines even when the diff contains colors.
	 */
	@Test
	public void emptyLineNumber_16Colors()
	{
		try (ApplicationScope scope = new TestApplicationScope(XTERM_16_COLORS))
		{
			String actual = "foo\nbar";
			String expected = "bar";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
			fail("Expected method to throw exception");
		}
		catch (IllegalArgumentException e)
		{
			Writer16Colors scheme = new Writer16Colors();
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@0  : " + scheme.decorateEqualText("\"") +
				scheme.decorateDeletedText("\"\"" + NEWLINE_MARKER) + scheme.stopDecoration() + "\n" +
				"Expected@0: " + scheme.decorateEqualText("\"") +
				scheme.decoratePadding(scheme.getPaddingMarker().repeat(("\"\"" + NEWLINE_MARKER).length())) +
				scheme.stopDecoration() + "\n" +
				"\n" +
				"Actual@1  : " + scheme.decorateDeletedText("foo" + NEWLINE_MARKER) + scheme.stopDecoration() +
				"\n" +
				"Expected  : " + scheme.decoratePadding(
				scheme.getPaddingMarker().repeat(("foo" + NEWLINE_MARKER).length())) + scheme.stopDecoration() +
				"\n" +
				"\n" +
				"Actual@2  : " + scheme.decorateEqualText("bar\"") + scheme.decorateDeletedText("\"\"") +
				scheme.decorateEqualText(EOS_MARKER) + scheme.stopDecoration() + "\n" +
				"Expected@0: " + scheme.decorateEqualText("bar\"") + scheme.decoratePadding(
				scheme.getPaddingMarker().repeat("\"\"".length())) + scheme.decorateEqualText(EOS_MARKER) +
				scheme.stopDecoration();
			assert (actualMessage.contains(expectedMessage)) : "Expected:\n" + expectedMessage +
				"\n**************** Actual:\n" + actualMessage;
		}
	}
}
