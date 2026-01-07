/*
 * Copyright (c) 2023 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java;

import io.github.cowwoc.requirements13.java.validator.BigDecimalValidator;
import io.github.cowwoc.requirements13.java.validator.BigIntegerValidator;
import io.github.cowwoc.requirements13.java.validator.BooleanValidator;
import io.github.cowwoc.requirements13.java.validator.ByteValidator;
import io.github.cowwoc.requirements13.java.validator.CharacterValidator;
import io.github.cowwoc.requirements13.java.validator.CollectionValidator;
import io.github.cowwoc.requirements13.java.validator.ComparableValidator;
import io.github.cowwoc.requirements13.java.validator.DoubleValidator;
import io.github.cowwoc.requirements13.java.validator.FloatValidator;
import io.github.cowwoc.requirements13.java.validator.GenericTypeValidator;
import io.github.cowwoc.requirements13.java.validator.InetAddressValidator;
import io.github.cowwoc.requirements13.java.validator.IntegerValidator;
import io.github.cowwoc.requirements13.java.validator.ListValidator;
import io.github.cowwoc.requirements13.java.validator.LongValidator;
import io.github.cowwoc.requirements13.java.validator.MapValidator;
import io.github.cowwoc.requirements13.java.validator.ObjectArrayValidator;
import io.github.cowwoc.requirements13.java.validator.ObjectValidator;
import io.github.cowwoc.requirements13.java.validator.OptionalValidator;
import io.github.cowwoc.requirements13.java.validator.PathValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveBooleanArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveBooleanValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveByteArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveByteValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveCharacterArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveCharacterValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveDoubleArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveDoubleValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveFloatArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveFloatValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveIntegerArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveIntegerValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveLongArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveLongValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveShortArrayValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveShortValidator;
import io.github.cowwoc.requirements13.java.validator.ShortValidator;
import io.github.cowwoc.requirements13.java.validator.StringValidator;
import io.github.cowwoc.requirements13.java.validator.UriValidator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Creates validators for the Java API that throw exceptions immediately on validation failure.
 */
public interface JavaRequireThat
{
	/**
	 * Validates the state of a {@code byte}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveByteValidator requireThat(byte value, String name);

	/**
	 * Validates the state of a {@code Byte}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	ByteValidator requireThat(Byte value, String name);

	/**
	 * Validates the state of a {@code short}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveShortValidator requireThat(short value, String name);

	/**
	 * Validates the state of a {@code Short}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	ShortValidator requireThat(Short value, String name);

	/**
	 * Validates the state of an {@code int}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveIntegerValidator requireThat(int value, String name);

	/**
	 * Validates the state of an {@code Integer}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	IntegerValidator requireThat(Integer value, String name);

	/**
	 * Validates the state of a {@code long}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveLongValidator requireThat(long value, String name);

	/**
	 * Validates the state of a {@code Long}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	LongValidator requireThat(Long value, String name);

	/**
	 * Validates the state of a {@code float}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveFloatValidator requireThat(float value, String name);

	/**
	 * Validates the state of a {@code Float}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	FloatValidator requireThat(Float value, String name);

	/**
	 * Validates the state of a {@code double}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveDoubleValidator requireThat(double value, String name);

	/**
	 * Validates the state of a {@code Double}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	DoubleValidator requireThat(Double value, String name);

	/**
	 * Validates the state of a {@code boolean}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveBooleanValidator requireThat(boolean value, String name);

	/**
	 * Validates the state of a {@code Boolean}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	BooleanValidator requireThat(Boolean value, String name);

	/**
	 * Validates the state of a {@code char}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveCharacterValidator requireThat(char value, String name);

	/**
	 * Validates the state of a {@code Character}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	CharacterValidator requireThat(Character value, String name);

	/**
	 * Validates the state of a {@code BigInteger}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	BigIntegerValidator requireThat(BigInteger value, String name);

	/**
	 * Validates the state of a {@code BigDecimal}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	BigDecimalValidator requireThat(BigDecimal value, String name);

	/**
	 * Validates the state of a {@code Comparable} object.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T extends Comparable<T>> ComparableValidator<T> requireThat(T value, String name);

	/**
	 * Validates the state of an {@code Object}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T> ObjectValidator<T> requireThat(T value, String name);

	/**
	 * Validates the state of a {@code Collection}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param <T>   the type of the value
	 * @param <E>   the type of elements in the collection
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T extends Collection<E>, E> CollectionValidator<T, E> requireThat(T value, String name);

	/**
	 * Validates the state of a {@code List}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param <T>   the type of the value
	 * @param <E>   the type of elements in the list
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T extends List<E>, E> ListValidator<T, E> requireThat(T value, String name);

	/**
	 * Validates the state of a primitive {@code byte} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveByteArrayValidator requireThat(byte[] value, String name);

	/**
	 * Validates the state of a primitive {@code short} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveShortArrayValidator requireThat(short[] value, String name);

	/**
	 * Validates the state of a primitive {@code int} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveIntegerArrayValidator requireThat(int[] value, String name);

	/**
	 * Validates the state of a primitive {@code long} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveLongArrayValidator requireThat(long[] value, String name);

	/**
	 * Validates the state of a primitive {@code float} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveFloatArrayValidator requireThat(float[] value, String name);

	/**
	 * Validates the state of a primitive {@code double} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveDoubleArrayValidator requireThat(double[] value, String name);

	/**
	 * Validates the state of a primitive {@code boolean} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveBooleanArrayValidator requireThat(boolean[] value, String name);

	/**
	 * Validates the state of a primitive {@code char} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveCharacterArrayValidator requireThat(char[] value, String name);

	/**
	 * Validates the state of an {@code Object} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param <E>   the type of elements in the array
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<E> ObjectArrayValidator<E[], E> requireThat(E[] value, String name);

	/**
	 * Validates the state of a {@code Map}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param <K>   the type of keys in the map
	 * @param <V>   the type of values in the map
	 * @param <T>   the type of the map
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T extends Map<K, V>, K, V> MapValidator<T, K, V> requireThat(T value, String name);

	/**
	 * Validates the state of a {@code Path}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PathValidator requireThat(Path value, String name);

	/**
	 * Validates the state of a {@code String}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	StringValidator requireThat(String value, String name);

	/**
	 * Validates the state of a {@code URI}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	UriValidator requireThat(URI value, String name);

	/**
	 * Validates the state of a {@code Class}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object. For types that contain
	 *              type-parameters, use the {@link #requireThat(GenericType, String) TypeToken} overload.
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T> GenericTypeValidator<T> requireThat(Class<T> value, String name);

	/**
	 * Validates the state of a {@code Class}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object. For types without
	 *              type-parameters, prefer the {@link #requireThat(Class, String) Class} overload.
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T> GenericTypeValidator<T> requireThat(GenericType<T> value, String name);

	/**
	 * Validates the state of an {@code Optional}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param <T>   the type of optional
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	<T> OptionalValidator<T> requireThat(Optional<T> value, String name);

	/**
	 * Validates the state of an {@code InetAddress}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	InetAddressValidator requireThat(InetAddress value, String name);
}