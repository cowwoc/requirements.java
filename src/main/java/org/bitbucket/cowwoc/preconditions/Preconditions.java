/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Verifies preconditions of a parameter.
 * <p/>
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
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public static <S extends Preconditions<S, T>, T> S requireThat(String name, T parameter)
	{
		@SuppressWarnings("unchecked")
		S self = (S) new Preconditions<>(name, parameter);
		return self;
	}

	/**
	 * Creates new CollectionPreconditions.
	 * <p>
	 * @param <E>       the type of element in the collection
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static <E> CollectionPreconditions<E> requireThat(String name, Collection<E> parameter)
	{
		return new CollectionPreconditions<>(name, parameter);
	}

	/**
	 * Creates new IntegerPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static IntegerPreconditions requireThat(String name, Integer parameter)
	{
		return new IntegerPreconditions(name, parameter);
	}

	/**
	 * Creates new LongPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static LongPreconditions requireThat(String name, Long parameter)
	{
		return new LongPreconditions(name, parameter);
	}

	/**
	 * Creates new MapPreconditions.
	 * <p>
	 * @param <K>       the type of key in the map
	 * @param <V>       the type of value in the map
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static <K, V> MapPreconditions<K, V> requireThat(String name, Map<K, V> parameter)
	{
		return new MapPreconditions<>(name, parameter);
	}

	/**
	 * Creates new PathPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static PathPreconditions requireThat(String name, Path parameter)
	{
		return new PathPreconditions(name, parameter);
	}

	/**
	 * Creates new StringPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static StringPreconditions requireThat(String name, String parameter)
	{
		return new StringPreconditions(name, parameter);
	}

	/**
	 * Creates new UriPreconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static UriPreconditions requireThat(String name, URI parameter)
	{
		return new UriPreconditions(name, parameter);
	}

	/**
	 * Creates new ClassPreconditions.
	 * <p>
	 * @param <T>       the type of class
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static <T> ClassPreconditions<T> requireThat(String name, Class<T> parameter)
	{
		return new ClassPreconditions<>(name, parameter);
	}

	protected final S self;
	protected final String name;
	protected final T parameter;

	/**
	 * Creates new Preconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	protected Preconditions(String name, T parameter)
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
		this.name = name;
		this.parameter = parameter;
	}

	/**
	 * Ensures that the parameter is not null.
	 * <p/>
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
	 * <p/>
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
	 * <p/>
	 * @param value the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if parameter is not equal to value
	 */
	public S isEqualTo(T value)
		throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			throw new IllegalArgumentException(name + " must be equal to " + value + ". Was: " + parameter);
		return self;
	}

	/**
	 * Ensures that the parameter is an instance of a class.
	 * <p/>
	 * @param type the class to compare to
	 * @return this
	 * @throws NullPointerException     if {@code parameter} or {@code type} are null
	 * @throws IllegalArgumentException if {@code parameter} is not an instance of {@code type}
	 */
	public S isInstanceOf(Class<?> type) throws NullPointerException
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
