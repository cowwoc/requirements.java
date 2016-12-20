/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.guava;

import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Verifier;
import org.bitbucket.cowwoc.requirements.core.scope.MainSingletonScope;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.util.Configuration;
import org.bitbucket.cowwoc.requirements.guava.impl.NoOpMultimapVerifier;

/**
 * Verifies a parameter if assertions are enabled; otherwise, does nothing.
 * <p>
 * Unlike {@link Requirements}, instances of this class can be configured prior to initiating
 * verification. Doing so causes the same configuration to get reused across runs.
 *
 * @since 3.0.0
 * @author Gili Tzabari
 */
public final class AssertionVerifier implements Verifier<AssertionVerifier>
{
	private final SingletonScope scope;
	private final boolean enabled;
	private final Configuration config;
	private final RequirementVerifier requirementVerifier;

	/**
	 * Creates a new assertion verifier.
	 * <p>
	 * To look up whether assertions are enabled for a particular class, invoke:
	 * <p>
	 * <code>
	 * boolean assertionsEnabled = false;<br>
	 * assert (assertionsEnabled = true);
	 * </code>
	 * <p>
	 * from within the class in question.
	 *
	 * @param enabled true if assertions are enabled for the class being verified
	 */
	public AssertionVerifier(boolean enabled)
	{
		this.scope = MainSingletonScope.INSTANCE;
		this.enabled = enabled;
		this.config = Configuration.initial();
		this.requirementVerifier = new RequirementVerifier(scope, config);
	}

	/**
	 * Creates a new assertion verifier.
	 * <p>
	 * To look up whether assertions are enabled for a particular class, invoke:
	 * <p>
	 * <code>
	 * boolean assertionsEnabled = false;<br>
	 * assert (assertionsEnabled = true);
	 * </code>
	 * <p>
	 * from within the class in question.
	 *
	 * @param scope   the system configuration
	 * @param enabled true if assertions are enabled for the class being verified
	 * @throws AssertionError if {@code scope} is null
	 */
	AssertionVerifier(SingletonScope scope, boolean enabled)
	{
		this.scope = scope;
		this.enabled = enabled;
		this.config = Configuration.initial();
		this.requirementVerifier = new RequirementVerifier(scope, config);
	}

	/**
	 * Creates a new assertion verifier.
	 *
	 * @param enabled true if assertions are enabled for the class being verified
	 * @param config  the instance configuration
	 * @throws AssertionError if {@code scope} or {@code config} are null
	 */
	AssertionVerifier(SingletonScope scope, boolean enabled, Configuration config)
	{
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.enabled = enabled;
		this.config = config;
		this.requirementVerifier = new RequirementVerifier(scope, config);
	}

	/**
	 * @return true if assertions are enabled
	 */
	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * Sets the assertions state of the verifier.
	 *
	 * @param enabled true if assertions are enabled
	 * @return a verifier with the specified assertion state
	 */
	public AssertionVerifier withEnabled(boolean enabled)
	{
		if (enabled == this.enabled)
			return this;
		return new AssertionVerifier(scope, enabled, config);
	}

	@Override
	public AssertionVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new AssertionVerifier(scope, enabled, newConfig);
	}

	@Override
	public AssertionVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new AssertionVerifier(scope, enabled, newConfig);
	}

	@Override
	public AssertionVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new AssertionVerifier(scope, enabled, newConfig);
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Multimap, String)} but does nothing if
	 * assertions are disabled.
	 *
	 * @param <K>    the type of key in the multimap
	 * @param <V>    the type of value in the multimap
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <K, V> MultimapVerifier<K, V> requireThat(Multimap<K, V> actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		@SuppressWarnings("unchecked")
		MultimapVerifier<K, V> result = (MultimapVerifier<K, V>) NoOpMultimapVerifier.INSTANCE;
		return result;
	}

	@Override
	public AssertionVerifier isolate(Consumer<AssertionVerifier> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
