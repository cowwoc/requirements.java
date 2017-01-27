/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.scope.TestApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.terminal.TerminalEncoding.NONE;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class StringTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new CoreRequirementVerifier(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new CoreRequirementVerifier(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test
	public void trimIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").trim().isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void trimIsEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").trim().isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test
	public void trimIsNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").trim().isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void trimIsNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").trim().isNotEmpty();
		}
	}

	@Test
	public void startsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = prefix + "1234";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").startsWith(prefix);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void startsWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = "1234" + prefix;
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").startsWith(prefix);
		}
	}

	@Test
	public void doesNotStartWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = "1234" + prefix;
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").doesNotStartWith(prefix);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotStartWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = prefix + "1234";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").doesNotStartWith(prefix);
		}
	}

	@Test
	public void endsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = "1234" + suffix;
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").endsWith(suffix);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void endsWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = suffix + "1234";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").endsWith(suffix);
		}
	}

	@Test
	public void doesNotEndWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = suffix + "1234";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").doesNotEndWith(suffix);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotEndWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = "1234" + suffix;
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").doesNotEndWith(suffix);
		}
	}

	@Test
	public void lengthIsEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").length().
				isEqualTo(actual.length());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").length().
				isEqualTo(actual.length() + 1);
		}
	}

	@Test
	public void lengthIsNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").length().
				isNotEqualTo(actual.length() + 1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new CoreRequirementVerifier(scope).requireThat(actual, "actual").length().
				isNotEqualTo(actual.length());
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			// Ensure that no exception is thrown if assertions are disabled
			String actual = null;
			new CoreAssertionVerifier(scope, false).requireThat(actual, "actual").isNotNull();
		}
	}
}
