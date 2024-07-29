/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java;

import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.validator.IntegerValidator;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static com.github.cowwoc.requirements10.java.terminal.TerminalEncoding.NONE;

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
			String actual = "actual";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEqualTo(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "actual";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEqualTo("expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_sameToStringDifferentTypes()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "null";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_differentHashCode()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			SameToStringDifferentHashCode actual = new SameToStringDifferentHashCode();
			SameToStringDifferentHashCode expected = new SameToStringDifferentHashCode();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEqualTo(expected);
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
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
	}

	@Test
	public void isEqualTo_nullToNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEqualTo(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_nullToNotNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEqualTo("expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_notNullToNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "actual";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEqualTo(null);
		}
	}

	@Test
	public void isNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotEqualTo(new Object());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotEqualTo(actual);
		}
	}

	@Test
	public void isSameReference()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isSameReferenceAs(actual, "itself");
		}
	}

	@Test
	public void isSameReferenceNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isSameReferenceAs(actual, "itself");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSameReference_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			Object expected = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isSameReferenceAs(expected, "expected");
		}
	}

	@Test
	public void isNotSameObject()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			Object expected = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotSameReferenceAs(expected, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotSameObject_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotSameReferenceAs(actual, "actual");
		}
	}

	@Test
	public void isInstanceOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isInstanceOf(Random.class).
				isInstanceOf(Object.class);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInstanceOf_actualIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isInstanceOf(Random.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isInstanceOf_expectedClassIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isInstanceOf((Class<?>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isInstanceOf_expectedGenericTypeIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isInstanceOf((GenericType<?>) null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInstanceOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isInstanceOf(String.class);
		}
	}

	@Test
	public void isNotInstanceOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotInstanceOf(Integer.class).
				isInstanceOf(Object.class);
		}
	}

	@Test
	public void isNotInstanceOf_actualIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotInstanceOf(Random.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isNotInstanceOf_expectedIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotInstanceOf((GenericType<?>) null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotInstanceOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotInstanceOf(Random.class);
		}
	}

	@Test
	public void isInstanceOf_Downcast()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object input = 25;
			TestValidatorsImpl validators = new TestValidatorsImpl(scope);
			validators.requireThat(input, "input").isInstanceOf(Integer.class).
				apply(v ->
				{
					IntegerValidator v2 = validators.requireThat(v.getValue(), v.getName());
					v2.isMultipleOf(5);
				});
		}
	}

	@Test
	public void isNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNull_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNull();
		}
	}

	@Test
	public void isNotNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isNotNull_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isNotNull();
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
			Object actual = new HashSet<>(List.of(1));
			Object expected = new LinkedHashSet<>(List.of(2));

			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}

	@Test(expectedExceptions = IOException.class)
	public void failWithCheckedException() throws IOException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Path actual = Paths.get("non-existing-path");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isDirectory();
		}
	}

	@Test
	public void isByte()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = (byte) 5;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isByte();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isByte_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isByte();
		}
	}

	@Test
	public void isShort()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = (short) 5;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isShort();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isShort_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = (byte) 5;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isShort();
		}
	}

	@Test
	public void isInt()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isInteger();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInt_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = (short) 5;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isInteger();
		}
	}

	@Test
	public void isLong()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5L;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isLong();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLong_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isLong();
		}
	}

	@Test
	public void isFloat()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5f;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isFloat();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isFloat_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5L;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isFloat();
		}
	}

	@Test
	public void isDouble()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5.0;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isDouble();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isDouble_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5f;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isDouble();
		}
	}

	@Test
	public void isBoolean()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = false;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBoolean();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBoolean_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5.0;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBoolean();
		}
	}

	@Test
	public void isChar()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = '5';
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isCharacter();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isChar_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = true;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isCharacter();
		}
	}

	@Test
	public void isBigInteger()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = BigInteger.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBigInteger();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBigInteger_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = '5';
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBigInteger();
		}
	}

	@Test
	public void isBigDecimal()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBigDecimal();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBigDecimal_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = BigInteger.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBigDecimal();
		}
	}

	@Test
	public void isComparable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = BigDecimal.ZERO;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isComparable(BigDecimal.class);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isComparable_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isComparable(Integer.class);
		}
	}

	@Test
	public void isCollection()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Set.of(1, 2, 3);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isCollection(
				new GenericType<Set<Integer>>()
				{
				});
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isCollection_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Map.of(1, 1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isCollection(new GenericType<>()
			{
			});
		}
	}

	@Test
	public void isList()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = List.of(1, 2, 3);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isList(new GenericType<List<Integer>>()
			{
			});
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isList_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Map.of(1, 1);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isList(new GenericType<>()
			{
			});
		}
	}

	@Test
	public void isByteArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new byte[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isByteArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isByteArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = List.of(1, 2, 3);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isByteArray();
		}
	}

	@Test
	public void isShortArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new short[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isShortArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isShortArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new byte[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isShortArray();
		}
	}

	@Test
	public void isIntArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new int[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isIntArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIntArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new short[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isIntArray();
		}
	}

	@Test
	public void isLongArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new long[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isLongArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLongArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new int[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isLongArray();
		}
	}

	@Test
	public void isFloatArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new float[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isFloatArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isFloatArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new long[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isFloatArray();
		}
	}

	@Test
	public void isDoubleArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new double[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isDoubleArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isDoubleArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new float[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isDoubleArray();
		}
	}

	@Test
	public void isBooleanArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new boolean[]{true, false, true};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBooleanArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isBooleanArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new double[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isBooleanArray();
		}
	}

	@Test
	public void isCharArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new char[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isCharArray();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isCharArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new boolean[]{true, false, true};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isCharArray();
		}
	}

	@Test
	public void isObjectArray()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isObjectArray(new GenericType<>()
			{
			});
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isObjectArray_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new char[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isObjectArray(new GenericType<Character>()
			{
			});
		}
	}

	@Test
	public void isMap()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Map.of(1, 2);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isMap(new GenericType<>()
			{
			});
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isMap_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object[]{1, 2, 3};
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isMap(new GenericType<>()
			{
			});
		}
	}

	@Test
	public void isPath()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Path.of("/");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isPath();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPath_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Map.of(1, 2);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isPath();
		}
	}

	@Test
	public void isString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = "";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isString();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isString_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Path.of("/");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isString();
		}
	}

	@Test
	public void isUri()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = URI.create("https://example.com/");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isUri();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isUri_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = "";
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isUri();
		}
	}

	@Test
	public void isUrl() throws MalformedURLException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = URI.create("https://example.com/").toURL();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isUrl();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isUrl_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = URI.create("https://example.com/");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isUrl();
		}
	}

	@Test
	public void isClass()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = int.class;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isClass(new GenericType<Class<Integer>>()
			{
			});
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isClass_False() throws MalformedURLException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = URI.create("https://example.com/").toURL();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isClass(new GenericType<>()
			{
			});
		}
	}

	@Test
	public void isOptional()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = Optional.of(5);
			new TestValidatorsImpl(scope).requireThat(actual, "actual")
				.isOptional(new GenericType<Optional<Integer>>()
				{
				});

			// The type parameters cannot be validated at runtime
			new TestValidatorsImpl(scope).requireThat(actual, "actual")
				.isOptional(new GenericType<Optional<Float>>()
				{
				});
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isOptional_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = int.class;
			new TestValidatorsImpl(scope).requireThat(actual, "actual")
				.isOptional(new GenericType<Optional<Float>>()
				{
				});
		}
	}

	@Test
	public void isInetAddress() throws UnknownHostException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = InetAddress.getByName("192.168.1.100");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isInetAddress();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInetAddress_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = 5;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isInetAddress();
		}
	}
}