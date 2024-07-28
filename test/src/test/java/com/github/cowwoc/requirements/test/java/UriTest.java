/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URL;
import java.util.List;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class UriTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = URI.create("http://host.com/");
			new TestValidatorsImpl(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = URI.create("http://host.com/");
			new TestValidatorsImpl(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isAbsolute()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = URI.create("http://host.com/index.html");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isAbsolute_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = URI.create("./index.html");
			new TestValidatorsImpl(scope).requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test
	public void fromString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "./index.html";
			URI actualAsUri = new TestValidatorsImpl(scope).requireThat(actual, "actual").asUri().getValue();
			assert (actualAsUri.toString().equals(actual)) : "actualAsUri: " + actualAsUri + ", actual: " +
				actual;
		}
	}

	@Test
	public void asUrl()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "http://host.com/index.html";
			URL actualAsUrl = new TestValidatorsImpl(scope).requireThat(actual, "actual").asUri().asUrl().
				getValue();
			assert (actualAsUrl.toString().equals(actual)) : "actualAsUri: " + actualAsUrl + ", actual: " +
				actual;
		}
	}

	@Test
	public void multipleFailuresAsUrlNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = null;
			List<String> expectedMessages = List.of("""
				"actual" must be a valid URL.
				actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				asUrl().elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").
				isEqualTo(expectedMessages);
		}
	}

	@Test
	public void multipleFailuresNullIsAbsolute()
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
	public void multipleFailuresNullAsUrl()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = null;
			List<String> expectedMessages = List.of("""
					"actual" must be a valid URL.
					actual: null""",
				"\"actual\" must be equal to \"notEqual\"");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				asUrl().isEqualTo("notEqual").elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
