/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class ObjectTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sameNameAsValue()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			List<Integer> actual = List.of(1, 2, 3);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").contains(2, "actual");
		}
	}

	@Test
	public void isEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "Actual";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEqualTo(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "Actual";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEqualTo("Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_sameToStringDifferentTypes()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "null";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_differentHashCode()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			SameToStringDifferentHashCode actual = new SameToStringDifferentHashCode();
			SameToStringDifferentHashCode expected = new SameToStringDifferentHashCode();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_differentIdentityHashCode()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			SameToStringAndHashCodeDifferentIdentity actual = new SameToStringAndHashCodeDifferentIdentity();
			SameToStringAndHashCodeDifferentIdentity expected =
				new SameToStringAndHashCodeDifferentIdentity();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEqualTo(expected);
		}
	}

	@Test
	public void isEqualTo_nullToNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEqualTo(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_nullToNotNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEqualTo("Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_notNullToNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "Actual";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEqualTo(null);
		}
	}

	@Test
	public void isNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotEqualTo(new Object());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotEqualTo(actual);
		}
	}

	@Test
	public void isSameReference()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isSameReferenceAs(actual, "itself");
		}
	}

	@Test
	public void isSameReferenceNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isSameReferenceAs(actual, "itself");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSameReference_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			Object expected = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isSameReferenceAs(expected, "Expected");
		}
	}

	@Test
	public void isNotSameObject()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			Object expected = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotSameReferenceAs(expected, "Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotSameObject_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotSameReferenceAs(actual, "Actual");
		}
	}

	@Test
	public void isInstanceOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isInstanceOf(Random.class).
				isInstanceOf(Object.class);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInstanceOf_actualIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isInstanceOf(Random.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isInstanceOf_expectedIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isInstanceOf(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInstanceOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isInstanceOf(String.class);
		}
	}

	@Test
	public void isNotInstanceOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotInstanceOf(Integer.class).
				isInstanceOf(Object.class);
		}
	}

	@Test
	public void isNotInstanceOf_actualIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotInstanceOf(Random.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isNotInstanceOf_expectedIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotInstanceOf(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotInstanceOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotInstanceOf(Random.class);
		}
	}

	@Test
	public void isNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNull_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNull();
		}
	}

	@Test
	public void isNotNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotNull();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isNotNull_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotNull();
		}
	}

	/**
	 * BUG: isEqualTo() was throwing AssertionError if actual.class != expected.class.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualToDifferentClassType()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new HashSet<>(Collections.singletonList(1));
			Object expected = new LinkedHashSet<>(Collections.singletonList(2));

			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEqualTo(expected, "Expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void failWithCheckedException() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get("non-existing-path");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isDirectory();
		}
	}

	@Test
	public void isByte()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = (byte) 5;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isByte();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isByte_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isByte();
		}
	}

	@Test
	public void isShort()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = (short) 5;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isShort();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isShort_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = (byte) 5;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isShort();
		}
	}

	@Test
	public void isInt()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isInt();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInt_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = (short) 5;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isInt();
		}
	}

	@Test
	public void isLong()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5L;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLong();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLong_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLong();
		}
	}

	@Test
	public void isFloat()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5f;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isFloat();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isFloat_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5L;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isFloat();
		}
	}

	@Test
	public void isDouble()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5.0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isDouble();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isDouble_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5f;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isDouble();
		}
	}

	@Test
	public void isBoolean()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = false;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBoolean();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBoolean_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5.0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBoolean();
		}
	}

	@Test
	public void isChar()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = '5';
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isChar();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isChar_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = true;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isChar();
		}
	}

	@Test
	public void isBigInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = BigInteger.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBigInteger();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBigInteger_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = '5';
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBigInteger();
		}
	}

	@Test
	public void isBigDecimal()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBigDecimal();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBigDecimal_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = BigInteger.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBigDecimal();
		}
	}

	@Test
	public void isComparable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isComparable();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isComparable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isComparable();
		}
	}

	@Test
	public void isCollection()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Set.of(1, 2, 3);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isCollection();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isCollection_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Map.of(1, 1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isCollection();
		}
	}

	@Test
	public void isList()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = List.of(1, 2, 3);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isList();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isList_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Map.of(1, 1);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isList();
		}
	}

	@Test
	public void isByteArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new byte[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isByteArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isByteArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = List.of(1, 2, 3);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isByteArray();
		}
	}

	@Test
	public void isShortArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new short[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isShortArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isShortArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new byte[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isShortArray();
		}
	}

	@Test
	public void isIntArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new int[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isIntArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIntArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new short[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isIntArray();
		}
	}

	@Test
	public void isLongArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new long[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLongArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLongArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new int[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isLongArray();
		}
	}

	@Test
	public void isFloatArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new float[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isFloatArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isFloatArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new long[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isFloatArray();
		}
	}

	@Test
	public void isDoubleArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new double[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isDoubleArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isDoubleArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new float[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isDoubleArray();
		}
	}

	@Test
	public void isBooleanArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new boolean[]{true, false, true};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBooleanArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBooleanArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new double[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isBooleanArray();
		}
	}

	@Test
	public void isCharArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new char[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isCharArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isCharArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new boolean[]{true, false, true};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isCharArray();
		}
	}

	@Test
	public void isObjectArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isObjectArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isObjectArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new char[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isObjectArray();
		}
	}

	@Test
	public void isMap()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Map.of(1, 2);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMap();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isMap_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isMap();
		}
	}

	@Test
	public void isPath()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Path.of("/");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isPath();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPath_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Map.of(1, 2);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isPath();
		}
	}

	@Test
	public void isString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = "";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isString();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isString_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Path.of("/");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isString();
		}
	}

	@Test
	public void isUri()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = URI.create("https://example.com/");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isUri();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isUri_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = "";
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isUri();
		}
	}

	@Test
	public void isUrl() throws MalformedURLException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = URI.create("https://example.com/").toURL();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isUrl();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isUrl_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = URI.create("https://example.com/");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isUrl();
		}
	}

	@Test
	public void isClass()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = int.class;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isClass();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isClass_False() throws MalformedURLException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = URI.create("https://example.com/").toURL();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isClass();
		}
	}

	@Test
	public void isOptional()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Optional.of(5);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isOptional();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isOptional_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = int.class;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isOptional();
		}
	}

	@Test
	public void isInetAddress() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = InetAddress.getByName("192.168.1.100");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isInetAddress();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInetAddress_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isInetAddress();
		}
	}
}