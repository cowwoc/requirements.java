/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.List;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

public final class FloatingPointTest
{
	@Test
	public void floatIsNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = 1.0f;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void floatIsNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = Float.NaN;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNumber();
		}
	}

	@Test
	public void doubleIsNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = Double.NaN;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNumber();
		}
	}

	@Test
	public void floatIsNotNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = Float.NaN;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void floatIsNotNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = 1.0f;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotNumber();
		}
	}

	@Test
	public void doubleIsNotNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = Double.NaN;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNotNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotNumber();
		}
	}

	@Test
	public void floatIsFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = 1.0f;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isFinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings({"divzero", "NumericOverflow"})
	public void floatIsFinite_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = 1.0f / 0.0f;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isFinite();
		}
	}

	@Test
	public void doubleIsFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isFinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings({"divzero", "NumericOverflow"})
	public void doubleIsFinite_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0 / 0.0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isFinite();
		}
	}

	@Test
	@SuppressWarnings({"divzero", "NumericOverflow"})
	public void floatIsNotFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = 1.0f / 0.0f;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isInfinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void floatIsNotFinite_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = 1.0f;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isInfinite();
		}
	}

	@Test
	@SuppressWarnings({"divzero", "NumericOverflow"})
	public void doubleisInfinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0 / 0.0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isInfinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNotFinite_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = 1.0;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isInfinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNegative_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = Double.NaN;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNegative();
		}
	}

	@Test
	public void doubleIsNotNegative_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = Double.NaN;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsZero_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = Double.NaN;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isZero();
		}
	}

	@Test
	public void doubleIsNotZero_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = Double.NaN;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsPositive_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = Double.NaN;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isPositive();
		}
	}

	@Test
	public void doubleIsNotPositive_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = Double.NaN;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNegative_NegativeZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = -0.0d;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNegative();
		}
	}

	@Test
	public void doubleIsNotNegative_NegativeZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = -0.0d;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotNegative();
		}
	}

	@Test
	public void doubleIsZero_NegativeZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = -0.0d;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNotZero_NegativeZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = -0.0d;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotZero();
		}
	}

	@Test
	public void doubleIsNotPositive_NegativeZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = -0.0d;
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotPositive();
		}
	}

	@Test
	public void multipleFailuresFloat_nullIsNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNumber().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresDouble_nullIsNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNumber().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresFloat_nullIsNotNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNotNumber().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresDouble_nullIsNotNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNotNumber().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresFloat_nullIsFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isFinite().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresDouble_nullIsFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isFinite().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresFloat_nullisInfinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Float actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isInfinite().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresDouble_nullisInfinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Double actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isInfinite().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
