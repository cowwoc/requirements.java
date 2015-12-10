/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.time.Year;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class YearPreconditionsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, "");
	}

	@Test
	public void isInLowerBound()
	{
		Year parameter = Year.of(0);
		Range<Year> range = Range.closed(Year.of(0), Year.of(2));
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}

	@Test
	public void isInBounds()
	{
		Year parameter = Year.of(1);
		Range<Year> range = Range.closed(Year.of(0), Year.of(2));
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}

	@Test
	public void isInUpperBound()
	{
		Year parameter = Year.of(2);
		Range<Year> range = Range.closed(Year.of(0), Year.of(2));
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInFalseOpenRange()
	{
		Year parameter = Year.of(1);
		Range<Year> range = Range.open(Year.of(10), Year.of(20));
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInFalseClosedRange()
	{
		Year parameter = Year.of(1);
		Range<Year> range = Range.closed(Year.of(10), Year.of(20));
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}

	@Test
	public void isLessThanVariable()
	{
		Year parameter = Year.of(0);
		Preconditions.requireThat(parameter, "parameter").isLessThan(Year.of(1), "value");
	}

	@Test
	public void isLessThanConstant()
	{
		Year parameter = Year.of(0);
		Preconditions.requireThat(parameter, "parameter").isLessThan(Year.of(1));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_Equal()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, "parameter").isLessThan(Year.of(1), "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_Equal()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, "parameter").isLessThan(Year.of(1));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanVariable_Greater()
	{
		Year parameter = Year.of(2);
		Preconditions.requireThat(parameter, "parameter").isLessThan(Year.of(1), "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanConstant_Greater()
	{
		Year parameter = Year.of(2);
		Preconditions.requireThat(parameter, "parameter").isLessThan(Year.of(1));
	}

	@Test
	public void isLessThanOrEqualToVariable()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, "parameter").isLessThanOrEqualTo(Year.of(1), "value");
	}

	@Test
	public void isLessThanOrEqualToConstant()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, "parameter").isLessThanOrEqualTo(Year.of(1));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToVariable_Greater()
	{
		Year parameter = Year.of(3);
		Preconditions.requireThat(parameter, "parameter").isLessThanOrEqualTo(Year.of(2), "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isLessThanOrEqualToConstant_Greater()
	{
		Year parameter = Year.of(3);
		Preconditions.requireThat(parameter, "parameter").isLessThanOrEqualTo(Year.of(2));
	}

	@Test
	public void isGreaterThanVariable()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(Year.of(0), "value");
	}

	@Test
	public void isGreaterThanConstant()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(Year.of(0));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_Equal()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(Year.of(1), "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_Equal()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(Year.of(1));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanVariable_Less()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(Year.of(2), "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanConstant_Less()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, "parameter").isGreaterThan(Year.of(2));
	}

	@Test
	public void isGreaterThanOrEqualToVariable()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, "parameter").isGreaterThanOrEqualTo(Year.of(1), "value");
	}

	@Test
	public void isGreaterThanOrEqualToConstant()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, "parameter").isGreaterThanOrEqualTo(Year.of(1));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToVariable_Less()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, "parameter").isGreaterThanOrEqualTo(Year.of(2), "value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isGreaterThanOrEqualToConstant_Less()
	{
		Year parameter = Year.of(1);
		Preconditions.requireThat(parameter, "parameter").isGreaterThanOrEqualTo(Year.of(2));
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Year parameter = null;
		new Assertions(false).requireThat(parameter, "parameter").isNotNull();
	}
}
