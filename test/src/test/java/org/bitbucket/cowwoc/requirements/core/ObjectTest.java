/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.Arrays;
import java.util.Random;
import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class ObjectTest
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
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new Verifiers(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new Verifiers(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "actual";
			new Verifiers(scope).requireThat(actual, "actual").isEqualTo(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "actual";
			new Verifiers(scope).requireThat(actual, "actual").isEqualTo("expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_sameToStringDifferentTypes()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "null";
			new Verifiers(scope).requireThat(actual, "actual").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_sameToStringAndTypeDifferentHashCode()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			SameToStringDifferentHashCode actual = new SameToStringDifferentHashCode();
			new Verifiers(scope).requireThat(actual, "actual").isEqualTo(
				new SameToStringDifferentHashCode());
		}
	}

	@Test
	public void isEqualTo_nullToNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new Verifiers(scope).requireThat(actual, "actual").isEqualTo(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_nullToNotNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new Verifiers(scope).requireThat(actual, "actual").isEqualTo("expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_notNullToNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "actual";
			new Verifiers(scope).requireThat(actual, "actual").isEqualTo(null);
		}
	}

	@Test
	public void isNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new Verifiers(scope).requireThat(actual, "actual").isNotEqualTo(new Object());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new Verifiers(scope).requireThat(actual, "actual").isNotEqualTo(actual);
		}
	}

	@Test
	public void isInCollection()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";

			// Make sure that the collection uses equals()
			@SuppressWarnings("RedundantStringConstructorCall")
			String equivalent = new String(actual);

			new Verifiers(scope).requireThat(actual, "actual").isIn(Arrays.asList("first",
				equivalent, "third"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInCollection_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new Verifiers(scope).requireThat(actual, "actual").isIn(Arrays.asList("first",
				"second", "third"));
		}
	}

	@Test
	public void isInstanceOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new Verifiers(scope).requireThat(actual, "actual").isInstanceOf(Random.class).
				isInstanceOf(Object.class);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInstanceOf_actualIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = null;
			new Verifiers(scope).requireThat(actual, "actual").isInstanceOf(Random.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isInstanceOf_expectedIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new Verifiers(scope).requireThat(actual, "actual").isInstanceOf(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInstanceOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new Verifiers(scope).requireThat(actual, "actual").isInstanceOf(String.class);
		}
	}

	@Test
	public void isNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = null;
			new Verifiers(scope).requireThat(actual, "actual").isNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNull_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new Verifiers(scope).requireThat(actual, "actual").isNull();
		}
	}

	@Test
	public void isNotNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new Verifiers(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isNotNull_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = null;
			new Verifiers(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void isNotNull_CustomException()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = null;
			new Verifiers(scope).withException(IllegalStateException.class).
				requireThat(actual, "actual").isNotNull();
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
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 5;
			new Verifiers(scope).withException(IllegalStateException.class).
				requireThat(actual, "actual").isNotNull();
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Object actual = null;
			new Verifiers(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void withAssertionsEnabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that verification occurs if we start with assertions disabled, then enable them
			Object actual = null;
			new Verifiers(scope).withAssertionsDisabled().withAssertionsEnabled().
				assertThat(actual, "actual").isNotNull();
		}
	}

	@Test
	public void withAssertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception gets thrown if we start with assertions enabled, then disable them
			Object actual = null;
			new Verifiers(scope).withAssertionsEnabled().withAssertionsDisabled().
				assertThat(actual, "actual").isNotNull();
		}
	}
}
