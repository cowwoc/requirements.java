/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.test;

import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class BooleanTest
{
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void booleanIsTrue_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = false;
			new Requirements(scope).requireThat(actual, "actual").isTrue();
		}
	}

	@Test
	public void booleanIsFalse()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = false;
			new Requirements(scope).requireThat(actual, "actual").isFalse();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void booleanIsFalse_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = true;
			new Requirements(scope).requireThat(actual, "actual").isFalse();
		}
	}

	@SuppressWarnings("deprecation")
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void booleanIsNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = true;
			new Requirements(scope).requireThat(actual, "actual").isNull();
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void booleanIsNotNull_deprecation()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = true;
			new Requirements(scope).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test
	public void booleanIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = false;
			new Requirements(scope).requireThat(actual, "actual").isEqualTo(true);
			assert (false) : "Expected verifier to throw an exception";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert (!actualMessage.contains("Diff")) :
				"Wasn't expecting boolean equals() to return diff.\n" +
					"\nActual:\n" + actualMessage;
		}
	}

	@Test
	public void validateThatNullIsTrue()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Boolean actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isTrue().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsFalse()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Boolean actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isFalse().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
