/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.test.java;

import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.test.TestValidators;
import com.github.cowwoc.requirements10.test.TestValidatorsImpl;
import com.github.cowwoc.requirements10.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.List;

import static com.github.cowwoc.requirements10.java.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class UriTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			URI actual = URI.create("http://host.com/");
			validators.requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			URI actual = URI.create("http://host.com/");
			validators.requireThat(actual, "");
		}
	}

	@Test
	public void isAbsolute()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			URI actual = URI.create("http://host.com/index.html");
			validators.requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isAbsolute_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			URI actual = URI.create("./index.html");
			validators.requireThat(actual, "actual").isAbsolute();
		}
	}

	@Test
	public void multipleFailuresNullIsAbsolute()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			URI actual = null;
			List<String> expectedMessages = List.of("\"actual\" may not be null",
				"\"actual\" must be an absolute URI", """
					"actual" must be equal to "equal".
					actual: null""");
			List<String> actualMessages = new TestValidatorsImpl(scope).checkIf(actual, "actual").
				isAbsolute().isEqualTo("equal").elseGetFailures().getMessages();
			validators.requireThat(actualMessages, "actualMessages").isEqualTo(expectedMessages);
		}
	}
}
