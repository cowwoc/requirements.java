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
import org.bitbucket.cowwoc.requirements.core.impl.ArrayVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.BigDecimalVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.ClassVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.CollectionVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.ComparableVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.DoubleVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.InetAddressVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.MapVerifierImpl;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpArrayVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpBigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpClassVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpCollectionVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpComparableVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpDoubleVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpInetAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpMapVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpNumberVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpOptionalVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpPathVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpStringVerifier;
import org.bitbucket.cowwoc.requirements.core.impl.NoOpUriVerifier;
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
 * Unlike {@link CoreRequirements}, instances of this class are configurable.
 * <p>
 * This class is immutable.
 *
 * @since 3.0.0
 * @author Gili Tzabari
 */
public final class CoreVerifiers implements Configurable<CoreVerifiers>
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
	public CoreVerifiers()
	{
		this(false);
	}

	/**
	 * Creates new verifiers.
	 *
	 * @param assertionsEnabled true if assertions should verify; false if they shouldn't do anything
	 */
	public CoreVerifiers(boolean assertionsEnabled)
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
	public CoreVerifiers(ApplicationScope scope)
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
	public CoreVerifiers(ApplicationScope scope, boolean assertionsEnabled)
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
	CoreVerifiers(ApplicationScope scope, Configuration config, boolean assertionsEnabled)
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
	public CoreVerifiers withAssertionsEnabled()
	{
		if (assertionsEnabled)
			return this;
		return new CoreVerifiers(scope, config, true);
	}

	/**
	 * @return a verifier with assertions disabled
	 */
	public CoreVerifiers withAssertionsDisabled()
	{
		if (!assertionsEnabled)
			return this;
		return new CoreVerifiers(scope, config, false);
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
	 * Same as {@link #requireThat(Object, String)} but does nothing if assertions are disabled.
	 *
	 * @param <T>    the type of the value
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T> ObjectVerifier<T> assertThat(T actual, String name)
	{
		if (assertionsEnabled)
			return requireThat(actual, name);
		return new NoOpObjectVerifier<>(config);
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
	 * Same as {@link #requireThat(Collection, String)} but does nothing if assertions are disabled.
	 *
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <E> CollectionVerifier<E> assertThat(Collection<E> actual, String name)
	{
		if (assertionsEnabled)
			return requireThat(actual, name);
		return new NoOpCollectionVerifier<>(config);
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
	 * Same as {@link #requireThat(Object[], String)} but does nothing if assertions are disabled.
	 *
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <E> ArrayVerifier<E> assertThat(E[] actual, String name)
	{
		if (assertionsEnabled)
			return requireThat(actual, name);
		return new NoOpArrayVerifier<>(config);
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
	 * Same as {@link #requireThat(Comparable, String)} but does nothing if assertions are disabled.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T extends Comparable<? super T>> ComparableVerifier<T> assertThat(T actual,
		String name)
	{
		if (assertionsEnabled)
			return requireThat(actual, name);
		return new NoOpComparableVerifier<>(config);
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
	 * Same as {@link #requireThat(Number, String)} but does nothing if assertions are disabled.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T extends Number & Comparable<? super T>> NumberVerifier<T> assertThat(T actual,
		String name)
	{
		if (assertionsEnabled)
			return requireThat(actual, name);
		return new NoOpNumberVerifier<>(config);
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
	 * Same as {@link #requireThat(Double, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public DoubleVerifier assertThat(Double actual, String name)
	{
		if (assertionsEnabled)
			return requireThat(actual, name);
		return new NoOpDoubleVerifier(config);
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
	 * Same as {@link #requireThat(BigDecimal, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public BigDecimalVerifier assertThat(BigDecimal actual, String name)
	{
		if (assertionsEnabled)
			return requireThat(actual, name);
		return new NoOpBigDecimalVerifier(config);
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
	 * Same as {@link #requireThat(Map, String)} but does nothing if assertions are disabled.
	 *
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <K, V> MapVerifier<K, V> assertThat(Map<K, V> actual, String name)
	{
		if (assertionsEnabled)
			return requireThat(actual, name);
		return new NoOpMapVerifier<>(config);
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
	 * Same as {@link #requireThat(Path, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public PathVerifier assertThat(Path actual, String name)
	{
		if (assertionsEnabled)
			return requireThat(actual, name);
		return new NoOpPathVerifier(config);
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
	 * Same as {@link #requireThat(String, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public StringVerifier assertThat(String actual, String name)
	{
		if (assertionsEnabled)
			return requireThat(actual, name);
		return new NoOpStringVerifier(config);
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
	 * Same as {@link #requireThat(URI, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public UriVerifier assertThat(URI actual, String name)
	{
		if (assertionsEnabled)
			return requireThat(actual, name);
		return new NoOpUriVerifier(config);
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
	 * Same as {@link #requireThat(Class, String)} but does nothing if assertions are disabled.
	 *
	 * @param <T>    the type of class
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T> ClassVerifier<T> assertThat(Class<T> actual, String name)
	{
		if (assertionsEnabled)
			return requireThat(actual, name);
		return new NoOpClassVerifier<>(config);
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
	 * Same as {@link #requireThat(Optional, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public OptionalVerifier assertThat(Optional<?> actual, String name)
	{
		if (assertionsEnabled)
			return requireThat(actual, name);
		return new NoOpOptionalVerifier(config);
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

	/**
	 * Same as {@link #requireThat(InetAddress, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public InetAddressVerifier assertThat(InetAddress actual, String name)
	{
		if (assertionsEnabled)
			return requireThat(actual, name);
		return new NoOpInetAddressVerifier(config);
	}

	@Override
	public CoreVerifiers withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig.equals(config))
			return this;
		return new CoreVerifiers(scope, newConfig, assertionsEnabled);
	}

	@Override
	public CoreVerifiers withDefaultException()
	{
		Configuration newConfig = config.withDefaultException();
		if (newConfig.equals(config))
			return this;
		return new CoreVerifiers(scope, newConfig, assertionsEnabled);
	}

	@Override
	public CoreVerifiers addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		if (newConfig.equals(config))
			return this;
		return new CoreVerifiers(scope, newConfig, assertionsEnabled);
	}
}
