/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.scope.TestSingletonScope;
import org.testng.annotations.Test;

/**
 * @author Gili Tzabari
 */
public final class StringTest
{
	@Test(expectedExceptions = NullPointerException.class)
	public void nameIsNull()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "value";
			new RequirementVerifier(scope).requireThat(actual, null);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void nameIsEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "value";
			new RequirementVerifier(scope).requireThat(actual, "");
		}
	}

	@Test
	public void isEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmpty_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "   ";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEmpty();
		}
	}

	@Test
	public void trimIsEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "   ";
			new RequirementVerifier(scope).requireThat(actual, "actual").trim().isEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void trimIsEmpty_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "value";
			new RequirementVerifier(scope).requireThat(actual, "actual").trim().isEmpty();
		}
	}

	@Test
	public void isNotEmptyTrue()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "   ";
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isNotEmpty_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "";
			new RequirementVerifier(scope).requireThat(actual, "actual").isNotEmpty();
		}
	}

	@Test
	public void trimIsNotEmpty()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "value";
			new RequirementVerifier(scope).requireThat(actual, "actual").trim().isNotEmpty();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void trimIsNotEmpty_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "   ";
			new RequirementVerifier(scope).requireThat(actual, "actual").trim().isNotEmpty();
		}
	}

	@Test
	public void isEmailFormat()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "name@gmail.com";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEmailFormat();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isEmailFormat_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "name.com";
			new RequirementVerifier(scope).requireThat(actual, "actual").isEmailFormat();
		}
	}

	@Test
	public void isIpAddressFormat_actualIsIpV4()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "1.2.3.4";
			new RequirementVerifier(scope).requireThat(actual, "actual").isIpAddressFormat();
		}
	}

	@Test
	public void isIpAddressFormat_actualIsIpV6()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "0000:0000:0000:0000:0000:0000:192.168.0.1";
			new RequirementVerifier(scope).requireThat(actual, "actual").isIpAddressFormat();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpAddressFormat_actualIsInvalidIpV4()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "1.256.3.4";
			new RequirementVerifier(scope).requireThat(actual, "actual").isIpAddressFormat();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void isIpAddressFormat_actualIsInvalidIpV6()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "0000:0000:0000:0000:0000:0000:192.168.0.1:";
			new RequirementVerifier(scope).requireThat(actual, "actual").isIpAddressFormat();
		}
	}

	@Test
	public void startsWith()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String prefix = "home";
			String actual = prefix + "1234";
			new RequirementVerifier(scope).requireThat(actual, "actual").startsWith(prefix);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void startsWith_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String prefix = "home";
			String actual = "1234" + prefix;
			new RequirementVerifier(scope).requireThat(actual, "actual").startsWith(prefix);
		}
	}

	@Test
	public void doesNotStartWith()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String prefix = "home";
			String actual = "1234" + prefix;
			new RequirementVerifier(scope).requireThat(actual, "actual").doesNotStartWith(prefix);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotStartWith_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String prefix = "home";
			String actual = prefix + "1234";
			new RequirementVerifier(scope).requireThat(actual, "actual").doesNotStartWith(prefix);
		}
	}

	@Test
	public void endsWith()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String suffix = "home";
			String actual = "1234" + suffix;
			new RequirementVerifier(scope).requireThat(actual, "actual").endsWith(suffix);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void endsWith_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String suffix = "home";
			String actual = suffix + "1234";
			new RequirementVerifier(scope).requireThat(actual, "actual").endsWith(suffix);
		}
	}

	@Test
	public void doesNotEndWith()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String suffix = "home";
			String actual = suffix + "1234";
			new RequirementVerifier(scope).requireThat(actual, "actual").doesNotEndWith(suffix);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void doesNotEndWith_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String suffix = "home";
			String actual = "1234" + suffix;
			new RequirementVerifier(scope).requireThat(actual, "actual").doesNotEndWith(suffix);
		}
	}

	@Test
	public void lengthIsEqualTo()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "value";
			new RequirementVerifier(scope).requireThat(actual, "actual").length().
				isEqualTo(actual.length());
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsEqualTo_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "1234567890";
			new RequirementVerifier(scope).requireThat(actual, "actual").length().
				isEqualTo(actual.length() + 1);
		}
	}

	@Test
	public void lengthIsNotEqualTo()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "value";
			new RequirementVerifier(scope).requireThat(actual, "actual").length().
				isNotEqualTo(actual.length() + 1);
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void lengthIsNotEqualTo_False()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			String actual = "1234567890";
			new RequirementVerifier(scope).requireThat(actual, "actual").length().
				isNotEqualTo(actual.length());
		}
	}

	@Test
	public void assertionsDisabled()
	{
		try (SingletonScope scope = new TestSingletonScope())
		{
			// Ensure that no exception is thrown if assertions are disabled
			String actual = null;
			new AssertionVerifier(scope, false).requireThat(actual, "actual").isNotNull();
		}
	}
}
