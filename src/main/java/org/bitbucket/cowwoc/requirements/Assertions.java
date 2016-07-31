/*
 * Copyright 2015 Gili Tzabari.
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
 * Verifies requirements of a parameter if assertions are enabled.
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
	 * @param enabled true if assertions are enabled for the class whose requirements are being
	 *                verified
	 */
	public Assertions(boolean enabled)
	{
		this.enabled = enabled;
	}

	/**
	 * @return true if assertions are enabled
	 */
	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * Same as {@link Requirements#requireThat(Object, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param <S>       the type of requirements that was instantiated
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <S extends ObjectRequirements<S, Object>> S requireThat(Object parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Requirements.requireThat(parameter, name);
		@SuppressWarnings("unchecked")
		S result = (S) NoOpObjectRequirements.INSTANCE;
		return result;
	}

	/**
	 * Same as {@link Requirements#requireThat(Collection, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param <E>       the type of element in the collection
	 * @param <T>       the type of the parameter
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <E, T extends Collection<E>> CollectionRequirements<E, T> requireThat(T parameter,
		String name) throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Requirements.requireThat(parameter, name);
		CollectionRequirements<E, T> result = new NoOpCollectionRequirements<>();
		return result;
	}

	/**
	 * Same as {@link Requirements#requireThat(Comparable, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param <T>       the type of the number
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T extends Comparable<? super T>> ComparableRequirements<T> requireThat(T parameter,
		String name) throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Requirements.requireThat(parameter, name);
		return new NoOpComparableRequirements<>();
	}

	/**
	 * Same as {@link Requirements#requireThat(Number, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param <S>       the type of requirements that was instantiated
	 * @param <T>       the type of the number
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <S extends NumberRequirements<S, T>, T extends Number & Comparable<? super T>>
		NumberRequirements<S, T> requireThat(T parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Requirements.requireThat(parameter, name);
		return new NoOpNumberRequirements<>();
	}

	/**
	 * Same as {@link Requirements#requireThat(BigDecimal, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public BigDecimalRequirements requireThat(BigDecimal parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Requirements.requireThat(parameter, name);
		return NoOpBigDecimalRequirements.INSTANCE;
	}

	/**
	 * Same as {@link Requirements#requireThat(Map, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param <K>       the type of key in the map
	 * @param <V>       the type of value in the map
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <K, V> MapRequirements<K, V> requireThat(Map<K, V> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Requirements.requireThat(parameter, name);
		@SuppressWarnings("unchecked")
		MapRequirements<K, V> result = (MapRequirements<K, V>) NoOpMapRequirements.INSTANCE;
		return result;
	}

	/**
	 * Same as {@link Requirements#requireThat(Path, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public PathRequirements requireThat(Path parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Requirements.requireThat(parameter, name);
		return NoOpPathRequirements.INSTANCE;
	}

	/**
	 * Same as {@link Requirements#requireThat(String, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public StringRequirements requireThat(String parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Requirements.requireThat(parameter, name);
		return NoOpStringRequirements.INSTANCE;
	}

	/**
	 * Same as {@link Requirements#requireThat(URI, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public UriRequirements requireThat(URI parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Requirements.requireThat(parameter, name);
		return NoOpUriRequirements.INSTANCE;
	}

	/**
	 * Same as {@link Requirements#requireThat(Class, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param <T>       the type of class
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public <T> ClassRequirements<T> requireThat(Class<T> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Requirements.requireThat(parameter, name);
		@SuppressWarnings("unchecked")
		ClassRequirements<T> result = (ClassRequirements<T>) NoOpClassRequirements.INSTANCE;
		return result;
	}

	/**
	 * Same as {@link Requirements#requireThat(Year, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public YearRequirements requireThat(Year parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Requirements.requireThat(parameter, name);
		return NoOpYearRequirements.INSTANCE;
	}

	/**
	 * Same as {@link Requirements#requireThat(Optional, String)} but does nothing if assertions are
	 * disabled.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	public OptionalRequirements requireThat(Optional<?> parameter, String name)
		throws NullPointerException, IllegalArgumentException
	{
		if (enabled)
			return Requirements.requireThat(parameter, name);
		return NoOpOptionalRequirements.INSTANCE;
	}
}
