/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java;

import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

import static com.github.cowwoc.requirements10.java.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class ClassTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = Object.class;
			new TestValidatorsImpl(scope).requireThat(actual, null);
		}
	}

	@Test
	public void isSupertypeOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = Object.class;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isSupertypeOf(Random.class);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSupertypeOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = Random.class;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isSupertypeOf(Object.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSupertypeOf_expectedClassIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = Random.class;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isSupertypeOf((Class<?>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSupertypeOf_expectedGenericTypeIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = Random.class;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isSupertypeOf((GenericType<?>) null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSupertypeOf_actualIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isSupertypeOf(Random.class);
		}
	}

	@Test
	public void isSubtypeOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = Random.class;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isSubtypeOf(Object.class);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSubtypeOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = Object.class;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isSubtypeOf(Random.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSubtypeOf_expectedClassIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = Object.class;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isSubtypeOf((Class<?>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSubtypeOf_expectedGenericTypeIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = Object.class;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isSubtypeOf((GenericType<?>) null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSubtypeOf_actualIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isSubtypeOf(Random.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresIsSupertypeOfClassNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isSupertypeOf((Class<?>) null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresIsSupertypeOfGenericTypeNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "actual").
				isSupertypeOf((GenericType<?>) null);
		}
	}

	@Test
	public void multipleFailuresNullIsInstanceOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Integer> actual = null;
			List<String> expectedMessages = List.of("""
					"actual" must be a supertype of java.lang.Integer.
					actual: null""",
				"\"actual\" may not be equal to class java.lang.Double");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isSupertypeOf(Integer.class).isNotEqualTo(Double.class).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
