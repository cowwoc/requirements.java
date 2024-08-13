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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;

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
				and(v ->
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
}