/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.test;

import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;

import static org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class ObjectTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new Requirements(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new Requirements(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "actual";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "actual";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo("expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_sameToStringDifferentTypes()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "null";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_differentHashCode()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			SameToStringDifferentHashCode actual = new SameToStringDifferentHashCode();
			SameToStringDifferentHashCode expected = new SameToStringDifferentHashCode();
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_differentIdentityHashCode()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			SameToStringAndHashCodeDifferentIdentity actual =
				new SameToStringAndHashCodeDifferentIdentity();
			SameToStringAndHashCodeDifferentIdentity expected =
				new SameToStringAndHashCodeDifferentIdentity();
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected);
		}
	}

	@Test
	public void isEqualTo_nullToNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_nullToNotNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = null;
			new Requirements(scope).requireThat(actual, "actual").isEqualTo("expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_notNullToNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "actual";
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(null);
		}
	}

	@Test
	public void isNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new Requirements(scope).requireThat(actual, "actual").isNotEqualTo(new Object());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new Requirements(scope).requireThat(actual, "actual").isNotEqualTo(actual);
		}
	}

	@Test
	public void isSameObject()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isSameObjectAs(actual, "actual");
		}
	}

	@Test
	public void isSameObjectNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = null;
			new Requirements(scope).requireThat(actual, "actual").isSameObjectAs(actual, "actual");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings({"deprecation", "UnnecessaryBoxing", "CachedNumberConstructorCall"})
	public void isSameObject_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = new Integer(1);
			Integer expected = new Integer(1);
			new Requirements(scope).requireThat(actual, "actual").isSameObjectAs(expected, "expected");
		}
	}

	@Test
	@SuppressWarnings({"deprecation", "UnnecessaryBoxing", "CachedNumberConstructorCall"})
	public void isNotSameObject()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = new Integer(1);
			Integer expected = new Integer(1);
			new Requirements(scope).requireThat(actual, "actual").isNotSameObjectAs(expected, "expected");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotSameObject_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Integer actual = 1;
			new Requirements(scope).requireThat(actual, "actual").isNotSameObjectAs(actual, "actual");
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

			new Requirements(scope).requireThat(actual, "actual").isOneOf(Arrays.asList("first",
				equivalent, "third"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInCollection_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new Requirements(scope).requireThat(actual, "actual").isOneOf(Arrays.asList("first",
				"second", "third"));
		}
	}

	@Test
	public void isInstanceOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new Requirements(scope).requireThat(actual, "actual").isInstanceOf(Random.class).
				isInstanceOf(Object.class);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInstanceOf_actualIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = null;
			new Requirements(scope).requireThat(actual, "actual").isInstanceOf(Random.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isInstanceOf_expectedIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new Requirements(scope).requireThat(actual, "actual").isInstanceOf(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInstanceOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new Requirements(scope).requireThat(actual, "actual").isInstanceOf(String.class);
		}
	}

	@Test
	public void isNotInstanceOf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new Requirements(scope).requireThat(actual, "actual").isNotInstanceOf(Integer.class).
				isInstanceOf(Object.class);
		}
	}

	@Test
	public void isNotInstanceOf_actualIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = null;
			new Requirements(scope).requireThat(actual, "actual").isNotInstanceOf(Random.class);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isNotInstanceOf_expectedIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new Requirements(scope).requireThat(actual, "actual").isNotInstanceOf(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotInstanceOf_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Random actual = new Random();
			new Requirements(scope).requireThat(actual, "actual").isNotInstanceOf(Random.class);
		}
	}

	@Test
	public void isNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = null;
			new Requirements(scope).requireThat(actual, "actual").isNull();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNull_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new Requirements(scope).requireThat(actual, "actual").isNull();
		}
	}

	@Test
	public void isNotNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = new Object();
			new Requirements(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void isNotNull_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = null;
			new Requirements(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void isNotNull_CustomException()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Object actual = null;
			new Requirements(scope).withException(IllegalStateException.class).
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
			new Requirements(scope).withException(IllegalStateException.class).
				requireThat(actual, "actual").isNotNull();
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

			new Requirements(scope).requireThat(actual, "actual").isEqualTo(expected, "expected");
		}
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Object actual = null;
			new Requirements(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void withAssertionsEnabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that verification occurs if we start with assertions disabled, then enable them
			Object actual = null;
			new Requirements(scope).withAssertionsDisabled().withAssertionsEnabled().
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
			new Requirements(scope).withAssertionsEnabled().withAssertionsDisabled().
				assertThat(actual, "actual").isNotNull();
		}
	}
}
