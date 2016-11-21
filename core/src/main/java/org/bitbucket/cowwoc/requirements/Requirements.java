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
import org.bitbucket.cowwoc.requirements.annotations.Beta;

/**
 * This convenience class constructs a {@link UnifiedVerifier} with the default configuration.
 * This class' assertion status determines whether {@code assertThat()} carries out a verification
 * or does nothing.
 *
 * @author Gili Tzabari
 */
@SuppressWarnings(
	{
		"AssertWithSideEffects", "NestedAssignment"
	})
public final class Requirements
{
	private static final UnifiedVerifier DELEGATE;

	static
	{
		boolean assertionsEnabled = false;
		assert (assertionsEnabled = true);
		DELEGATE = new UnifiedVerifier(assertionsEnabled);
	}

	/**
	 * @return true if assertions are enabled for this class
	 */
	@Beta
	public static boolean assertionsAreEnabled()
	{
		return DELEGATE.assertionsAreEnabled();
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
	public static ObjectRequirements<Object> requireThat(Object parameter, String name)
	{
		return DELEGATE.requireThat(parameter, name);
	}

	/**
	 * Same as {@link #requireThat(Object, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static ObjectRequirements<Object> assertThat(Object parameter, String name)
	{
		return DELEGATE.assertThat(parameter, name);
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
	public static <E> CollectionRequirements<E> requireThat(Collection<E> parameter, String name)
	{
		return DELEGATE.requireThat(parameter, name);
	}

	/**
	 * Same as {@link #requireThat(Collection, String)} but does nothing if assertions are disabled
	 * for this class.
	 *
	 * @param <E>       the type of elements in the collection
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <E> CollectionRequirements<E> assertThat(Collection<E> parameter, String name)
	{
		return DELEGATE.assertThat(parameter, name);
	}

	/**
	 * Verifies requirements of an array.
	 *
	 * @param <E>       the type of elements in the array
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <E> ArrayRequirements<E> requireThat(E[] parameter, String name)
	{
		return DELEGATE.requireThat(parameter, name);
	}

	/**
	 * Same as {@link #requireThat(Object[], String)} but does nothing if assertions are disabled
	 * for this class.
	 *
	 * @param <E>       the type of elements in the array
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <E> ArrayRequirements<E> assertThat(E[] parameter, String name)
	{
		return DELEGATE.requireThat(parameter, name);
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
	public static <T extends Comparable<? super T>> ComparableRequirements<T> requireThat(T parameter,
		String name)
	{
		return DELEGATE.requireThat(parameter, name);
	}

	/**
	 * Same as {@link #requireThat(Comparable, String)} but does nothing if assertions are disabled
	 * for this class.
	 *
	 * @param <T>       the type of objects that the parameter may be compared to
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T extends Comparable<? super T>> ComparableRequirements<T> assertThat(T parameter,
		String name)
	{
		return DELEGATE.assertThat(parameter, name);
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
	public static <T extends Number & Comparable<? super T>> NumberRequirements<T> requireThat(
		T parameter, String name)
	{
		return DELEGATE.requireThat(parameter, name);
	}

	/**
	 * Same as {@link #requireThat(Number, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param <T>       the type of the number
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T extends Number & Comparable<? super T>> NumberRequirements<T> assertThat(
		T parameter, String name)
	{
		return DELEGATE.assertThat(parameter, name);
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
	public static DoubleRequirements requireThat(Double parameter, String name)
	{
		return DELEGATE.requireThat(parameter, name);
	}

	/**
	 * Same as {@link #requireThat(Double, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static DoubleRequirements assertThat(Double parameter, String name)
	{
		return DELEGATE.assertThat(parameter, name);
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
	public static BigDecimalRequirements requireThat(BigDecimal parameter, String name)
	{
		return DELEGATE.requireThat(parameter, name);
	}

	/**
	 * Same as {@link #requireThat(BigDecimal, String)} but does nothing if assertions are disabled
	 * for this class.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static BigDecimalRequirements assertThat(BigDecimal parameter, String name)
	{
		return DELEGATE.assertThat(parameter, name);
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
	public static <K, V> MapRequirements<K, V> requireThat(Map<K, V> parameter, String name)
	{
		return DELEGATE.requireThat(parameter, name);
	}

	/**
	 * Same as {@link #requireThat(Map, String)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param <K>       the type of key in the map
	 * @param <V>       the type of value in the map
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <K, V> MapRequirements<K, V> assertThat(Map<K, V> parameter, String name)
	{
		return DELEGATE.assertThat(parameter, name);
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
	public static PathRequirements requireThat(Path parameter, String name)
	{
		return DELEGATE.requireThat(parameter, name);
	}

	/**
	 * Same as {@link #requireThat(Path, String)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PathRequirements assertThat(Path parameter, String name)
	{
		return DELEGATE.assertThat(parameter, name);
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
	public static StringRequirements requireThat(String parameter, String name)
	{
		return DELEGATE.requireThat(parameter, name);
	}

	/**
	 * Same as {@link #requireThat(String, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static StringRequirements assertThat(String parameter, String name)
	{
		return DELEGATE.assertThat(parameter, name);
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
	public static UriRequirements requireThat(URI parameter, String name)
	{
		return DELEGATE.requireThat(parameter, name);
	}

	/**
	 * Same as {@link #requireThat(URI, String)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static UriRequirements assertThat(URI parameter, String name)
	{
		return DELEGATE.assertThat(parameter, name);
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
	public static <T> ClassRequirements<T> requireThat(Class<T> parameter, String name)
	{
		return DELEGATE.requireThat(parameter, name);
	}

	/**
	 * Same as {@link #requireThat(Class, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param <T>       the type of class
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T> ClassRequirements<T> assertThat(Class<T> parameter, String name)
	{
		return DELEGATE.assertThat(parameter, name);
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
	public static OptionalRequirements requireThat(Optional<?> parameter, String name)
	{
		return DELEGATE.requireThat(parameter, name);
	}

	/**
	 * Same as {@link #requireThat(Optional, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static OptionalRequirements assertThat(Optional<?> parameter, String name)
	{
		return DELEGATE.assertThat(parameter, name);
	}

	/**
	 * Prevent construction.
	 */
	private Requirements()
	{
	}
}
