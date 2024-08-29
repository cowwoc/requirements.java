/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java;

import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.List;

import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class StringTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new TestValidatorsImpl(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new TestValidatorsImpl(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test
	public void isTrimmed()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "abc";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isTrimmed();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isTrimmed_LeadingWhitespace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " abc";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isTrimmed();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isTrimmed_TrailingWhitespace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "abc ";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isTrimmed();
		}
	}

	@Test
	public void isStripped()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "abc";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isStripped();
		}
	}

	@Test
	public void isBlank()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " \t ";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBlank();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBlank_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " a ";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBlank();
		}
	}

	@Test
	public void isNotBlank()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " a ";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotBlank();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotBlank_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " \t ";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotBlank();
		}
	}

	@Test
	public void startsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = prefix + "1234";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").startsWith(prefix);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void startsWith_Null()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234home";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").startsWith(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void startsWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = "1234" + prefix;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").startsWith(prefix);
		}
	}

	@Test
	public void doesNotStartWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = "1234" + prefix;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").doesNotStartWith(prefix);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void doesNotStartWith_Null()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "home1234";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").doesNotStartWith(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotStartWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = prefix + "1234";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").doesNotStartWith(prefix);
		}
	}

	@Test
	public void endsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = "1234" + suffix;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").endsWith(suffix);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void endsWith_Null()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "home1234";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").endsWith(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void endsWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = suffix + "1234";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").endsWith(suffix);
		}
	}

	@Test
	public void doesNotEndWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = suffix + "1234";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").doesNotEndWith(suffix);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void doesNotEndWith_Null()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "home1234";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").doesNotEndWith(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotEndWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = "1234" + suffix;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").doesNotEndWith(suffix);
		}
	}

	@Test
	public void contains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "cat";
			String actual = "my " + expected + " is the best";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").contains(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "cat";
			String actual = "my dog is the best";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").contains(expected);
		}
	}

	@Test
	public void matches()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "^.*(?:\\s)dog(?:\\s).*$";
			String actual = "my dog is the best";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").matches(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void matches_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "^.*(?:\\s)cat(?:\\s).*$";
			String actual = "my dog is the best";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").matches(expected);
		}
	}

	@Test
	public void doesNotContain()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String unwanted = "cat";
			String actual = "my dog is the best";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").doesNotContain(unwanted);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String unwanted = "cat";
			String actual = "my " + unwanted + " is the best";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").doesNotContain(unwanted);
		}
	}

	@Test
	public void lengthIsEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").length().
				isEqualTo(actual.length());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").length().
				isEqualTo(actual.length() + 1);
		}
	}

	@Test
	public void lengthIsNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").length().
				isNotEqualTo(actual.length() + 1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").length().
				isNotEqualTo(actual.length());
		}
	}

	@Test
	public void lengthIsBetween()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").length().isBetween(1, 11);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsBetween_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").length().isBetween(1, 10);
		}
	}

	@Test
	public void lengthIsBetweenClosed()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").length().isBetween(1, true, 10, true);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsBetweenClosed_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").length().isBetween(1, true, 9, true);
		}
	}

	@Test
	public void multipleFailuresNullIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be empty");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isEmpty().isNotEqualTo("someValue").elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not be empty");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isNotEmpty().isNotEqualTo("someValue").elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsTrimmed()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not contain leading or trailing whitespace");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isTrimmed().isNotEqualTo(5).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullLength()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not contain 5 characters");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				length().isNotEqualTo(5).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullStartsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must start with \"prefix\"", """
					"actual" must be equal to "equal".
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				startsWith("prefix").isEqualTo("equal").elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotStartWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not start with \"prefix\"", """
					"actual" must be equal to "equal".
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				doesNotStartWith("prefix").isEqualTo("equal").elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullEndsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must end with \"suffix\"", """
					"actual" must be equal to "equal".
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				endsWith("suffix").isEqualTo("equal").elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotEndWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not end with \"suffix\"", """
					"actual" must be equal to "equal".
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				doesNotEndWith("suffix").isEqualTo("equal").elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must contain \"value\"", """
					"actual" must be equal to "equal".
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				contains("value").isEqualTo("equal").elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContain()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not contain \"value\"", """
					"actual" must be equal to "equal".
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				doesNotContain("value").isEqualTo("equal").elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}