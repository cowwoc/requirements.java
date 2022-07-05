/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Verifies the requirements of types from the Java core API.
 */
public interface JavaRequirements extends Configuration
{
	@Override
	JavaRequirements copy();

	@Override
	JavaRequirements withContext(String name, Object value);

	@Override
	JavaRequirements withoutContext(String name);

	@Override
	JavaRequirements withoutAnyContext();

	/**
	 * Returns the contextual information associated with this configuration.
	 *
	 * @param message the exception message ({@code null} if absent)
	 * @return the contextual information associated with this configuration
	 */
	String getContextMessage(String message);

	@Override
	JavaRequirements withAssertionsDisabled();

	@Override
	JavaRequirements withAssertionsEnabled();

	@Override
	JavaRequirements withCleanStackTrace();

	@Override
	JavaRequirements withoutCleanStackTrace();

	@Override
	JavaRequirements withDiff();

	@Override
	JavaRequirements withoutDiff();

	@Override
	<T> JavaRequirements withStringConverter(Class<T> type, Function<T, String> converter);

	@Override
	<T> JavaRequirements withoutStringConverter(Class<T> type);

	@Override
	JavaRequirements withConfiguration(Configuration configuration);

	/**
	 * Verifies the requirements of an {@code Object}.
	 *
	 * @param <T>    the type of the value
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
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
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<T> ObjectVerifier<T> assertThat(T actual, String name);

	/**
	 * Validates the requirements of an {@code Object}.
	 *
	 * @param <T>    the type of the value
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<T> ObjectValidator<T> validateThat(T actual, String name);

	/**
	 * Verifies the requirements of a {@code Collection}.
	 *
	 * @param <C>    the type of the collection
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
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
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<C extends Collection<E>, E> CollectionVerifier<C, E> assertThat(C actual, String name);

	/**
	 * Validates the requirements of a {@code Collection}.
	 *
	 * @param <C>    the type of the collection
	 * @param <E>    the type of elements in the collection
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<C extends Collection<E>, E> CollectionValidator<C, E> validateThat(C actual, String name);

	/**
	 * Verifies the requirements of a {@code List}.
	 *
	 * @param <L>    the type of the list
	 * @param <E>    the type of elements in the list
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<L extends List<E>, E> ListVerifier<L, E> requireThat(L actual, String name);

	/**
	 * Same as {@link #requireThat(List, String)} but does nothing if assertions are disabled.
	 *
	 * @param <L>    the type of the list
	 * @param <E>    the type of elements in the list
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<L extends List<E>, E> ListVerifier<L, E> assertThat(L actual, String name);

	/**
	 * Validates the requirements of a {@code List}.
	 *
	 * @param <L>    the type of the list
	 * @param <E>    the type of elements in the list
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<L extends List<E>, E> ListValidator<L, E> validateThat(L actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code byte} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<byte[], Byte> requireThat(byte[] actual, String name);

	/**
	 * Same as {@link #requireThat(byte[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<byte[], Byte> assertThat(byte[] actual, String name);

	/**
	 * Validates the requirements of a primitive {@code byte} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayValidator<byte[], Byte> validateThat(byte[] actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code short} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<short[], Short> requireThat(short[] actual, String name);

	/**
	 * Same as {@link #requireThat(short[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<short[], Short> assertThat(short[] actual, String name);

	/**
	 * Validates the requirements of a primitive {@code short} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayValidator<short[], Short> validateThat(short[] actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code int} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<int[], Integer> requireThat(int[] actual, String name);

	/**
	 * Same as {@link #requireThat(int[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<int[], Integer> assertThat(int[] actual, String name);

	/**
	 * Validates the requirements of a primitive {@code int} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayValidator<int[], Integer> validateThat(int[] actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code long} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<long[], Long> requireThat(long[] actual, String name);

	/**
	 * Same as {@link #requireThat(long[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<long[], Long> assertThat(long[] actual, String name);

	/**
	 * Validates the requirements of a primitive {@code long} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayValidator<long[], Long> validateThat(long[] actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code float} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<float[], Float> requireThat(float[] actual, String name);

	/**
	 * Same as {@link #requireThat(float[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<float[], Float> assertThat(float[] actual, String name);

	/**
	 * Validates the requirements of a primitive {@code float} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayValidator<float[], Float> validateThat(float[] actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code double} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<double[], Double> requireThat(double[] actual, String name);

	/**
	 * Same as {@link #requireThat(double[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<double[], Double> assertThat(double[] actual, String name);

	/**
	 * Validates the requirements of a primitive {@code double} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayValidator<double[], Double> validateThat(double[] actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code boolean} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<boolean[], Boolean> requireThat(boolean[] actual, String name);

	/**
	 * Same as {@link #requireThat(boolean[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<boolean[], Boolean> assertThat(boolean[] actual, String name);

	/**
	 * Validates the requirements of a primitive {@code boolean} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayValidator<boolean[], Boolean> validateThat(boolean[] actual, String name);

	/**
	 * Verifies the requirements of a primitive {@code char} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<char[], Character> requireThat(char[] actual, String name);

	/**
	 * Same as {@link #requireThat(char[], String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayVerifier<char[], Character> assertThat(char[] actual, String name);

	/**
	 * Validates the requirements of a primitive {@code char} array.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	ArrayValidator<char[], Character> validateThat(char[] actual, String name);

	/**
	 * Verifies the requirements of an {@code Object} array.
	 *
	 * @param <E>    the type of elements in the array
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<E> ArrayVerifier<E[], E> requireThat(E[] actual, String name);

	/**
	 * Same as {@link #requireThat(Object[], String)} but does nothing if assertions are disabled.
	 *
	 * @param <E>    the type of elements in the array
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<E> ArrayVerifier<E[], E> assertThat(E[] actual, String name);

	/**
	 * Validates the requirements of an {@code Object} array.
	 *
	 * @param <E>    the type of elements in the array
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<E> ArrayValidator<E[], E> validateThat(E[] actual, String name);

	/**
	 * Verifies the requirements of a {@code Comparable}.
	 *
	 * @param <T>    the type of objects that the value may be compared to
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
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
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<T extends Comparable<? super T>> ComparableVerifier<T> assertThat(T actual, String name);

	/**
	 * Validates the requirements of a {@code Comparable}.
	 *
	 * @param <T>    the type of objects that the value may be compared to
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<T extends Comparable<? super T>> ComparableValidator<T> validateThat(T actual, String name);

	/**
	 * Verifies the requirements of a {@code byte}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
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
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveNumberVerifier<Byte> assertThat(byte actual, String name);

	/**
	 * Validates the requirements of a {@code byte}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveNumberValidator<Byte> validateThat(byte actual, String name);

	/**
	 * Verifies the requirements of a {@code short}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
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
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveNumberVerifier<Short> assertThat(short actual, String name);

	/**
	 * Validates the requirements of a {@code short}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveNumberValidator<Short> validateThat(short actual, String name);

	/**
	 * Verifies the requirements of an {@code int}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
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
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveIntegerVerifier<Integer> assertThat(int actual, String name);

	/**
	 * Validates the requirements of an {@code int}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveIntegerValidator<Integer> validateThat(int actual, String name);

	/**
	 * Verifies the requirements of an {@code Integer}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
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
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	IntegerVerifier<Integer> assertThat(Integer actual, String name);

	/**
	 * Validates the requirements of an {@code Integer}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	IntegerValidator<Integer> validateThat(Integer actual, String name);

	/**
	 * Verifies the requirements of a {@code long}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
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
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveIntegerVerifier<Long> assertThat(long actual, String name);

	/**
	 * Validates the requirements of a {@code long}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveIntegerValidator<Long> validateThat(long actual, String name);

	/**
	 * Verifies the requirements of a {@code Long}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
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
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	IntegerVerifier<Long> assertThat(Long actual, String name);

	/**
	 * Validates the requirements of a {@code Long}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	IntegerValidator<Long> validateThat(Long actual, String name);

	/**
	 * Verifies the requirements of a {@code float}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
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
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveFloatingPointVerifier<Float> assertThat(float actual, String name);

	/**
	 * Validates the requirements of a {@code float}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveFloatingPointValidator<Float> validateThat(float actual, String name);

	/**
	 * Verifies the requirements of a {@code double}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
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
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveFloatingPointVerifier<Double> assertThat(double actual, String name);

	/**
	 * Validates the requirements of a {@code double}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveFloatingPointValidator<Double> validateThat(double actual, String name);

	/**
	 * Verifies the requirements of a {@code boolean}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
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
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveBooleanVerifier assertThat(boolean actual, String name);

	/**
	 * Validates the requirements of a {@code boolean}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveBooleanValidator validateThat(boolean actual, String name);

	/**
	 * Verifies the requirements of a {@code char}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
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
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveCharacterVerifier assertThat(char actual, String name);

	/**
	 * Validates the requirements of a {@code char}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PrimitiveCharacterValidator validateThat(char actual, String name);

	/**
	 * Verifies the requirements of a {@code Number}.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<T extends Number & Comparable<? super T>> NumberVerifier<T> requireThat(T actual, String name);

	/**
	 * Same as {@link #requireThat(Number, String)} but does nothing if assertions are disabled.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<T extends Number & Comparable<? super T>> NumberVerifier<T> assertThat(T actual, String name);

	/**
	 * Validates the requirements of a {@code Number}.
	 *
	 * @param <T>    the type of the number
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<T extends Number & Comparable<? super T>> NumberValidator<T> validateThat(T actual, String name);

	/**
	 * Verifies the requirements of a {@code Boolean}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
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
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	BooleanVerifier assertThat(Boolean actual, String name);

	/**
	 * Validates the requirements of a {@code Boolean}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	BooleanValidator validateThat(Boolean actual, String name);

	/**
	 * Verifies the requirements of a {@code Float}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	FloatingPointVerifier<Float> requireThat(Float actual, String name);

	/**
	 * Same as {@link #requireThat(Float, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	FloatingPointVerifier<Float> assertThat(Float actual, String name);

	/**
	 * Validates the requirements of a {@code Float}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	FloatingPointValidator<Float> validateThat(Float actual, String name);

	/**
	 * Verifies the requirements of a {@code Double}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	FloatingPointVerifier<Double> requireThat(Double actual, String name);

	/**
	 * Same as {@link #requireThat(Double, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	FloatingPointVerifier<Double> assertThat(Double actual, String name);

	/**
	 * Validates the requirements of a {@code Double}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	FloatingPointValidator<Double> validateThat(Double actual, String name);

	/**
	 * Verifies the requirements of a {@code BigDecimal}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	BigDecimalVerifier requireThat(BigDecimal actual, String name);

	/**
	 * Same as {@link #requireThat(BigDecimal, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	BigDecimalVerifier assertThat(BigDecimal actual, String name);

	/**
	 * Validates the requirements of a {@code BigDecimal}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	BigDecimalValidator validateThat(BigDecimal actual, String name);

	/**
	 * Verifies the requirements of a {@code Map}.
	 *
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
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
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<K, V> MapVerifier<K, V> assertThat(Map<K, V> actual, String name);

	/**
	 * Validates the requirements of a {@code Map}.
	 *
	 * @param <K>    the type of key in the map
	 * @param <V>    the type of value in the map
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<K, V> MapValidator<K, V> validateThat(Map<K, V> actual, String name);

	/**
	 * Verifies the requirements of a {@code Path}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PathVerifier requireThat(Path actual, String name);

	/**
	 * Same as {@link #requireThat(Path, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PathVerifier assertThat(Path actual, String name);

	/**
	 * Validates the requirements of a {@code Path}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	PathValidator validateThat(Path actual, String name);

	/**
	 * Verifies the requirements of a {@code String}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	StringVerifier requireThat(String actual, String name);

	/**
	 * Same as {@link #requireThat(String, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	StringVerifier assertThat(String actual, String name);

	/**
	 * Validates the requirements of a {@code String}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	StringValidator validateThat(String actual, String name);

	/**
	 * Verifies the requirements of a {@code URI}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	UriVerifier requireThat(URI actual, String name);

	/**
	 * Same as {@link #requireThat(URI, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	UriVerifier assertThat(URI actual, String name);

	/**
	 * Validates the requirements of a {@code URI}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	UriValidator validateThat(URI actual, String name);

	/**
	 * Verifies the requirements of a {@code URL}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	UrlVerifier requireThat(URL actual, String name);

	/**
	 * Same as {@link #requireThat(URI, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	UrlVerifier assertThat(URL actual, String name);

	/**
	 * Validates the requirements of a {@code URL}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	UrlValidator validateThat(URL actual, String name);

	/**
	 * Verifies the requirements of a {@code Class}.
	 *
	 * @param <T>    the type of class
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<T> ClassVerifier<T> requireThat(Class<T> actual, String name);

	/**
	 * Same as {@link #requireThat(Class, String)} but does nothing if assertions are disabled.
	 *
	 * @param <T>    the type of class
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<T> ClassVerifier<T> assertThat(Class<T> actual, String name);

	/**
	 * Validates the requirements of a {@code Class}.
	 *
	 * @param <T>    the type of class
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	<T> ClassValidator<T> validateThat(Class<T> actual, String name);

	/**
	 * Verifies the requirements of an {@code Optional}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	OptionalVerifier requireThat(Optional<?> actual, String name);

	/**
	 * Same as {@link #requireThat(Optional, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	OptionalVerifier assertThat(Optional<?> actual, String name);

	/**
	 * Validates the requirements of an {@code Optional}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	OptionalValidator validateThat(Optional<?> actual, String name);

	/**
	 * Verifies the requirements of an {@code InetAddress}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	InetAddressVerifier requireThat(InetAddress actual, String name);

	/**
	 * Same as {@link #requireThat(InetAddress, String)} but does nothing if assertions are disabled.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a verifier for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	InetAddressVerifier assertThat(InetAddress actual, String name);

	/**
	 * Validates the requirements of an {@code InetAddress}.
	 *
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	InetAddressValidator validateThat(InetAddress actual, String name);
}