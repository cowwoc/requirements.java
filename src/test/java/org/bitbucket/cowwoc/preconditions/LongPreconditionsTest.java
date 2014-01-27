/*
 * Copyright 2014 Gili.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class LongPreconditionsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Long parameter = 1L;
		Preconditions.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Long parameter = 1L;
		Preconditions.requireThat(parameter, "");
	}

	@Test
	public void isInTrue()
	{
		Long parameter = 1L;
		Range<Long> range = Range.closed(0L, 2L);
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInFalse()
	{
		Long parameter = 1L;
		Range<Long> range = Range.closed(10L, 20L);
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}

	@Test
	public void isNotNegativeTrue()
	{
		Long parameter = 0L;
		Preconditions.requireThat(parameter, "parameter").isNotNegative();

		parameter = 1L;
		Preconditions.requireThat(parameter, "parameter").isNotNegative();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotNegativeFalse()
	{
		Long parameter = -1L;
		Preconditions.requireThat(parameter, "parameter").isNotNegative();
	}

	@Test
	public void isPositiveTrue()
	{
		Long parameter = 1L;
		Preconditions.requireThat(parameter, "parameter").isPositive();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isPositiveFalse()
	{
		Long parameter = 0L;
		Preconditions.requireThat(parameter, "parameter").isPositive();
	}

	@Test
	public void isGreaterThanIntegerTrue()
	{
		Long parameter = 1L;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(0, "value");
	}

	@Test
	public void isGreaterThanLongTrue()
	{
		Long parameter = 1L;
		Preconditions.requireThat(parameter, "intParameter").isGreaterThan(0L, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanIntegerFalse()
	{
		Long parameter = 1L;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(1, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanLongFalseEquals()
	{
		Long parameter = 1L;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(1L, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanIntegerFalseSmaller()
	{
		Long parameter = 1L;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(2, "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanLongFalseSmaller()
	{
		Long parameter = 1L;
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(2L, "value");
	}
}
