/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.bitbucket.cowwoc.requirements.annotations.Beta;
import org.bitbucket.cowwoc.requirements.scope.MainSingletonScope;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import org.bitbucket.cowwoc.requirements.spi.Verifier;

/**
 * Verifies a parameter if assertions are enabled; otherwise, does nothing.
 * <p>
 * Unlike {@link Requirements}, instances of this class can be configured prior to initiating
 * verification. Doing so causes the same configuration to get reused across runs.
 *
 * @since 2.0.3
 * @author Gili Tzabari
 */
public final class AssertionVerifier implements Verifier
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
	 * @param enabled true if assertions are enabled for the class whose requirements are being
	 *                verified
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
	 * @param enabled true if assertions are enabled for the class whose requirements are being
	 *                verified
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
	 * @param enabled true if assertions are enabled for the class whose requirements are being
	 *                verified
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
	@Beta
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

	@Beta
	@Override
	public AssertionVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new AssertionVerifier(scope, enabled, newConfig);
	}

	@Beta
	@Override
	public AssertionVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new AssertionVerifier(scope, enabled, newConfig);
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Object, String)} but does nothing if assertions
	 * are disabled.
	 * <p>
	 * @param <T>    the type of the parameter
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T> ObjectRequirements<T> requireThat(T actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpObjectRequirements<>();
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Collection, String)} but does nothing if
	 * assertions are disabled.
	 * <p>
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <E> CollectionRequirements<E> requireThat(Collection<E> actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpCollectionRequirements<>();
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Object[], String)} but does nothing if
	 * assertions are disabled.
	 * <p>
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <E> ArrayRequirements<E> requireThat(E[] actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpArrayRequirements<>();
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Comparable, String)} but does nothing if
	 * assertions are disabled.
	 * <p>
	 * @param <T>    the type of the number
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T extends Comparable<? super T>> ComparableRequirements<T> requireThat(T actual,
		String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpComparableRequirements<>();
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Number, String)} but does nothing if assertions
	 * are disabled.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T extends Number & Comparable<? super T>> NumberRequirements<T> requireThat(T actual,
		String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpNumberRequirements<>();
	}

	/**
	 * Verifies a {@code Double}.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public DoubleRequirements requireThat(Double actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return NoOpDoubleRequirements.INSTANCE;
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(BigDecimal, String)} but does nothing if
	 * assertions are disabled.
	 * <p>
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public BigDecimalRequirements requireThat(BigDecimal actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return NoOpBigDecimalRequirements.INSTANCE;
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Map, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <K, V> MapRequirements<K, V> requireThat(Map<K, V> actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		@SuppressWarnings("unchecked")
		MapRequirements<K, V> result = (MapRequirements<K, V>) NoOpMapRequirements.INSTANCE;
		return result;
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Path, String)} but does nothing if assertions
	 * are disabled.
	 * <p>
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public PathRequirements requireThat(Path actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return NoOpPathRequirements.INSTANCE;
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(String, String)} but does nothing if assertions
	 * are disabled.
	 * <p>
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public StringRequirements requireThat(String actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return NoOpStringRequirements.INSTANCE;
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(URI, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public UriRequirements requireThat(URI actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return NoOpUriRequirements.INSTANCE;
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Class, String)} but does nothing if assertions
	 * are disabled.
	 * <p>
	 * @param <T>    the type of class
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T> ClassRequirements<T> requireThat(Class<T> actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		@SuppressWarnings("unchecked")
		ClassRequirements<T> result = (ClassRequirements<T>) NoOpClassRequirements.INSTANCE;
		return result;
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Optional, String)} but does nothing if
	 * assertions are disabled.
	 * <p>
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public OptionalRequirements requireThat(Optional<?> actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return NoOpOptionalRequirements.INSTANCE;
	}
}
