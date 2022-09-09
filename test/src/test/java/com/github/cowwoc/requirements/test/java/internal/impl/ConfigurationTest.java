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
import com.github.cowwoc.requirements.natives.terminal.TerminalEncoding;
import com.github.cowwoc.requirements.test.natives.internal.util.scope.TestApplicationScope;
import org.testng.annotations.Test;

public final class ConfigurationTest
{
	/**
	 * Ensure that modifying one instance of DefaultConfiguration does not modify the other.
	 */
	@Test
	public void separateConfigurations()
	{
		try (ApplicationScope scope = new TestApplicationScope(TerminalEncoding.NONE))
		{
			Configuration first = scope.getDefaultConfiguration().get().
				withContext("name1", "value1");

			Configuration second = scope.getDefaultConfiguration().get().
				withContext("name2", "value2");

			new Requirements(scope).requireThat(first, "first.config").isNotEqualTo(second, "second.config");
		}
	}

	/**
	 * Ensure that modifying state inherited from GlobalConfiguration does not modify other instances.
	 */
	@Test
	public void inheritDefaultConfiguration()
	{
		try (ApplicationScope scope = new TestApplicationScope(TerminalEncoding.NONE))
		{
			Configuration first = scope.getDefaultConfiguration().get().withDiff();
			Configuration second = scope.getDefaultConfiguration().get().withoutDiff();

			new Requirements(scope).requireThat(first, "first.config").isNotEqualTo(second, "second.config");
		}
	}

	/**
	 * Ensure that modifying a copied configuration does not modify the original instance.
	 */
	@Test
	public void copyConfiguration()
	{
		try (ApplicationScope scope = new TestApplicationScope(TerminalEncoding.NONE))
		{
			Configuration first = scope.getDefaultConfiguration().get().withDiff();
			Configuration second = first.copy().withoutDiff();

			new Requirements(scope).requireThat(first, "first.config").isNotEqualTo(second, "second.config");
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
			requirements.requireThat(failure.getMessage(), "message").contains("exceptionName: \"exceptionValue\"");
			requirements.requireThat(failure.getMessage(), "message").contains("verifierName : \"verifierValue\"");
			requirements.requireThat(failure.getMessage(), "message").contains("threadName   : \"threadValue\"");
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
			requirements.requireThat(failure.getMessage(), "message").contains("exceptionName: \"exceptionValue\"");
			requirements.requireThat(failure.getMessage(), "message").contains("name         : \"verifierValue\"");
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
			requirements.requireThat(failure.getMessage(), "message").contains("name: \"exceptionValue\"");
		}
	}
}
