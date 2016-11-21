/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.List;
import java.util.Map.Entry;
import org.bitbucket.cowwoc.requirements.annotations.Beta;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Verifies requirements of a parameter.
 * <p>
 * Unlike {@link Requirements}, instances of this class can be configured prior to initiating
 * verification. Doing so causes the same configuration to get reused across runs.
 *
 * @since 2.0.3
 * @author Gili Tzabari
 */
public final class RequirementVerifier extends AbstractRequirementVerifier
{
	/**
	 * Creates a new requirement verifier.
	 */
	public RequirementVerifier()
	{
		super();
	}

	/**
	 * Creates a new requirement verifier.
	 *
	 * @param scope the system configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	public RequirementVerifier(SingletonScope scope)
	{
		super(scope);
	}

	/**
	 * Creates a new requirement verifier.
	 *
	 * @param scope  the system configuration
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope} or {@code config} are null
	 */
	RequirementVerifier(SingletonScope scope, Configuration config)
	{
		super(scope, config);
	}

	@Override
	protected AbstractRequirementVerifier newInstance(SingletonScope scope, Configuration config)
	{
		return new RequirementVerifier(scope, config);
	}

	@Override
	public RequirementVerifier withException(Class<? extends RuntimeException> exception)
	{
		return (RequirementVerifier) super.withException(exception);
	}

	@Beta
	@Override
	public RequirementVerifier addContext(String key, Object value)
	{
		return (RequirementVerifier) super.addContext(key, value);
	}

	@Beta
	@Override
	public RequirementVerifier withContext(List<Entry<String, Object>> context)
	{
		return (RequirementVerifier) super.withContext(context);
	}
}
