/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Path;
import java.time.Year;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Verifies preconditions of a parameter.
 * <p>
 * All implementations must be immutable and (when possible) final.
 * <p>
 * @author Gili Tzabari
 */
public final class Preconditions
{
	/**
	 * Creates a precondition for an {@code Object}.
	 * <p>
	 * @param <S>       the type of preconditions that was instantiated
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static <S extends ObjectPreconditions<S, Object>> S requireThat(Object parameter,
		String name)
		throws NullPointerException, IllegalArgumentException
	{
		@SuppressWarnings("unchecked")
		S self = (S) new ObjectPreconditionsImpl<>(parameter, name, Optional.empty());
		return self;
	}

	/**
	 * Creates a precondition for a {@code Collection}.
	 * <p>
	 * @param <E>       the type of element in the collection
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static <E> CollectionPreconditions<E> requireThat(Collection<E> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new CollectionPreconditionsImpl<>(parameter, name, Optional.empty());
	}

	/**
	 * Creates a precondition for a {@code Number}.
	 * <p>
	 * @param <S>       the type of preconditions that was instantiated
	 * @param <T>       the type of the number
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static <S extends NumberPreconditions<S, T>, T extends Number & Comparable<? super T>>
		NumberPreconditions<S, T> requireThat(T parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new NumberPreconditionsImpl<>(parameter, name, Optional.empty());
	}

	/**
	 * Creates a precondition for a {@code BigDecimal}.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static BigDecimalPreconditions requireThat(BigDecimal parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new BigDecimalPreconditionsImpl(parameter, name, Optional.empty());
	}

	/**
	 * Creates a precondition for a {@code Map}.
	 * <p>
	 * @param <K>       the type of key in the map
	 * @param <V>       the type of value in the map
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static <K, V> MapPreconditions<K, V> requireThat(Map<K, V> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new MapPreconditionsImpl<>(parameter, name, Optional.empty());
	}

	/**
	 * Creates a precondition for a {@code Path}.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static PathPreconditions requireThat(Path parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new PathPreconditionsImpl(parameter, name, Optional.empty());
	}

	/**
	 * Creates a precondition for a {@code String}.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static StringPreconditions requireThat(String parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new StringPreconditionsImpl(parameter, name, Optional.empty());
	}

	/**
	 * Creates a precondition for a {@code Uri}.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static UriPreconditions requireThat(URI parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new UriPreconditionsImpl(parameter, name, Optional.empty());
	}

	/**
	 * Creates a precondition for a {@code Class}.
	 * <p>
	 * @param <T>       the type of class
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static <T> ClassPreconditions<T> requireThat(Class<T> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new ClassPreconditionsImpl<>(parameter, name, Optional.empty());
	}

	/**
	 * Creates a precondition for a {@code Year}.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static YearPreconditions requireThat(Year parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new YearPreconditionsImpl(parameter, name, Optional.empty());
	}

	/**
	 * Creates a precondition for an {@code Optional}.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static OptionalPreconditions requireThat(Optional<?> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		return new OptionalPreconditionsImpl(parameter, name, Optional.empty());
	}

	/**
	 * Prevent construction.
	 */
	private Preconditions()
	{
	}
}
