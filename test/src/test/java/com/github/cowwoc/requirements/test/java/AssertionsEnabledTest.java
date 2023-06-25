package com.github.cowwoc.requirements.test.java;

import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.test.TestValidatorsImpl;
import com.github.cowwoc.requirements.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import static com.github.cowwoc.requirements.java.terminal.TerminalEncoding.NONE;

public final class AssertionsEnabledTest
{
	@Test(expectedExceptions = AssertionError.class)
	public void assertionsEnabled()
	{
		assert false;
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void assertRequireThat()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean value = true;
			assert new TestValidatorsImpl(scope).requireThat(value, "value").isFalse().elseThrow();
		}
	}

	@Test(expectedExceptions = AssertionError.class)
	public void assertAssumeThat()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean value = true;
			assert new TestValidatorsImpl(scope).assumeThat(value, "value").isFalse().elseThrow();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void assertCheckIf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			boolean value = true;
			assert new TestValidatorsImpl(scope).checkIf(value, "value").isFalse().elseThrow();
		}
	}
}