/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.testng.annotations.Test;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.Map;

import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;

public final class MapTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.emptyMap();
			new Verifiers(scope).requireThat(null, actual);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.emptyMap();
			new Verifiers(scope).requireThat("", actual);
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.emptyMap();
			new Verifiers(scope).requireThat("actual", actual).isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Verifiers(scope).requireThat("actual", actual).isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Verifiers(scope).requireThat("actual", actual).isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.emptyMap();
			new Verifiers(scope).requireThat("actual", actual).isNotEmpty();
		}
	}

	@Test
	public void containsKey()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Verifiers(scope).requireThat("actual", actual).keySet().contains("key");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsKey_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new Verifiers(scope).requireThat("actual", actual).keySet().contains("key");
		}
	}

	@Test
	public void doesNotContainKey()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Verifiers(scope).requireThat("actual", actual).keySet().
				doesNotContain("notKey");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainKey_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new Verifiers(scope).requireThat("actual", actual).keySet().
				doesNotContain("notKey");
		}
	}

	@Test
	public void containsValue()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Verifiers(scope).requireThat("actual", actual).values().contains("value");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsValue_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "notValue");
			new Verifiers(scope).requireThat("actual", actual).values().contains("value");
		}
	}

	@Test
	public void doesNotContainValue()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Verifiers(scope).requireThat("actual", actual).values().
				doesNotContain("notValue");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainValue_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "notValue");
			new Verifiers(scope).requireThat("actual", actual).values().
				doesNotContain("notValue");
		}
	}

	@Test
	public void containsEntry()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Verifiers(scope).requireThat("actual", actual).entrySet().
				contains(new SimpleImmutableEntry<>("key", "value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsEntry_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new Verifiers(scope).requireThat("actual", actual).entrySet().
				contains(new SimpleImmutableEntry<>("key", "value"));
		}
	}

	@Test
	public void doesNotContainEntry()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Verifiers(scope).requireThat("actual", actual).entrySet().
				doesNotContain(new SimpleImmutableEntry<>("notKey", "value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainEntry_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new Verifiers(scope).requireThat("actual", actual).entrySet().
				doesNotContain(new SimpleImmutableEntry<>("notKey", "value"));
		}
	}

	@Test
	public void sizeIsEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Verifiers(scope).requireThat("actual", actual).size().isEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new Verifiers(scope).requireThat("actual", actual).size().isEqualTo(2);
		}
	}

	@Test
	public void sizeIsNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Verifiers(scope).requireThat("actual", actual).size().isNotEqualTo(2);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new Verifiers(scope).requireThat("actual", actual).size().isNotEqualTo(1);
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Map<?, ?> actual = null;
			new Verifiers(scope).withAssertionsDisabled().assertThat("actual", actual).isNotNull();
		}
	}

	@Test
	public void childConsumers()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Verifiers(scope).requireThat("actual", actual).
				keySet(k -> k.contains("key")).
				values(v -> v.contains("value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void keySetConsumer_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Verifiers(scope).requireThat("actual", actual).
				keySet(k -> k.contains("notTheKey")).
				values(v -> v.contains("value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void valuesConsumer_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Verifiers(scope).requireThat("actual", actual).
				keySet(k -> k.contains("key")).
				values(v -> v.contains("notTheValue"));
		}
	}
}
