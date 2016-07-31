/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.Assertions;
import org.bitbucket.cowwoc.requirements.Requirements;
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
	public void isEmptyTrue()
	{
		Map<String, String> parameter = Collections.emptyMap();
		Requirements.requireThat(parameter, "parameter").isEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmptyFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").isEmpty();
	}

	@Test
	public void isNotEmptyTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").isNotEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmptyFalse()
	{
		Map<String, String> parameter = Collections.emptyMap();
		Requirements.requireThat(parameter, "parameter").isNotEmpty();
	}

	@Test
	public void containsKeyTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").keySet().contains("key");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsKeyFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Requirements.requireThat(parameter, "parameter").keySet().contains("key");
	}

	@Test
	public void doesNotContainKeyTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").keySet().doesNotContain("notKey");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainKeyFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Requirements.requireThat(parameter, "parameter").keySet().doesNotContain("notKey");
	}

	@Test
	public void containsValueTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").values().contains("value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsValueFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "notValue");
		Requirements.requireThat(parameter, "parameter").values().contains("value");
	}

	@Test
	public void doesNotContainValueTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").values().doesNotContain("notValue");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainValueFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "notValue");
		Requirements.requireThat(parameter, "parameter").values().doesNotContain("notValue");
	}

	@Test
	public void containsEntryTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").entrySet().contains(new SimpleEntry<>("key",
			"value"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsEntryFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Requirements.requireThat(parameter, "parameter").entrySet().contains(new SimpleEntry<>("key",
			"value"));
	}

	@Test
	public void doesNotContainEntryTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").entrySet().doesNotContain(
			new SimpleEntry<>("notKey", "value"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainEntryFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Requirements.requireThat(parameter, "parameter").entrySet().doesNotContain(
			new SimpleEntry<>("notKey", "value"));
	}

	@Test
	public void sizeIsEqualToTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").size().isEqualTo(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualToFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Requirements.requireThat(parameter, "parameter").size().isEqualTo(2);
	}

	@Test
	public void sizeIsNotEqualToTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").size().isNotEqualTo(2);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualToFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Requirements.requireThat(parameter, "parameter").size().isNotEqualTo(1);
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Map<?, ?> parameter = null;
		new Assertions(false).requireThat(parameter, "parameter").isNotNull();
	}

	@Test
	public void isolateTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").
			isolate(p -> p.keySet().contains("key")).
			isolate(p -> p.values().contains("value"));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isolateFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Requirements.requireThat(parameter, "parameter").
			isolate(p -> p.keySet().contains("notTheKey")).
			isolate(p -> p.values().contains("value"));
	}
}
