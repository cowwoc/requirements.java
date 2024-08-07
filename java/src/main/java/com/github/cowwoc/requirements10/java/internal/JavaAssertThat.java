/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal;

import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.validator.BigDecimalValidator;
import com.github.cowwoc.requirements10.java.validator.BigIntegerValidator;
import com.github.cowwoc.requirements10.java.validator.BooleanValidator;
import com.github.cowwoc.requirements10.java.validator.ByteValidator;
import com.github.cowwoc.requirements10.java.validator.CharacterValidator;
import com.github.cowwoc.requirements10.java.validator.CollectionValidator;
import com.github.cowwoc.requirements10.java.validator.ComparableValidator;
import com.github.cowwoc.requirements10.java.validator.DoubleValidator;
import com.github.cowwoc.requirements10.java.validator.FloatValidator;
import com.github.cowwoc.requirements10.java.validator.GenericTypeValidator;
import com.github.cowwoc.requirements10.java.validator.InetAddressValidator;
import com.github.cowwoc.requirements10.java.validator.IntegerValidator;
import com.github.cowwoc.requirements10.java.validator.ListValidator;
import com.github.cowwoc.requirements10.java.validator.LongValidator;
import com.github.cowwoc.requirements10.java.validator.MapValidator;
import com.github.cowwoc.requirements10.java.validator.ObjectArrayValidator;
import com.github.cowwoc.requirements10.java.validator.ObjectValidator;
import com.github.cowwoc.requirements10.java.validator.OptionalValidator;
import com.github.cowwoc.requirements10.java.validator.PathValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveBooleanArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveBooleanValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveByteArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveByteValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveCharacterArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveCharacterValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveDoubleArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveDoubleValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveFloatArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveFloatValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveIntegerArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveIntegerValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveLongArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveLongValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveShortArrayValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveShortValidator;
import com.github.cowwoc.requirements10.java.validator.ShortValidator;
import com.github.cowwoc.requirements10.java.validator.StringValidator;
import com.github.cowwoc.requirements10.java.validator.UriValidator;
import com.github.cowwoc.requirements10.java.validator.UrlValidator;

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
 * Creates validators for the Java API that throw {@code AssertionError} immediately on validation failure.
 */
public interface JavaAssertThat
{
	/**
	 * Validates the state of a {@code byte}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveByteValidator assertThat(byte value, String name);

	/**
	 * Validates the state of a {@code byte}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveByteValidator assertThat(byte value);

	/**
	 * Validates the state of a {@code Byte}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	ByteValidator assertThat(Byte value, String name);

	/**
	 * Validates the state of a {@code Byte}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	ByteValidator assertThat(Byte value);

	/**
	 * Validates the state of a {@code short}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	PrimitiveShortValidator assertThat(short value, String name);

	/**
	 * Validates the state of a {@code short}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveShortValidator assertThat(short value);

	/**
	 * Validates the state of a {@code Short}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	ShortValidator assertThat(Short value, String name);

	/**
	 * Validates the state of a {@code Short}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	ShortValidator assertThat(Short value);

	/**
	 * Validates the state of an {@code int}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveIntegerValidator assertThat(int value, String name);

	/**
	 * Validates the state of an {@code int}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveIntegerValidator assertThat(int value);

	/**
	 * Validates the state of an {@code Integer}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	IntegerValidator assertThat(Integer value, String name);

	/**
	 * Validates the state of an {@code Integer}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	IntegerValidator assertThat(Integer value);

	/**
	 * Validates the state of a {@code long}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveLongValidator assertThat(long value, String name);

	/**
	 * Validates the state of a {@code long}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveLongValidator assertThat(long value);

	/**
	 * Validates the state of a {@code Long}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	LongValidator assertThat(Long value, String name);

	/**
	 * Validates the state of a {@code Long}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	LongValidator assertThat(Long value);

	/**
	 * Validates the state of a {@code float}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveFloatValidator assertThat(float value, String name);

	/**
	 * Validates the state of a {@code float}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveFloatValidator assertThat(float value);

	/**
	 * Validates the state of a {@code Float}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	FloatValidator assertThat(Float value, String name);

	/**
	 * Validates the state of a {@code Float}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	FloatValidator assertThat(Float value);

	/**
	 * Validates the state of a {@code double}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveDoubleValidator assertThat(double value, String name);

	/**
	 * Validates the state of a {@code double}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveDoubleValidator assertThat(double value);

	/**
	 * Validates the state of a {@code Double}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	DoubleValidator assertThat(Double value, String name);

	/**
	 * Validates the state of a {@code Double}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	DoubleValidator assertThat(Double value);

	/**
	 * Validates the state of a {@code boolean}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveBooleanValidator assertThat(boolean value, String name);

	/**
	 * Validates the state of a {@code boolean}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveBooleanValidator assertThat(boolean value);

	/**
	 * Validates the state of a {@code Boolean}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	BooleanValidator assertThat(Boolean value, String name);

	/**
	 * Validates the state of a {@code Boolean}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	BooleanValidator assertThat(Boolean value);

	/**
	 * Validates the state of a {@code char}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveCharacterValidator assertThat(char value, String name);

	/**
	 * Validates the state of a {@code char}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveCharacterValidator assertThat(char value);

	/**
	 * Validates the state of a {@code Character}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	CharacterValidator assertThat(Character value, String name);

	/**
	 * Validates the state of a {@code Character}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	CharacterValidator assertThat(Character value);

	/**
	 * Validates the state of a {@code BigInteger}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	BigIntegerValidator assertThat(BigInteger value, String name);

	/**
	 * Validates the state of a {@code BigInteger}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	BigIntegerValidator assertThat(BigInteger value);

	/**
	 * Validates the state of a {@code BigDecimal}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	BigDecimalValidator assertThat(BigDecimal value, String name);

	/**
	 * Validates the state of a {@code BigDecimal}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	BigDecimalValidator assertThat(BigDecimal value);

	/**
	 * Validates the state of a {@code Comparable} object.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T extends Comparable<T>> ComparableValidator<T> assertThat(T value, String name);

	/**
	 * Validates the state of a {@code Comparable} object.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	<T extends Comparable<T>> ComparableValidator<T> assertThat(T value);

	/**
	 * Validates the state of an {@code Object}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T> ObjectValidator<T> assertThat(T value, String name);

	/**
	 * Validates the state of an {@code Object}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	<T> ObjectValidator<T> assertThat(T value);

	/**
	 * Validates the state of a {@code Collection}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the value
	 * @param <E>   the type of elements in the collection
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T extends Collection<E>, E> CollectionValidator<T, E> assertThat(T value, String name);

	/**
	 * Validates the state of a {@code Collection}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the value
	 * @param <E>   the type of elements in the collection
	 * @param value the value
	 * @return a validator for the value
	 */
	<T extends Collection<E>, E> CollectionValidator<T, E> assertThat(T value);

	/**
	 * Validates the state of a {@code List}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the value
	 * @param <E>   the type of elements in the list
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T extends List<E>, E> ListValidator<T, E> assertThat(T value, String name);

	/**
	 * Validates the state of a {@code List}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the value
	 * @param <E>   the type of elements in the list
	 * @param value the value
	 * @return a validator for the value
	 */
	<T extends List<E>, E> ListValidator<T, E> assertThat(T value);

	/**
	 * Validates the state of a primitive {@code byte} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveByteArrayValidator assertThat(byte[] value, String name);

	/**
	 * Validates the state of a primitive {@code byte} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveByteArrayValidator assertThat(byte[] value);

	/**
	 * Validates the state of a primitive {@code short} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveShortArrayValidator assertThat(short[] value, String name);

	/**
	 * Validates the state of a primitive {@code short} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveShortArrayValidator assertThat(short[] value);

	/**
	 * Validates the state of a primitive {@code int} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveIntegerArrayValidator assertThat(int[] value, String name);

	/**
	 * Validates the state of a primitive {@code int} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveIntegerArrayValidator assertThat(int[] value);

	/**
	 * Validates the state of a primitive {@code long} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveLongArrayValidator assertThat(long[] value, String name);

	/**
	 * Validates the state of a primitive {@code long} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveLongArrayValidator assertThat(long[] value);

	/**
	 * Validates the state of a primitive {@code float} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveFloatArrayValidator assertThat(float[] value, String name);

	/**
	 * Validates the state of a primitive {@code float} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveFloatArrayValidator assertThat(float[] value);

	/**
	 * Validates the state of a primitive {@code double} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveDoubleArrayValidator assertThat(double[] value, String name);

	/**
	 * Validates the state of a primitive {@code double} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveDoubleArrayValidator assertThat(double[] value);

	/**
	 * Validates the state of a primitive {@code boolean} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveBooleanArrayValidator assertThat(boolean[] value, String name);

	/**
	 * Validates the state of a primitive {@code boolean} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveBooleanArrayValidator assertThat(boolean[] value);

	/**
	 * Validates the state of a primitive {@code char} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PrimitiveCharacterArrayValidator assertThat(char[] value, String name);

	/**
	 * Validates the state of a primitive {@code char} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveCharacterArrayValidator assertThat(char[] value);

	/**
	 * Validates the state of an {@code Object} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <E>   the type of elements in the array
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<E> ObjectArrayValidator<E[], E> assertThat(E[] value, String name);

	/**
	 * Validates the state of an {@code Object} array.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <E>   the type of elements in the array
	 * @param value the value
	 * @return a validator for the value
	 */
	<E> ObjectArrayValidator<E[], E> assertThat(E[] value);

	/**
	 * Validates the state of a {@code Map}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
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
	<T extends Map<K, V>, K, V> MapValidator<T, K, V> assertThat(T value, String name);

	/**
	 * Validates the state of a {@code Map}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <K>   the type of keys in the map
	 * @param <V>   the type of values in the map
	 * @param <T>   the type of the map
	 * @param value the value
	 * @return a validator for the value
	 */
	<T extends Map<K, V>, K, V> MapValidator<T, K, V> assertThat(T value);

	/**
	 * Validates the state of a {@code Path}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	PathValidator assertThat(Path value, String name);

	/**
	 * Validates the state of a {@code Path}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PathValidator assertThat(Path value);

	/**
	 * Validates the state of a {@code String}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	StringValidator assertThat(String value, String name);

	/**
	 * Validates the state of a {@code String}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	StringValidator assertThat(String value);

	/**
	 * Validates the state of a {@code URI}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	UriValidator assertThat(URI value, String name);

	/**
	 * Validates the state of a {@code URI}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	UriValidator assertThat(URI value);

	/**
	 * Validates the state of a {@code URL}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	UrlValidator assertThat(URL value, String name);

	/**
	 * Validates the state of a {@code URL}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	UrlValidator assertThat(URL value);

	/**
	 * Validates the state of a {@code Class}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object. For types that contain
	 *              type-parameters, use the {@link #assertThat(GenericType) TypeToken} overload.
	 * @param value the value
	 * @return a validator for the value
	 */
	<T> GenericTypeValidator<T> assertThat(Class<T> value);

	/**
	 * Validates the state of a {@code Class}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object. For types without
	 *              type-parameters, prefer the {@link #assertThat(Class) Class} overload.
	 * @param value the value
	 * @return a validator for the value
	 */
	<T> GenericTypeValidator<T> assertThat(GenericType<T> value);

	/**
	 * Validates the state of a {@code Class}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object. For types that contain
	 *              type-parameters, use the {@link #assertThat(GenericType) TypeToken} overload.
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T> GenericTypeValidator<T> assertThat(Class<T> value, String name);

	/**
	 * Validates the state of a {@code Class}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object. For types without
	 *              type-parameters, prefer the {@link #assertThat(Class) Class} overload.
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T> GenericTypeValidator<T> assertThat(GenericType<T> value, String name);

	/**
	 * Validates the state of an {@code Optional}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of optional
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	<T> OptionalValidator<T> assertThat(Optional<T> value, String name);

	/**
	 * Validates the state of an {@code Optional}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of optional
	 * @param value the value
	 * @return a validator for the value
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	<T> OptionalValidator<T> assertThat(Optional<T> value);

	/**
	 * Validates the state of an {@code InetAddress}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	InetAddressValidator assertThat(InetAddress value, String name);

	/**
	 * Validates the state of an {@code InetAddress}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	InetAddressValidator assertThat(InetAddress value);
}