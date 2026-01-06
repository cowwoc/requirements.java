/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.test.java;

import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.test.TestValidators;
import io.github.cowwoc.requirements13.test.TestValidatorsImpl;
import io.github.cowwoc.requirements13.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.cowwoc.requirements13.java.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class MapTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of();
			validators.requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of();
			validators.requireThat(actual, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of();
			validators.requireThat(actual, "actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("key", "value");
			validators.requireThat(actual, "actual").isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("key", "value");
			validators.requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of();
			validators.requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test
	public void containsKey()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("key", "value");
			validators.requireThat(actual, "actual").keySet().contains("key");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsKey_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("notKey", "value");
			validators.requireThat(actual, "actual").keySet().contains("key");
		}
	}

	@Test
	public void doesNotContainKey()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("key", "value");
			validators.requireThat(actual, "actual").keySet().
				doesNotContain("notKey");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainKey_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("notKey", "value");
			validators.requireThat(actual, "actual").keySet().
				doesNotContain("notKey");
		}
	}

	@Test
	public void containsValue()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("key", "value");
			validators.requireThat(actual, "actual").values().contains("value");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsValue_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("key", "notValue");
			validators.requireThat(actual, "actual").values().contains("value");
		}
	}

	@Test
	public void doesNotContainValue()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("key", "value");
			validators.requireThat(actual, "actual").values().
				doesNotContain("notValue");
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainValue_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("key", "notValue");
			validators.requireThat(actual, "actual").values().
				doesNotContain("notValue");
		}
	}

	@Test
	public void containsEntry()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("key", "value");
			validators.requireThat(actual, "actual").entrySet().
				contains(new SimpleImmutableEntry<>("key", "value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsEntry_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("notKey", "value");
			validators.requireThat(actual, "actual").entrySet().
				contains(new SimpleImmutableEntry<>("key", "value"));
		}
	}

	@Test
	public void doesNotContainEntry()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("key", "value");
			validators.requireThat(actual, "actual").entrySet().
				doesNotContain(new SimpleImmutableEntry<>("notKey", "value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainEntry_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("notKey", "value");
			validators.requireThat(actual, "actual").entrySet().
				doesNotContain(new SimpleImmutableEntry<>("notKey", "value"));
		}
	}

	@Test
	public void sizeIsEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("key", "value");
			validators.requireThat(actual, "actual").size().isEqualTo(1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("notKey", "value");
			validators.requireThat(actual, "actual").size().isEqualTo(2);
		}
	}

	@Test
	public void sizeIsNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("key", "value");
			validators.requireThat(actual, "actual").size().isNotEqualTo(2);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("notKey", "value");
			validators.requireThat(actual, "actual").size().isNotEqualTo(1);
		}
	}

	@Test
	public void nestedValidators()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("key", "value");
			validators.requireThat(actual, "actual").
				and(v -> v.keySet().contains("key")).
				and(v -> v.values().contains("value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void keySetNestedValidator_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("key", "value");
			validators.requireThat(actual, "actual").
				and(v -> v.keySet().contains("notTheKey")).
				and(v -> v.values().contains("value"));
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void valuesNestedValidator_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<String, String> actual = Map.of("key", "value");
			validators.requireThat(actual, "actual").
				and(v -> v.keySet().contains("key")).
				and(v -> v.values().contains("notTheValue"));
		}
	}

	@Test
	public void multipleFailuresNullKeySet()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<?, ?> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"actual.keySet() must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				keySet().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullValues()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<?, ?> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"actual.values() must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				values().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullEntrySet()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<?, ?> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"actual.entrySet() must be equal to 5");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				entrySet().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<?, ?> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be empty", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isEmpty().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<?, ?> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" may not be empty", """
					"actual" must be equal to 5.
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isNotEmpty().isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullSize()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			Map<?, ?> actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must contain 5 entries");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").size().
				isEqualTo(5).elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").
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

			TestValidators validators = TestValidators.of(scope);
			Map<Integer, Integer> sorted = new TreeMap<>(input);
			String output = validators.configuration().stringMappers().toString(sorted);

			// Ordering should not change
			StringJoiner expected = new StringJoiner(", ", "{", "}");
			for (Entry<Integer, Integer> entry : sorted.entrySet())
				expected.add(entry.getKey().toString() + "=" + entry.getValue().toString());

			validators.requireThat(output, "output").isEqualTo(expected.toString(), "expected");
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

			TestValidators validators = TestValidators.of(scope);
			String output = validators.configuration().stringMappers().toString(input);

			// Ordering should not change
			StringJoiner expected = new StringJoiner(", ", "{", "}");
			for (Entry<AtomicInteger, Integer> entry : input.entrySet())
				expected.add(entry.getKey().toString() + "=" + entry.getValue().toString());

			validators.requireThat(output, "output").isEqualTo(expected.toString(), "expected");
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

			TestValidators validators = TestValidators.of(scope);
			String output = validators.configuration().stringMappers().toString(input);

			// Input should get sorted
			Map<Integer, Integer> sorted = new TreeMap<>(input);
			StringJoiner expected = new StringJoiner(", ", "{", "}");
			for (Entry<Integer, Integer> entry : sorted.entrySet())
				expected.add(entry.getKey().toString() + "=" + entry.getValue().toString());

			validators.requireThat(output, "output").isEqualTo(expected.toString(), "expected");
		}
	}
}