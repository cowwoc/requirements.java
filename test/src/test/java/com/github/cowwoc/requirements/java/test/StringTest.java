/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.test;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class StringTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new Requirements(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new Requirements(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "";
			new Requirements(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new Requirements(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test
	public void trimIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new Requirements(scope).requireThat(actual, "actual").trim().isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void trimIsEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new Requirements(scope).requireThat(actual, "actual").trim().isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new Requirements(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "";
			new Requirements(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test
	public void trimIsNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new Requirements(scope).requireThat(actual, "actual").trim().isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void trimIsNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new Requirements(scope).requireThat(actual, "actual").trim().isNotEmpty();
		}
	}

	@Test
	public void isTrimmed()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "abc";
			new Requirements(scope).requireThat(actual, "actual").isTrimmed();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isTrimmed_LeadingWhitespace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " abc";
			new Requirements(scope).requireThat(actual, "actual").isTrimmed();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isTrimmed_TrailingWhitespace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "abc ";
			new Requirements(scope).requireThat(actual, "actual").isTrimmed();
		}
	}

	@Test
	public void isBlank()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " \t ";
			new Requirements(scope).requireThat(actual, "actual").isBlank();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBlank_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " a ";
			new Requirements(scope).requireThat(actual, "actual").isBlank();
		}
	}

	@Test
	public void isNotBlank()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " a ";
			new Requirements(scope).requireThat(actual, "actual").isNotBlank();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotBlank_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " \t ";
			new Requirements(scope).requireThat(actual, "actual").isNotBlank();
		}
	}

	@Test
	public void startsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = prefix + "1234";
			new Requirements(scope).requireThat(actual, "actual").startsWith(prefix);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void startsWith_Null()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234home";
			new Requirements(scope).requireThat(actual, "actual").startsWith(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void startsWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = "1234" + prefix;
			new Requirements(scope).requireThat(actual, "actual").startsWith(prefix);
		}
	}

	@Test
	public void doesNotStartWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = "1234" + prefix;
			new Requirements(scope).requireThat(actual, "actual").doesNotStartWith(prefix);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void doesNotStartWith_Null()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "home1234";
			new Requirements(scope).requireThat(actual, "actual").doesNotStartWith(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotStartWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = prefix + "1234";
			new Requirements(scope).requireThat(actual, "actual").doesNotStartWith(prefix);
		}
	}

	@Test
	public void endsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = "1234" + suffix;
			new Requirements(scope).requireThat(actual, "actual").endsWith(suffix);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void endsWith_Null()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "home1234";
			new Requirements(scope).requireThat(actual, "actual").endsWith(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void endsWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = suffix + "1234";
			new Requirements(scope).requireThat(actual, "actual").endsWith(suffix);
		}
	}

	@Test
	public void doesNotEndWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = suffix + "1234";
			new Requirements(scope).requireThat(actual, "actual").doesNotEndWith(suffix);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void doesNotEndWith_Null()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "home1234";
			new Requirements(scope).requireThat(actual, "actual").doesNotEndWith(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotEndWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = "1234" + suffix;
			new Requirements(scope).requireThat(actual, "actual").doesNotEndWith(suffix);
		}
	}

	@Test
	public void contains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "cat";
			String actual = "my " + expected + " is the best";
			new Requirements(scope).requireThat(actual, "actual").contains(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "cat";
			String actual = "my dog is the best";
			new Requirements(scope).requireThat(actual, "actual").contains(expected);
		}
	}

	@Test
	public void doesNotContain()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "cat";
			String actual = "my dog is the best";
			new Requirements(scope).requireThat(actual, "actual").doesNotContain(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "cat";
			String actual = "my " + expected + " is the best";
			new Requirements(scope).requireThat(actual, "actual").doesNotContain(expected);
		}
	}

	@Test
	public void lengthIsEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new Requirements(scope).requireThat(actual, "actual").length().
				isEqualTo(actual.length());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new Requirements(scope).requireThat(actual, "actual").length().
				isEqualTo(actual.length() + 1);
		}
	}

	@Test
	public void lengthIsNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new Requirements(scope).requireThat(actual, "actual").length().
				isNotEqualTo(actual.length() + 1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new Requirements(scope).requireThat(actual, "actual").length().
				isNotEqualTo(actual.length());
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			String actual = null;
			new Requirements(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatAsInetAddressNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new Requirements(scope).validateThat(actual, "actual").asInetAddress(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatAsUriNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new Requirements(scope).validateThat(actual, "actual").asUri(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatAsUrlNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new Requirements(scope).validateThat(actual, "actual").asUrl(null);
		}
	}

	@Test
	public void validateThatNullIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isEmpty().isNotEqualTo("someValue").getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isNotEmpty().isNotEqualTo("someValue").getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullTrim()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				trim().isNotEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsTrimmed()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isTrimmed().isNotEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullLength()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				length().isNotEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullAsInetAddress()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = Collections.singletonList("actual must be an InetAddress.\n" +
				"Actual: null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				asInetAddress().isIpV4().getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullAsUri()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = Collections.singletonList("actual must be a URI.\n" +
				"Actual: null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				asUri().isAbsolute().getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullAsUrl()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = Collections.singletonList("actual must be a URL.\n" +
				"Actual: null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				asUrl().isEqualTo("notEqual").getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullStartsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				startsWith("prefix").isEqualTo("notEqual").getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullDoesNotStartWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				doesNotStartWith("prefix").isEqualTo("notEqual").getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullEndsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				endsWith("suffix").isEqualTo("notEqual").getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullDoesNotEndWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				doesNotEndWith("suffix").isEqualTo("notEqual").getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullContains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				contains("value").isEqualTo("notEqual").getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullDoesNotContain()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				doesNotContain("value").isEqualTo("notEqual").getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void falseStringAsBoolean()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "false";
			new Requirements(scope).requireThat(actual, "actual").asBoolean().isEqualTo(false);
		}
	}

	@Test
	public void stringAsBoolean_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "false";
			new Requirements(scope).requireThat(actual, "actual").asBoolean().isEqualTo(true);
			assert (false) : "Expected verifier to throw an exception";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert (!actualMessage.contains("Diff")) :
				"Wasn't expecting boolean equals() to return diff.\n" +
					"\nActual:\n" + actualMessage;
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatAsBooleanNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new Requirements(scope).validateThat(actual, "actual").asBoolean(null);
		}
	}
}
