/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.test.java;

import io.github.cowwoc.requirements12.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements12.test.TestValidators;
import io.github.cowwoc.requirements12.test.TestValidatorsImpl;
import io.github.cowwoc.requirements12.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.List;

import static io.github.cowwoc.requirements12.java.TerminalEncoding.NONE;

public final class BooleanTest
{
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void booleanIsTrue_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			boolean actual = false;
			validators.requireThat(actual, "actual").isTrue();
		}
	}

	@Test
	public void booleanIsFalse()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			boolean actual = false;
			validators.requireThat(actual, "actual").isFalse();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void booleanIsFalse_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			boolean actual = true;
			validators.requireThat(actual, "actual").isFalse();
		}
	}

	@Test
	public void booleanIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			boolean actual = false;
			validators.requireThat(actual, "actual").isEqualTo(true);
			assert false : "Expected validator to throw an exception";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert !actualMessage.contains("diff") :
				"Wasn't expecting boolean equals() to return diff.\n" +
					"actual:\n" + actualMessage;
		}
	}

	@Test
	public void multipleFailuresNullIsTrue()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Boolean actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be true", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isTrue().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsFalse()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Boolean actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be false", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isFalse().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
