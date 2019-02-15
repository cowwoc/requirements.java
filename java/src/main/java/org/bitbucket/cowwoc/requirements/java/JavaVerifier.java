/*
 * Copyright (c) 2017 Gili Tzabari
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
 * Verifies the requirements of the Java library API.
 * <p>
 * Implementations must be immutable.
 */
public interface JavaVerifier extends Configuration
{
	@Override
	JavaVerifier putContext(String name, Object value);

	@Override
	JavaVerifier removeContext(String name);

	@Override
	JavaVerifier withDefaultException();

	@Override
	JavaVerifier withException(Class<? extends RuntimeException> exception);

	@Override
	JavaVerifier withAssertionsDisabled();

	@Override
	JavaVerifier withAssertionsEnabled();

	@Override
	JavaVerifier withDiff();

	@Override
	JavaVerifier withoutDiff();

	@Override
	<T> JavaVerifier withStringConverter(Class<T> type, Function<T, String> converter);

	@Override
	<T> JavaVerifier withoutStringConverter(Class<T> type);

	@Override
	JavaVerifier withConfiguration(Configuration configuration);

	/**
	 * Verifies the requirements of an {@code Object}.
	 *
	 * @param <T>    the type of the value
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<T> ObjectVerifier<T> requireThat(T actual, String name);

	/**
	 * Same as {@link #requireThat(Object, String)} but does nothing if assertions are disabled.
	 *
	 * @param <T>    the type of the value
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<T> ObjectVerifier<T> assertThat(T actual, String name);

	/**
	 * Verifies the requirements of a {@code Collection}.
	 *
	 * @param <C>    the type of the collection
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<C extends Collection<E>, E> CollectionVerifier<C, E> requireThat(C actual, String name);

	/**
	 * Same as {@link #requireThat(Collection, String)} but does nothing if assertions are disabled.
	 *
	 * @param <C>    the type of the collection
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<C extends Collection<E>, E> CollectionVerifier<C, E> assertThat(C actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code byte} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveByteArrayVerifier requireThat(byte[] actual, String name);

	/**
	 * Same as {@link #requireThat(byte[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveByteArrayVerifier assertThat(byte[] actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code short} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveShortArrayVerifier requireThat(short[] actual, String name);

	/**
	 * Same as {@link #requireThat(short[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveShortArrayVerifier assertThat(short[] actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code int} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveIntegerArrayVerifier requireThat(int[] actual, String name);

	/**
	 * Same as {@link #requireThat(int[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveIntegerArrayVerifier assertThat(int[] actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code long} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveLongArrayVerifier requireThat(long[] actual, String name);

	/**
	 * Same as {@link #requireThat(long[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveLongArrayVerifier assertThat(long[] actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code float} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveFloatArrayVerifier requireThat(float[] actual, String name);

	/**
	 * Same as {@link #requireThat(float[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveFloatArrayVerifier assertThat(float[] actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code double} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveDoubleArrayVerifier requireThat(double[] actual, String name);

	/**
	 * Same as {@link #requireThat(double[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveDoubleArrayVerifier assertThat(double[] actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code boolean} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveBooleanArrayVerifier requireThat(boolean[] actual, String name);

	/**
	 * Same as {@link #requireThat(boolean[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveBooleanArrayVerifier assertThat(boolean[] actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code char} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveCharacterArrayVerifier requireThat(char[] actual, String name);

	/**
	 * Same as {@link #requireThat(char[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveCharacterArrayVerifier assertThat(char[] actual, String name);

	/**
	 * Verifies the requirements of an {@code Object} array.
	 *
	 * @param <E>    the type of elements in the array
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<E> ArrayVerifier<E> requireThat(E[] actual, String name);

	/**
	 * Same as {@link #requireThat(Object[], String)} but does nothing if assertions are disabled.
	 *
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<E> ArrayVerifier<E> assertThat(E[] actual, String name);

	/**
	 * Verifies the requirements of a {@code Comparable}.
	 *
	 * @param <T>    the type of objects that the value may be compared to
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<T extends Comparable<? super T>> ComparableVerifier<T> requireThat(T actual, String name);

	/**
	 * Same as {@link #requireThat(Comparable, String)} but does nothing if assertions are disabled.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<T extends Comparable<? super T>> ComparableVerifier<T> assertThat(T actual, String name);

	/**
	 * Verifies the requirements of a {@code byte}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveNumberVerifier<Byte> requireThat(byte actual, String name);

	/**
	 * Same as {@link #requireThat(byte, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveNumberVerifier<Byte> assertThat(byte actual, String name);

	/**
	 * Verifies the requirements of a {@code short}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveNumberVerifier<Short> requireThat(short actual, String name);

	/**
	 * Same as {@link #requireThat(short, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveNumberVerifier<Short> assertThat(short actual, String name);

	/**
	 * Verifies the requirements of an {@code int}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveIntegerVerifier<Integer> requireThat(int actual, String name);

	/**
	 * Same as {@link #requireThat(int, String)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveIntegerVerifier<Integer> assertThat(int actual, String name);

	/**
	 * Verifies the requirements of an {@code Integer}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	IntegerVerifier<Integer> requireThat(Integer actual, String name);

	/**
	 * Same as {@link #requireThat(Integer, String)} but does nothing if assertions are disabled for this
	 * class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	IntegerVerifier<Integer> assertThat(Integer actual, String name);

	/**
	 * Verifies the requirements of a {@code long}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveIntegerVerifier<Long> requireThat(long actual, String name);

	/**
	 * Same as {@link #requireThat(long, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveIntegerVerifier<Long> assertThat(long actual, String name);

	/**
	 * Verifies the requirements of a {@code Long}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	IntegerVerifier<Long> requireThat(Long actual, String name);

	/**
	 * Same as {@link #requireThat(Long, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	IntegerVerifier<Long> assertThat(Long actual, String name);

	/**
	 * Verifies the requirements of a {@code float}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveFloatingPointVerifier<Float> requireThat(float actual, String name);

	/**
	 * Same as {@link #requireThat(float, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveFloatingPointVerifier<Float> assertThat(float actual, String name);

	/**
	 * Verifies the requirements of a {@code double}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveFloatingPointVerifier<Double> requireThat(double actual, String name);

	/**
	 * Same as {@link #requireThat(double, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveFloatingPointVerifier<Double> assertThat(double actual, String name);

	/**
	 * Verifies the requirements of a {@code boolean}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveBooleanVerifier requireThat(boolean actual, String name);

	/**
	 * Same as {@link #requireThat(boolean, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveBooleanVerifier assertThat(boolean actual, String name);

	/**
	 * Verifies the requirements of a {@code char}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveCharacterVerifier requireThat(char actual, String name);

	/**
	 * Same as {@link #requireThat(char, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PrimitiveCharacterVerifier assertThat(char actual, String name);

	/**
	 * Verifies the requirements of a {@code Number}.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<T extends Number & Comparable<? super T>> NumberVerifier<T> requireThat(T actual, String name);

	/**
	 * Same as {@link #requireThat(Number, String)} but does nothing if assertions are disabled.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	<T extends Number & Comparable<? super T>> NumberVerifier<T> assertThat(T actual, String name);

	/**
	 * Verifies the requirements of a {@code Boolean}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	BooleanVerifier requireThat(Boolean actual, String name);

	/**
	 * Same as {@link #requireThat(Boolean, String)} but does nothing if assertions are disabled for
	 * this class.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	BooleanVerifier assertThat(Boolean actual, String name);

	/**
	 * Verifies the requirements of a {@code Float}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	FloatingPointVerifier<Float> requireThat(Float actual, String name);

	/**
	 * Same as {@link #requireThat(Float, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	FloatingPointVerifier<Float> assertThat(Float actual, String name);

	/**
	 * Verifies the requirements of a {@code Double}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	FloatingPointVerifier<Double> requireThat(Double actual, String name);

	/**
	 * Same as {@link #requireThat(Double, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	FloatingPointVerifier<Double> assertThat(Double actual, String name);

	/**
	 * Verifies the requirements of a {@code BigDecimal}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	BigDecimalVerifier requireThat(BigDecimal actual, String name);

	/**
	 * Same as {@link #requireThat(BigDecimal, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	BigDecimalVerifier assertThat(BigDecimal actual, String name);

	/**
	 * Verifies the requirements of a {@code Map}.
	 *
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<K, V> MapVerifier<K, V> requireThat(Map<K, V> actual, String name);

	/**
	 * Same as {@link #requireThat(Map, String)} but does nothing if assertions are disabled.
	 *
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	<K, V> MapVerifier<K, V> assertThat(Map<K, V> actual, String name);

	/**
	 * Verifies the requirements of a {@code Path}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	PathVerifier requireThat(Path actual, String name);

	/**
	 * Same as {@link #requireThat(Path, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	PathVerifier assertThat(Path actual, String name);

	/**
	 * Verifies the requirements of a {@code String}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	StringVerifier requireThat(String actual, String name);

	/**
	 * Same as {@link #requireThat(String, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	StringVerifier assertThat(String actual, String name);

	/**
	 * Verifies the requirements of a {@code Uri}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	UriVerifier requireThat(URI actual, String name);

	/**
	 * Same as {@link #requireThat(URI, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	UriVerifier assertThat(URI actual, String name);

	/**
	 * Verifies the requirements of a {@code Class}.
	 *
	 * @param <T>    the type of class
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	<T> ClassVerifier<T> requireThat(Class<T> actual, String name);

	/**
	 * Same as {@link #requireThat(Class, String)} but does nothing if assertions are disabled.
	 *
	 * @param <T>    the type of class
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	<T> ClassVerifier<T> assertThat(Class<T> actual, String name);

	/**
	 * Verifies the requirements of an {@code Optional}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	OptionalVerifier requireThat(Optional<?> actual, String name);

	/**
	 * Same as {@link #requireThat(Optional, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	OptionalVerifier assertThat(Optional<?> actual, String name);

	/**
	 * Verifies the requirements of an {@code InetAddress}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	InetAddressVerifier requireThat(InetAddress actual, String name);

	/**
	 * Same as {@link #requireThat(InetAddress, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if name is null
	 * @throws IllegalArgumentException if name is empty
	 */
	InetAddressVerifier assertThat(InetAddress actual, String name);
}
