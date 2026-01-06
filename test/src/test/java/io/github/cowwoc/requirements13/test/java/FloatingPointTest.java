/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.test.java;

import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.test.TestValidators;
import io.github.cowwoc.requirements13.test.TestValidatorsImpl;
import io.github.cowwoc.requirements13.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.List;

import static io.github.cowwoc.requirements13.java.TerminalEncoding.NONE;

public final class FloatingPointTest
{
	@Test
	public void floatIsNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = 1.0f;
			validators.requireThat(actual, "actual").isNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void floatIsNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = Float.NaN;
			validators.requireThat(actual, "actual").isNumber();
		}
	}

	@Test
	public void doubleIsNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = 1.0;
			validators.requireThat(actual, "actual").isNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = Double.NaN;
			validators.requireThat(actual, "actual").isNumber();
		}
	}

	@Test
	public void floatIsNotNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = Float.NaN;
			validators.requireThat(actual, "actual").isNotNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void floatIsNotNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = 1.0f;
			validators.requireThat(actual, "actual").isNotNumber();
		}
	}

	@Test
	public void doubleIsNotNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = Double.NaN;
			validators.requireThat(actual, "actual").isNotNumber();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNotNumber_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = 1.0;
			validators.requireThat(actual, "actual").isNotNumber();
		}
	}

	@Test
	public void floatIsFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = 1.0f;
			validators.requireThat(actual, "actual").isFinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings({"divzero", "NumericOverflow"})
	public void floatIsFinite_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = 1.0f / 0.0f;
			validators.requireThat(actual, "actual").isFinite();
		}
	}

	@Test
	public void doubleIsFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = 1.0;
			validators.requireThat(actual, "actual").isFinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SuppressWarnings({"divzero", "NumericOverflow"})
	public void doubleIsFinite_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = 1.0 / 0.0;
			validators.requireThat(actual, "actual").isFinite();
		}
	}

	@Test
	@SuppressWarnings({"divzero", "NumericOverflow"})
	public void floatIsNotFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = 1.0f / 0.0f;
			validators.requireThat(actual, "actual").isInfinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void floatIsNotFinite_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = 1.0f;
			validators.requireThat(actual, "actual").isInfinite();
		}
	}

	@Test
	@SuppressWarnings({"divzero", "NumericOverflow"})
	public void doubleIsInfinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = 1.0 / 0.0;
			validators.requireThat(actual, "actual").isInfinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNotFinite_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = 1.0;
			validators.requireThat(actual, "actual").isInfinite();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void floatIsNegative_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = Float.NaN;
			validators.requireThat(actual, "actual").isNegative();
		}
	}

	@Test
	public void floatIsNotNegative_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = Float.NaN;
			validators.requireThat(actual, "actual").isNotNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNegative_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = Double.NaN;
			validators.requireThat(actual, "actual").isNegative();
		}
	}

	@Test
	public void doubleIsNotNegative_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = Double.NaN;
			validators.requireThat(actual, "actual").isNotNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void floatIsZero_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = Float.NaN;
			validators.requireThat(actual, "actual").isZero();
		}
	}

	@Test
	public void floatIsNotZero_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = Float.NaN;
			validators.requireThat(actual, "actual").isNotZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsZero_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = Double.NaN;
			validators.requireThat(actual, "actual").isZero();
		}
	}

	@Test
	public void doubleIsNotZero_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = Double.NaN;
			validators.requireThat(actual, "actual").isNotZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void floatIsPositive_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = Float.NaN;
			validators.requireThat(actual, "actual").isPositive();
		}
	}

	@Test
	public void floatIsNotPositive_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = Float.NaN;
			validators.requireThat(actual, "actual").isNotPositive();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsPositive_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = Double.NaN;
			validators.requireThat(actual, "actual").isPositive();
		}
	}

	@Test
	public void doubleIsNotPositive_NaN()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = Double.NaN;
			validators.requireThat(actual, "actual").isNotPositive();
		}
	}

	@Test
	public void floatIsNegative_NegativeZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = -0.0f;
			validators.requireThat(actual, "actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void floatIsNotNegative_NegativeZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = -0.0f;
			validators.requireThat(actual, "actual").isNotNegative();
		}
	}

	@Test
	public void doubleIsNegative_NegativeZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = -0.0d;
			validators.requireThat(actual, "actual").isNegative();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNotNegative_NegativeZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = -0.0d;
			validators.requireThat(actual, "actual").isNotNegative();
		}
	}

	@Test
	public void floatIsZero_NegativeZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = -0.0f;
			validators.requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void floatIsNotZero_NegativeZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = -0.0f;
			validators.requireThat(actual, "actual").isNotZero();
		}
	}

	@Test
	public void doubleIsZero_NegativeZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = -0.0d;
			validators.requireThat(actual, "actual").isZero();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doubleIsNotZero_NegativeZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = -0.0d;
			validators.requireThat(actual, "actual").isNotZero();
		}
	}

	@Test
	public void floatIsNotPositive_NegativeZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			float actual = -0.0f;
			validators.requireThat(actual, "actual").isNotPositive();
		}
	}

	@Test
	public void doubleIsNotPositive_NegativeZero()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			double actual = -0.0d;
			validators.requireThat(actual, "actual").isNotPositive();
		}
	}

	@Test
	public void multipleFailuresFloat_nullIsNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Float actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be a well-defined number", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isNumber().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresDouble_nullIsNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Double actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be a well-defined number", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isNumber().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresFloat_nullIsNotNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Float actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not be a well-defined number", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isNotNumber().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresDouble_nullIsNotNumber()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Double actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not be a well-defined number", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isNotNumber().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresFloat_nullIsFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Float actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be a finite number", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isFinite().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresDouble_nullIsFinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Double actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be a finite number", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isFinite().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresFloat_nullisInfinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Float actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be an infinite number", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isInfinite().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresDouble_nullisInfinite()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Double actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be an infinite number", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isInfinite().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
