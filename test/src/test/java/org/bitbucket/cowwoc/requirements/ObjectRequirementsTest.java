/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Arrays;
import java.util.Random;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.scope.TestSingletonScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class ObjectRequirementsTest
{
	/**
	 * A class whose instances have the same toString() but are never equal.
	 */
	private static final class SameToStringDifferentHashCode
	{
		@Override
		public String toString()
		{
			return "SameToStringDifferentHashCode";
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Object actual = new Object();
			new RequirementVerifier(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Object actual = new Object();
			new RequirementVerifier(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEquals()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "actual";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEquals_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "actual";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo("expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEquals_sameToStringDifferentTypes()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "null";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEquals_sameToStringAndTypeDifferentHashCode()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			SameToStringDifferentHashCode actual = new SameToStringDifferentHashCode();
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo(
				new SameToStringDifferentHashCode());
		}
	}

	@Test
	public void isEquals_nullToNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = null;
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEquals_nullToNotNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = null;
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo("expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEquals_notNullToNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "actual";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEqualTo(null);
		}
	}

	@Test
	public void isNotEquals()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Object actual = new Object();
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotEqualTo(new Object());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEquals_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Object actual = new Object();
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotEqualTo(actual);
		}
	}

	@Test
	public void isInCollection()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "value";

			// Make sure that the collection uses equals()
			@SuppressWarnings("RedundantStringConstructorCall")
			String equivalent = new String(actual);

			new RequirementVerifier(scope).requireThat(actual, "actual").
				isIn(Arrays.asList("first", equivalent, "third"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInCollection_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "value";
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isIn(Arrays.asList("first", "second", "third"));
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isInstanceOf_actualIsNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Random actual = null;
			new RequirementVerifier(scope).requireThat(actual, "actual").isInstanceOf(Random.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isInstanceOf_expectedIsNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Random actual = new Random();
			new RequirementVerifier(scope).requireThat(actual, "actual").isInstanceOf(null);
		}
	}

	@Test
	public void isInstanceOf()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Random actual = new Random();
			new RequirementVerifier(scope).requireThat(actual, "actual").isInstanceOf(Random.class).
				isInstanceOf(Object.class);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInstanceOf_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Random actual = new Random();
			new RequirementVerifier(scope).requireThat(actual, "actual").isInstanceOf(String.class);
		}
	}

	@Test
	public void isNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Object actual = null;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNull_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Object actual = new Object();
			new RequirementVerifier(scope).requireThat(actual, "actual").isNull();
		}
	}

	@Test
	public void isNotNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Object actual = new Object();
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isNotNull_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Object actual = null;
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void isNotNull_CustomException()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Object actual = null;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				withException(IllegalStateException.class).isNotNull();
		}
	}

	/**
	 * BUG: {@code RequirementVerifier.withException(Class<? extends RuntimeException>)}
	 * was throwing a {@code ClassCastException} if the instance was anything other than
	 * {@code ObjectRequirementsImpl}.
	 */
	@Test
	public void customExceptionRequirementSubclass()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			int actual = 5;
			new RequirementVerifier(scope).requireThat(actual, "actual").
				withException(IllegalStateException.class).isNotNull();
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			// Ensure that no exception is thrown if assertions are disabled
			Object actual = null;
			new AssertionVerifier(scope, false).requireThat(actual, "actual").isNotNull();
		}
	}
}
