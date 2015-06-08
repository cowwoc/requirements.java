/*
 * Copyright 2014 Gili.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Collections;
import java.util.Map;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public class MapPreconditionsTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		Map<String, String> parameter = Collections.emptyMap();
		Preconditions.requireThat(parameter, null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Map<String, String> parameter = Collections.emptyMap();
		Preconditions.requireThat(parameter, "");
	}

	@Test
	public void isEmptyTrue()
	{
		Map<String, String> parameter = Collections.emptyMap();
		Preconditions.requireThat(parameter, "parameter").isEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmptyFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Preconditions.requireThat(parameter, "parameter").isEmpty();
	}

	@Test
	public void isNotEmptyTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Preconditions.requireThat(parameter, "parameter").isNotEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmptyFalse()
	{
		Map<String, String> parameter = Collections.emptyMap();
		Preconditions.requireThat(parameter, "parameter").isNotEmpty();
	}

	@Test
	public void containsKeyTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Preconditions.requireThat(parameter, "parameter").containsKey("key");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsKeyFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Preconditions.requireThat(parameter, "parameter").containsKey("key");
	}

	@Test
	public void doesNotContainKeyTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Preconditions.requireThat(parameter, "parameter").doesNotContainKey("notKey");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainKeyFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Preconditions.requireThat(parameter, "parameter").doesNotContainKey("notKey");
	}

	@Test
	public void containsValueTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Preconditions.requireThat(parameter, "parameter").containsValue("value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsValueFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "notValue");
		Preconditions.requireThat(parameter, "parameter").containsValue("value");
	}

	@Test
	public void doesNotContainValueTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Preconditions.requireThat(parameter, "parameter").doesNotContainValue("notValue");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContainValueFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "notValue");
		Preconditions.requireThat(parameter, "parameter").doesNotContainValue("notValue");
	}

	@Test
	public void sizeIsEqualToTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Preconditions.requireThat(parameter, "parameter").size().isEqualTo(1);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsEqualToFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Preconditions.requireThat(parameter, "parameter").size().isEqualTo(2);
	}

	@Test
	public void sizeIsNotEqualToTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Preconditions.requireThat(parameter, "parameter").size().isNotEqualTo(2);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void sizeIsNotEqualToFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Preconditions.requireThat(parameter, "parameter").size().isNotEqualTo(1);
	}

	@Test
	public void assertionsDisabled()
	{
		// Ensure that no exception is thrown if assertions are disabled
		Map<?, ?> parameter = null;
		new Assertions(false).requireThat(parameter, "parameter").isNotNull();
	}
}
