/*
 * Copyright (c) 2019 Gili Tzabari
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

public final class FloatingPointTest
{
	@Test
	public void floatIsNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = 1.0f;
			new Requirements(scope).requireThat(actual, "actual").isNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void floatIsNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = Float.NaN;
			new Requirements(scope).requireThat(actual, "actual").isNumber();
		}
	}

	@Test
	public void doubleIsNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0;
			new Requirements(scope).requireThat(actual, "actual").isNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = Double.NaN;
			new Requirements(scope).requireThat(actual, "actual").isNumber();
		}
	}

	@Test
	public void floatIsNotNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = Float.NaN;
			new Requirements(scope).requireThat(actual, "actual").isNotNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void floatIsNotNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = 1.0f;
			new Requirements(scope).requireThat(actual, "actual").isNotNumber();
		}
	}

	@Test
	public void doubleIsNotNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = Double.NaN;
			new Requirements(scope).requireThat(actual, "actual").isNotNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNotNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0;
			new Requirements(scope).requireThat(actual, "actual").isNotNumber();
		}
	}

	@Test
	public void floatIsFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = 1.0f;
			new Requirements(scope).requireThat(actual, "actual").isFinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings({"divzero", "NumericOverflow"})
	public void floatIsFinite_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = 1.0f / 0.0f;
			new Requirements(scope).requireThat(actual, "actual").isFinite();
		}
	}

	@Test
	public void doubleIsFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0;
			new Requirements(scope).requireThat(actual, "actual").isFinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings({"divzero", "NumericOverflow"})
	public void doubleIsFinite_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0 / 0.0;
			new Requirements(scope).requireThat(actual, "actual").isFinite();
		}
	}

	@Test
	@SuppressWarnings({"divzero", "NumericOverflow"})
	public void floatIsNotFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = 1.0f / 0.0f;
			new Requirements(scope).requireThat(actual, "actual").isNotFinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void floatIsNotFinite_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = 1.0f;
			new Requirements(scope).requireThat(actual, "actual").isNotFinite();
		}
	}

	@Test
	@SuppressWarnings({"divzero", "NumericOverflow"})
	public void doubleIsNotFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0 / 0.0;
			new Requirements(scope).requireThat(actual, "actual").isNotFinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNotFinite_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0;
			new Requirements(scope).requireThat(actual, "actual").isNotFinite();
		}
	}

	@Test
	public void validateThatFloat_nullIsNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isNumber().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatDouble_nullIsNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isNumber().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatFloat_nullIsNotNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isNotNumber().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatDouble_nullIsNotNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isNotNumber().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatFloat_nullIsFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isFinite().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatDouble_nullIsFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isFinite().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatFloat_nullIsNotFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isNotFinite().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatDouble_nullIsNotFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isNotFinite().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
