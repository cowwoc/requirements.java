/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.bitbucket.cowwoc.requirements.core.annotations.Beta;

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
	 * Verifies an {@code Object}.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static ObjectVerifier<Object> requireThat(Object actual, String name)
	{
		return DELEGATE.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Object, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static ObjectVerifier<Object> assertThat(Object actual, String name)
	{
		return DELEGATE.assertThat(actual, name);
	}

	/**
	 * Verifies a {@code Collection}.
	 *
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <E> CollectionVerifier<E> requireThat(Collection<E> actual, String name)
	{
		return DELEGATE.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Collection, String)} but does nothing if assertions are disabled
	 * for this class.
	 *
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <E> CollectionVerifier<E> assertThat(Collection<E> actual, String name)
	{
		return DELEGATE.assertThat(actual, name);
	}

	/**
	 * Verifies an array.
	 *
	 * @param <E>    the type of elements in the array
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <E> ArrayVerifier<E> requireThat(E[] actual, String name)
	{
		return DELEGATE.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Object[], String)} but does nothing if assertions are disabled
	 * for this class.
	 *
	 * @param <E>    the type of elements in the array
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <E> ArrayVerifier<E> assertThat(E[] actual, String name)
	{
		return DELEGATE.requireThat(actual, name);
	}

	/**
	 * Verifies a {@code Comparable}.
	 *
	 * @param <T>    the type of objects that the parameter may be compared to
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T extends Comparable<? super T>> ComparableVerifier<T> requireThat(T actual,
		String name)
	{
		return DELEGATE.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Comparable, String)} but does nothing if assertions are disabled
	 * for this class.
	 *
	 * @param <T>    the type of objects that the parameter may be compared to
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T extends Comparable<? super T>> ComparableVerifier<T> assertThat(T actual,
		String name)
	{
		return DELEGATE.assertThat(actual, name);
	}

	/**
	 * Verifies a {@code Number}.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T extends Number & Comparable<? super T>> NumberVerifier<T> requireThat(
		T actual, String name)
	{
		return DELEGATE.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Number, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T extends Number & Comparable<? super T>> NumberVerifier<T> assertThat(
		T actual, String name)
	{
		return DELEGATE.assertThat(actual, name);
	}

	/**
	 * Verifies a {@code Double}.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static DoubleVerifier requireThat(Double actual, String name)
	{
		return DELEGATE.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Double, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static DoubleVerifier assertThat(Double actual, String name)
	{
		return DELEGATE.assertThat(actual, name);
	}

	/**
	 * Verifies a {@code BigDecimal}.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static BigDecimalVerifier requireThat(BigDecimal actual, String name)
	{
		return DELEGATE.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(BigDecimal, String)} but does nothing if assertions are disabled
	 * for this class.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static BigDecimalVerifier assertThat(BigDecimal actual, String name)
	{
		return DELEGATE.assertThat(actual, name);
	}

	/**
	 * Verifies a {@code Map}.
	 *
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <K, V> MapVerifier<K, V> requireThat(Map<K, V> actual, String name)
	{
		return DELEGATE.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Map, String)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <K, V> MapVerifier<K, V> assertThat(Map<K, V> actual, String name)
	{
		return DELEGATE.assertThat(actual, name);
	}

	/**
	 * Verifies a {@code Path}.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PathVerifier requireThat(Path actual, String name)
	{
		return DELEGATE.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Path, String)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static PathVerifier assertThat(Path actual, String name)
	{
		return DELEGATE.assertThat(actual, name);
	}

	/**
	 * Verifies a {@code String}.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static StringVerifier requireThat(String actual, String name)
	{
		return DELEGATE.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(String, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static StringVerifier assertThat(String actual, String name)
	{
		return DELEGATE.assertThat(actual, name);
	}

	/**
	 * Verifies a {@code Uri}.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static UriVerifier requireThat(URI actual, String name)
	{
		return DELEGATE.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(URI, String)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static UriVerifier assertThat(URI actual, String name)
	{
		return DELEGATE.assertThat(actual, name);
	}

	/**
	 * Verifies a {@code Class}.
	 *
	 * @param <T>    the type of class
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T> ClassVerifier<T> requireThat(Class<T> actual, String name)
	{
		return DELEGATE.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Class, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param <T>    the type of class
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static <T> ClassVerifier<T> assertThat(Class<T> actual, String name)
	{
		return DELEGATE.assertThat(actual, name);
	}

	/**
	 * Verifies an {@code Optional}.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static OptionalVerifier requireThat(Optional<?> actual, String name)
	{
		return DELEGATE.requireThat(actual, name);
	}

	/**
	 * Same as {@link #requireThat(Optional, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @return a verifier for the parameter
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	public static OptionalVerifier assertThat(Optional<?> actual, String name)
	{
		return DELEGATE.assertThat(actual, name);
	}

	/**
	 * Prevent construction.
	 */
	private Requirements()
	{
	}
}
