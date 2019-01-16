/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.test;

import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import org.testng.annotations.Test;

import static org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding.NONE;

@SuppressWarnings("ConstantConditions")
public final class StringTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new Requirements(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new Requirements(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "";
			new Requirements(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new Requirements(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test
	public void trimIsEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new Requirements(scope).requireThat(actual, "actual").trim().isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void trimIsEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new Requirements(scope).requireThat(actual, "actual").trim().isEmpty();
		}
	}

	@Test
	public void isNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new Requirements(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "";
			new Requirements(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test
	public void trimIsNotEmpty()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new Requirements(scope).requireThat(actual, "actual").trim().isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void trimIsNotEmpty_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "   ";
			new Requirements(scope).requireThat(actual, "actual").trim().isNotEmpty();
		}
	}

	@Test
	public void isTrimmed()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "abc";
			new Requirements(scope).requireThat(actual, "actual").isTrimmed();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isTrimmed_LeadingWhitespace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = " abc";
			new Requirements(scope).requireThat(actual, "actual").isTrimmed();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isTrimmed_TrailingWhitespace()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "abc ";
			new Requirements(scope).requireThat(actual, "actual").isTrimmed();
		}
	}

	@Test
	public void startsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = prefix + "1234";
			new Requirements(scope).requireThat(actual, "actual").startsWith(prefix);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void startsWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = "1234" + prefix;
			new Requirements(scope).requireThat(actual, "actual").startsWith(prefix);
		}
	}

	@Test
	public void doesNotStartWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = "1234" + prefix;
			new Requirements(scope).requireThat(actual, "actual").doesNotStartWith(prefix);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotStartWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String prefix = "home";
			String actual = prefix + "1234";
			new Requirements(scope).requireThat(actual, "actual").doesNotStartWith(prefix);
		}
	}

	@Test
	public void endsWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = "1234" + suffix;
			new Requirements(scope).requireThat(actual, "actual").endsWith(suffix);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void endsWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = suffix + "1234";
			new Requirements(scope).requireThat(actual, "actual").endsWith(suffix);
		}
	}

	@Test
	public void doesNotEndWith()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = suffix + "1234";
			new Requirements(scope).requireThat(actual, "actual").doesNotEndWith(suffix);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotEndWith_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String suffix = "home";
			String actual = "1234" + suffix;
			new Requirements(scope).requireThat(actual, "actual").doesNotEndWith(suffix);
		}
	}

	@Test
	public void contains()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "cat";
			String actual = "my " + expected + " is the best";
			new Requirements(scope).requireThat(actual, "actual").contains(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void contains_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "cat";
			String actual = "my dog is the best";
			new Requirements(scope).requireThat(actual, "actual").contains(expected);
		}
	}

	@Test
	public void doesNotContain()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "cat";
			String actual = "my dog is the best";
			new Requirements(scope).requireThat(actual, "actual").doesNotContain(expected);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotContain_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String expected = "cat";
			String actual = "my " + expected + " is the best";
			new Requirements(scope).requireThat(actual, "actual").doesNotContain(expected);
		}
	}

	@Test
	public void lengthIsEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new Requirements(scope).requireThat(actual, "actual").length().
				isEqualTo(actual.length());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new Requirements(scope).requireThat(actual, "actual").length().
				isEqualTo(actual.length() + 1);
		}
	}

	@Test
	public void lengthIsNotEqualTo()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "value";
			new Requirements(scope).requireThat(actual, "actual").length().
				isNotEqualTo(actual.length() + 1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualTo_False()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			String actual = "1234567890";
			new Requirements(scope).requireThat(actual, "actual").length().
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
			new Requirements(scope).withAssertionsDisabled().assertThat(actual, "actual").isNotNull();
		}
	}
}
