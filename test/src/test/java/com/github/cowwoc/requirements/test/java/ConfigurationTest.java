/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.natives.internal.util.scope.TestApplicationScope;
import com.google.common.collect.Sets;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.github.cowwoc.requirements.DefaultRequirements.requireThat;
import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

public final class ConfigurationTest
{
	@Test
	public void withStringConverter()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Set<Integer> actual = Sets.newLinkedHashSetWithExpectedSize(2);
			actual.add(1);
			actual.add(2);

			Set<Integer> notEqual = Collections.emptySet();

			new Requirements(scope).withStringConverter(LinkedHashSet.class, s ->
			{
				@SuppressWarnings({"unchecked", "rawtypes"})
				List<Integer> result = new ArrayList<>(s);
				result.sort(Comparator.reverseOrder());
				return result.toString();
			}).requireThat(actual, "actual").isEqualTo(notEqual);
		}
		catch (IllegalArgumentException e)
		{
			requireThat(e.getMessage(), "e.getMessage()").contains("[2, 1]");
		}
	}
}
