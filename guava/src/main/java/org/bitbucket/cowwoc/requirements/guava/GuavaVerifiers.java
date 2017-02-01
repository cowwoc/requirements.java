/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import org.bitbucket.cowwoc.requirements.core.Configurable;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.scope.MainApplicationScope;
import org.bitbucket.cowwoc.requirements.guava.impl.MultimapVerifierImpl;
import org.bitbucket.cowwoc.requirements.guava.impl.NoOpMultimapVerifier;

/**
 * Verifies a parameter.
 * <p>
 * Unlike {@link GuavaRequirements}, instances of this class can be configured prior to initiating
 * verification. Doing so causes the same configuration to get reused across runs.
 * <p>
 * This class is immutable.
 *
 * @since 3.0.0
 * @author Gili Tzabari
 */
public final class GuavaVerifiers implements Configurable<GuavaVerifiers>
{
	/**
	 * @param name the name of the actual value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	private static void verifyName(String name)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		if (name.trim().isEmpty())
			throw new IllegalArgumentException("name may not be empty");
	}

	/**
	 * @param scope the application configuration
	 * @return scope
	 * @throws AssertionError if {@code scope} is null
	 */
	private static ApplicationScope verifyScope(ApplicationScope scope)
	{
		assert (scope != null): "scope may not be null";
		return scope;
	}
	private final ApplicationScope scope;
	private final Configuration config;
	private final boolean assertionsEnabled;

	/**
	 * Creates new verifiers with assertions disabled.
	 */
	public GuavaVerifiers()
	{
		this(false);
	}

	/**
	 * Creates new verifiers.
	 *
	 * @param assertionsEnabled true if assertions should verify; false if they shouldn't do anything
	 */
	public GuavaVerifiers(boolean assertionsEnabled)
	{
		this(MainApplicationScope.INSTANCE, assertionsEnabled);
	}

	/**
	 * Creates new verifiers with assertions disabled. This constructor is meant to be used by
	 * automated tests, not by users.
	 *
	 * @param scope the application configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	public GuavaVerifiers(ApplicationScope scope)
	{
		this(verifyScope(scope), scope.getDefaultConfiguration(), false);
	}

	/**
	 * Creates new verifiers. This constructor is meant to be used by automated tests, not by users.
	 *
	 * @param scope             the application configuration
	 * @param assertionsEnabled true if assertions should verify; false if they shouldn't do anything
	 * @throws AssertionError if {@code scope} is null
	 */
	public GuavaVerifiers(ApplicationScope scope, boolean assertionsEnabled)
	{
		this(verifyScope(scope), scope.getDefaultConfiguration(), assertionsEnabled);
	}

	/**
	 * Creates new verifiers.
	 *
	 * @param scope             the application configuration
	 * @param config            the instance configuration
	 * @param assertionsEnabled true if assertions should verify; false if they shouldn't do anything
	 * @throws AssertionError if any of the arguments are null
	 */
	GuavaVerifiers(ApplicationScope scope, Configuration config, boolean assertionsEnabled)
	{
		assert (scope != null): "scope may not be null";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.config = config;
		this.assertionsEnabled = assertionsEnabled;
	}

	/**
	 * @return true if assertions should verify; false if they shouldn't do anything
	 */
	public boolean assertionsAreEnabled()
	{
		return assertionsEnabled;
	}

	/**
	 * @return a verifier with assertions enabled
	 */
	public GuavaVerifiers withAssertionsEnabled()
	{
		if (assertionsEnabled)
			return this;
		return new GuavaVerifiers(scope, config, true);
	}

	/**
	 * @return a verifier with assertions disabled
	 */
	public GuavaVerifiers withAssertionsDisabled()
	{
		if (!assertionsEnabled)
			return this;
		return new GuavaVerifiers(scope, config, false);
	}

	/**
	 * Verifies a {@code Multimap}.
	 *
	 * @param <K>    the type of key in the multimap
	 * @param <V>    the type of value in the multimap
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <K, V> MultimapVerifier<K, V> requireThat(Multimap<K, V> actual, String name)
	{
		verifyName(name);
		return new MultimapVerifierImpl<>(scope, actual, name, config);
	}

	/**
	 * Same as {@link #requireThat(Multimap, String)} but does nothing if assertions are disabled.
	 *
	 * @param <K>    the type of key in the multimap
	 * @param <V>    the type of value in the multimap
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <K, V> MultimapVerifier<K, V> assertThat(Multimap<K, V> actual, String name)
	{
		if (assertionsEnabled)
			return requireThat(actual, name);
		return new NoOpMultimapVerifier<>(config);
	}

	@Override
	public GuavaVerifiers withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig.equals(config))
			return this;
		return new GuavaVerifiers(scope, newConfig, assertionsEnabled);
	}

	@Override
	public GuavaVerifiers withDefaultException()
	{
		Configuration newConfig = config.withDefaultException();
		if (newConfig.equals(config))
			return this;
		return new GuavaVerifiers(scope, newConfig, assertionsEnabled);
	}

	@Override
	public GuavaVerifiers addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		if (newConfig.equals(config))
			return this;
		return new GuavaVerifiers(scope, newConfig, assertionsEnabled);
	}
}
