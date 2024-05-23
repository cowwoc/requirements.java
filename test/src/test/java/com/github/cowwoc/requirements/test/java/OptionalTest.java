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
import java.util.Optional;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

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
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isPresent();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPresent_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isPresent();
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEmpty();
		}
	}

	@Test
	public void contains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").contains(5);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").contains(6);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_actualIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").contains(5);
		}
	}

	@Test
	public void containsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.empty();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").contains(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_expectedIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").contains(null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_expectedIsEmptyVariable()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = Optional.of(5);
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").contains("Expected", null);
		}
	}

	@Test
	public void multipleFailuresNullIsPresent()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isPresent().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isEmpty().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				contains(5).isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullContainsWithName()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Optional<?> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				contains(5, "name").isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
