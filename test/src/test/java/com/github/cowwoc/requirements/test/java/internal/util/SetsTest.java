/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java.internal.util;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Sets;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Set;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

public final class SetsTest
{
	@Test
	public void asSet()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> input = ImmutableList.of(1, 2, 3);
			Set<Integer> output = Sets.asSet(input);
			new TestValidatorsImpl(scope).requireThat(input, "Input").isNotSameReferenceAs(output, "output");
		}
	}

	@Test
	public void fromSet()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Collection<Integer> input = ImmutableSet.of(1, 2, 3);
			Set<Integer> output = Sets.asSet(input);
			new TestValidatorsImpl(scope).requireThat(input, "Input").isSameReferenceAs(output, "output");
		}
	}

	@Test
	public void firstMinusSecond()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Set<Integer> first = ImmutableSet.of(1, 2, 3);
			Set<Integer> second = ImmutableSet.of(2, 3, 4);
			Set<Integer> difference = Sets.firstMinusSecond(first, second);
			new TestValidatorsImpl(scope).requireThat(difference, "difference").
				containsExactly(ImmutableSet.of(1));
		}
	}

	@Test
	public void intersection()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Set<Integer> first = ImmutableSet.of(1, 2, 3);
			Set<Integer> second = ImmutableSet.of(2, 3, 4);
			Set<Integer> intersection = Sets.intersection(first, second);
			new TestValidatorsImpl(scope).requireThat(intersection, "intersection").
				containsExactly(ImmutableSet.of(2, 3));
		}
	}
}
