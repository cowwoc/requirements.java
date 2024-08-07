/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java;

import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class UrlTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull() throws MalformedURLException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URL actual = URI.create("http://host.com/").toURL();
			new TestValidatorsImpl(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty() throws MalformedURLException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URL actual = URI.create("http://host.com/").toURL();
			new TestValidatorsImpl(scope).requireThat(actual, "");
		}
	}

	@Test
	public void multipleFailuresAsUrlNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be an absolute URI",
				"\"actual\" must be equal to \"notEqual\"");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isAbsolute().isEqualTo("notEqual").elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullAsUri()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URL actual = null;
			List<String> expectedMessages = List.of("""
					"actual" must be a valid URI.
					actual: null""",
				"\"actual\" must be equal to \"notEqual\"");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				asUri().isEqualTo("notEqual").elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
