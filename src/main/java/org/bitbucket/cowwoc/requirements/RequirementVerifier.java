/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.annotations.Beta;
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
		throws NullPointerException, IllegalArgumentException
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		if (name.trim().isEmpty())
			throw new IllegalArgumentException("name may not be empty");
	}
	private final Configuration config;

	/**
	 * Creates a new requirement verifier.
	 */
	public RequirementVerifier()
	{
		this.config = Configuration.initial();
	}

	/**
	 * Creates a new requirement verifier.
	 *
	 * @param config determines the behavior of this verifier
	 * @throws AssertionError if {@code config} is null
	 */
	RequirementVerifier(Configuration config) throws AssertionError
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	public RequirementVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new RequirementVerifier(newConfig);
	}

	@Beta
	@Override
	public RequirementVerifier addContext(String key, Object value) throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		return new RequirementVerifier(newConfig);
	}

	@Beta
	@Override
	public RequirementVerifier withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new RequirementVerifier(newConfig);
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
		throws NullPointerException, IllegalArgumentException
	{
		verifyName(name);
		return new ObjectRequirementsImpl<>(parameter, name, config);
	}

	/**
	 * Verifies requirements of a {@code Collection}.
	 *
	 * @param <E>       the type of elements in the collection
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <E> CollectionRequirements<E> requireThat(Collection<E> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		verifyName(name);
		return new CollectionRequirementsImpl<>(parameter, name, config);
	}

	/**
	 * Verifies requirements of a primitive array.
	 *
	 * @param <E>       the type of elements in the array
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public <E> ArrayRequirements<E> requireThat(E[] parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		verifyName(name);
		return new ArrayRequirementsImpl<>(parameter, name, config);
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
		String name) throws NullPointerException, IllegalArgumentException
	{
		verifyName(name);
		return new ComparableRequirementsImpl<>(parameter, name, config);
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
	public <T extends Number & Comparable<? super T>> NumberRequirements<T> requireThat(
		T parameter, String name) throws NullPointerException, IllegalArgumentException
	{
		verifyName(name);
		return new NumberRequirementsImpl<>(parameter, name, config);
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
		verifyName(name);
		return new DoubleRequirementsImpl(parameter, name, config);
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
		throws NullPointerException, IllegalArgumentException
	{
		verifyName(name);
		return new BigDecimalRequirementsImpl(parameter, name, config);
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
		throws NullPointerException, IllegalArgumentException
	{
		verifyName(name);
		return new MapRequirementsImpl<>(parameter, name, config);
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
		throws NullPointerException, IllegalArgumentException
	{
		verifyName(name);
		return new PathRequirementsImpl(parameter, name, config);
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
		throws NullPointerException, IllegalArgumentException
	{
		verifyName(name);
		return new StringRequirementsImpl(parameter, name, config);
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
		throws NullPointerException, IllegalArgumentException
	{
		verifyName(name);
		return new UriRequirementsImpl(parameter, name, config);
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
		throws NullPointerException, IllegalArgumentException
	{
		verifyName(name);
		return new ClassRequirementsImpl<>(parameter, name, config);
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
		throws NullPointerException, IllegalArgumentException
	{
		verifyName(name);
		return new OptionalRequirementsImpl(parameter, name, config);
	}
}
