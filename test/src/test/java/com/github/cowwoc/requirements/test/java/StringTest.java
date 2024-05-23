/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.util.List;

import static com.github.cowwoc.requirements.java.DefaultJavaValidators.requireThat;
import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEmpty();
		}
	}

	@Test
	public void trimIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").trim().isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void trimIsEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").trim().isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotEmpty();
		}
	}

	@Test
	public void trimIsNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").trim().isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void trimIsNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").trim().isNotEmpty();
		}
	}

	@Test
	public void isTrimmed()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "abc";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isTrimmed();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isTrimmed_LeadingWhitespace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " abc";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isTrimmed();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isTrimmed_TrailingWhitespace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "abc ";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isTrimmed();
		}
	}

	@Test
	public void stripIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").strip().isEmpty();
		}
	}

	@Test
	public void isStripped()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "abc";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isStripped();
		}
	}

	@Test
	public void isBlank()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " \t ";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBlank();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBlank_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " a ";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBlank();
		}
	}

	@Test
	public void isNotBlank()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " a ";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotBlank();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotBlank_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " \t ";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotBlank();
		}
	}

	@Test
	public void startsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = prefix + "1234";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").startsWith(prefix);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void startsWith_Null()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234home";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").startsWith(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void startsWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = "1234" + prefix;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").startsWith(prefix);
		}
	}

	@Test
	public void doesNotStartWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = "1234" + prefix;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotStartWith(prefix);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void doesNotStartWith_Null()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "home1234";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotStartWith(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotStartWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = prefix + "1234";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotStartWith(prefix);
		}
	}

	@Test
	public void endsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = "1234" + suffix;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").endsWith(suffix);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void endsWith_Null()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "home1234";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").endsWith(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void endsWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = suffix + "1234";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").endsWith(suffix);
		}
	}

	@Test
	public void doesNotEndWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = suffix + "1234";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotEndWith(suffix);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void doesNotEndWith_Null()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "home1234";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotEndWith(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotEndWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = "1234" + suffix;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotEndWith(suffix);
		}
	}

	@Test
	public void contains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "cat";
			String actual = "my " + expected + " is the best";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").contains(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "cat";
			String actual = "my dog is the best";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").contains(expected);
		}
	}

	@Test
	public void matches()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "^.*(?:\\s)dog(?:\\s).*$";
			String actual = "my dog is the best";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").matches(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void matches_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "^.*(?:\\s)cat(?:\\s).*$";
			String actual = "my dog is the best";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").matches(expected);
		}
	}

	@Test
	public void doesNotContain()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String unwanted = "cat";
			String actual = "my dog is the best";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotContain(unwanted);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String unwanted = "cat";
			String actual = "my " + unwanted + " is the best";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").doesNotContain(unwanted);
		}
	}

	@Test
	public void lengthIsEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().
				isEqualTo(actual.length());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().
				isEqualTo(actual.length() + 1);
		}
	}

	@Test
	public void lengthIsNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().
				isNotEqualTo(actual.length() + 1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().
				isNotEqualTo(actual.length());
		}
	}

	@Test
	public void lengthIsBetween()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().isBetween(1, 11);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsBetween_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().isBetween(1, 10);
		}
	}

	@Test
	public void lengthIsBetweenClosed()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().isBetween(1, true, 10, true);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsBetweenClosed_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").length().isBetween(1, true, 9, true);
		}
	}

	@Test
	public void multipleFailuresNullIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"""
					"Actual" may not be equal to "someValue"\
					""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isEmpty().isNotEqualTo("someValue").elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"""
					"Actual" may not be equal to "someValue"\
					""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNotEmpty().isNotEqualTo("someValue").elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullTrim()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"Actual.trim() may not be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				trim().isNotEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsTrimmed()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" may not be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isTrimmed().isNotEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullStrip()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"Actual.strip() may not be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				strip().isNotEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullLength()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" may not contain 5 characters");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				length().isNotEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullStartsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"""
					"Actual" must be equal to "notEqual"\
					""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				startsWith("prefix").isEqualTo("notEqual").elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotStartWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"""
					"Actual" must be equal to "notEqual"\
					""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				doesNotStartWith("prefix").isEqualTo("notEqual").elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullEndsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"""
					"Actual" must be equal to "notEqual"\
					""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				endsWith("suffix").isEqualTo("notEqual").elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotEndWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"""
					"Actual" must be equal to "notEqual"\
					""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				doesNotEndWith("suffix").isEqualTo("notEqual").elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"""
					"Actual" must be equal to "notEqual"\
					""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				contains("value").isEqualTo("notEqual").elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullDoesNotContain()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"""
					"Actual" must be equal to "notEqual"\
					""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				doesNotContain("value").isEqualTo("notEqual").elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void stringAsByte()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			byte expected = (byte) 1;
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveByte().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsByte_Overflow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf(Byte.MAX_VALUE + 1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveByte();
		}
	}

	@Test
	public void stringAsByte_getValueOrDefault()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf(Byte.MAX_VALUE + 1);
			byte value = new TestValidatorsImpl(scope).checkIf(actual, "Actual").asPrimitiveByte().
				getValueOrDefault((byte) 5);
			requireThat(value, "value").isEqualTo((byte) 5);
		}
	}

	@Test
	public void stringAsByte_getValueOrNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf(Byte.MAX_VALUE + 1);
			Byte value = new TestValidatorsImpl(scope).checkIf(actual, "Actual").asPrimitiveByte().asBoxed().
				getValueOrDefault(null);
			requireThat(value, "value").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsByte_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveByte();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsByte()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveByte();
		}
	}

	@Test
	public void stringAsShort()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			short expected = (short) 1;
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveShort().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsShort_Overflow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf(Short.MAX_VALUE + 1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveShort();
		}
	}

	@Test
	public void stringAsShort_getValueOrDefault()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf(Short.MAX_VALUE + 1);
			short value = new TestValidatorsImpl(scope).checkIf(actual, "Actual").asPrimitiveShort().
				getValueOrDefault((short) 10);
			requireThat(value, "value").isEqualTo((short) 10);
		}
	}

	@Test
	public void stringAsShort_getValueOrNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf(Short.MAX_VALUE + 1);
			Short value = new TestValidatorsImpl(scope).checkIf(actual, "Actual").asPrimitiveShort().asBoxed().
				getValueOrDefault(null);
			requireThat(value, "value").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsShort_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveShort();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsShort()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveShort();
		}
	}

	@Test
	public void stringAsInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer expected = Short.MAX_VALUE + 1;
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveInteger().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsInteger_Overflow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf((long) Integer.MAX_VALUE + 1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveInteger();
		}
	}

	@Test
	public void stringAsInteger_getValueOrDefault()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf((long) Integer.MAX_VALUE + 1);
			int value = new TestValidatorsImpl(scope).checkIf(actual, "Actual").asPrimitiveInteger().
				getValueOrDefault(15);
			requireThat(value, "value").isEqualTo(15);
		}
	}

	@Test
	public void stringAsInteger_getValueOrNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf((long) Integer.MAX_VALUE + 1);
			Integer value = new TestValidatorsImpl(scope).checkIf(actual, "Actual").asPrimitiveInteger().asBoxed().
				getValueOrDefault(null);
			requireThat(value, "value").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsInteger_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveInteger();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveInteger();
		}
	}

	@Test
	public void stringAsLong()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Long expected = (long) Integer.MAX_VALUE + 1;
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveLong().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsLong_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveLong();
		}
	}

	@Test
	public void stringAsLong_getValueOrDefault()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			long value = new TestValidatorsImpl(scope).checkIf(actual, "Actual").asPrimitiveLong().
				getValueOrDefault(20);
			requireThat(value, "value").isEqualTo(20);
		}
	}

	@Test
	public void stringAsLong_getValueOrNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			Long value = new TestValidatorsImpl(scope).checkIf(actual, "Actual").asPrimitiveLong().asBoxed().
				getValueOrDefault(null);
			requireThat(value, "value").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsLong()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveLong();
		}
	}

	@Test
	public void stringAsFloat()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float expected = 1.234f;
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveFloat().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsFloat_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveFloat();
		}
	}

	@Test
	public void stringAsFloat_getValueOrDefault()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			float value = new TestValidatorsImpl(scope).checkIf(actual, "Actual").asPrimitiveFloat().
				getValueOrDefault(0.5f);
			requireThat(value, "value").isEqualTo(0.5f);
		}
	}

	@Test
	public void stringAsFloat_getValueOrNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			Float value = new TestValidatorsImpl(scope).checkIf(actual, "Actual").asPrimitiveFloat().asBoxed().
				getValueOrDefault(null);
			requireThat(value, "value").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsFloat()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveFloat();
		}
	}

	@Test
	public void stringAsDouble()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double expected = 1.234;
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveDouble().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsDouble_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveDouble();
		}
	}

	@Test
	public void stringAsDouble_getValueOrDefault()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			double value = new TestValidatorsImpl(scope).checkIf(actual, "Actual").asPrimitiveDouble().
				getValueOrDefault(0.1);
			requireThat(value, "value").isEqualTo(0.1);
		}
	}

	@Test
	public void stringAsDouble_getValueOrNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			Double value = new TestValidatorsImpl(scope).checkIf(actual, "Actual").asPrimitiveDouble().asBoxed().
				getValueOrDefault(null);
			requireThat(value, "value").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsDouble()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveDouble();
		}
	}

	@Test
	public void stringAsBoolean()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Boolean expected = true;
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveBoolean().isEqualTo(expected);
		}
	}

	@Test
	public void stringAsBoolean_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a boolean";
			// Boolean.parseBoolean() returns false for any string that is not equal to "true"
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveBoolean().isFalse();
		}
	}

	@Test
	public void nullAsBoolean()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveBoolean();
		}
	}

	@Test
	public void stringAsCharacter()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char expected = 'c';
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveCharacter().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsCharacter_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a char";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveCharacter();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsCharacter()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPrimitiveCharacter();
		}
	}

	@Test
	public void stringAsCharacter_getValueOrDefault()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "";
			char value = new TestValidatorsImpl(scope).checkIf(actual, "Actual").asPrimitiveCharacter().
				getValueOrDefault('/');
			requireThat(value, "value").isEqualTo('/');
		}
	}

	@Test
	public void stringAsBigInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigInteger expected = BigInteger.ZERO;
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asBigInteger().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsBigInteger_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a BigInteger";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asBigInteger();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsBigInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asBigInteger();
		}
	}

	@Test
	public void stringAsBigDecimal()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal expected = BigDecimal.valueOf(Long.MAX_VALUE).add(BigDecimal.ONE);
			String actual = expected.toString();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asBigDecimal().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsBigDecimal_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a BigDecimal";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asBigDecimal();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsBigDecimal()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asBigDecimal();
		}
	}

	@Test
	public void stringAsPath()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path expected = Path.of("/");
			String actual = expected.toString();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPath().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsPath_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "\0";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPath();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsPath()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asPath();
		}
	}

	@Test
	public void stringAsUri()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI expected = URI.create("https://example.com/");
			String actual = expected.toString();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asUri().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsUri_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a URI";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asUri();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsUri()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asUri();
		}
	}

	@Test
	public void stringAsUrl() throws MalformedURLException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URL expected = URI.create("https://example.com/").toURL();
			String actual = expected.toString();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asUrl().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsUrl_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a URI";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asUrl();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsUrl()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asUrl();
		}
	}

	@Test
	public void stringAsIPv4() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress expected = InetAddress.getByName("216.154.1.83");
			String actual = expected.getHostAddress();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asInetAddress().isEqualTo(expected);
		}
	}

	@Test
	public void stringAsIPv6() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress expected = InetAddress.getByName("2607:f2c0:920d:3f00:696c:f64a:f5ca:3abf");
			String actual = expected.getHostAddress();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asInetAddress().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsInetAddress_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not an InetAddress";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asInetAddress();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsInetAddress()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").asInetAddress();
		}
	}
}