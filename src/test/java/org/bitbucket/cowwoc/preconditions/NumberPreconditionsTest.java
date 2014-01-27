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
public class NumberPreconditionsTest
{
	@Test
	public void isInTrue()
	{
		Integer parameter = 1;
		Range<Integer> range = Range.closed(0, 2);
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isInFalse()
	{
		Integer parameter = 1;
		Range<Integer> range = Range.closed(10, 20);
		Preconditions.requireThat(parameter, "parameter").isIn(range);
	}
}
