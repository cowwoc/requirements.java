/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.validator.BigDecimalValidator;
import com.github.cowwoc.requirements.java.validator.BigIntegerValidator;
import com.github.cowwoc.requirements.java.validator.InetAddressValidator;
import com.github.cowwoc.requirements.java.validator.PathValidator;
import com.github.cowwoc.requirements.java.validator.UriValidator;
import com.github.cowwoc.requirements.java.validator.UrlValidator;
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
	public void trimIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").trim().isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void trimIsEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").trim().isEmpty();
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
	public void trimIsNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").trim().isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void trimIsNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").trim().isNotEmpty();
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
	public void stripIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").strip().isEmpty();
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
				"\"actual\" must be empty",
				"\"actual\" may not be equal to \"someValue\"");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
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
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not be empty",
				"\"actual\" may not be equal to \"someValue\"");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
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
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
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
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not contain leading or trailing whitespace",
				"\"actual\" may not be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
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
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
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
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not contain 5 characters");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
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
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must start with \"prefix\"",
				"\"actual\" must be equal to \"notEqual\"");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
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
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not start with \"prefix\"",
				"\"actual\" must be equal to \"notEqual\"");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
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
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must end with \"suffix\"",
				"\"actual\" must be equal to \"notEqual\"");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
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
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not end with \"suffix\"",
				"\"actual\" must be equal to \"notEqual\"");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
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
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must contain \"value\"",
				"\"actual\" must be equal to \"notEqual\"");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
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
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not contain \"value\"",
				"\"actual\" must be equal to \"notEqual\"");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				doesNotContain("value").isEqualTo("notEqual").elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void stringAsPrimitiveByte()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			byte expected = (byte) 1;
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveByte().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsPrimitiveByte_Overflow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf(Byte.MAX_VALUE + 1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveByte();
		}
	}

	@Test
	public void stringAsPrimitiveByte_getValueOrDefault()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf(Byte.MAX_VALUE + 1);
			byte value = new TestValidatorsImpl(scope).checkIf(actual, "actual").asPrimitiveByte().
				getValueOrDefault((byte) 5);
			requireThat(value, "value").isEqualTo((byte) 5);
		}
	}

	@Test
	public void stringAsPrimitiveByte_getValueOrNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf(Byte.MAX_VALUE + 1);
			Byte value = new TestValidatorsImpl(scope).checkIf(actual, "actual").asPrimitiveByte().
				getValueOrDefault(null);
			requireThat(value, "value").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsPrimitiveByte_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveByte();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsPrimitiveByte()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveByte();
		}
	}

	@Test
	public void stringAsPrimitiveShort()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			short expected = (short) 1;
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveShort().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsPrimitiveShort_Overflow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf(Short.MAX_VALUE + 1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveShort();
		}
	}

	@Test
	public void stringAsPrimitiveShort_getValueOrDefault()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf(Short.MAX_VALUE + 1);
			short value = new TestValidatorsImpl(scope).checkIf(actual, "actual").asPrimitiveShort().
				getValueOrDefault((short) 10);
			requireThat(value, "value").isEqualTo((short) 10);
		}
	}

	@Test
	public void stringAsPrimitiveShort_getValueOrNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf(Short.MAX_VALUE + 1);
			Short value = new TestValidatorsImpl(scope).checkIf(actual, "actual").asPrimitiveShort().
				getValueOrDefault(null);
			requireThat(value, "value").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsPrimitiveShort_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveShort();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsPrimitiveShort()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveShort();
		}
	}

	@Test
	public void stringAsPrimitiveInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer expected = Short.MAX_VALUE + 1;
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveInteger().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsPrimitiveInteger_Overflow()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf((long) Integer.MAX_VALUE + 1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveInteger();
		}
	}

	@Test
	public void stringAsPrimitiveInteger_getValueOrDefault()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf((long) Integer.MAX_VALUE + 1);
			int value = new TestValidatorsImpl(scope).checkIf(actual, "actual").asPrimitiveInteger().
				getValueOrDefault(15);
			requireThat(value, "value").isEqualTo(15);
		}
	}

	@Test
	public void stringAsPrimitiveInteger_getValueOrNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = String.valueOf((long) Integer.MAX_VALUE + 1);
			Integer value = new TestValidatorsImpl(scope).checkIf(actual, "actual").asPrimitiveInteger().
				getValueOrDefault(null);
			requireThat(value, "value").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsPrimitiveInteger_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveInteger();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsPrimitiveInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveInteger();
		}
	}

	@Test
	public void stringAsPrimitiveLong()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Long expected = (long) Integer.MAX_VALUE + 1;
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveLong().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsPrimitiveLong_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveLong();
		}
	}

	@Test
	public void stringAsPrimitiveLong_getValueOrDefault()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			long value = new TestValidatorsImpl(scope).checkIf(actual, "actual").asPrimitiveLong().
				getValueOrDefault(20);
			requireThat(value, "value").isEqualTo(20);
		}
	}

	@Test
	public void stringAsPrimitiveLong_getValueOrNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			Long value = new TestValidatorsImpl(scope).checkIf(actual, "actual").asPrimitiveLong().
				getValueOrDefault(null);
			requireThat(value, "value").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsPrimitiveLong()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveLong();
		}
	}

	@Test
	public void stringAsPrimitiveFloat()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float expected = 1.234f;
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveFloat().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsPrimitiveFloat_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveFloat();
		}
	}

	@Test
	public void stringAsPrimitiveFloat_getValueOrDefault()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			float value = new TestValidatorsImpl(scope).checkIf(actual, "actual").asPrimitiveFloat().
				getValueOrDefault(0.5f);
			requireThat(value, "value").isEqualTo(0.5f);
		}
	}

	@Test
	public void stringAsPrimitiveFloat_getValueOrNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			Float value = new TestValidatorsImpl(scope).checkIf(actual, "actual").asPrimitiveFloat().
				getValueOrDefault(null);
			requireThat(value, "value").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsPrimitiveFloat()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveFloat();
		}
	}

	@Test
	public void stringAsPrimitiveDouble()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double expected = 1.234;
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveDouble().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsPrimitiveDouble_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveDouble();
		}
	}

	@Test
	public void stringAsPrimitiveDouble_getValueOrDefault()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			double value = new TestValidatorsImpl(scope).checkIf(actual, "actual").asPrimitiveDouble().
				getValueOrDefault(0.1);
			requireThat(value, "value").isEqualTo(0.1);
		}
	}

	@Test
	public void stringAsPrimitiveDouble_getValueOrNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a number";
			Double value = new TestValidatorsImpl(scope).checkIf(actual, "actual").asPrimitiveDouble().
				getValueOrDefault(null);
			requireThat(value, "value").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsPrimitiveDouble()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveDouble();
		}
	}

	@Test
	public void stringAsPrimitiveBoolean()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Boolean expected = true;
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveBoolean().isEqualTo(expected);
		}
	}

	@Test
	public void stringAsPrimitiveBoolean_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a boolean";
			// Boolean.parseBoolean() returns false for any string that is not equal to "true"
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveBoolean().isFalse();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsPrimitiveBoolean()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveBoolean();
		}
	}

	@Test
	public void stringAsPrimitiveCharacter()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			char expected = 'c';
			String actual = String.valueOf(expected);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveCharacter().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsPrimitiveCharacter_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a char";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveCharacter();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsPrimitiveCharacter()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPrimitiveCharacter();
		}
	}

	@Test
	public void stringAsPrimitiveCharacter_getValueOrDefault()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "";
			char value = new TestValidatorsImpl(scope).checkIf(actual, "actual").asPrimitiveCharacter().
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
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asBigInteger().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsBigInteger_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a BigInteger";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asBigInteger();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsBigInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			BigIntegerValidator validator;
			try
			{
				validator = new TestValidatorsImpl(scope).requireThat(actual, "actual").asBigInteger();
			}
			catch (Exception e)
			{
				throw new AssertionError("Exception thrown too early", e);
			}
			validator.isNotNull();
		}
	}

	@Test
	public void stringAsBigDecimal()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			BigDecimal expected = BigDecimal.valueOf(Long.MAX_VALUE).add(BigDecimal.ONE);
			String actual = expected.toString();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asBigDecimal().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsBigDecimal_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a BigDecimal";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asBigDecimal();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsBigDecimal()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			BigDecimalValidator validator;
			try
			{
				validator = new TestValidatorsImpl(scope).requireThat(actual, "actual").asBigDecimal();
			}
			catch (Exception e)
			{
				throw new AssertionError("Exception thrown too early", e);
			}
			validator.isNotNull();
		}
	}

	@Test
	public void stringAsPath()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path expected = Path.of("/");
			String actual = expected.toString();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPath().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsPath_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "\0";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asPath();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsPath()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			PathValidator validator;
			try
			{
				validator = new TestValidatorsImpl(scope).requireThat(actual, "actual").asPath();
			}
			catch (Exception e)
			{
				throw new AssertionError("Exception thrown too early", e);
			}
			validator.isNotNull();
		}
	}

	@Test
	public void stringAsUri()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI expected = URI.create("https://example.com/");
			String actual = expected.toString();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asUri().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsUri_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a URI";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asUri();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsUri()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			UriValidator validator;
			try
			{
				validator = new TestValidatorsImpl(scope).requireThat(actual, "actual").asUri();
			}
			catch (Exception e)
			{
				throw new AssertionError("Exception thrown too early", e);
			}
			validator.isNotNull();
		}
	}

	@Test
	public void stringAsUrl() throws MalformedURLException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URL expected = URI.create("https://example.com/").toURL();
			String actual = expected.toString();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asUrl().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsUrl_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not a URI";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asUrl();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsUrl()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			UrlValidator validator;
			try
			{
				validator = new TestValidatorsImpl(scope).requireThat(actual, "actual").asUrl();
			}
			catch (Exception e)
			{
				throw new AssertionError("Exception thrown too early", e);
			}
			validator.isNotNull();
		}
	}

	@Test
	public void stringAsIPv4() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress expected = InetAddress.getByName("216.154.1.83");
			String actual = expected.getHostAddress();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asInetAddress().isEqualTo(expected);
		}
	}

	@Test
	public void stringAsIPv6() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			InetAddress expected = InetAddress.getByName("2607:f2c0:920d:3f00:696c:f64a:f5ca:3abf");
			String actual = expected.getHostAddress();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asInetAddress().isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void stringAsInetAddress_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "not an InetAddress";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").asInetAddress();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nullAsInetAddress()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			InetAddressValidator validator;
			try
			{
				validator = new TestValidatorsImpl(scope).requireThat(actual, "actual").asInetAddress();
			}
			catch (Exception e)
			{
				throw new AssertionError("Exception thrown too early", e);
			}
			validator.isNotNull();
		}
	}
}