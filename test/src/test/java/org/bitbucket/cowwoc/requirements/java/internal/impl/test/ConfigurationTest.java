/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl.test;

import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.ValidationFailureImpl;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.scope.MainConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.scope.test.TestApplicationScope;
import org.bitbucket.cowwoc.requirements.natives.terminal.TerminalEncoding;
import org.testng.annotations.Test;

import static org.bitbucket.cowwoc.requirements.DefaultRequirements.assertThat;

public final class ConfigurationTest
{
	/**
	 * Regression test. Ensure that invoking addContext() on one verifier does not impact the context
	 * of a second verifier.
	 */
	@Test
	public void separateConfigurations()
	{
		Configuration first = new MainConfiguration();
		first = first.putContext("name1", "value1");

		Configuration second = new MainConfiguration();
		second = second.putContext("name2", "value2");

		assertThat(first, "first.config").isNotEqualTo(second, "second.config");
	}

	/**
	 * Regression test. Ensure that modifying inherited configurations does not modify the default instance.
	 */
	@Test
	public void inheritDefaultConfiguration()
	{
		try (ApplicationScope scope = new TestApplicationScope(TerminalEncoding.NONE))
		{
			Configuration first = scope.getDefaultConfiguration().get();
			first = first.withAssertionsDisabled();

			Configuration second = scope.getDefaultConfiguration().get();
			second = second.withAssertionsEnabled();

			assertThat(first, "first.config").isNotEqualTo(second, "second.config");
		}
	}

	@Test
	public void threadConfiguration()
	{
		try (ApplicationScope scope = new TestApplicationScope(TerminalEncoding.NONE))
		{
			Requirements requirements = new Requirements(scope).
				putContext("verifierName", "verifierValue");
			scope.getThreadConfiguration().get().putContext("threadName", "threadValue");
			ValidationFailure failure = new ValidationFailureImpl(scope, requirements,
				IllegalArgumentException.class, "message").
				addContext("exceptionName", "exceptionValue");
			assertThat(failure.getMessage(), "message").contains("exceptionName: exceptionValue");
			assertThat(failure.getMessage(), "message").contains("verifierName : verifierValue");
			assertThat(failure.getMessage(), "message").contains("threadName   : threadValue");
		}
	}

	@Test
	public void verifierContextShadows()
	{
		try (ApplicationScope scope = new TestApplicationScope(TerminalEncoding.NONE))
		{
			Requirements requirements = new Requirements(scope).putContext("name", "verifierValue");
			scope.getThreadConfiguration().get().putContext("name", "threadValue");
			ValidationFailure failure = new ValidationFailureImpl(scope, requirements,
				IllegalArgumentException.class, "message").
				addContext("exceptionName", "exceptionValue");
			assertThat(failure.getMessage(), "message").contains("exceptionName: exceptionValue");
			assertThat(failure.getMessage(), "message").contains("name         : verifierValue");
		}
	}

	@Test
	public void exceptionContextShadows()
	{
		try (ApplicationScope scope = new TestApplicationScope(TerminalEncoding.NONE))
		{
			Requirements requirements = new Requirements(scope).putContext("name", "verifierValue");
			scope.getThreadConfiguration().get().putContext("name", "threadValue");
			ValidationFailure failure = new ValidationFailureImpl(scope, requirements,
				IllegalArgumentException.class, "message").
				addContext("name", "exceptionValue");
			assertThat(failure.getMessage(), "message").contains("name: exceptionValue");
		}
	}
}
