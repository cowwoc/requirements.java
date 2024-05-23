/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isSupertypeOf(Random.class);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSupertypeOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = Random.class;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isSupertypeOf(Object.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSupertypeOf_expectedIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = Random.class;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isSupertypeOf(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSupertypeOf_actualIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isSupertypeOf(Random.class);
		}
	}

	@Test
	public void isSubtypeOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = Random.class;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isSubtypeOf(Object.class);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSubtypeOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = Object.class;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isSubtypeOf(Random.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isSubtypeOf_expectedIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = Object.class;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isSubtypeOf(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSubtypeOf_actualIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<?> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isSubtypeOf(Random.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void multipleFailuresIsSupertypeOfNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Integer> actual = null;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				isSupertypeOf(null);
		}
	}

	@Test
	public void multipleFailuresNullIsInstanceOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Class<Integer> actual = null;
			List<String> expectedMessages = List.of("""
					Actual must be a supertype of class java.lang.Integer.
					Actual: null""",
				"\"Actual\" may not be equal to class java.lang.Double");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isSupertypeOf(Integer.class).isNotEqualTo(Double.class).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
