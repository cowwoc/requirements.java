/*
 * Copyright 2016 Gili Tzabari.
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
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import org.bitbucket.cowwoc.requirements.spi.Verifier;

/**
 * An abstract class used to hide test methods from end-users.
 *
 * @author Gili Tzabari
 */
abstract class AbstractUnifiedVerifier implements Verifier
{
	private final SingletonScope scope;
	private final Configuration config;
	private final AssertionVerifier assertions;
	private final RequirementVerifier requirements;

	/**
	 * @param scope             the system configuration
	 * @param config            the instance configuration
	 * @param assertionsEnabled true if {@code assertThat()} should carry out a verification
	 * @throws AssertionError if any of the arguments are null
	 */
	AbstractUnifiedVerifier(SingletonScope scope, Configuration config, boolean assertionsEnabled)
	{
		this.scope = scope;
		this.config = config;
		this.assertions = new AssertionVerifier(scope, assertionsEnabled, config);
		this.requirements = new RequirementVerifier(scope, config);
	}

	/**
	 * @param scope   the system configuration
	 * @param enabled true if assertions are enabled for the class whose requirements are being
	 *                verified
	 * @param config  the instance configuration
	 * @return a new assertion verifier
	 * @throws AssertionError if {@code scope} or {@code config} are null
	 */
	protected abstract AbstractUnifiedVerifier newInstance(SingletonScope scope,
		Configuration config, boolean enabled);

	/**
	 * @return true if {@code assertThat()} carries out a verification
	 */
	public boolean assertionsAreEnabled()
	{
		return assertions.isEnabled();
	}

	/**
	 * Verifies an {@code Object}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T> ObjectRequirements<T> requireThat(T actual, String name)
	{
		return requirements.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Object, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param <T>    the type of the parameter
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T> ObjectRequirements<T> assertThat(T actual, String name)
	{
		return assertions.requireThat(actual, name);
	}

	/**
	 * Verifies a {@code Collection}.
	 *
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <E> CollectionRequirements<E> requireThat(Collection<E> actual, String name)
	{
		return requirements.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Collection, String)} but does nothing if assertions are disabled
	 * for this class.
	 *
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <E> CollectionRequirements<E> assertThat(Collection<E> actual, String name)
	{
		return assertions.requireThat(actual, name);
	}

	/**
	 * Verifies an array.
	 * <p>
	 * @param <E>    the type of elements in the array
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <E> ArrayRequirements<E> requireThat(E[] actual, String name)
	{
		return requirements.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Object[], String)} but does nothing if assertions are disabled.
	 * <p>
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <E> ArrayRequirements<E> assertThat(E[] actual, String name)
	{
		return assertions.requireThat(actual, name);
	}

	/**
	 * Verifies a {@code Comparable}.
	 *
	 * @param <T>    the type of objects that the parameter may be compared to
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T extends Comparable<? super T>> ComparableRequirements<T> requireThat(T actual,
		String name)
	{
		return requirements.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Comparable, String)} but does nothing if assertions are disabled
	 * for this class.
	 *
	 * @param <T>    the type of objects that the parameter may be compared to
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T extends Comparable<? super T>> ComparableRequirements<T> assertThat(T actual,
		String name)
	{
		return assertions.requireThat(actual, name);
	}

	/**
	 * Verifies a {@code Number}.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T extends Number & Comparable<? super T>> NumberRequirements<T> requireThat(T actual,
		String name)
	{
		return requirements.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Number, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T extends Number & Comparable<? super T>> NumberRequirements<T> assertThat(T actual,
		String name)
	{
		return assertions.requireThat(actual, name);
	}

	/**
	 * Verifies a {@code Double}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public DoubleRequirements requireThat(Double actual, String name)
	{
		return requirements.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Double, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public DoubleRequirements assertThat(Double actual, String name)
	{
		return assertions.requireThat(actual, name);
	}

	/**
	 * Verifies a {@code BigDecimal}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public BigDecimalRequirements requireThat(BigDecimal actual, String name)
	{
		return requirements.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(BigDecimal, String)} but does nothing if assertions are disabled
	 * for this class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public BigDecimalRequirements assertThat(BigDecimal actual, String name)
	{
		return assertions.requireThat(actual, name);
	}

	/**
	 * Verifies a {@code Map}.
	 *
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <K, V> MapRequirements<K, V> requireThat(Map<K, V> actual, String name)
	{
		return requirements.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Map, String)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <K, V> MapRequirements<K, V> assertThat(Map<K, V> actual, String name)
	{
		return assertions.requireThat(actual, name);
	}

	/**
	 * Verifies a {@code Path}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public PathRequirements requireThat(Path actual, String name)
	{
		return requirements.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Path, String)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public PathRequirements assertThat(Path actual, String name)
	{
		return assertions.requireThat(actual, name);
	}

	/**
	 * Verifies a {@code String}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public StringRequirements requireThat(String actual, String name)
	{
		return requirements.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(String, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public StringRequirements assertThat(String actual, String name)
	{
		return assertions.requireThat(actual, name);
	}

	/**
	 * Verifies a {@code Uri}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public UriRequirements requireThat(URI actual, String name)
	{
		return requirements.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(URI, String)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public UriRequirements assertThat(URI actual, String name)
	{
		return assertions.requireThat(actual, name);
	}

	/**
	 * Verifies a {@code Class}.
	 *
	 * @param <T>    the type of class
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T> ClassRequirements<T> requireThat(Class<T> actual, String name)
	{
		return requirements.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Class, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param <T>    the type of class
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T> ClassRequirements<T> assertThat(Class<T> actual, String name)
	{
		return assertions.requireThat(actual, name);
	}

	/**
	 * Verifies an {@code Optional}.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public OptionalRequirements requireThat(Optional<?> actual, String name)
	{
		return requirements.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Optional, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public OptionalRequirements assertThat(Optional<?> actual, String name)
	{
		return assertions.requireThat(actual, name);
	}

	@Override
	public AbstractUnifiedVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return newInstance(scope, newConfig, assertions.isEnabled());
	}

	@Override
	public AbstractUnifiedVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return newInstance(scope, newConfig, assertions.isEnabled());
	}

	@Override
	public AbstractUnifiedVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return newInstance(scope, newConfig, assertions.isEnabled());
	}
}
