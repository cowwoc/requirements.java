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
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Verifies requirements of a parameter if assertions are enabled; otherwise, does nothing.
 * <p>
 * Unlike {@link Requirements}, instances of this class can be configured prior to initiating
 * verification. Doing so causes the same configuration to get reused across runs.
 *
 * @since 2.0.3
 * @author Gili Tzabari
 */
public final class AssertionVerifier implements Verifier
{
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
	 * <p>
	 * @param enabled true if assertions are enabled for the class whose requirements are being
	 *                verified
	 */
	public AssertionVerifier(boolean enabled)
	{
		this.enabled = enabled;
		this.config = Configuration.initial();
		this.requirementVerifier = new RequirementVerifier(config);
	}

	/**
	 * Creates a new assertion verifier.
	 *
	 * @param enabled true if assertions are enabled for the class whose requirements are being
	 *                verified
	 * @param config  determines the behavior of this verifier
	 * @throws AssertionError if {@code config} is null
	 */
	AssertionVerifier(boolean enabled, Configuration config) throws AssertionError
	{
		assert (config != null): "config may not be null";
		this.enabled = enabled;
		this.config = config;
		this.requirementVerifier = new RequirementVerifier(config);
	}

	/**
	 * @return true if assertions are enabled
	 */
	public boolean isEnabled()
	{
		return enabled;
	}

	@Override
	public Verifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new AssertionVerifier(enabled, newConfig);
	}

	@Override
	public Verifier addContext(String key, Object value) throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		return new AssertionVerifier(enabled, newConfig);
	}

	@Override
	public Verifier withContext(List<Entry<String, Object>> context) throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new AssertionVerifier(enabled, newConfig);
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Object, String)} but does nothing if assertions
	 * are disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public ObjectRequirements<Object> requireThat(Object parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return requirementVerifier.requireThat(parameter, name);
		return NoOpObjectRequirements.INSTANCE;
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Collection, String)} but does nothing if
	 * assertions are disabled.
	 * <p>
	 * @param <E>       the type of element in the collection
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <E> CollectionRequirements<E> requireThat(Collection<E> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return requirementVerifier.requireThat(parameter, name);
		return new NoOpCollectionRequirements<>();
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Comparable, String)} but does nothing if
	 * assertions are disabled.
	 * <p>
	 * @param <T>       the type of the number
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T extends Comparable<? super T>> ComparableRequirements<T> requireThat(T parameter,
		String name) throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return requirementVerifier.requireThat(parameter, name);
		return new NoOpComparableRequirements<>();
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Number, String)} but does nothing if assertions
	 * are disabled.
	 *
	 * @param <T>       the type of the number
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T extends Number & Comparable<? super T>> NumberRequirements<T> requireThat(T parameter,
		String name) throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return requirementVerifier.requireThat(parameter, name);
		return new NoOpNumberRequirements<>();
	}

	/**
	 * Verifies requirements of a {@code Double}.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public DoubleRequirements requireThat(Double parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return requirementVerifier.requireThat(parameter, name);
		return NoOpDoubleRequirements.INSTANCE;
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(BigDecimal, String)} but does nothing if
	 * assertions are disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public BigDecimalRequirements requireThat(BigDecimal parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return requirementVerifier.requireThat(parameter, name);
		return NoOpBigDecimalRequirements.INSTANCE;
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Map, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param <K>       the type of key in the map
	 * @param <V>       the type of value in the map
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <K, V> MapRequirements<K, V> requireThat(Map<K, V> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return requirementVerifier.requireThat(parameter, name);
		@SuppressWarnings("unchecked")
		MapRequirements<K, V> result = (MapRequirements<K, V>) NoOpMapRequirements.INSTANCE;
		return result;
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Path, String)} but does nothing if assertions
	 * are disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public PathRequirements requireThat(Path parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return requirementVerifier.requireThat(parameter, name);
		return NoOpPathRequirements.INSTANCE;
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(String, String)} but does nothing if assertions
	 * are disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public StringRequirements requireThat(String parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return requirementVerifier.requireThat(parameter, name);
		return NoOpStringRequirements.INSTANCE;
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(URI, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public UriRequirements requireThat(URI parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return requirementVerifier.requireThat(parameter, name);
		return NoOpUriRequirements.INSTANCE;
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Class, String)} but does nothing if assertions
	 * are disabled.
	 * <p>
	 * @param <T>       the type of class
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T> ClassRequirements<T> requireThat(Class<T> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return requirementVerifier.requireThat(parameter, name);
		@SuppressWarnings("unchecked")
		ClassRequirements<T> result = (ClassRequirements<T>) NoOpClassRequirements.INSTANCE;
		return result;
	}

	/**
	 * Same as {@link RequirementVerifier#requireThat(Optional, String)} but does nothing if
	 * assertions are disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public OptionalRequirements requireThat(Optional<?> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return requirementVerifier.requireThat(parameter, name);
		return NoOpOptionalRequirements.INSTANCE;
	}
}
