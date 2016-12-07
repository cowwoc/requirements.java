/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.AbstractMap.SimpleEntry;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.scope.TestSingletonScope;
import org.testng.annotations.Test;

public final class MultimapTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = HashMultimap.create();
			new RequirementVerifier(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = HashMultimap.create();
			new RequirementVerifier(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = HashMultimap.create();
			new RequirementVerifier(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("key", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("key", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = HashMultimap.create();
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test
	public void containsKey()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("key", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").keySet().contains("key");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsKey_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("notKey", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").keySet().contains("key");
		}
	}

	@Test
	public void doesNotContainKey()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("key", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").keySet().
				doesNotContain("notKey");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainKey_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("notKey", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").keySet().
				doesNotContain("notKey");
		}
	}

	@Test
	public void containsValue()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("key", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").values().contains("value");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsValue_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("key", "notValue");
			new RequirementVerifier(scope).requireThat(actual, "actual").values().contains("value");
		}
	}

	@Test
	public void doesNotContainValue()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("key", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").values().
				doesNotContain("notValue");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainValue_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("key", "notValue");
			new RequirementVerifier(scope).requireThat(actual, "actual").values().
				doesNotContain("notValue");
		}
	}

	@Test
	public void containsEntry()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("key", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").entries().
				contains(new SimpleEntry<>("key", "value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsEntry_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("notKey", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").entries().
				contains(new SimpleEntry<>("key", "value"));
		}
	}

	@Test
	public void doesNotContainEntry()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("key", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").entries().
				doesNotContain(new SimpleEntry<>("notKey", "value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainEntry_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("notKey", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").entries().
				doesNotContain(new SimpleEntry<>("notKey", "value"));
		}
	}

	@Test
	public void sizeIsEqualTo()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("key", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").size().isEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("notKey", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").size().isEqualTo(2);
		}
	}

	@Test
	public void sizeIsNotEqualTo()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("key", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").size().isNotEqualTo(2);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("notKey", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").size().isNotEqualTo(1);
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			// Ensure that no exception is thrown if assertions are disabled
			Multimap<?, ?> actual = null;
			new AssertionVerifier(scope, false).requireThat(actual, "actual").isNotNull();
		}
	}

	@Test
	public void isolate()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("key", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isolate(p -> p.keySet().contains("key")).
				isolate(p -> p.values().contains("value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isolate_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Multimap<String, String> actual = ImmutableMultimap.of("key", "value");
			new RequirementVerifier(scope).requireThat(actual, "actual").
				isolate(p -> p.keySet().contains("notTheKey")).
				isolate(p -> p.values().contains("value"));
		}
	}
}
