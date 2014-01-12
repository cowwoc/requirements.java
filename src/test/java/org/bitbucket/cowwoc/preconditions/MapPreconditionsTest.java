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
		Preconditions.requireThat(null, parameter);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		Map<String, String> parameter = Collections.emptyMap();
		Preconditions.requireThat("", parameter);
	}

	@Test
	public void isNotEmptyTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Preconditions.requireThat("parameter", parameter).isNotEmpty();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmptyFalse()
	{
		Map<String, String> parameter = Collections.emptyMap();
		Preconditions.requireThat("parameter", parameter).isNotEmpty();
	}

	@Test
	public void containsKeyTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Preconditions.requireThat("parameter", parameter).containsKey("key");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsKeyFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("notKey", "value");
		Preconditions.requireThat("parameter", parameter).containsKey("key");
	}

	@Test
	public void containsValueTrue()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "value");
		Preconditions.requireThat("parameter", parameter).containsValue("value");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void containsValueFalse()
	{
		Map<String, String> parameter = Collections.singletonMap("key", "notValue");
		Preconditions.requireThat("parameter", parameter).containsValue("value");
	}
}
