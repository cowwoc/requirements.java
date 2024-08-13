/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java;

import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;

@SuppressWarnings({"ConstantConditions", "OptionalAssignedToNull"})
public final class OptionalTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new TestValidatorsImpl(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new TestValidatorsImpl(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isPresent()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isPresent();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPresent_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isPresent();
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test
	public void contains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").contains(5);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").contains(6);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_actualIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").contains(5);
		}
	}

	@Test
	public void containsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new TestValidatorsImpl(scope).requireThat(actual, "actual").contains(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_expectedIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").contains(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_expectedIsEmptyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").contains(null, "expected");
		}
	}

	@Test
	public void isEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEqualTo(Optional.of(5));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isEqualTo(5);
		}
	}

	@Test
	public void multipleFailuresNullIsPresent()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not be empty",
				"\"actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isPresent().isEqualTo(5).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be empty",
				"\"actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isEmpty().isEqualTo(5).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must contain 5",
				"\"actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				contains(5).isEqualTo(5).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null", """
					"actual" must contain the same value as "expected".
					expected: 5""",
				"\"actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				contains(5, "expected").isEqualTo(5).elseGetFailures().getMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
