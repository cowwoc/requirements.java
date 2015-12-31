/*
 * Copyright 2015 Gili Tzabari.
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
 * Verifies preconditions of a parameter if assertions are enabled.
 * <p>
 * All implementations must be immutable and (when possible) final.
 * <p>
 * @author Gili Tzabari
 */
public final class Assertions
{
	private final boolean enabled;

	/**
	 * Creates a new assertion.
	 * <p>
	 * To look up whether assertions are enabled for a particular class, invoke:
	 * <p>
	 * <code>
	 * boolean assertionsEnabled = false;<br>
	 * assert (assertionsEnabled = true);
	 * </code>
	 * <p>
	 * from within the class in question.
	 * <p>
	 * @param enabled true if assertions are enabled for the class whose preconditions are being
	 *                verified
	 */
	public Assertions(boolean enabled)
	{
		this.enabled = enabled;
	}

	/**
	 * Same as {@link Preconditions#requireThat(Object, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param <S>       the type of preconditions that was instantiated
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <S extends ObjectPreconditions<S, Object>> S requireThat(Object parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Preconditions.requireThat(parameter, name);
		@SuppressWarnings("unchecked")
		S result = (S) NoOpObjectPreconditions.INSTANCE;
		return result;
	}

	/**
	 * Same as {@link Preconditions#requireThat(Collection, String)} but does nothing if assertions
	 * are disabled.
	 * <p>
	 * @param <E>       the type of element in the collection
	 * @param <T>       the type of the parameter
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <E, T extends Collection<E>> CollectionPreconditions<E, T> requireThat(T parameter,
		String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Preconditions.requireThat(parameter, name);
		CollectionPreconditions<E, T> result = new NoOpCollectionPreconditions<>();
		return result;
	}

	/**
	 * Same as {@link Preconditions#requireThat(Number, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param <S>       the type of preconditions that was instantiated
	 * @param <T>       the type of the number
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <S extends NumberPreconditions<S, T>, T extends Number & Comparable<? super T>>
		NumberPreconditions<S, T> requireThat(T parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Preconditions.requireThat(parameter, name);
		return new NoOpNumberPreconditions<>();
	}

	/**
	 * Same as {@link Preconditions#requireThat(BigDecimal, String)} but does nothing if assertions
	 * are disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public BigDecimalPreconditions requireThat(BigDecimal parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Preconditions.requireThat(parameter, name);
		return NoOpBigDecimalPreconditions.INSTANCE;
	}

	/**
	 * Same as {@link Preconditions#requireThat(Map, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param <K>       the type of key in the map
	 * @param <V>       the type of value in the map
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <K, V> MapPreconditions<K, V> requireThat(Map<K, V> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Preconditions.requireThat(parameter, name);
		@SuppressWarnings("unchecked")
		MapPreconditions<K, V> result = (MapPreconditions<K, V>) NoOpMapPreconditions.INSTANCE;
		return result;
	}

	/**
	 * Same as {@link Preconditions#requireThat(Path, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public PathPreconditions requireThat(Path parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Preconditions.requireThat(parameter, name);
		return NoOpPathPreconditions.INSTANCE;
	}

	/**
	 * Same as {@link Preconditions#requireThat(String, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public StringPreconditions requireThat(String parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Preconditions.requireThat(parameter, name);
		return NoOpStringPreconditions.INSTANCE;
	}

	/**
	 * Same as {@link Preconditions#requireThat(URI, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public UriPreconditions requireThat(URI parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Preconditions.requireThat(parameter, name);
		return NoOpUriPreconditions.INSTANCE;
	}

	/**
	 * Same as {@link Preconditions#requireThat(Class, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param <T>       the type of class
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T> ClassPreconditions<T> requireThat(Class<T> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Preconditions.requireThat(parameter, name);
		@SuppressWarnings("unchecked")
		ClassPreconditions<T> result = (ClassPreconditions<T>) NoOpClassPreconditions.INSTANCE;
		return result;
	}

	/**
	 * Same as {@link Preconditions#requireThat(Year, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public YearPreconditions requireThat(Year parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Preconditions.requireThat(parameter, name);
		return NoOpYearPreconditions.INSTANCE;
	}

	/**
	 * Same as {@link Preconditions#requireThat(Optional, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Preconditions for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public OptionalPreconditions requireThat(Optional<?> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Preconditions.requireThat(parameter, name);
		return NoOpOptionalPreconditions.INSTANCE;
	}
}
