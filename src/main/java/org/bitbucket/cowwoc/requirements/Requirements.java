/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Path;
import java.time.Year;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Verifies requirements of a parameter.
 * <p>
 * All implementations must be immutable and (when possible) final.
 * <p>
 * @author Gili Tzabari
 */
public final class Requirements
{
	/**
	 * Creates a requirement for an {@code Object}.
	 * <p>
	 * @param <S>       the type of requirements that was instantiated
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <S extends ObjectRequirements<S, Object>> S requireThat(Object parameter,
		String name) throws NullPointerException, IllegalArgumentException
	{
		@SuppressWarnings("unchecked")
		S result = (S) new ObjectRequirementsImpl<>(parameter, name, null);
		return result;
	}

	/**
	 * Creates a requirement for a {@code Collection}.
	 * <p>
	 * @param <E>       the type of element in the collection
	 * @param <T>       the type of the parameter
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <E, T extends Collection<E>> CollectionRequirements<E, T> requireThat(T parameter,
		String name) throws NullPointerException, IllegalArgumentException
	{
		return new CollectionRequirementsImpl<>(parameter, name, null);
	}

	/**
	 * Creates a requirement for a {@code Comparable}.
	 * <p>
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
		return new ComparableRequirementsImpl<>(parameter, name, null);
	}

	/**
	 * Creates a requirement for a {@code Number}.
	 * <p>
	 * @param <S>       the type of requirements that was instantiated
	 * @param <T>       the type of the number
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <S extends NumberRequirements<S, T>, T extends Number & Comparable<? super T>>
		NumberRequirements<S, T> requireThat(T parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new NumberRequirementsImpl<>(parameter, name, null);
	}

	/**
	 * Creates a requirement for a {@code BigDecimal}.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static BigDecimalRequirements requireThat(BigDecimal parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new BigDecimalRequirementsImpl(parameter, name, null);
	}

	/**
	 * Creates a requirement for a {@code Map}.
	 * <p>
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
		return new MapRequirementsImpl<>(parameter, name, null);
	}

	/**
	 * Creates a requirement for a {@code Path}.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PathRequirements requireThat(Path parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new PathRequirementsImpl(parameter, name, null);
	}

	/**
	 * Creates a requirement for a {@code String}.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static StringRequirements requireThat(String parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new StringRequirementsImpl(parameter, name, null);
	}

	/**
	 * Creates a requirement for a {@code Uri}.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static UriRequirements requireThat(URI parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new UriRequirementsImpl(parameter, name, null);
	}

	/**
	 * Creates a requirement for a {@code Class}.
	 * <p>
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
		return new ClassRequirementsImpl<>(parameter, name, null);
	}

	/**
	 * Creates a requirement for a {@code Year}.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static YearRequirements requireThat(Year parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new YearRequirementsImpl(parameter, name, null);
	}

	/**
	 * Creates a requirement for an {@code Optional}.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static OptionalRequirements requireThat(Optional<?> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new OptionalRequirementsImpl(parameter, name, null);
	}

	/**
	 * Prevent construction.
	 */
	private Requirements()
	{
	}
}
