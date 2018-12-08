/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import com.google.common.collect.Sets;
import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.bitbucket.cowwoc.requirements.DefaultRequirements.requireThat;
import static org.bitbucket.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

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
				@SuppressWarnings("unchecked")
				List<Integer> result = new ArrayList<>(s);
				Collections.sort(result, Comparator.reverseOrder());
				return result.toString();
			}).requireThat("actual", actual).isEqualTo(notEqual);
		}
		catch (IllegalArgumentException e)
		{
			requireThat("e.getMessage()", e.getMessage()).contains("[2, 1]");
		}
	}
}
