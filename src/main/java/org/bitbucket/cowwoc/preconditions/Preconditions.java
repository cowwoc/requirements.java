/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Verifies preconditions of a parameter.
 * <p>
 * @author Gili Tzabari
 * @param <S> the type of preconditions that was instantiated
 * @param <T> the type of the parameter
 */
public class Preconditions<S extends Preconditions<S, T>, T>
{
	/**
	 * Creates new Preconditions.
	 * <p>
	 * @param <S>       the type of preconditions that was instantiated
	 * @param <T>       the type of the parameter
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static <S extends Preconditions<S, T>, T> S requireThat(T parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		@SuppressWarnings("unchecked")
		S self = (S) new Preconditions<>(parameter, name);
		return self;
	}

	/**
	 * Creates new CollectionPreconditions.
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
		return new CollectionPreconditions<>(parameter, name);
	}

	/**
	 * Creates new NumberPreconditions.
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
		return new NumberPreconditions<>(parameter, name);
	}

	/**
	 * Creates new BigDecimalPreconditions.
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
		return new BigDecimalPreconditions(parameter, name);
	}

	/**
	 * Creates new MapPreconditions.
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
		return new MapPreconditions<>(parameter, name);
	}

	/**
	 * Creates new PathPreconditions.
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
		return new PathPreconditions(parameter, name);
	}

	/**
	 * Creates new StringPreconditions.
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
		return new StringPreconditions(parameter, name);
	}

	/**
	 * Creates new UriPreconditions.
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
		return new UriPreconditions(parameter, name);
	}

	/**
	 * Creates new ClassPreconditions.
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
		return new ClassPreconditions<>(parameter, name);
	}

	protected final S self;
	protected final T parameter;
	protected final String name;

	/**
	 * Creates new Preconditions.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	protected Preconditions(T parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		if (name.trim().isEmpty())
			throw new IllegalArgumentException("name may not be empty");
		@SuppressWarnings(
			{
				"unchecked", "LocalVariableHidesMemberVariable"
			})
		S self = (S) this;
		this.self = self;
		this.parameter = parameter;
		this.name = name;
	}

	/**
	 * Ensures that the parameter is not null.
	 * <p>
	 * @return this
	 * @throws NullPointerException if parameter is null
	 */
	public S isNotNull() throws NullPointerException
	{
		if (parameter == null)
			throw new NullPointerException(name + " may not be null");
		return self;
	}

	/**
	 * Ensures that the parameter is not null.
	 * <p>
	 * @return this
	 * @throws IllegalStateException if parameter is null
	 */
	public S stateIsNotNull() throws IllegalStateException
	{
		if (parameter == null)
			throw new IllegalStateException(name + " may not be null");
		return self;
	}

	/**
	 * Ensures that the parameter is equal to a value.
	 * <p>
	 * @param value the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if parameter is not equal to value
	 */
	public S isEqualTo(T value) throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			throw new IllegalArgumentException(name + " must be equal to " + value + ". Was: " + parameter);
		return self;
	}

	/**
	 * Ensures that the parameter is an instance of a class.
	 * <p>
	 * @param type the class to compare to
	 * @return this
	 * @throws NullPointerException     if {@code parameter} or {@code type} are null
	 * @throws IllegalArgumentException if {@code parameter} is not an instance of {@code type}
	 */
	public S isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		if (parameter == null)
			throw new NullPointerException("parameter may not be null");
		if (type == null)
			throw new NullPointerException("type may not be null");
		if (!type.isInstance(parameter))
		{
			throw new IllegalArgumentException(name + " must be an instance of " + type + ". Was: " +
				parameter.getClass());
		}
		return self;
	}
}
