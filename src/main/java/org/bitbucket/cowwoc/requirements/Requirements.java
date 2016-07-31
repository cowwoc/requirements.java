/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Verifies requirements of a parameter.
 * <p>
 * All implementations must be immutable and final.
 *
 * @author Gili Tzabari
 */
@SuppressWarnings(
	{
		"AssertWithSideEffects", "NestedAssignment"
	})
public final class Requirements
{
	/**
	 * @param name the name of the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	private static void validateName(String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		if (name.trim().isEmpty())
			throw new IllegalArgumentException("name may not be empty");
	}

	/**
	 * Creates requirements for an {@code Object}.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static ObjectRequirements<Object> requireThat(Object parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		validateName(name);
		return new ObjectRequirementsImpl<>(parameter, name, new Configuration(null));
	}

	/**
	 * Creates requirements for a {@code Collection}.
	 *
	 * @param <E>       the type of element in the collection
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <E> CollectionRequirements<E> requireThat(Collection<E> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		validateName(name);
		return new CollectionRequirementsImpl<>(parameter, name, new Configuration(null));
	}

	/**
	 * Creates requirements for a {@code Comparable}.
	 *
	 * @param <T>       the type of objects that the parameter may be compared to
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T extends Comparable<? super T>> ComparableRequirements<T> requireThat(T parameter,
		String name) throws NullPointerException, IllegalArgumentException
	{
		validateName(name);
		return new ComparableRequirementsImpl<>(parameter, name, new Configuration(null));
	}

	/**
	 * Creates requirements for a {@code Number}.
	 *
	 * @param <T>       the type of the number
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T extends Number & Comparable<? super T>> NumberRequirements<T> requireThat(
		T parameter, String name) throws NullPointerException, IllegalArgumentException
	{
		validateName(name);
		return new NumberRequirementsImpl<>(parameter, name, new Configuration(null));
	}

	/**
	 * Creates requirements for a {@code BigDecimal}.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static BigDecimalRequirements requireThat(BigDecimal parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		validateName(name);
		return new BigDecimalRequirementsImpl(parameter, name, new Configuration(null));
	}

	/**
	 * Creates requirements for a {@code Map}.
	 *
	 * @param <K>       the type of key in the map
	 * @param <V>       the type of value in the map
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <K, V> MapRequirements<K, V> requireThat(Map<K, V> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		validateName(name);
		return new MapRequirementsImpl<>(parameter, name, new Configuration(null));
	}

	/**
	 * Creates requirements for a {@code Path}.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PathRequirements requireThat(Path parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		validateName(name);
		return new PathRequirementsImpl(parameter, name, new Configuration(null));
	}

	/**
	 * Creates requirements for a {@code String}.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static StringRequirements requireThat(String parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		validateName(name);
		return new StringRequirementsImpl(parameter, name, new Configuration(null));
	}

	/**
	 * Creates requirements for a {@code Uri}.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static UriRequirements requireThat(URI parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		validateName(name);
		return new UriRequirementsImpl(parameter, name, new Configuration(null));
	}

	/**
	 * Creates requirements for a {@code Class}.
	 *
	 * @param <T>       the type of class
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T> ClassRequirements<T> requireThat(Class<T> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		validateName(name);
		return new ClassRequirementsImpl<>(parameter, name, new Configuration(null));
	}

	/**
	 * Creates requirements for an {@code Optional}.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static OptionalRequirements requireThat(Optional<?> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		validateName(name);
		return new OptionalRequirementsImpl(parameter, name, new Configuration(null));
	}

	/**
	 * Prevent construction.
	 */
	private Requirements()
	{
	}
}
