/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.impl.ArrayVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.BigDecimalVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.ClassVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.CollectionVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.ComparableVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.DoubleVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.InetAddressVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.MapVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.NumberVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.ObjectVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.OptionalVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.PathVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.Pluralizer;
import org.bitbucket.cowwoc.requirements.core.impl.StringVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.UriVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.scope.MainApplicationScope;

/**
 * Verifies a value.
 * <p>
 * Unlike {@link CoreRequirements}, instances of this class can be configured prior to initiating
 * verification. Doing so causes the same configuration to get reused across runs.
 *
 * @since 2.0.3
 * @author Gili Tzabari
 */
public final class CoreRequirementVerifier implements Configurable<CoreRequirementVerifier>
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
	private final ApplicationScope scope;
	private final Configuration config;

	/**
	 * Creates a new requirement verifier.
	 */
	public CoreRequirementVerifier()
	{
		this.scope = MainApplicationScope.INSTANCE;
		this.config = scope.getDefaultConfiguration();
	}

	/**
	 * Creates a new requirement verifier. This constructor is meant to be used by automated tests,
	 * not by users.
	 *
	 * @param scope the application configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	public CoreRequirementVerifier(ApplicationScope scope)
	{
		assert (scope != null): "scope may not be null";
		this.scope = scope;
		this.config = scope.getDefaultConfiguration();
	}

	/**
	 * Creates a new requirement verifier.
	 *
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope} or {@code config} are null
	 */
	CoreRequirementVerifier(ApplicationScope scope, Configuration config)
	{
		assert (scope != null): "scope may not be null";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.config = config;
	}

	/**
	 * Verifies an {@code Object}.
	 *
	 * @param <T>    the type of the value
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
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
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <E> CollectionVerifier<E> requireThat(Collection<E> actual, String name)
	{
		verifyName(name);
		return new CollectionVerifierImpl<>(scope, actual, name, Pluralizer.ELEMENT, config);
	}

	/**
	 * Verifies a primitive array.
	 *
	 * @param <E>    the type of elements in the array
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
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
	 * @param <T>    the type of objects that the value may be compared to
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T extends Comparable<? super T>> ComparableVerifier<T> requireThat(T actual, String name)
	{
		verifyName(name);
		return new ComparableVerifierImpl<>(scope, actual, name, config);
	}

	/**
	 * Verifies a {@code Number}.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
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
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
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
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
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
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
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
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
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
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
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
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
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
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
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
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public OptionalVerifier requireThat(Optional<?> actual, String name)
	{
		verifyName(name);
		return new OptionalVerifierImpl(scope, actual, name, config);
	}

	/**
	 * Verifies an {@code InetAddress}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public InetAddressVerifier requireThat(InetAddress actual, String name)
	{
		verifyName(name);
		return new InetAddressVerifierImpl(scope, actual, name, config);
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public CoreRequirementVerifier configuration(Consumer<Configuration> consumer)
	{
		consumer.accept(config);
		return this;
	}
}
