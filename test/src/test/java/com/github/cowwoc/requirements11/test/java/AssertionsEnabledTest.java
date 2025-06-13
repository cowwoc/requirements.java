package com.github.cowwoc.requirements11.test.java;

import com.github.cowwoc.requirements11.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements11.test.TestValidators;
import com.github.cowwoc.requirements11.test.TestValidatorsImpl;
import com.github.cowwoc.requirements11.test.scope.TestApplicationScope;
import org.testng.annotations.Test;

import static com.github.cowwoc.requirements11.java.TerminalEncoding.NONE;

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
			TestValidators validators = TestValidators.of(scope);

			boolean value = true;
			assert validators.requireThat(value, "value").isFalse().elseThrow();
		}
	}

	@Test(expectedExceptions = AssertionError.class)
	public void assertassertThat()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			boolean value = true;
			assert new TestValidatorsImpl(scope).that(value, "value").isFalse().elseThrow();
		}
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void assertCheckIf()
	{
		try (ApplicationScope scope = new TestApplicationScope(NONE))
		{
			TestValidators validators = TestValidators.of(scope);

			boolean value = true;
			assert new TestValidatorsImpl(scope).checkIf(value, "value").isFalse().elseThrow();
		}
	}
}