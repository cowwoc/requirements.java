/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java;

import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.test.TestValidators;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;

public final class ListTest
{
	@Test
	public void isSorted()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			List<Integer> actual = List.of(1, 2, 3);
			validators.requireThat(actual, "actual").isSorted(Comparator.naturalOrder());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSorted_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			List<Integer> actual = List.of(3, 2, 1);
			validators.requireThat(actual, "actual").isSorted(Comparator.naturalOrder());
		}
	}
}
