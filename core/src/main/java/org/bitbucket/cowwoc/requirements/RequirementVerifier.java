/*
 * Copyright 2013 Gili Tzabari.
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
 * Verifies a parameter.
 * <p>
 * Unlike {@link Requirements}, instances of this class can be configured prior to initiating
 * verification. Doing so causes the same configuration to get reused across runs.
 *
 * @since 2.0.3
 * @author Gili Tzabari
 */
public final class RequirementVerifier implements Verifier
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
	private final SingletonScope scope;
	private final Configuration config;

	/**
	 * Creates a new requirement verifier.
	 */
	public RequirementVerifier()
	{
		this.scope = MainSingletonScope.INSTANCE;
		this.config = Configuration.initial();
	}

	/**
	 * Creates a new requirement verifier. This constructor is meant to be used by automated tests,
	 * not by users.
	 *
	 * @param scope the system configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	RequirementVerifier(SingletonScope scope)
	{
		assert (scope != null): "scope may not be null";
		this.scope = scope;
		this.config = Configuration.initial();
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
		assert (scope != null): "scope may not be null";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.config = config;
	}

	@Override
	public RequirementVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new RequirementVerifier(scope, newConfig);
	}

	@Beta
	@Override
	public RequirementVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new RequirementVerifier(scope, newConfig);
	}

	@Beta
	@Override
	public RequirementVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new RequirementVerifier(scope, newConfig);
	}

	/**
	 * Verifies an {@code Object}.
	 *
	 * @param <T>    the type of the parameter
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T> ObjectVerifier<T> requireThat(T actual, String name)
	{
		verifyName(name);
		return new ObjectVerifierImpl<>(scope, actual, name, config);
	}

	/**
	 * Verifies a {@code Collection}.
	 *
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <E> CollectionVerifier<E> requireThat(Collection<E> actual, String name)
	{
		verifyName(name);
		return new CollectionVerifierImpl<>(scope, actual, name, config, Pluralizer.ELEMENT);
	}

	/**
	 * Verifies a primitive array.
	 *
	 * @param <E>    the type of elements in the array
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <E> ArrayVerifier<E> requireThat(E[] actual, String name)
	{
		verifyName(name);
		return new ArrayVerifierImpl<>(scope, actual, name, config);
	}

	/**
	 * Verifies a {@code Comparable}.
	 *
	 * @param <T>    the type of objects that the parameter may be compared to
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T extends Comparable<? super T>> ComparableVerifier<T> requireThat(T actual,
		String name)
	{
		verifyName(name);
		return new ComparableVerifierImpl<>(scope, actual, name, config);
	}

	/**
	 * Verifies a {@code Number}.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T extends Number & Comparable<? super T>> NumberVerifier<T> requireThat(T actual,
		String name)
	{
		verifyName(name);
		return new NumberVerifierImpl<>(scope, actual, name, config);
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
	public DoubleVerifier requireThat(Double actual, String name)
	{
		verifyName(name);
		return new DoubleVerifierImpl(scope, actual, name, config);
	}

	/**
	 * Verifies a {@code BigDecimal}.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public BigDecimalVerifier requireThat(BigDecimal actual, String name)
	{
		verifyName(name);
		return new BigDecimalVerifierImpl(scope, actual, name, config);
	}

	/**
	 * Verifies a {@code Map}.
	 *
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <K, V> MapVerifier<K, V> requireThat(Map<K, V> actual, String name)
	{
		verifyName(name);
		return new MapVerifierImpl<>(scope, actual, name, config);
	}

	/**
	 * Verifies a {@code Path}.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public PathVerifier requireThat(Path actual, String name)
	{
		verifyName(name);
		return new PathVerifierImpl(scope, actual, name, config);
	}

	/**
	 * Verifies a {@code String}.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public StringVerifier requireThat(String actual, String name)
	{
		verifyName(name);
		return new StringVerifierImpl(scope, actual, name, config);
	}

	/**
	 * Verifies a {@code Uri}.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public UriVerifier requireThat(URI actual, String name)
	{
		verifyName(name);
		return new UriVerifierImpl(scope, actual, name, config);
	}

	/**
	 * Verifies a {@code Class}.
	 *
	 * @param <T>    the type of class
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T> ClassVerifier<T> requireThat(Class<T> actual, String name)
	{
		verifyName(name);
		return new ClassVerifierImpl<>(scope, actual, name, config);
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
	public OptionalVerifier requireThat(Optional<?> actual, String name)
	{
		verifyName(name);
		return new OptionalVerifierImpl(scope, actual, name, config);
	}
}
