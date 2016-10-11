/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Map;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class MapRequirementsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Map<String, String> parameter = Collections.emptyMap();
		Requirements.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Map<String, String> parameter = Collections.emptyMap();
		Requirements.requireThat(parameter, "");
	}

	@Test
	public void isEmpty()
	{
		Map<String, String> parameter = Collections.emptyMap();
		Requirements.requireThat(parameter, "parameter").isEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").isEmpty();
	}

	@Test
	public void isNotEmpty()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").isNotEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		Map<String, String> parameter = Collections.emptyMap();
		Requirements.requireThat(parameter, "parameter").isNotEmpty();
	}

	@Test
	public void containsKey()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").keySet().contains("key");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsKey_False()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Requirements.requireThat(parameter, "parameter").keySet().contains("key");
	}

	@Test
	public void doesNotContainKey()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").keySet().doesNotContain("notKey");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainKey_False()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Requirements.requireThat(parameter, "parameter").keySet().doesNotContain("notKey");
	}

	@Test
	public void containsValue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").values().contains("value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsValue_False()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "notValue");
		Requirements.requireThat(parameter, "parameter").values().contains("value");
	}

	@Test
	public void doesNotContainValue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").values().doesNotContain("notValue");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainValue_False()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "notValue");
		Requirements.requireThat(parameter, "parameter").values().doesNotContain("notValue");
	}

	@Test
	public void containsEntry()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").entrySet().contains(new SimpleEntry<>("key",
			"value"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsEntry_False()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Requirements.requireThat(parameter, "parameter").entrySet().contains(new SimpleEntry<>("key",
			"value"));
	}

	@Test
	public void doesNotContainEntry()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").entrySet().doesNotContain(
			new SimpleEntry<>("notKey", "value"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainEntry_False()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Requirements.requireThat(parameter, "parameter").entrySet().doesNotContain(
			new SimpleEntry<>("notKey", "value"));
	}

	@Test
	public void sizeIsEqualTo()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").size().isEqualTo(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualTo_False()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Requirements.requireThat(parameter, "parameter").size().isEqualTo(2);
	}

	@Test
	public void sizeIsNotEqualTo()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").size().isNotEqualTo(2);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualTo_False()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Requirements.requireThat(parameter, "parameter").size().isNotEqualTo(1);
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Map<?, ?> parameter = null;
		new AssertionVerifier(false).requireThat(parameter, "parameter").isNotNull();
	}

	@Test
	public void isolate()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").
			isolate(p -> p.keySet().contains("key")).
			isolate(p -> p.values().contains("value"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isolate_False()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").
			isolate(p -> p.keySet().contains("notTheKey")).
			isolate(p -> p.values().contains("value"));
	}
}
