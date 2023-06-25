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
import java.util.function.Function;

/**
 * Creates validators for class invariants and method postconditions of the Java API, throwing
 * {@code AssertionError} if a failure occurs.
 */
public interface JavaAssumeThat
{
	/**
	 * Validates the state of a {@code byte}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveByteValidator assumeThat(byte value, String name);

	/**
	 * Validates the state of a {@code byte}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveByteValidator assumeThat(byte value);

	/**
	 * Validates the state of a {@code Byte}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	ByteValidator assumeThat(Byte value, String name);

	/**
	 * Validates the state of a {@code Byte}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	ByteValidator assumeThat(Byte value);

	/**
	 * Validates the state of a {@code short}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveShortValidator assumeThat(short value, String name);

	/**
	 * Validates the state of a {@code short}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveShortValidator assumeThat(short value);

	/**
	 * Validates the state of a {@code Short}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	ShortValidator assumeThat(Short value, String name);

	/**
	 * Validates the state of a {@code Short}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	ShortValidator assumeThat(Short value);

	/**
	 * Validates the state of an {@code int}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveIntegerValidator assumeThat(int value, String name);

	/**
	 * Validates the state of an {@code int}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveIntegerValidator assumeThat(int value);

	/**
	 * Validates the state of an {@code Integer}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	IntegerValidator assumeThat(Integer value, String name);

	/**
	 * Validates the state of an {@code Integer}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	IntegerValidator assumeThat(Integer value);

	/**
	 * Validates the state of a {@code long}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveLongValidator assumeThat(long value, String name);

	/**
	 * Validates the state of a {@code long}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveLongValidator assumeThat(long value);

	/**
	 * Validates the state of a {@code Long}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	LongValidator assumeThat(Long value, String name);

	/**
	 * Validates the state of a {@code Long}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	LongValidator assumeThat(Long value);

	/**
	 * Validates the state of a {@code float}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveFloatValidator assumeThat(float value, String name);

	/**
	 * Validates the state of a {@code float}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveFloatValidator assumeThat(float value);

	/**
	 * Validates the state of a {@code Float}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	FloatValidator assumeThat(Float value, String name);

	/**
	 * Validates the state of a {@code Float}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	FloatValidator assumeThat(Float value);

	/**
	 * Validates the state of a {@code double}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveDoubleValidator assumeThat(double value, String name);

	/**
	 * Validates the state of a {@code double}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveDoubleValidator assumeThat(double value);

	/**
	 * Validates the state of a {@code Double}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	DoubleValidator assumeThat(Double value, String name);

	/**
	 * Validates the state of a {@code Double}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	DoubleValidator assumeThat(Double value);

	/**
	 * Validates the state of a {@code boolean}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveBooleanValidator assumeThat(boolean value, String name);

	/**
	 * Validates the state of a {@code boolean}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveBooleanValidator assumeThat(boolean value);

	/**
	 * Validates the state of a {@code Boolean}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	BooleanValidator assumeThat(Boolean value, String name);

	/**
	 * Validates the state of a {@code Boolean}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	BooleanValidator assumeThat(Boolean value);

	/**
	 * Validates the state of a {@code char}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveCharacterValidator assumeThat(char value, String name);

	/**
	 * Validates the state of a {@code char}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveCharacterValidator assumeThat(char value);

	/**
	 * Validates the state of a {@code Character}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	CharacterValidator assumeThat(Character value, String name);

	/**
	 * Validates the state of a {@code Character}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	CharacterValidator assumeThat(Character value);

	/**
	 * Validates the state of a {@code BigInteger}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	BigIntegerValidator assumeThat(BigInteger value, String name);

	/**
	 * Validates the state of a {@code BigInteger}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	BigIntegerValidator assumeThat(BigInteger value);

	/**
	 * Validates the state of a {@code BigDecimal}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	BigDecimalValidator assumeThat(BigDecimal value, String name);

	/**
	 * Validates the state of a {@code BigDecimal}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	BigDecimalValidator assumeThat(BigDecimal value);

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
	<T extends Comparable<T>> ComparableValidator<T> assumeThat(T value, String name);

	/**
	 * Validates the state of a {@code Comparable} object.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	<T extends Comparable<T>> ComparableValidator<T> assumeThat(T value);

	/**
	 * Validates the state of an {@code Object}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	<T> ObjectValidator<T> assumeThat(T value, String name);

	/**
	 * Validates the state of an {@code Object}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	<T> ObjectValidator<T> assumeThat(T value);

	/**
	 * Validates the state of a {@code Collection}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <E>   the type of elements in the collection
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	<E, T extends Collection<E>> CollectionValidator<E, T> assumeThat(T value, String name);

	/**
	 * Validates the state of a {@code Collection}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <E>   the type of elements in the collection
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	<E, T extends Collection<E>> CollectionValidator<E, T> assumeThat(T value);

	/**
	 * Validates the state of a {@code List}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <E>   the type of elements in the list
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	<E, T extends List<E>> ListValidator<E, T> assumeThat(T value, String name);

	/**
	 * Validates the state of a {@code List}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <E>   the type of elements in the list
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	<E, T extends List<E>> ListValidator<E, T> assumeThat(T value);

	/**
	 * Validates the state of a primitive {@code byte} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveByteArrayValidator assumeThat(byte[] value, String name);

	/**
	 * Validates the state of a primitive {@code byte} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveByteArrayValidator assumeThat(byte[] value);

	/**
	 * Validates the state of a primitive {@code short} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveShortArrayValidator assumeThat(short[] value, String name);

	/**
	 * Validates the state of a primitive {@code short} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveShortArrayValidator assumeThat(short[] value);

	/**
	 * Validates the state of a primitive {@code int} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveIntegerArrayValidator assumeThat(int[] value, String name);

	/**
	 * Validates the state of a primitive {@code int} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveIntegerArrayValidator assumeThat(int[] value);

	/**
	 * Validates the state of a primitive {@code long} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveLongArrayValidator assumeThat(long[] value, String name);

	/**
	 * Validates the state of a primitive {@code long} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveLongArrayValidator assumeThat(long[] value);

	/**
	 * Validates the state of a primitive {@code float} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveFloatArrayValidator assumeThat(float[] value, String name);

	/**
	 * Validates the state of a primitive {@code float} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveFloatArrayValidator assumeThat(float[] value);

	/**
	 * Validates the state of a primitive {@code double} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveDoubleArrayValidator assumeThat(double[] value, String name);

	/**
	 * Validates the state of a primitive {@code double} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveDoubleArrayValidator assumeThat(double[] value);

	/**
	 * Validates the state of a primitive {@code boolean} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveBooleanArrayValidator assumeThat(boolean[] value, String name);

	/**
	 * Validates the state of a primitive {@code boolean} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveBooleanArrayValidator assumeThat(boolean[] value);

	/**
	 * Validates the state of a primitive {@code char} array.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PrimitiveCharacterArrayValidator assumeThat(char[] value, String name);

	/**
	 * Validates the state of a primitive {@code char} array.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveCharacterArrayValidator assumeThat(char[] value);

	/**
	 * Validates the state of an {@code Object} array.
	 *
	 * @param <E>   the type of elements in the array
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	<E> ObjectArrayValidator<E, E[]> assumeThat(E[] value, String name);

	/**
	 * Validates the state of an {@code Object} array.
	 *
	 * @param <E>   the type of elements in the array
	 * @param value the value
	 * @return a validator for the value
	 */
	<E> ObjectArrayValidator<E, E[]> assumeThat(E[] value);

	/**
	 * Validates the state of a {@code Map}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
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
	<K, V, T extends Map<K, V>> MapValidator<K, V, T> assumeThat(T value, String name);

	/**
	 * Validates the state of a {@code Map}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <K>   the type of keys in the map
	 * @param <V>   the type of values in the map
	 * @param <T>   the type of the map
	 * @param value the value
	 * @return a validator for the value
	 */
	<K, V, T extends Map<K, V>> MapValidator<K, V, T> assumeThat(T value);

	/**
	 * Validates the state of a {@code Path}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	PathValidator assumeThat(Path value, String name);

	/**
	 * Validates the state of a {@code Path}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PathValidator assumeThat(Path value);

	/**
	 * Validates the state of a {@code String}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	StringValidator assumeThat(String value, String name);

	/**
	 * Validates the state of a {@code String}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	StringValidator assumeThat(String value);

	/**
	 * Validates the state of a {@code URI}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	UriValidator assumeThat(URI value, String name);

	/**
	 * Validates the state of a {@code URI}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	UriValidator assumeThat(URI value);

	/**
	 * Validates the state of a {@code URL}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	UrlValidator assumeThat(URL value, String name);

	/**
	 * Validates the state of a {@code URL}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	UrlValidator assumeThat(URL value);

	/**
	 * Validates the state of a {@code Class}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of class
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	<T> ClassValidator<T> assumeThat(Class<T> value, String name);

	/**
	 * Validates the state of a {@code Class}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of class
	 * @param value the value
	 * @return a validator for the value
	 */
	<T> ClassValidator<T> assumeThat(Class<T> value);

	/**
	 * Validates the state of an {@code Optional}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of optional
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	<T> OptionalValidator<T> assumeThat(Optional<T> value, String name);

	/**
	 * Validates the state of an {@code Optional}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param <T>   the type of optional
	 * @param value the value
	 * @return a validator for the value
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	<T> OptionalValidator<T> assumeThat(Optional<T> value);

	/**
	 * Validates the state of an {@code InetAddress}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	InetAddressValidator assumeThat(InetAddress value, String name);

	/**
	 * Validates the state of an {@code InetAddress}. Any exceptions thrown due to validation failure are
	 * {@link ConfigurationUpdater#exceptionTransformer(Function) transformed} into an {@code AssertionError}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	InetAddressValidator assumeThat(InetAddress value);
}