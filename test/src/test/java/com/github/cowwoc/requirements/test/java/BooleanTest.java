/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.List;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

public final class BooleanTest
{
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void booleanIsTrue_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = false;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isTrue();
		}
	}

	@Test
	public void booleanIsFalse()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = false;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isFalse();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void booleanIsFalse_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = true;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isFalse();
		}
	}

	@Test
	public void booleanIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean actual = false;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEqualTo(true);
			assert (false) : "Expected validator to throw an exception";
		}
		catch (IllegalArgumentException e)
		{
			String actualMessage = e.getMessage();
			assert (!actualMessage.contains("Diff")) :
				"Wasn't expecting boolean equals() to return diff.\n" +
					" Actual:\n" + actualMessage;
		}
	}

	@Test
	public void multipleFailuresNullIsTrue()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Boolean actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isTrue().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsFalse()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Boolean actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isFalse().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
