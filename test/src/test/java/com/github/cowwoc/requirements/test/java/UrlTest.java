/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.natives.internal.util.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class UrlTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull() throws MalformedURLException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URL actual = new URL("http://host.com/");
			new Requirements(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty() throws MalformedURLException
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URL actual = new URL("http://host.com/");
			new Requirements(scope).requireThat(actual, "");
		}
	}

	@Test
	public void fromString()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "http://host.com/index.html";
			URL actualAsUrl = new Requirements(scope).requireThat(actual, "actual").asUrl().getActual();
			assert (actualAsUrl.toString().equals(actual)) : "actualAsUrl: " + actualAsUrl + ", actual: " + actual;
		}
	}

	@Test
	public void asUrl()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "http://host.com/index.html";
			URI actualAsUri = new Requirements(scope).requireThat(actual, "actual").asUrl().asUri().
				getActual();
			assert (actualAsUri.toString().equals(actual)) : "actualAsUri: " + actualAsUri + ", actual: " + actual;
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			URL actual = null;
			new Requirements(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void validateThatAsUrlNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URL actual = null;
			new Requirements(scope).validateThat(actual, "actual").asUri(null);
		}
	}

	@Test
	public void validateThatNullAsUri()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			URL actual = null;
			List<String> expectedMessages = Collections.singletonList("actual must be a URI.\n" +
				"Actual: null");
			List<ValidationFailure> actualFailures = new Requirements(scope).validateThat(actual, "actual").
				asUri().isEqualTo("notEqual").getFailures();
			List<String> actualMessages = actualFailures.stream().map(ValidationFailure::getMessage).
				collect(Collectors.toList());
			new Requirements(scope).requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
