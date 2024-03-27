/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidators;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import com.google.common.collect.Sets;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

public final class ConfigurationTest
{
	@Test
	public void withStringConverter()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = new TestValidatorsImpl(scope);
			try
			{
				Set<Integer> actual = Sets.newLinkedHashSetWithExpectedSize(2);
				actual.add(1);
				actual.add(2);

				Set<Integer> notEqual = Collections.emptySet();
				try (ConfigurationUpdater configurationUpdater = validators.updateConfiguration())
				{
					configurationUpdater.stringMappers().put(LinkedHashSet.class, s ->
					{
						@SuppressWarnings("unchecked")
						List<Integer> result = new ArrayList<>((LinkedHashSet<Integer>) s);
						result.sort(Comparator.reverseOrder());
						return result.toString();
					});
				}
				validators.requireThat(actual, "Actual").isEqualTo(notEqual);
			}
			catch (IllegalArgumentException e)
			{
				validators.requireThat(e.getMessage(), "e.getMessage()").contains("[2, 1]");
			}
		}
	}
}