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
 * Verifies requirements of a parameter.
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
	 * @param name the name of the parameter
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
		this.scope = new MainSingletonScope();
		this.config = Configuration.initial();
	}

	/**
	 * Creates a new requirement verifier. This constructor is meant to be used by automated tests,
	 * not by users.
	 *
	 * @param scope the system configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	@Beta
	public RequirementVerifier(SingletonScope scope)
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
	 * Verifies requirements of an {@code Object}.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public ObjectRequirements<Object> requireThat(Object parameter, String name)
	{
		verifyName(name);
		return new ObjectRequirementsImpl<>(scope, parameter, name, config);
	}

	/**
	 * Verifies requirements of a {@code Collection}.
	 *
	 * @param <E>       the type of element in the collection
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <E> CollectionRequirements<E> requireThat(Collection<E> parameter, String name)
	{
		verifyName(name);
		return new CollectionRequirementsImpl<>(scope, parameter, name, config);
	}

	/**
	 * Verifies requirements of a {@code Comparable}.
	 *
	 * @param <T>       the type of objects that the parameter may be compared to
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T extends Comparable<? super T>> ComparableRequirements<T> requireThat(T parameter,
		String name)
	{
		verifyName(name);
		return new ComparableRequirementsImpl<>(scope, parameter, name, config);
	}

	/**
	 * Verifies requirements of a {@code Number}.
	 *
	 * @param <T>       the type of the number
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T extends Number & Comparable<? super T>> NumberRequirements<T> requireThat(T parameter,
		String name)
	{
		verifyName(name);
		return new NumberRequirementsImpl<>(scope, parameter, name, config);
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
	{
		verifyName(name);
		return new DoubleRequirementsImpl(scope, parameter, name, config);
	}

	/**
	 * Verifies requirements of a {@code BigDecimal}.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public BigDecimalRequirements requireThat(BigDecimal parameter, String name)
	{
		verifyName(name);
		return new BigDecimalRequirementsImpl(scope, parameter, name, config);
	}

	/**
	 * Verifies requirements of a {@code Map}.
	 *
	 * @param <K>       the type of key in the map
	 * @param <V>       the type of value in the map
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <K, V> MapRequirements<K, V> requireThat(Map<K, V> parameter, String name)
	{
		verifyName(name);
		return new MapRequirementsImpl<>(scope, parameter, name, config);
	}

	/**
	 * Verifies requirements of a {@code Path}.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public PathRequirements requireThat(Path parameter, String name)
	{
		verifyName(name);
		return new PathRequirementsImpl(scope, parameter, name, config);
	}

	/**
	 * Verifies requirements of a {@code String}.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public StringRequirements requireThat(String parameter, String name)
	{
		verifyName(name);
		return new StringRequirementsImpl(scope, parameter, name, config);
	}

	/**
	 * Verifies requirements of a {@code Uri}.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public UriRequirements requireThat(URI parameter, String name)
	{
		verifyName(name);
		return new UriRequirementsImpl(scope, parameter, name, config);
	}

	/**
	 * Verifies requirements of a {@code Class}.
	 *
	 * @param <T>       the type of class
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <T> ClassRequirements<T> requireThat(Class<T> parameter, String name)
	{
		verifyName(name);
		return new ClassRequirementsImpl<>(scope, parameter, name, config);
	}

	/**
	 * Verifies requirements of an {@code Optional}.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public OptionalRequirements requireThat(Optional<?> parameter, String name)
	{
		verifyName(name);
		return new OptionalRequirementsImpl(scope, parameter, name, config);
	}
}
