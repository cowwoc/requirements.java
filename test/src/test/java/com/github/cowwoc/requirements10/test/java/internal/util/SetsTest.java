/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java.internal.util;

import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.Collections;
import com.github.cowwoc.requirements10.test.TestValidators;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Set;

import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;

public final class SetsTest
{
	@Test
	public void asSet()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Collection<Integer> input = ImmutableList.of(1, 2, 3);
			Set<Integer> output = Collections.asSet(input);
			validators.requireThat(input, "Input").isReferenceNotEqualTo(output, "output");
		}
	}

	@Test
	public void fromSet()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Collection<Integer> input = ImmutableSet.of(1, 2, 3);
			Set<Integer> output = Collections.asSet(input);
			validators.requireThat(input, "Input").isReferenceEqualTo(output, "output");
		}
	}

	@Test
	public void firstMinusSecond()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Set<Integer> first = ImmutableSet.of(1, 2, 3);
			Set<Integer> second = ImmutableSet.of(2, 3, 4);
			Set<Integer> difference = Collections.firstMinusSecond(first, second);
			validators.requireThat(difference, "difference").
				containsExactly(ImmutableSet.of(1));
		}
	}

	@Test
	public void intersection()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Set<Integer> first = ImmutableSet.of(1, 2, 3);
			Set<Integer> second = ImmutableSet.of(2, 3, 4);
			Set<Integer> intersection = Collections.intersection(first, second);
			validators.requireThat(intersection, "intersection").
				containsExactly(ImmutableSet.of(2, 3));
		}
	}
}
