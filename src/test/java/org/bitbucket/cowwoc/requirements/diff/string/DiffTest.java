/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import com.google.common.base.Strings;
import org.bitbucket.cowwoc.requirements.Requirements;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.DIFF_DELETE;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.DIFF_EQUAL;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.DIFF_INSERT;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.EOS_MARKER;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.NEWLINE_MARKER;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class DiffTest
{
	@Test
	public void diffArraySize()
	{
		System.setProperty("org.bitbucket.cowwoc.requirements.terminal", "NONE");
		try
		{
			String actual = "int[6]";
			String expected = "int[5]";
			Requirements.requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : int[6 ]\n" +
				"Diff    : " + Strings.repeat(DIFF_EQUAL, 4) + DIFF_DELETE + DIFF_INSERT +
				DIFF_EQUAL + "\n" +
				"Expected: int[ 5]";
			assert (actualMessage.contains(expectedMessage)): actualMessage;
		}
	}

	@Test
	public void diffInsertThenDelete()
	{
		System.setProperty("org.bitbucket.cowwoc.requirements.terminal", "NONE");
		try
		{
			String actual = "actual";
			String expected = "expected";
			Requirements.requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : actual\n" +
				"Diff    : " + Strings.repeat(DIFF_DELETE, "actual".length()) +
				Strings.repeat(DIFF_INSERT, "expected".length()) + "\n" +
				"Expected:       expected";
			assert (actualMessage.contains(expectedMessage)): actualMessage;
		}
	}

	@Test
	public void diffMissingWhitespace()
	{
		System.setProperty("org.bitbucket.cowwoc.requirements.terminal", "NONE");
		try
		{
			String actual = "\"key\": \"value \"";
			String expected = "\"key\": \"value\"";
			Requirements.requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : \"key\": \"value \"\n" +
				"Diff    : " + Strings.repeat(DIFF_EQUAL, 13) + DIFF_DELETE + DIFF_EQUAL + "\n" +
				"Expected: \"key\": \"value \"";
			assert (actualMessage.contains(expectedMessage)): actualMessage;
		}
	}

	@Test
	public void diffNewlinePrefix()
	{
		System.setProperty("org.bitbucket.cowwoc.requirements.terminal", "NONE");
		try
		{
			String actual = "\nactual";
			String expected = "expected";
			Requirements.requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@1  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_DELETE, NEWLINE_MARKER.length()) + "\n" +
				"Expected  : \n" +
				"\n" +
				"Actual@2  : actual" + Strings.repeat(TextOnly.PADDING_MARKER, "expected".length()) +
				EOS_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_DELETE, "actual".length()) +
				Strings.repeat(DIFF_INSERT, "expected".length()) +
				Strings.repeat(DIFF_EQUAL, EOS_MARKER.length()) + "\n" +
				"Expected@1: " + Strings.repeat(TextOnly.PADDING_MARKER, "actual".length()) + "expected" +
				EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)): actualMessage;
		}
	}

	@Test
	public void diffNewlinePostfix()
	{
		System.setProperty("org.bitbucket.cowwoc.requirements.terminal", "NONE");
		try
		{
			String actual = "actual\n";
			String expected = "expected";
			Requirements.requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@1  : actual" + NEWLINE_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_DELETE, "actual".length() + NEWLINE_MARKER.length()) +
				"\n" +
				"Expected  : \n" +
				"\n" +
				"Actual@2  : " + Strings.repeat(TextOnly.PADDING_MARKER, "expected".length()) + EOS_MARKER +
				"\n" +
				"Diff      : " + Strings.repeat(DIFF_INSERT, "expected".length()) +
				Strings.repeat(DIFF_EQUAL, EOS_MARKER.length()) + "\n" +
				"Expected@1: expected" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)): actualMessage;
		}
	}

	@Test
	public void matchAcrossLines()
	{
		System.setProperty("org.bitbucket.cowwoc.requirements.terminal", "NONE");
		try
		{
			String actual = "\n\nvalue";
			String expected = "value";
			Requirements.requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@1  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_DELETE, NEWLINE_MARKER.length()) + "\n" +
				"Expected  : \n" +
				"\n" +
				"Actual@2  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_DELETE, NEWLINE_MARKER.length()) + "\n" +
				"Expected  : \n" +
				"\n" +
				"Actual@3  : value" + EOS_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_EQUAL, "value".length() + EOS_MARKER.length()) + "\n" +
				"Expected@1: value" + EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)): actualMessage;
		}
	}

	@Test
	public void skipDuplicateLinesTest()
	{
		System.setProperty("org.bitbucket.cowwoc.requirements.terminal", "NONE");
		try
		{
			String actual = "1\n2\n3\n4\n5";
			String expected = "1\n2\n9\n4\n5";
			Requirements.requireThat(actual, "actual").isEqualTo(expected);
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
				"Actual@3  : 3" + TextOnly.PADDING_MARKER + NEWLINE_MARKER + "\n" +
				"Diff      : " + DIFF_DELETE + DIFF_INSERT +
				Strings.repeat(DIFF_EQUAL, NEWLINE_MARKER.length()) + "\n" +
				"Expected@3: " + TextOnly.PADDING_MARKER + "9" + NEWLINE_MARKER + "\n" +
				"\n" +
				"[...]\n" +
				"\n" +
				"Actual@5  : 5\\0\n" +
				"Diff      : " + Strings.repeat(DIFF_EQUAL, "5".length() + EOS_MARKER.length()) + "\n" +
				"Expected@5: 5\\0";
			assert (actualMessage.contains(expectedMessage)): actualMessage;
		}
	}

	@Test
	public void charlesTest()
	{
		System.setProperty("org.bitbucket.cowwoc.requirements.terminal", "NONE");
		try
		{
			String actual = "The dog is brown";
			String expected = "The fox is down";
			Requirements.requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : The dog" +
				Strings.repeat(TextOnly.PADDING_MARKER, "fox".length()) + " is br" + TextOnly.PADDING_MARKER +
				"own\n" +
				"Diff    : ====   ^^^====  ^===\n" +
				"Expected: The " + Strings.repeat(TextOnly.PADDING_MARKER, "dog".length()) + "fox is " +
				Strings.repeat(DIFF_DELETE, "br".length()) + "down";
			assert (actualMessage.contains(expectedMessage)): actualMessage;
		}
	}
}
