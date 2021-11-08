/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.test;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

public final class ListTest
{
	@Test
	public void isSorted()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			List<Integer> actual = List.of(1, 2, 3);
			new Requirements(scope).requireThat(actual, "actual").isSorted(Comparator.naturalOrder());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isSorted_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			List<Integer> actual = List.of(3, 2, 1);
			new Requirements(scope).requireThat(actual, "actual").isSorted(Comparator.naturalOrder());
		}
	}
}
