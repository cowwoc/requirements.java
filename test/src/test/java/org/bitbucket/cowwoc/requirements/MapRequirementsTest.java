/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Map;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.scope.TestSingletonScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class MapRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.emptyMap();
			new RequirementVerifier(scope).requireThat(parameter, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.emptyMap();
			new RequirementVerifier(scope).requireThat(parameter, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.emptyMap();
			new RequirementVerifier(scope).requireThat(parameter, "parameter").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("key", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("key", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.emptyMap();
			new RequirementVerifier(scope).requireThat(parameter, "parameter").isNotEmpty();
		}
	}

	@Test
	public void containsKey()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("key", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").keySet().contains("key");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsKey_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("notKey", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").keySet().contains("key");
		}
	}

	@Test
	public void doesNotContainKey()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("key", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").keySet().
				doesNotContain("notKey");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainKey_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("notKey", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").keySet().
				doesNotContain("notKey");
		}
	}

	@Test
	public void containsValue()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("key", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").values().contains("value");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsValue_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("key", "notValue");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").values().contains("value");
		}
	}

	@Test
	public void doesNotContainValue()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("key", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").values().
				doesNotContain("notValue");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainValue_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("key", "notValue");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").values().
				doesNotContain("notValue");
		}
	}

	@Test
	public void containsEntry()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("key", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").entrySet().
				contains(new SimpleEntry<>("key", "value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsEntry_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("notKey", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").entrySet().
				contains(new SimpleEntry<>("key", "value"));
		}
	}

	@Test
	public void doesNotContainEntry()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("key", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").entrySet().
				doesNotContain(new SimpleEntry<>("notKey", "value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainEntry_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("notKey", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").entrySet().
				doesNotContain(new SimpleEntry<>("notKey", "value"));
		}
	}

	@Test
	public void sizeIsEqualTo()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("key", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").size().isEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("notKey", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").size().isEqualTo(2);
		}
	}

	@Test
	public void sizeIsNotEqualTo()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("key", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").size().isNotEqualTo(2);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("notKey", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").size().isNotEqualTo(1);
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			// Ensure that no exception is thrown if assertions are disabled
			Map<?, ?> parameter = null;
			new AssertionVerifier(scope, false).requireThat(parameter, "parameter").isNotNull();
		}
	}

	@Test
	public void isolate()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("key", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").
				isolate(p -> p.keySet().contains("key")).
				isolate(p -> p.values().contains("value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isolate_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			Map<String, String> parameter = Collections.singletonMap("key", "value");
			new RequirementVerifier(scope).requireThat(parameter, "parameter").
				isolate(p -> p.keySet().contains("notTheKey")).
				isolate(p -> p.values().contains("value"));
		}
	}
}
