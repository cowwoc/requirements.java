/*
 * Copyright 2015 Gili Tzabari.
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
import org.bitbucket.cowwoc.requirements.core.ext.Configurable;
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
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.scope.MainApplicationScope;

/**
 * Verifies a value if assertions are enabled; otherwise, does nothing.
 * <p>
 * Unlike {@link CoreRequirements}, instances of this class can be configured prior to initiating
 * verification. Doing so causes the same configuration to get reused across runs.
 *
 * @since 2.0.3
 * @author Gili Tzabari
 */
public final class CoreAssertionVerifier implements Configurable<CoreAssertionVerifier>
{
	private final ApplicationScope scope;
	private final boolean enabled;
	private final Configuration config;
	private final CoreRequirementVerifier requirementVerifier;

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
	public CoreAssertionVerifier(boolean enabled)
	{
		this.scope = MainApplicationScope.INSTANCE;
		this.enabled = enabled;
		this.config = scope.getDefaultConfiguration();
		this.requirementVerifier = new CoreRequirementVerifier(scope, config);
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
	 * @param scope   the application configuration
	 * @param enabled true if assertions are enabled for the class being verified
	 * @throws AssertionError if {@code scope} is null
	 */
	CoreAssertionVerifier(ApplicationScope scope, boolean enabled)
	{
		this.scope = scope;
		this.enabled = enabled;
		this.config = scope.getDefaultConfiguration();
		this.requirementVerifier = new CoreRequirementVerifier(scope, config);
	}

	/**
	 * Creates a new assertion verifier.
	 *
	 * @param enabled true if assertions are enabled for the class being verified
	 * @param config  the instance configuration
	 * @throws AssertionError if {@code scope} or {@code config} are null
	 */
	CoreAssertionVerifier(ApplicationScope scope, boolean enabled, Configuration config)
	{
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.enabled = enabled;
		this.config = config;
		this.requirementVerifier = new CoreRequirementVerifier(scope, config);
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
	public CoreAssertionVerifier withEnabled(boolean enabled)
	{
		if (enabled == this.enabled)
			return this;
		return new CoreAssertionVerifier(scope, enabled, config);
	}

	/**
	 * Same as {@link CoreRequirementVerifier#requireThat(Object, String)} but does nothing if
	 * assertions are disabled.
	 *
	 * @param <T>    the type of the value
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T> ObjectVerifier<T> requireThat(T actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpObjectVerifier<>(config);
	}

	/**
	 * Same as {@link CoreRequirementVerifier#requireThat(Collection, String)} but does nothing if
	 * assertions are disabled.
	 *
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <E> CollectionVerifier<E> requireThat(Collection<E> actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpCollectionVerifier<>(config);
	}

	/**
	 * Same as {@link CoreRequirementVerifier#requireThat(Object[], String)} but does nothing if
	 * assertions are disabled.
	 *
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <E> ArrayVerifier<E> requireThat(E[] actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpArrayVerifier<>(config);
	}

	/**
	 * Same as {@link CoreRequirementVerifier#requireThat(Comparable, String)} but does nothing if
	 * assertions are disabled.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T extends Comparable<? super T>> ComparableVerifier<T> requireThat(T actual,
		String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpComparableVerifier<>(config);
	}

	/**
	 * Same as {@link CoreRequirementVerifier#requireThat(Number, String)} but does nothing if
	 * assertions are disabled.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T extends Number & Comparable<? super T>> NumberVerifier<T> requireThat(T actual,
		String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
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
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpDoubleVerifier(config);
	}

	/**
	 * Same as {@link CoreRequirementVerifier#requireThat(BigDecimal, String)} but does nothing if
	 * assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public BigDecimalVerifier requireThat(BigDecimal actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpBigDecimalVerifier(config);
	}

	/**
	 * Same as {@link CoreRequirementVerifier#requireThat(Map, String)} but does nothing if assertions
	 * are disabled.
	 *
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <K, V> MapVerifier<K, V> requireThat(Map<K, V> actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpMapVerifier<>(config);
	}

	/**
	 * Same as {@link CoreRequirementVerifier#requireThat(Path, String)} but does nothing if
	 * assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public PathVerifier requireThat(Path actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpPathVerifier(config);
	}

	/**
	 * Same as {@link CoreRequirementVerifier#requireThat(String, String)} but does nothing if
	 * assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public StringVerifier requireThat(String actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpStringVerifier(config);
	}

	/**
	 * Same as {@link CoreRequirementVerifier#requireThat(URI, String)} but does nothing if assertions
	 * are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public UriVerifier requireThat(URI actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpUriVerifier(config);
	}

	/**
	 * Same as {@link CoreRequirementVerifier#requireThat(Class, String)} but does nothing if
	 * assertions are disabled.
	 *
	 * @param <T>    the type of class
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T> ClassVerifier<T> requireThat(Class<T> actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpClassVerifier<>(config);
	}

	/**
	 * Same as {@link CoreRequirementVerifier#requireThat(Optional, String)} but does nothing if
	 * assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public OptionalVerifier requireThat(Optional<?> actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpOptionalVerifier(config);
	}

	/**
	 * Same as {@link CoreRequirementVerifier#requireThat(InetAddress, String)} but does nothing if
	 * assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public InetAddressVerifier requireThat(InetAddress actual, String name)
	{
		if (enabled)
			return requirementVerifier.requireThat(actual, name);
		return new NoOpInetAddressVerifier(config);
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public CoreAssertionVerifier configuration(Consumer<Configuration> consumer)
	{
		consumer.accept(config);
		return this;
	}
}
