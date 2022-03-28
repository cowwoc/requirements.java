/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.test.java.internal.impl;

import com.github.cowwoc.requirements.Requirements;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.ValidationFailureImpl;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.scope.MainConfiguration;
import com.github.cowwoc.requirements.natives.terminal.TerminalEncoding;
import com.github.cowwoc.requirements.test.natives.internal.util.scope.TestApplicationScope;
import org.testng.annotations.Test;

import static com.github.cowwoc.requirements.DefaultRequirements.assertThat;

public final class ConfigurationTest
{
	/**
	 * Regression test. Ensure that invoking addContext() on one verifier does not impact the context
	 * of a second verifier.
	 */
	@Test
	public void separateConfigurations()
	{
		try (ApplicationScope scope = new TestApplicationScope(TerminalEncoding.NONE))
		{
			Configuration first = new MainConfiguration(scope);
			first = first.withContext("name1", "value1");

			Configuration second = new MainConfiguration(scope);
			second = second.withContext("name2", "value2");

			assertThat(first, "first.config").isNotEqualTo(second, "second.config");
		}
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
				withContext("verifierName", "verifierValue");
			scope.getThreadConfiguration().get().withContext("threadName", "threadValue");
			ValidationFailure failure = new ValidationFailureImpl(scope, requirements,
				IllegalArgumentException.class, "message").
				addContext("exceptionName", "exceptionValue");
			assertThat(failure.getMessage(), "message").contains("exceptionName: exceptionValue");
			assertThat(failure.getMessage(), "message").contains("verifierName : verifierValue");
			assertThat(failure.getMessage(), "message").contains("threadName   : threadValue");
		}
	}

	@Test
	public void verifierOverridesThreadContext()
	{
		try (ApplicationScope scope = new TestApplicationScope(TerminalEncoding.NONE))
		{
			Requirements requirements = new Requirements(scope).withContext("name", "verifierValue");
			scope.getThreadConfiguration().get().withContext("name", "threadValue");
			ValidationFailure failure = new ValidationFailureImpl(scope, requirements,
				IllegalArgumentException.class, "message").
				addContext("exceptionName", "exceptionValue");
			assertThat(failure.getMessage(), "message").contains("exceptionName: exceptionValue");
			assertThat(failure.getMessage(), "message").contains("name         : verifierValue");
		}
	}

	@Test
	public void exceptionOverridesVerifierContext()
	{
		try (ApplicationScope scope = new TestApplicationScope(TerminalEncoding.NONE))
		{
			Requirements requirements = new Requirements(scope).withContext("name", "verifierValue");
			scope.getThreadConfiguration().get().withContext("name", "threadValue");
			ValidationFailure failure = new ValidationFailureImpl(scope, requirements,
				IllegalArgumentException.class, "message").
				addContext("name", "exceptionValue");
			assertThat(failure.getMessage(), "message").contains("name: exceptionValue");
		}
	}
}
