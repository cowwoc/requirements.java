/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java;

import io.github.cowwoc.requirements12.java.validator.BigDecimalValidator;
import io.github.cowwoc.requirements12.java.validator.BigIntegerValidator;
import io.github.cowwoc.requirements12.java.validator.BooleanValidator;
import io.github.cowwoc.requirements12.java.validator.ByteValidator;
import io.github.cowwoc.requirements12.java.validator.CharacterValidator;
import io.github.cowwoc.requirements12.java.validator.CollectionValidator;
import io.github.cowwoc.requirements12.java.validator.ComparableValidator;
import io.github.cowwoc.requirements12.java.validator.DoubleValidator;
import io.github.cowwoc.requirements12.java.validator.FloatValidator;
import io.github.cowwoc.requirements12.java.validator.GenericTypeValidator;
import io.github.cowwoc.requirements12.java.validator.InetAddressValidator;
import io.github.cowwoc.requirements12.java.validator.IntegerValidator;
import io.github.cowwoc.requirements12.java.validator.ListValidator;
import io.github.cowwoc.requirements12.java.validator.LongValidator;
import io.github.cowwoc.requirements12.java.validator.MapValidator;
import io.github.cowwoc.requirements12.java.validator.ObjectArrayValidator;
import io.github.cowwoc.requirements12.java.validator.ObjectValidator;
import io.github.cowwoc.requirements12.java.validator.OptionalValidator;
import io.github.cowwoc.requirements12.java.validator.PathValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveBooleanArrayValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveBooleanValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveByteArrayValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveByteValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveCharacterArrayValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveCharacterValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveDoubleArrayValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveDoubleValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveFloatArrayValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveFloatValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveIntegerArrayValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveIntegerValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveLongArrayValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveLongValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveShortArrayValidator;
import io.github.cowwoc.requirements12.java.validator.PrimitiveShortValidator;
import io.github.cowwoc.requirements12.java.validator.ShortValidator;
import io.github.cowwoc.requirements12.java.validator.StringValidator;
import io.github.cowwoc.requirements12.java.validator.UriValidator;

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
 * Creates validators for the Java API that capture exceptions on validation failure rather than throwing them
 * immediately. The exceptions are converted into an {@link AssertionError}.
 */
public interface JavaAssertThat
{
	/**
	 * Validates the state of a {@code byte}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	PrimitiveByteValidator that(byte value, String name);

	/**
	 * Validates the state of a {@code byte}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveByteValidator that(byte value);

	/**
	 * Validates the state of a {@code Byte}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	ByteValidator that(Byte value, String name);

	/**
	 * Validates the state of a {@code Byte}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	ByteValidator that(Byte value);

	/**
	 * Validates the state of a {@code short}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	PrimitiveShortValidator that(short value, String name);

	/**
	 * Validates the state of a {@code short}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveShortValidator that(short value);

	/**
	 * Validates the state of a {@code Short}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	ShortValidator that(Short value, String name);

	/**
	 * Validates the state of a {@code Short}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	ShortValidator that(Short value);

	/**
	 * Validates the state of an {@code int}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	PrimitiveIntegerValidator that(int value, String name);

	/**
	 * Validates the state of an {@code int}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveIntegerValidator that(int value);

	/**
	 * Validates the state of an {@code Integer}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	IntegerValidator that(Integer value, String name);

	/**
	 * Validates the state of an {@code Integer}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	IntegerValidator that(Integer value);

	/**
	 * Validates the state of a {@code long}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	PrimitiveLongValidator that(long value, String name);

	/**
	 * Validates the state of a {@code long}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveLongValidator that(long value);

	/**
	 * Validates the state of a {@code Long}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	LongValidator that(Long value, String name);

	/**
	 * Validates the state of a {@code Long}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	LongValidator that(Long value);

	/**
	 * Validates the state of a {@code float}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	PrimitiveFloatValidator that(float value, String name);

	/**
	 * Validates the state of a {@code float}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveFloatValidator that(float value);

	/**
	 * Validates the state of a {@code Float}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	FloatValidator that(Float value, String name);

	/**
	 * Validates the state of a {@code Float}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	FloatValidator that(Float value);

	/**
	 * Validates the state of a {@code double}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	PrimitiveDoubleValidator that(double value, String name);

	/**
	 * Validates the state of a {@code double}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveDoubleValidator that(double value);

	/**
	 * Validates the state of a {@code Double}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	DoubleValidator that(Double value, String name);

	/**
	 * Validates the state of a {@code Double}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	DoubleValidator that(Double value);

	/**
	 * Validates the state of a {@code boolean}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	PrimitiveBooleanValidator that(boolean value, String name);

	/**
	 * Validates the state of a {@code boolean}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveBooleanValidator that(boolean value);

	/**
	 * Validates the state of a {@code Boolean}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	BooleanValidator that(Boolean value, String name);

	/**
	 * Validates the state of a {@code Boolean}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	BooleanValidator that(Boolean value);

	/**
	 * Validates the state of a {@code char}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	PrimitiveCharacterValidator that(char value, String name);

	/**
	 * Validates the state of a {@code char}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveCharacterValidator that(char value);

	/**
	 * Validates the state of a {@code Character}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	CharacterValidator that(Character value, String name);

	/**
	 * Validates the state of a {@code Character}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	CharacterValidator that(Character value);

	/**
	 * Validates the state of a {@code BigInteger}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	BigIntegerValidator that(BigInteger value, String name);

	/**
	 * Validates the state of a {@code BigInteger}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	BigIntegerValidator that(BigInteger value);

	/**
	 * Validates the state of a {@code BigDecimal}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	BigDecimalValidator that(BigDecimal value, String name);

	/**
	 * Validates the state of a {@code BigDecimal}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	BigDecimalValidator that(BigDecimal value);

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
	<T extends Comparable<T>> ComparableValidator<T> that(T value, String name);

	/**
	 * Validates the state of a {@code Comparable} object.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	<T extends Comparable<T>> ComparableValidator<T> that(T value);

	/**
	 * Validates the state of an {@code Object}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	<T> ObjectValidator<T> that(T value, String name);

	/**
	 * Validates the state of an {@code Object}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	<T> ObjectValidator<T> that(T value);

	/**
	 * Validates the state of a {@code Collection}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	<T extends Collection<E>, E> CollectionValidator<T, E> that(T value, String name);

	/**
	 * Validates the state of a {@code Collection}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the value
	 * @param <E>   the type of elements in the collection
	 * @param value the value
	 * @return a validator for the value
	 */
	<T extends Collection<E>, E> CollectionValidator<T, E> that(T value);

	/**
	 * Validates the state of a {@code List}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	<T extends List<E>, E> ListValidator<T, E> that(T value, String name);

	/**
	 * Validates the state of a {@code List}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the value
	 * @param <E>   the type of elements in the list
	 * @param value the value
	 * @return a validator for the value
	 */
	<T extends List<E>, E> ListValidator<T, E> that(T value);

	/**
	 * Validates the state of a primitive {@code byte} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	PrimitiveByteArrayValidator that(byte[] value, String name);

	/**
	 * Validates the state of a primitive {@code byte} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveByteArrayValidator that(byte[] value);

	/**
	 * Validates the state of a primitive {@code short} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	PrimitiveShortArrayValidator that(short[] value, String name);

	/**
	 * Validates the state of a primitive {@code short} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveShortArrayValidator that(short[] value);

	/**
	 * Validates the state of a primitive {@code int} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	PrimitiveIntegerArrayValidator that(int[] value, String name);

	/**
	 * Validates the state of a primitive {@code int} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveIntegerArrayValidator that(int[] value);

	/**
	 * Validates the state of a primitive {@code long} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	PrimitiveLongArrayValidator that(long[] value, String name);

	/**
	 * Validates the state of a primitive {@code long} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveLongArrayValidator that(long[] value);

	/**
	 * Validates the state of a primitive {@code float} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
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
	PrimitiveFloatArrayValidator that(float[] value, String name);

	/**
	 * Validates the state of a primitive {@code float} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * The exceptions are converted into an {@link AssertionError} and can be retrieved or thrown once the
	 * validation completes. Exceptions unrelated to validation failures are thrown immediately.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	PrimitiveFloatArrayValidator that(float[] value);

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
	PrimitiveDoubleArrayValidator that(double[] value, String name);

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
	PrimitiveDoubleArrayValidator that(double[] value);

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
	PrimitiveBooleanArrayValidator that(boolean[] value, String name);

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
	PrimitiveBooleanArrayValidator that(boolean[] value);

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
	PrimitiveCharacterArrayValidator that(char[] value, String name);

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
	PrimitiveCharacterArrayValidator that(char[] value);

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
	<E> ObjectArrayValidator<E[], E> that(E[] value, String name);

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
	<E> ObjectArrayValidator<E[], E> that(E[] value);

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
	<T extends Map<K, V>, K, V> MapValidator<T, K, V> that(T value, String name);

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
	<T extends Map<K, V>, K, V> MapValidator<T, K, V> that(T value);

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
	PathValidator that(Path value, String name);

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
	PathValidator that(Path value);

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
	StringValidator that(String value, String name);

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
	StringValidator that(String value);

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
	UriValidator that(URI value, String name);

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
	UriValidator that(URI value);

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
	 *              type-parameters, use the {@link #that(GenericType) TypeToken} overload.
	 * @param value the value
	 * @return a validator for the value
	 */
	<T> GenericTypeValidator<T> that(Class<T> value);

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
	 *              type-parameters, prefer the {@link #that(Class) Class} overload.
	 * @param value the value
	 * @return a validator for the value
	 */
	<T> GenericTypeValidator<T> that(GenericType<T> value);

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
	 *              type-parameters, use the {@link #that(GenericType) TypeToken} overload.
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T> GenericTypeValidator<T> that(Class<T> value, String name);

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
	 *              type-parameters, prefer the {@link #that(Class) Class} overload.
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	<T> GenericTypeValidator<T> that(GenericType<T> value, String name);

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
	<T> OptionalValidator<T> that(Optional<T> value, String name);

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
	<T> OptionalValidator<T> that(Optional<T> value);

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
	InetAddressValidator that(InetAddress value, String name);

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
	InetAddressValidator that(InetAddress value);
}