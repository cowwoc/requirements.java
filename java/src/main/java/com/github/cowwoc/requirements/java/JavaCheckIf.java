/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.type.BigDecimalValidator;
import com.github.cowwoc.requirements.java.type.BigIntegerValidator;
import com.github.cowwoc.requirements.java.type.BooleanValidator;
import com.github.cowwoc.requirements.java.type.ByteValidator;
import com.github.cowwoc.requirements.java.type.CharacterValidator;
import com.github.cowwoc.requirements.java.type.ClassValidator;
import com.github.cowwoc.requirements.java.type.CollectionValidator;
import com.github.cowwoc.requirements.java.type.ComparableValidator;
import com.github.cowwoc.requirements.java.type.DoubleValidator;
import com.github.cowwoc.requirements.java.type.FloatValidator;
import com.github.cowwoc.requirements.java.type.InetAddressValidator;
import com.github.cowwoc.requirements.java.type.IntegerValidator;
import com.github.cowwoc.requirements.java.type.ListValidator;
import com.github.cowwoc.requirements.java.type.LongValidator;
import com.github.cowwoc.requirements.java.type.MapValidator;
import com.github.cowwoc.requirements.java.type.ObjectArrayValidator;
import com.github.cowwoc.requirements.java.type.ObjectValidator;
import com.github.cowwoc.requirements.java.type.OptionalValidator;
import com.github.cowwoc.requirements.java.type.PathValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveBooleanArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveBooleanValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveByteArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveByteValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveCharacterArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveCharacterValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveDoubleArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveDoubleValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveFloatArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveFloatValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveIntegerArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveIntegerValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveLongArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveLongValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveShortArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveShortValidator;
import com.github.cowwoc.requirements.java.type.ShortValidator;
import com.github.cowwoc.requirements.java.type.StringValidator;
import com.github.cowwoc.requirements.java.type.UriValidator;
import com.github.cowwoc.requirements.java.type.UrlValidator;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Creates validators for the Java API, recording any failures that occur.
 * <p>
 * A validation method may throw exceptions in three scenarios:
 * <ol>
 * <li>The method arguments are invalid, e.g. {@code isGreaterThan(value, null)}.</li>
 * <li>The method encounters a predictable but unavoidable failure that can be recovered from, e.g. an I/O
 * error.</li>
 * <li>The value fails the validation check, e.g. {@code isGreaterThan(5)} on a value of 0.</li>
 * </ol>
 * {@code requireThat()} throws an exception in all scenarios. {@code checkIf()} only throws exceptions in
 * scenarios 1 and 2. For scenario 3, the exception is available via
 * {@link Validator#elseGetException() validator.elseGetException()}}.
 */
public interface JavaCheckIf
{

	/**
	 * Validates the state of a {@code byte}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveByteValidator checkIf(byte value, String name);

	/**
	 * Validates the state of a {@code byte}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveByteValidator checkIf(byte value);

	/**
	 * Validates the state of a {@code Byte}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	ByteValidator checkIf(Byte value, String name);

	/**
	 * Validates the state of a {@code Byte}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	ByteValidator checkIf(Byte value);

	/**
	 * Validates the state of a {@code short}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveShortValidator checkIf(short value, String name);

	/**
	 * Validates the state of a {@code short}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveShortValidator checkIf(short value);

	/**
	 * Validates the state of a {@code Short}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	ShortValidator checkIf(Short value, String name);

	/**
	 * Validates the state of a {@code Short}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	ShortValidator checkIf(Short value);

	/**
	 * Validates the state of an {@code int}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveIntegerValidator checkIf(int value, String name);

	/**
	 * Validates the state of an {@code int}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveIntegerValidator checkIf(int value);

	/**
	 * Validates the state of an {@code Integer}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	IntegerValidator checkIf(Integer value, String name);

	/**
	 * Validates the state of an {@code Integer}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	IntegerValidator checkIf(Integer value);

	/**
	 * Validates the state of a {@code long}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveLongValidator checkIf(long value, String name);

	/**
	 * Validates the state of a {@code long}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveLongValidator checkIf(long value);

	/**
	 * Validates the state of a {@code Long}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	LongValidator checkIf(Long value, String name);

	/**
	 * Validates the state of a {@code Long}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	LongValidator checkIf(Long value);

	/**
	 * Validates the state of a {@code float}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveFloatValidator checkIf(float value, String name);

	/**
	 * Validates the state of a {@code float}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveFloatValidator checkIf(float value);

	/**
	 * Validates the state of a {@code Float}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	FloatValidator checkIf(Float value, String name);

	/**
	 * Validates the state of a {@code Float}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	FloatValidator checkIf(Float value);

	/**
	 * Validates the state of a {@code double}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveDoubleValidator checkIf(double value, String name);

	/**
	 * Validates the state of a {@code double}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveDoubleValidator checkIf(double value);

	/**
	 * Validates the state of a {@code Double}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	DoubleValidator checkIf(Double value, String name);

	/**
	 * Validates the state of a {@code Double}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	DoubleValidator checkIf(Double value);

	/**
	 * Validates the state of a {@code boolean}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveBooleanValidator checkIf(boolean value, String name);

	/**
	 * Validates the state of a {@code boolean}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveBooleanValidator checkIf(boolean value);

	/**
	 * Validates the state of a {@code Boolean}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	BooleanValidator checkIf(Boolean value, String name);

	/**
	 * Validates the state of a {@code Boolean}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	BooleanValidator checkIf(Boolean value);

	/**
	 * Validates the state of a {@code char}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveCharacterValidator checkIf(char value, String name);

	/**
	 * Validates the state of a {@code char}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveCharacterValidator checkIf(char value);

	/**
	 * Validates the state of a {@code Character}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	CharacterValidator checkIf(Character value, String name);

	/**
	 * Validates the state of a {@code Character}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	CharacterValidator checkIf(Character value);

	/**
	 * Validates the state of a {@code BigInteger}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	BigIntegerValidator checkIf(BigInteger value, String name);

	/**
	 * Validates the state of a {@code BigInteger}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	BigIntegerValidator checkIf(BigInteger value);

	/**
	 * Validates the state of a {@code BigDecimal}, recording any failures.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	BigDecimalValidator checkIf(BigDecimal value, String name);

	/**
	 * Validates the state of a {@code BigDecimal}, recording any failures.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	BigDecimalValidator checkIf(BigDecimal value);

	/**
	 * Validates the state of a {@code Comparable} object.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	<T extends Comparable<T>> ComparableValidator<T> checkIf(T value, String name);

	/**
	 * Validates the state of a {@code Comparable} object.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	<T extends Comparable<T>> ComparableValidator<T> checkIf(T value);

	/**
	 * Validates the state of an {@code Object}, recording any failures.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	<T> ObjectValidator<T> checkIf(T value, String name);

	/**
	 * Validates the state of an {@code Object}, recording any failures.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	<T> ObjectValidator<T> checkIf(T value);

	/**
	 * Validates the state of a {@code Collection}, recording any failures.
	 *
	 * @param <E>   the type of elements in the collection
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	<E, T extends Collection<E>> CollectionValidator<E, T> checkIf(T value, String name);

	/**
	 * Validates the state of a {@code Collection}, recording any failures.
	 *
	 * @param <E>   the type of elements in the collection
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	<E, T extends Collection<E>> CollectionValidator<E, T> checkIf(T value);

	/**
	 * Validates the state of a {@code List}, recording any failures.
	 *
	 * @param <E>   the type of elements in the list
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	<E, T extends List<E>> ListValidator<E, T> checkIf(T value, String name);

	/**
	 * Validates the state of a {@code List}, recording any failures.
	 *
	 * @param <E>   the type of elements in the list
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	<E, T extends List<E>> ListValidator<E, T> checkIf(T value);

	/**
	 * Validates the state of a primitive {@code byte} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveByteArrayValidator checkIf(byte[] value, String name);

	/**
	 * Validates the state of a primitive {@code byte} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveByteArrayValidator checkIf(byte[] value);

	/**
	 * Validates the state of a primitive {@code short} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveShortArrayValidator checkIf(short[] value, String name);

	/**
	 * Validates the state of a primitive {@code short} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveShortArrayValidator checkIf(short[] value);

	/**
	 * Validates the state of a primitive {@code int} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveIntegerArrayValidator checkIf(int[] value, String name);

	/**
	 * Validates the state of a primitive {@code int} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveIntegerArrayValidator checkIf(int[] value);

	/**
	 * Validates the state of a primitive {@code long} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveLongArrayValidator checkIf(long[] value, String name);

	/**
	 * Validates the state of a primitive {@code long} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveLongArrayValidator checkIf(long[] value);

	/**
	 * Validates the state of a primitive {@code float} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveFloatArrayValidator checkIf(float[] value, String name);

	/**
	 * Validates the state of a primitive {@code float} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveFloatArrayValidator checkIf(float[] value);

	/**
	 * Validates the state of a primitive {@code double} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveDoubleArrayValidator checkIf(double[] value, String name);

	/**
	 * Validates the state of a primitive {@code double} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveDoubleArrayValidator checkIf(double[] value);

	/**
	 * Validates the state of a primitive {@code boolean} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveBooleanArrayValidator checkIf(boolean[] value, String name);

	/**
	 * Validates the state of a primitive {@code boolean} array, recording failures without throwing an
	 * exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveBooleanArrayValidator checkIf(boolean[] value);

	/**
	 * Validates the state of a primitive {@code char} array, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveCharacterArrayValidator checkIf(char[] value, String name);

	/**
	 * Validates the state of a primitive {@code char} array, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveCharacterArrayValidator checkIf(char[] value);

	/**
	 * Validates the state of an {@code Object} array, recording failures without throwing an exception.
	 *
	 * @param <E>   the type of elements in the array
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	<E> ObjectArrayValidator<E, E[]> checkIf(E[] value, String name);

	/**
	 * Validates the state of an {@code Object} array, recording failures without throwing an exception.
	 *
	 * @param <E>   the type of elements in the array
	 * @param value the value
	 * @return a validator for the value
	 */
	<E> ObjectArrayValidator<E, E[]> checkIf(E[] value);

	/**
	 * Validates the state of a {@code Map}, recording failures without throwing an exception.
	 *
	 * @param <K>   the type of keys in the map
	 * @param <V>   the type of values in the map
	 * @param <T>   the type of the map
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	<K, V, T extends Map<K, V>> MapValidator<K, V, T> checkIf(T value, String name);

	/**
	 * Validates the state of a {@code Map}, recording failures without throwing an exception.
	 *
	 * @param <K>   the type of keys in the map
	 * @param <V>   the type of values in the map
	 * @param <T>   the type of the map
	 * @param value the value
	 * @return a validator for the value
	 */
	<K, V, T extends Map<K, V>> MapValidator<K, V, T> checkIf(T value);

	/**
	 * Validates the state of a {@code Path}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PathValidator checkIf(Path value, String name);

	/**
	 * Validates the state of a {@code Path}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PathValidator checkIf(Path value);

	/**
	 * Validates the state of a {@code String}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	StringValidator checkIf(String value, String name);

	/**
	 * Validates the state of a {@code String}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	StringValidator checkIf(String value);

	/**
	 * Validates the state of a {@code URI}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	UriValidator checkIf(URI value, String name);

	/**
	 * Validates the state of a {@code URI}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	UriValidator checkIf(URI value);

	/**
	 * Validates the state of a {@code URL}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	UrlValidator checkIf(URL value, String name);

	/**
	 * Validates the state of a {@code URL}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	UrlValidator checkIf(URL value);

	/**
	 * Validates the state of a {@code Class}, recording failures without throwing an exception.
	 *
	 * @param <T>   the type of class
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	<T> ClassValidator<T> checkIf(Class<T> value, String name);

	/**
	 * Validates the state of a {@code Class}, recording failures without throwing an exception.
	 *
	 * @param <T>   the type of class
	 * @param value the value
	 * @return a validator for the value
	 */
	<T> ClassValidator<T> checkIf(Class<T> value);

	/**
	 * Validates the state of an {@code Optional}, recording failures without throwing an exception.
	 *
	 * @param <T>   the type of optional
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	<T> OptionalValidator<T> checkIf(Optional<T> value, String name);

	/**
	 * Validates the state of an {@code Optional}, recording failures without throwing an exception.
	 *
	 * @param <T>   the type of optional
	 * @param value the value
	 * @return a validator for the value
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	<T> OptionalValidator<T> checkIf(Optional<T> value);

	/**
	 * Validates the state of an {@code InetAddress}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	InetAddressValidator checkIf(InetAddress value, String name);

	/**
	 * Validates the state of an {@code InetAddress}, recording failures without throwing an exception.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	InetAddressValidator checkIf(InetAddress value);
}