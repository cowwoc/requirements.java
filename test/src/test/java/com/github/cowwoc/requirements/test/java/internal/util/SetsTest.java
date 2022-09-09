/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java.internal.util;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.scope.MainApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Sets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Set;

public final class SetsTest
{
	@Test
	public void fromCollection()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Collection<Integer> input = ImmutableList.of(1, 2, 3);
			Set<Integer> output = Sets.fromCollection(input);
			new Requirements(scope).requireThat(input, "input").isNotSameObjectAs(output, "output");
		}
	}

	@Test
	public void fromSet()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Collection<Integer> input = ImmutableSet.of(1, 2, 3);
			Set<Integer> output = Sets.fromCollection(input);
			new Requirements(scope).requireThat(input, "input").isSameObjectAs(output, "output");
		}
	}

	@Test
	public void difference()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Set<Integer> first = ImmutableSet.of(1, 2, 3);
			Set<Integer> second = ImmutableSet.of(2, 3, 4);
			Set<Integer> difference = Sets.difference(first, second);
			new Requirements(scope).requireThat(difference, "difference").containsExactly(ImmutableSet.of(1));
		}
	}

	@Test
	public void intersection()
	{
		try (ApplicationScope scope = new MainApplicationScope())
		{
			Set<Integer> first = ImmutableSet.of(1, 2, 3);
			Set<Integer> second = ImmutableSet.of(2, 3, 4);
			Set<Integer> intersection = Sets.intersection(first, second);
			new Requirements(scope).requireThat(intersection, "intersection").containsExactly(ImmutableSet.of(2, 3));
		}
	}
}
