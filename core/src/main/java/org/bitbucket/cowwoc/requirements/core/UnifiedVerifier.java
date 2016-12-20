/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.List;
import java.util.Map.Entry;
import org.bitbucket.cowwoc.requirements.core.scope.MainSingletonScope;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.util.Configuration;

/**
 * Combines the functionality of {@link RequirementVerifier} and {@link AssertionVerifier} into a
 * single class.
 * <p>
 * Unlike {@link Requirements}, instances of this class can be configured prior to initiating
 * verification. Doing so causes the same configuration to get reused across runs.
 *
 * @author Gili Tzabari
 */
public final class UnifiedVerifier extends AbstractUnifiedVerifier
	implements Verifier<UnifiedVerifier>
{
	/**
	 * @return true if assertions are enabled for this class
	 */
	@SuppressWarnings(
		{
			"AssertWithSideEffects", "NestedAssignment"
		})
	private static boolean classAssertionsAreEnabled()
	{
		boolean assertionsEnabled = false;
		assert (assertionsEnabled = true);
		return assertionsEnabled;
	}

	/**
	 * Creates a new verifier.
	 * <p>
	 * This class' assertion status determines whether {@code assertThat()} carries out a verification
	 * or does nothing.
	 */
	public UnifiedVerifier()
	{
		super(MainSingletonScope.INSTANCE, Configuration.initial(), classAssertionsAreEnabled());
	}

	/**
	 * Creates a new verifier.
	 *
	 * @param assertionsEnabled true if {@code assertThat()} should carry out a verification
	 */
	UnifiedVerifier(boolean assertionsEnabled)
	{
		super(MainSingletonScope.INSTANCE, Configuration.initial(), assertionsEnabled);
	}

	/**
	 * @param scope             the system configuration
	 * @param config            the instance configuration
	 * @param assertionsEnabled true if {@code assertThat()} should carry out a verification
	 * @throws AssertionError if any of the arguments are null
	 */
	private UnifiedVerifier(SingletonScope scope, Configuration config, boolean assertionsEnabled)
	{
		super(scope, config, assertionsEnabled);
	}

	@Override
	protected UnifiedVerifier newInstance(SingletonScope scope, Configuration config,
		boolean enabled)
	{
		return new UnifiedVerifier(scope, config, enabled);
	}

	@Override
	public UnifiedVerifier withException(Class<? extends RuntimeException> exception)
	{
		return (UnifiedVerifier) super.withException(exception);
	}

	@Override
	public UnifiedVerifier addContext(String key, Object value)
	{
		return (UnifiedVerifier) super.addContext(key, value);
	}

	@Override
	public UnifiedVerifier withContext(List<Entry<String, Object>> context)
	{
		return (UnifiedVerifier) super.withContext(context);
	}
}
