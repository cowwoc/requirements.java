/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import org.bitbucket.cowwoc.requirements.RequirementVerifier;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.DIFF_DELETE;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.DIFF_EQUAL;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.DIFF_INSERT;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.EOS_MARKER;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.NEWLINE_MARKER;
import static org.bitbucket.cowwoc.requirements.diff.string.TextOnly.PADDING_MARKER;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.scope.TestSingletonScope;
import org.bitbucket.cowwoc.requirements.util.Strings;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class DiffTest
{
	@Test
	public void diffArraySize()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "int[6]";
			String expected = "int[5]";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo(expected);
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
	public void diffArraySize_XTerm_16Color()
	{
		try (SingletonScope scope = new TestSingletonScope(TerminalType.XTERM_16COLOR))
		{
			String actual = "int[6]";
			String expected = "int[5]";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			XTerm16Color scheme = new XTerm16Color("actual", "expected");

			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : int[" + scheme.deleteColor + "6" +
				scheme.paddingColor + scheme.paddingMarker + scheme.resetColor + "]\n" +
				"Expected: int[" + scheme.paddingColor + scheme.paddingMarker +
				scheme.insertColor + "5" + scheme.resetColor + "]";
			assert (actualMessage.contains(expectedMessage)): "expected:\n" + expectedMessage +
				"\nactual:\n" + actualMessage;
		}
	}

	@Test
	public void diffArraySize_XTerm_256Color()
	{
		try (SingletonScope scope = new TestSingletonScope(TerminalType.XTERM_256COLOR))
		{
			String actual = "int[6]";
			String expected = "int[5]";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			XTerm256Color scheme = new XTerm256Color("actual", "expected");

			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : int[" + scheme.deleteColor + "6" +
				scheme.paddingColor + scheme.paddingMarker + scheme.resetColor + "]\n" +
				"Expected: int[" + scheme.paddingColor + scheme.paddingMarker +
				scheme.insertColor + "5" + scheme.resetColor + "]";
			assert (actualMessage.contains(expectedMessage)): "expected:\n" + expectedMessage +
				"\nactual:\n" + actualMessage;
		}
	}

	@Test
	public void diffInsertThenDelete()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "actual";
			String expected = "expected";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo(expected);
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
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "\"key\": \"value \"";
			String expected = "\"key\": \"value\"";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo(expected);
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
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "\nactual";
			String expected = "expected";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@1  : " + NEWLINE_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_DELETE, NEWLINE_MARKER.length()) + "\n" +
				"Expected  : \n" +
				"\n" +
				"Actual@2  : actual" + Strings.repeat(PADDING_MARKER, "expected".length()) +
				EOS_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_DELETE, "actual".length()) +
				Strings.repeat(DIFF_INSERT, "expected".length()) +
				Strings.repeat(DIFF_EQUAL, EOS_MARKER.length()) + "\n" +
				"Expected@1: " + Strings.repeat(PADDING_MARKER, "actual".length()) + "expected" +
				EOS_MARKER;
			assert (actualMessage.contains(expectedMessage)): actualMessage;
		}
	}

	@Test
	public void diffNewlinePostfix()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "actual\n";
			String expected = "expected";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual@1  : actual" + NEWLINE_MARKER + "\n" +
				"Diff      : " + Strings.repeat(DIFF_DELETE, "actual".length() + NEWLINE_MARKER.length()) +
				"\n" +
				"Expected  : \n" +
				"\n" +
				"Actual@2  : " + Strings.repeat(PADDING_MARKER, "expected".length()) + EOS_MARKER +
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
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "\n\nvalue";
			String expected = "value";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo(expected);
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
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "1\n2\n3\n4\n5";
			String expected = "1\n2\n9\n4\n5";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo(expected);
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
			assert (actualMessage.contains(expectedMessage)): actualMessage;
		}
	}

	/**
	 * A test suggested by Charles Drolet.
	 */
	@Test
	public void charlesTest()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "The dog is brown";
			String expected = "The fox is down";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			String expectedMessage = "Actual  : The dog" +
				Strings.repeat(PADDING_MARKER, "fox".length()) + " is br" + PADDING_MARKER + "own\n" +
				"Diff    : ====   ^^^====  ^===\n" +
				"Expected: The " + Strings.repeat(PADDING_MARKER, "dog".length()) + "fox is " +
				Strings.repeat(DIFF_DELETE, "br".length()) + "down";
			assert (actualMessage.contains(expectedMessage)): actualMessage;
		}
	}

	@Test
	public void lastConsecutiveIndexOf()
	{
		int result = AbstractDiffWriter.lastConsecutiveIndexOf("textex", " ");
		assert (result == -1): result;

		result = AbstractDiffWriter.lastConsecutiveIndexOf("  text", " ");
		assert (result == -1): result;

		result = AbstractDiffWriter.lastConsecutiveIndexOf("text  ", " ");
		assert (result == 4): result;

		result = AbstractDiffWriter.lastConsecutiveIndexOf("      ", " ");
		assert (result == 0): result;
	}
}
