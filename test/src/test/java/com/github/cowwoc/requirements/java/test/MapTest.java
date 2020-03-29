/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.test;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class MapTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.emptyMap();
			new Requirements(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.emptyMap();
			new Requirements(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.emptyMap();
			new Requirements(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Requirements(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Requirements(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.emptyMap();
			new Requirements(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test
	public void containsKey()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Requirements(scope).requireThat(actual, "actual").keySet().contains("key");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsKey_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new Requirements(scope).requireThat(actual, "actual").keySet().contains("key");
		}
	}

	@Test
	public void doesNotContainKey()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Requirements(scope).requireThat(actual, "actual").keySet().
				doesNotContain("notKey");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainKey_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new Requirements(scope).requireThat(actual, "actual").keySet().
				doesNotContain("notKey");
		}
	}

	@Test
	public void containsValue()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Requirements(scope).requireThat(actual, "actual").values().contains("value");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsValue_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "notValue");
			new Requirements(scope).requireThat(actual, "actual").values().contains("value");
		}
	}

	@Test
	public void doesNotContainValue()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Requirements(scope).requireThat(actual, "actual").values().
				doesNotContain("notValue");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainValue_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "notValue");
			new Requirements(scope).requireThat(actual, "actual").values().
				doesNotContain("notValue");
		}
	}

	@Test
	public void containsEntry()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Requirements(scope).requireThat(actual, "actual").entrySet().
				contains(new SimpleImmutableEntry<>("key", "value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsEntry_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new Requirements(scope).requireThat(actual, "actual").entrySet().
				contains(new SimpleImmutableEntry<>("key", "value"));
		}
	}

	@Test
	public void doesNotContainEntry()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Requirements(scope).requireThat(actual, "actual").entrySet().
				doesNotContain(new SimpleImmutableEntry<>("notKey", "value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainEntry_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new Requirements(scope).requireThat(actual, "actual").entrySet().
				doesNotContain(new SimpleImmutableEntry<>("notKey", "value"));
		}
	}

	@Test
	public void sizeIsEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Requirements(scope).requireThat(actual, "actual").size().isEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new Requirements(scope).requireThat(actual, "actual").size().isEqualTo(2);
		}
	}

	@Test
	public void sizeIsNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Requirements(scope).requireThat(actual, "actual").size().isNotEqualTo(2);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new Requirements(scope).requireThat(actual, "actual").size().isNotEqualTo(1);
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			Map<?, ?> actual = null;
			new Requirements(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}

	@Test
	public void childConsumers()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
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
			new Requirements(scope).requireThat(actual, "actual").
				keySet(k -> k.contains("key")).
				values(v -> v.contains("notTheValue"));
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatKeySetNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").keySet(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatValuesSetNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").values(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatEntrySetNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").entrySet(null);
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatSizeNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			new Requirements(scope).validateThat(actual, "actual").size(null);
		}
	}

	@Test
	public void validateThatNullKeySet()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				keySet().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullValues()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				values().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullEntrySet()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				entrySet().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isEmpty().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullIsNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				isNotEmpty().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void validateThatNullSize()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			List<String> expectedMessages = Collections.singletonList("actual may not be null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				size().isEqualTo(5).getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
