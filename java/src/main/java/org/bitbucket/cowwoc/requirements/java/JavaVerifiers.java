/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * An entry point for verifying API requirements.
 * <p>
 * Unlike {@link Requirements}, instances of this interface are configurable.
 * <p>
 * Implementations must be immutable.
 */
public interface JavaVerifiers extends Configurable
{
	@Override
	JavaVerifiers addContext(String name, Object value);

	@Override
	JavaVerifiers withDefaultException();

	@Override
	JavaVerifiers withException(Class<? extends RuntimeException> exception);

	@Override
	JavaVerifiers withAssertionsDisabled();

	@Override
	JavaVerifiers withAssertionsEnabled();

	@Override
	JavaVerifiers withDiff();

	@Override
	JavaVerifiers withoutDiff();

	@Override
	<T> JavaVerifiers withStringConverter(Class<T> type, Function<T, String> converter);

	@Override
	<T> JavaVerifiers withoutStringConverter(Class<T> type);

	/**
	 * Verifies an {@code Object}.
	 *
	 * @param <T>    the type of the value
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<T> ObjectVerifier<T> requireThat(String name, T actual);

	/**
	 * Same as {@link #requireThat(String, Object)} but does nothing if assertions are disabled.
	 *
	 * @param <T>    the type of the value
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	<T> ObjectVerifier<T> assertThat(String name, T actual);

	/**
	 * Verifies a {@code Collection}.
	 *
	 * @param <C>    the type of the collection
	 * @param <E>    the type of elements in the collection
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<C extends Collection<E>, E> CollectionVerifier<C, E> requireThat(String name, C actual);

	/**
	 * Same as {@link #requireThat(String, Collection)} but does nothing if assertions are disabled.
	 *
	 * @param <C>    the type of the collection
	 * @param <E>    the type of elements in the collection
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	<C extends Collection<E>, E> CollectionVerifier<C, E> assertThat(String name, C actual);

	/**
	 * Verifies a primitive {@code byte} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveByteArrayVerifier requireThat(String name, byte[] actual);

	/**
	 * Same as {@link #requireThat(String, byte[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	PrimitiveByteArrayVerifier assertThat(String name, byte[] actual);

	/**
	 * Verifies a primitive {@code short} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveShortArrayVerifier requireThat(String name, short[] actual);

	/**
	 * Same as {@link #requireThat(String, short[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	PrimitiveShortArrayVerifier assertThat(String name, short[] actual);

	/**
	 * Verifies a primitive {@code int} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveIntegerArrayVerifier requireThat(String name, int[] actual);

	/**
	 * Same as {@link #requireThat(String, int[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	PrimitiveIntegerArrayVerifier assertThat(String name, int[] actual);

	/**
	 * Verifies a primitive {@code long} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveLongArrayVerifier requireThat(String name, long[] actual);

	/**
	 * Same as {@link #requireThat(String, long[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	PrimitiveLongArrayVerifier assertThat(String name, long[] actual);

	/**
	 * Verifies a primitive {@code float} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveFloatArrayVerifier requireThat(String name, float[] actual);

	/**
	 * Same as {@link #requireThat(String, float[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	PrimitiveFloatArrayVerifier assertThat(String name, float[] actual);

	/**
	 * Verifies a primitive {@code double} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveDoubleArrayVerifier requireThat(String name, double[] actual);

	/**
	 * Same as {@link #requireThat(String, double[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	PrimitiveDoubleArrayVerifier assertThat(String name, double[] actual);

	/**
	 * Verifies a primitive {@code boolean} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveBooleanArrayVerifier requireThat(String name, boolean[] actual);

	/**
	 * Same as {@link #requireThat(String, boolean[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	PrimitiveBooleanArrayVerifier assertThat(String name, boolean[] actual);

	/**
	 * Verifies a primitive {@code char} array.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveCharacterArrayVerifier requireThat(String name, char[] actual);

	/**
	 * Same as {@link #requireThat(String, char[])} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	PrimitiveCharacterArrayVerifier assertThat(String name, char[] actual);

	/**
	 * Verifies an {@code Object} array.
	 *
	 * @param <E>    the type of elements in the array
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<E> ArrayVerifier<E> requireThat(String name, E[] actual);

	/**
	 * Same as {@link #requireThat(String, Object[])} but does nothing if assertions are disabled.
	 *
	 * @param <E>    the type of elements in the collection
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	<E> ArrayVerifier<E> assertThat(String name, E[] actual);

	/**
	 * Verifies a {@code Comparable}.
	 *
	 * @param <T>    the type of objects that the value may be compared to
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<T extends Comparable<? super T>> ComparableVerifier<T> requireThat(String name, T actual);

	/**
	 * Same as {@link #requireThat(String, Comparable)} but does nothing if assertions are disabled.
	 *
	 * @param <T>    the type of the number
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	<T extends Comparable<? super T>> ComparableVerifier<T> assertThat(String name, T actual);

	/**
	 * Verifies a {@code byte}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveNumberVerifier<Byte> requireThat(String name, byte actual);

	/**
	 * Same as {@link #requireThat(String, byte)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveNumberVerifier<Byte> assertThat(String name, byte actual);

	/**
	 * Verifies a {@code short}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveNumberVerifier<Short> requireThat(String name, short actual);

	/**
	 * Same as {@link #requireThat(String, short)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveNumberVerifier<Short> assertThat(String name, short actual);

	/**
	 * Verifies an {@code int}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveIntegerVerifier<Integer> requireThat(String name, int actual);

	/**
	 * Same as {@link #requireThat(String, int)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveIntegerVerifier<Integer> assertThat(String name, int actual);

	/**
	 * Verifies an {@code Integer}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	IntegerVerifier<Integer> requireThat(String name, Integer actual);

	/**
	 * Same as {@link #requireThat(String, Integer)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	IntegerVerifier<Integer> assertThat(String name, Integer actual);

	/**
	 * Verifies a {@code long}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveIntegerVerifier<Long> requireThat(String name, long actual);

	/**
	 * Same as {@link #requireThat(String, long)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveIntegerVerifier<Long> assertThat(String name, long actual);

	/**
	 * Verifies a {@code Long}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	IntegerVerifier<Long> requireThat(String name, Long actual);

	/**
	 * Same as {@link #requireThat(String, Long)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	IntegerVerifier<Long> assertThat(String name, Long actual);

	/**
	 * Verifies a {@code float}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveFloatingPointVerifier<Float> requireThat(String name, float actual);

	/**
	 * Same as {@link #requireThat(String, float)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveFloatingPointVerifier<Float> assertThat(String name, float actual);

	/**
	 * Verifies a {@code double}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveFloatingPointVerifier<Double> requireThat(String name, double actual);

	/**
	 * Same as {@link #requireThat(String, double)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveFloatingPointVerifier<Double> assertThat(String name, double actual);

	/**
	 * Verifies a {@code boolean}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveBooleanVerifier requireThat(String name, boolean actual);

	/**
	 * Same as {@link #requireThat(String, boolean)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveBooleanVerifier assertThat(String name, boolean actual);

	/**
	 * Verifies a {@code char}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveCharacterVerifier requireThat(String name, char actual);

	/**
	 * Same as {@link #requireThat(String, char)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveCharacterVerifier assertThat(String name, char actual);

	/**
	 * Verifies a {@code Number}.
	 *
	 * @param <T>    the type of the number
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<T extends Number & Comparable<? super T>> NumberVerifier<T> requireThat(String name, T actual);

	/**
	 * Same as {@link #requireThat(String, Number)} but does nothing if assertions are disabled.
	 *
	 * @param <T>    the type of the number
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	<T extends Number & Comparable<? super T>> NumberVerifier<T> assertThat(String name, T actual);

	/**
	 * Verifies a {@code Boolean}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	BooleanVerifier requireThat(String name, Boolean actual);

	/**
	 * Same as {@link #requireThat(String, Boolean)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	BooleanVerifier assertThat(String name, Boolean actual);

	/**
	 * Verifies a {@code Float}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	FloatingPointVerifier<Float> requireThat(String name, Float actual);

	/**
	 * Same as {@link #requireThat(String, Float)} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	FloatingPointVerifier<Float> assertThat(String name, Float actual);

	/**
	 * Verifies a {@code Double}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	FloatingPointVerifier<Double> requireThat(String name, Double actual);

	/**
	 * Same as {@link #requireThat(String, Double)} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	FloatingPointVerifier<Double> assertThat(String name, Double actual);

	/**
	 * Verifies a {@code BigDecimal}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	BigDecimalVerifier requireThat(String name, BigDecimal actual);

	/**
	 * Same as {@link #requireThat(String, BigDecimal)} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	BigDecimalVerifier assertThat(String name, BigDecimal actual);

	/**
	 * Verifies a {@code Map}.
	 *
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<K, V> MapVerifier<K, V> requireThat(String name, Map<K, V> actual);

	/**
	 * Same as {@link #requireThat(String, Map)} but does nothing if assertions are disabled.
	 *
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	<K, V> MapVerifier<K, V> assertThat(String name, Map<K, V> actual);

	/**
	 * Verifies a {@code Path}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PathVerifier requireThat(String name, Path actual);

	/**
	 * Same as {@link #requireThat(String, Path)} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	PathVerifier assertThat(String name, Path actual);

	/**
	 * Verifies a {@code String}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	StringVerifier requireThat(String name, String actual);

	/**
	 * Same as {@link #requireThat(String, String)} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	StringVerifier assertThat(String name, String actual);

	/**
	 * Verifies a {@code Uri}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	UriVerifier requireThat(String name, URI actual);

	/**
	 * Same as {@link #requireThat(String, URI)} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	UriVerifier assertThat(String name, URI actual);

	/**
	 * Verifies a {@code Class}.
	 *
	 * @param <T>    the type of class
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<T> ClassVerifier<T> requireThat(String name, Class<T> actual);

	/**
	 * Same as {@link #requireThat(String, Class)} but does nothing if assertions are disabled.
	 *
	 * @param <T>    the type of class
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	<T> ClassVerifier<T> assertThat(String name, Class<T> actual);

	/**
	 * Verifies an {@code Optional}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	OptionalVerifier requireThat(String name, Optional<?> actual);

	/**
	 * Same as {@link #requireThat(String, Optional)} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	OptionalVerifier assertThat(String name, Optional<?> actual);

	/**
	 * Verifies an {@code InetAddress}.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	InetAddressVerifier requireThat(String name, InetAddress actual);

	/**
	 * Same as {@link #requireThat(String, InetAddress)} but does nothing if assertions are disabled.
	 *
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	InetAddressVerifier assertThat(String name, InetAddress actual);
}
