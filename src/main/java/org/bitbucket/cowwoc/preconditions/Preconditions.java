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
 * @param <T> the type of the parameter
 */
public class Preconditions<T>
{
	/**
	 * Creates new Preconditions.
	 * <p>
	 * @param <T>       the type of the parameter
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static <T> Preconditions<T> requireThat(String name, T parameter)
	{
		return new Preconditions<>(name, parameter);
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
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static <K> MapPreconditions<K> requireThat(String name, Map<K, ?> parameter)
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

	protected final String name;
	protected final T parameter;

	/**
	 * Creates new Preconditions.
	 * <p>
	 * @param name      the name of the parameter
	 * @param parameter the value of the parameter
	 * @throws NullPointerException if name is null
	 */
	protected Preconditions(String name, T parameter)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		this.name = name;
		this.parameter = parameter;
	}

	/**
	 * Ensures that the parameter is not null.
	 * <p/>
	 * @return this
	 * @throws NullPointerException if parameter is null
	 */
	public Preconditions<T> isNotNull() throws NullPointerException
	{
		if (parameter == null)
			throw new NullPointerException(name + " may not be null");
		return this;
	}

	/**
	 * Ensures that the parameter is not null.
	 * <p/>
	 * @return this
	 * @throws IllegalStateException if parameter is null
	 */
	public Preconditions<T> stateIsNotNull() throws IllegalStateException
	{
		if (parameter == null)
			throw new IllegalStateException(name + " may not be null");
		return this;
	}

	/**
	 * Ensures that the parameter is equal to a value.
	 * <p/>
	 * @param value the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if parameter is not equal to value
	 */
	public Preconditions<T> isEqualTo(Object value)
		throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			throw new IllegalArgumentException(name + " must be equal to " + value + ". Was: " + parameter);
		return this;
	}

	/**
	 * Ensures that the parameter is an instance of a class.
	 * <p/>
	 * @param type the class to compare to
	 * @return this
	 * @throws NullPointerException     if {@code type} is null
	 * @throws IllegalArgumentException if {@code parameter} is not an instance of {@code type}
	 */
	public Preconditions<T> isInstanceOf(Class<?> type) throws NullPointerException
	{
		if (type == null)
			throw new NullPointerException("type may not be null");
		if (!type.isInstance(parameter))
		{
			Class<?> actualType;
			if (parameter == null)
				actualType = null;
			else
				actualType = parameter.getClass();
			throw new IllegalArgumentException(name + " must be an instance of " + type + ". Was: " +
				actualType);
		}
		return this;
	}
}
