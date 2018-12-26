/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.util.test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.bitbucket.cowwoc.requirements.java.internal.util.Sets;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Set;

import static org.bitbucket.cowwoc.requirements.DefaultRequirements.requireThat;

public final class SetsTest
{
	@Test
	public void fromCollection()
	{
		Collection<Integer> input = ImmutableList.of(1, 2, 3);
		Set<Integer> output = Sets.fromCollection(input);
		requireThat(input, "input").isNotSameObjectAs(output, "output");
	}

	@Test
	public void fromSet()
	{
		Collection<Integer> input = ImmutableSet.of(1, 2, 3);
		Set<Integer> output = Sets.fromCollection(input);
		requireThat(input, "input").isSameObjectAs(output, "output");
	}

	@Test
	public void difference()
	{
		Set<Integer> first = ImmutableSet.of(1, 2, 3);
		Set<Integer> second = ImmutableSet.of(2, 3, 4);
		Set<Integer> difference = Sets.difference(first, second);
		requireThat(difference, "difference").containsExactly(ImmutableSet.of(1));
	}

	@Test
	public void intersection()
	{
		Set<Integer> first = ImmutableSet.of(1, 2, 3);
		Set<Integer> second = ImmutableSet.of(2, 3, 4);
		Set<Integer> intersection = Sets.intersection(first, second);
		requireThat(intersection, "intersection").containsExactly(ImmutableSet.of(2, 3));
	}
}
