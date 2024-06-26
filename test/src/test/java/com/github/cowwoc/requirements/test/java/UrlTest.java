/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

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
	public void fromString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "http://host.com/index.html";
			URL actualAsUrl = new TestValidatorsImpl(scope).requireThat(actual, "Actual").asUrl().getValue();
			assert (actualAsUrl.toString().equals(actual)) : "actualAsUrl: " + actualAsUrl + ", actual: " + actual;
		}
	}

	@Test
	public void asUrl()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "http://host.com/index.html";
			URI actualAsUri = new TestValidatorsImpl(scope).requireThat(actual, "Actual").asUrl().asUri().
				getValue();
			assert (actualAsUri.toString().equals(actual)) : "actualAsUri: " + actualAsUri + ", actual: " + actual;
		}
	}

	@Test
	public void multipleFailuresAsUrlNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URI actual = null;
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"""
					"Actual" must be equal to "notEqual"\
					""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
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
			List<String> expectedMessages = List.of("\"Actual\" may not be null",
				"""
					"Actual" must be equal to "notEqual"\
					""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "Actual").
				asUri().isEqualTo("notEqual").elseGetMessages();
			new TestValidatorsImpl(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
