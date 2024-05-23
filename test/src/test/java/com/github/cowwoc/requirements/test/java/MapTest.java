/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidators;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class MapTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.emptyMap();
			new TestValidatorsImpl(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.emptyMap();
			new TestValidatorsImpl(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.emptyMap();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.emptyMap();
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").isNotEmpty();
		}
	}

	@Test
	public void containsKey()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").keySet().contains("key");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsKey_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").keySet().contains("key");
		}
	}

	@Test
	public void doesNotContainKey()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").keySet().
				doesNotContain("notKey");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainKey_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").keySet().
				doesNotContain("notKey");
		}
	}

	@Test
	public void containsValue()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").values().contains("value");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsValue_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "notValue");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").values().contains("value");
		}
	}

	@Test
	public void doesNotContainValue()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").values().
				doesNotContain("notValue");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainValue_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "notValue");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").values().
				doesNotContain("notValue");
		}
	}

	@Test
	public void containsEntry()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").entrySet().
				contains(new SimpleImmutableEntry<>("key", "value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsEntry_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").entrySet().
				contains(new SimpleImmutableEntry<>("key", "value"));
		}
	}

	@Test
	public void doesNotContainEntry()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").entrySet().
				doesNotContain(new SimpleImmutableEntry<>("notKey", "value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainEntry_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").entrySet().
				doesNotContain(new SimpleImmutableEntry<>("notKey", "value"));
		}
	}

	@Test
	public void sizeIsEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().isEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().isEqualTo(2);
		}
	}

	@Test
	public void sizeIsNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().isNotEqualTo(2);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("notKey", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").size().isNotEqualTo(1);
		}
	}

	@Test
	public void nestedValidators()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				apply(v -> v.keySet().contains("key")).
				apply(v -> v.values().contains("value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void keySetNestedValidator_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				apply(v -> v.keySet().contains("notTheKey")).
				apply(v -> v.values().contains("value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void valuesNestedValidator_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<String, String> actual = Collections.singletonMap("key", "value");
			new TestValidatorsImpl(scope).requireThat(actual, "Actual").
				apply(v -> v.keySet().contains("key")).
				apply(v -> v.values().contains("notTheValue"));
		}
	}

	@Test
	public void multipleFailuresNullKeySet()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "Actual.keySet() must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				keySet().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullValues()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"Actual.values() must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				values().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullEntrySet()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"Actual.entrySet() must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				entrySet().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isEmpty().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				isNotEmpty().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullSize()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<?, ?> actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null", "\"Actual\" must contain 5 entries");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				size().isEqualTo(5).elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").
				isEqualTo(expectedMessages);
		}
	}

	@Test
	public void mapToStringWithSortedKeys()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<Integer, Integer> input = new LinkedHashMap<>();
			input.put(2, 2);
			input.put(3, 1);
			input.put(1, 3);

			TestValidators validators = new TestValidatorsImpl(scope);
			Map<Integer, Integer> sorted = new TreeMap<>(input);
			String output = validators.configuration().stringMappers().toString(sorted);

			// Ordering should not change
			StringJoiner expected = new StringJoiner(", ", "{", "}");
			for (Entry<Integer, Integer> entry : sorted.entrySet())
				expected.add(entry.getKey().toString() + "=" + entry.getValue().toString());

			validators.requireThat(output, "output").isEqualTo(expected.toString(), "Expected");
		}
	}

	@Test
	public void mapToStringWithNonComparableKeys()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<AtomicInteger, Integer> input = new LinkedHashMap<>();
			input.put(new AtomicInteger(2), 2);
			input.put(new AtomicInteger(3), 1);
			input.put(new AtomicInteger(1), 3);

			TestValidators validators = new TestValidatorsImpl(scope);
			String output = validators.configuration().stringMappers().toString(input);

			// Ordering should not change
			StringJoiner expected = new StringJoiner(", ", "{", "}");
			for (Entry<AtomicInteger, Integer> entry : input.entrySet())
				expected.add(entry.getKey().toString() + "=" + entry.getValue().toString());

			validators.requireThat(output, "output").isEqualTo(expected.toString(), "Expected");
		}
	}

	@Test
	public void mapToStringWithComparableKeys()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			Map<Integer, Integer> input = new LinkedHashMap<>();
			input.put(2, 2);
			input.put(3, 1);
			input.put(1, 3);

			TestValidators validators = new TestValidatorsImpl(scope);
			String output = validators.configuration().stringMappers().toString(input);

			// Input should get sorted
			Map<Integer, Integer> sorted = new TreeMap<>(input);
			StringJoiner expected = new StringJoiner(", ", "{", "}");
			for (Entry<Integer, Integer> entry : sorted.entrySet())
				expected.add(entry.getKey().toString() + "=" + entry.getValue().toString());

			validators.requireThat(output, "output").isEqualTo(expected.toString(), "Expected");
		}
	}
}