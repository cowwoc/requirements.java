/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java;

import io.github.cowwoc.requirements12.java.internal.Configuration;
import io.github.cowwoc.requirements12.java.internal.scope.MainApplicationScope;
import io.github.cowwoc.requirements12.java.internal.util.StampedLocks;
import io.github.cowwoc.requirements12.java.internal.validator.JavaValidatorsImpl;
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
import io.github.cowwoc.requirements12.java.validator.component.ValidatorComponent;
import io.github.cowwoc.requirements12.annotation.CheckReturnValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.StampedLock;

/**
 * Creates validators for the Java API.
 * <p>
 * There are three kinds of validators:
 * <ul>
 *   <li>{@code requireThat()} for method preconditions.</li>
 *   <li>{@code assert that()} for class invariants, and method postconditions.</li>
 *   <li>{@code checkIf()} for returning multiple validation failures.</li>
 * </ul>
 * <p>
 * <b>Thread Safety</b>: This class is thread-safe.
 */
public final class DefaultJavaValidators
{
	private static final JavaValidatorsImpl DELEGATE = new JavaValidatorsImpl(MainApplicationScope.INSTANCE,
		Configuration.DEFAULT);
	private static final StampedLock CONTEXT_LOCK = new StampedLock();

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
	public static PrimitiveByteValidator requireThat(byte value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static ByteValidator requireThat(Byte value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PrimitiveShortValidator requireThat(short value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static ShortValidator requireThat(Short value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PrimitiveIntegerValidator requireThat(int value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static IntegerValidator requireThat(Integer value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PrimitiveLongValidator requireThat(long value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static LongValidator requireThat(Long value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PrimitiveFloatValidator requireThat(float value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static FloatValidator requireThat(Float value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PrimitiveDoubleValidator requireThat(double value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code Double}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	public static DoubleValidator requireThat(Double value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PrimitiveBooleanValidator requireThat(boolean value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static BooleanValidator requireThat(Boolean value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PrimitiveCharacterValidator requireThat(char value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static CharacterValidator requireThat(Character value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static BigIntegerValidator requireThat(BigInteger value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static BigDecimalValidator requireThat(BigDecimal value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static <T extends Comparable<T>> ComparableValidator<T> requireThat(T value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static <T> ObjectValidator<T> requireThat(T value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static <T extends Collection<E>, E> CollectionValidator<T, E> requireThat(T value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static <T extends List<E>, E> ListValidator<T, E> requireThat(T value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PrimitiveByteArrayValidator requireThat(byte[] value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PrimitiveShortArrayValidator requireThat(short[] value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PrimitiveIntegerArrayValidator requireThat(int[] value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PrimitiveLongArrayValidator requireThat(long[] value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PrimitiveFloatArrayValidator requireThat(float[] value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PrimitiveDoubleArrayValidator requireThat(double[] value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PrimitiveBooleanArrayValidator requireThat(boolean[] value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PrimitiveCharacterArrayValidator requireThat(char[] value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static <E> ObjectArrayValidator<E[], E> requireThat(E[] value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static <T extends Map<K, V>, K, V> MapValidator<T, K, V> requireThat(T value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static PathValidator requireThat(Path value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static StringValidator requireThat(String value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static UriValidator requireThat(URI value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

	/**
	 * Validates the state of a {@code Class}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails.
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static <T> GenericTypeValidator<T> requireThat(Class<T> value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static <T> OptionalValidator<T> requireThat(Optional<T> value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	public static InetAddressValidator requireThat(InetAddress value, String name)
	{
		return DELEGATE.requireThat(value, name);
	}

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
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveByteValidator that(byte value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveByteValidator that(byte value)
	{
		return DELEGATE.that(value);
	}

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
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static ByteValidator that(Byte value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static ByteValidator that(Byte value)
	{
		return DELEGATE.that(value);
	}

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
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveShortValidator that(short value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveShortValidator that(short value)
	{
		return DELEGATE.that(value);
	}

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
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static ShortValidator that(Short value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static ShortValidator that(Short value)
	{
		return DELEGATE.that(value);
	}

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
	public static PrimitiveIntegerValidator that(int value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveIntegerValidator that(int value)
	{
		return DELEGATE.that(value);
	}

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
	public static IntegerValidator that(Integer value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static IntegerValidator that(Integer value)
	{
		return DELEGATE.that(value);
	}

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
	public static PrimitiveLongValidator that(long value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveLongValidator that(long value)
	{
		return DELEGATE.that(value);
	}

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
	public static LongValidator that(Long value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static LongValidator that(Long value)
	{
		return DELEGATE.that(value);
	}

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
	public static PrimitiveFloatValidator that(float value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveFloatValidator that(float value)
	{
		return DELEGATE.that(value);
	}

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
	public static FloatValidator that(Float value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static FloatValidator that(Float value)
	{
		return DELEGATE.that(value);
	}

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
	public static PrimitiveDoubleValidator that(double value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveDoubleValidator that(double value)
	{
		return DELEGATE.that(value);
	}

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
	public static DoubleValidator that(Double value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static DoubleValidator that(Double value)
	{
		return DELEGATE.that(value);
	}

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
	public static PrimitiveBooleanValidator that(boolean value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveBooleanValidator that(boolean value)
	{
		return DELEGATE.that(value);
	}

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
	public static BooleanValidator that(Boolean value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static BooleanValidator that(Boolean value)
	{
		return DELEGATE.that(value);
	}

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
	public static PrimitiveCharacterValidator that(char value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveCharacterValidator that(char value)
	{
		return DELEGATE.that(value);
	}

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
	public static CharacterValidator that(Character value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static CharacterValidator that(Character value)
	{
		return DELEGATE.that(value);
	}

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
	public static BigIntegerValidator that(BigInteger value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static BigIntegerValidator that(BigInteger value)
	{
		return DELEGATE.that(value);
	}

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
	public static BigDecimalValidator that(BigDecimal value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static BigDecimalValidator that(BigDecimal value)
	{
		return DELEGATE.that(value);
	}

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
	public static <T extends Comparable<T>> ComparableValidator<T> that(T value, String name)
	{
		return DELEGATE.that(value, name);
	}

	/**
	 * Validates the state of a {@code Comparable} object.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T extends Comparable<T>> ComparableValidator<T> that(T value)
	{
		return DELEGATE.that(value);
	}

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
	 * @param <T>   the type of value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static <T> ObjectValidator<T> that(T value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	 * @param <T>   the type of value
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T> ObjectValidator<T> that(T value)
	{
		return DELEGATE.that(value);
	}

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
	public static <T extends Collection<E>, E> CollectionValidator<T, E> that(T value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static <T extends Collection<E>, E> CollectionValidator<T, E> that(T value)
	{
		return DELEGATE.that(value);
	}

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
	public static <T extends List<E>, E> ListValidator<T, E> that(T value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static <T extends List<E>, E> ListValidator<T, E> that(T value)
	{
		return DELEGATE.that(value);
	}

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
	public static PrimitiveByteArrayValidator that(byte[] value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveByteArrayValidator that(byte[] value)
	{
		return DELEGATE.that(value);
	}

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
	public static PrimitiveShortArrayValidator that(short[] value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveShortArrayValidator that(short[] value)
	{
		return DELEGATE.that(value);
	}

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
	public static PrimitiveIntegerArrayValidator that(int[] value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveIntegerArrayValidator that(int[] value)
	{
		return DELEGATE.that(value);
	}

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
	public static PrimitiveLongArrayValidator that(long[] value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveLongArrayValidator that(long[] value)
	{
		return DELEGATE.that(value);
	}

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
	public static PrimitiveFloatArrayValidator that(float[] value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveFloatArrayValidator that(float[] value)
	{
		return DELEGATE.that(value);
	}

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
	public static PrimitiveDoubleArrayValidator that(double[] value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveDoubleArrayValidator that(double[] value)
	{
		return DELEGATE.that(value);
	}

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
	public static PrimitiveBooleanArrayValidator that(boolean[] value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveBooleanArrayValidator that(boolean[] value)
	{
		return DELEGATE.that(value);
	}

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
	public static PrimitiveCharacterArrayValidator that(char[] value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PrimitiveCharacterArrayValidator that(char[] value)
	{
		return DELEGATE.that(value);
	}

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
	public static <E> ObjectArrayValidator<E[], E> that(E[] value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static <E> ObjectArrayValidator<E[], E> that(E[] value)
	{
		return DELEGATE.that(value);
	}

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
	public static <T extends Map<K, V>, K, V> MapValidator<T, K, V> that(T value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static <T extends Map<K, V>, K, V> MapValidator<T, K, V> that(T value)
	{
		return DELEGATE.that(value);
	}

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
	public static PathValidator that(Path value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static PathValidator that(Path value)
	{
		return DELEGATE.that(value);
	}

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
	public static StringValidator that(String value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static StringValidator that(String value)
	{
		return DELEGATE.that(value);
	}

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
	public static UriValidator that(URI value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static UriValidator that(URI value)
	{
		return DELEGATE.that(value);
	}

	/**
	 * Validates the state of a {@code Class}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static <T> GenericTypeValidator<T> that(Class<T> value, String name)
	{
		return DELEGATE.that(value, name);
	}

	/**
	 * Validates the state of a {@code Class}.
	 * <p>
	 * The returned validator throws an exception immediately if a validation fails. This exception is then
	 * converted into an {@link AssertionError}. Exceptions unrelated to validation failures are not converted.
	 * <p>
	 * This method is intended to be used with the {@code assert} keyword, like so:
	 * {@code assert that(value, name)}.
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T> GenericTypeValidator<T> that(Class<T> value)
	{
		return DELEGATE.that(value);
	}

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
	public static <T> OptionalValidator<T> that(Optional<T> value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static <T> OptionalValidator<T> that(Optional<T> value)
	{
		return DELEGATE.that(value);
	}

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
	public static InetAddressValidator that(InetAddress value, String name)
	{
		return DELEGATE.that(value, name);
	}

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
	public static InetAddressValidator that(InetAddress value)
	{
		return DELEGATE.that(value);
	}

	/**
	 * Validates the state of a {@code byte}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveByteValidator checkIf(byte value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code byte}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveByteValidator checkIf(byte value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Byte}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static ByteValidator checkIf(Byte value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Byte}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static ByteValidator checkIf(Byte value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code short}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 */
	public static PrimitiveShortValidator checkIf(short value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code short}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveShortValidator checkIf(short value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Short}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static ShortValidator checkIf(Short value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Short}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static ShortValidator checkIf(Short value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of an {@code int}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveIntegerValidator checkIf(int value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of an {@code int}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveIntegerValidator checkIf(int value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of an {@code Integer}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static IntegerValidator checkIf(Integer value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of an {@code Integer}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static IntegerValidator checkIf(Integer value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code long}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveLongValidator checkIf(long value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code long}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveLongValidator checkIf(long value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Long}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static LongValidator checkIf(Long value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Long}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static LongValidator checkIf(Long value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code float}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveFloatValidator checkIf(float value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code float}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveFloatValidator checkIf(float value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Float}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static FloatValidator checkIf(Float value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Float}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static FloatValidator checkIf(Float value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code double}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveDoubleValidator checkIf(double value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code double}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveDoubleValidator checkIf(double value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Double}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static DoubleValidator checkIf(Double value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Double}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static DoubleValidator checkIf(Double value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code boolean}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveBooleanValidator checkIf(boolean value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code boolean}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveBooleanValidator checkIf(boolean value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Boolean}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static BooleanValidator checkIf(Boolean value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Boolean}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static BooleanValidator checkIf(Boolean value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code char}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveCharacterValidator checkIf(char value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code char}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveCharacterValidator checkIf(char value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Character}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static CharacterValidator checkIf(Character value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Character}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static CharacterValidator checkIf(Character value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code BigInteger}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static BigIntegerValidator checkIf(BigInteger value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code BigInteger}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static BigIntegerValidator checkIf(BigInteger value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code BigDecimal}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static BigDecimalValidator checkIf(BigDecimal value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code BigDecimal}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static BigDecimalValidator checkIf(BigDecimal value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Comparable} object.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static <T extends Comparable<T>> ComparableValidator<T> checkIf(T value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Comparable} object.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T extends Comparable<T>> ComparableValidator<T> checkIf(T value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of an {@code Object}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static <T> ObjectValidator<T> checkIf(T value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of an {@code Object}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <T>   the type of the value
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T> ObjectValidator<T> checkIf(T value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Collection}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <T>   the type of the value
	 * @param <E>   the type of elements in the collection
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static <T extends Collection<E>, E> CollectionValidator<T, E> checkIf(T value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Collection}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <T>   the type of the value
	 * @param <E>   the type of elements in the collection
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T extends Collection<E>, E> CollectionValidator<T, E> checkIf(T value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code List}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <T>   the type of the value
	 * @param <E>   the type of elements in the list
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static <T extends List<E>, E> ListValidator<T, E> checkIf(T value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code List}
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <T>   the type of the value
	 * @param <E>   the type of elements in the list
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T extends List<E>, E> ListValidator<T, E> checkIf(T value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code byte} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveByteArrayValidator checkIf(byte[] value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code byte} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveByteArrayValidator checkIf(byte[] value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code short} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveShortArrayValidator checkIf(short[] value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code short} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveShortArrayValidator checkIf(short[] value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code int} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveIntegerArrayValidator checkIf(int[] value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code int} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveIntegerArrayValidator checkIf(int[] value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code long} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveLongArrayValidator checkIf(long[] value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code long} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveLongArrayValidator checkIf(long[] value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code float} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveFloatArrayValidator checkIf(float[] value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code float} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveFloatArrayValidator checkIf(float[] value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code double} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveDoubleArrayValidator checkIf(double[] value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code double} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveDoubleArrayValidator checkIf(double[] value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code boolean} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveBooleanArrayValidator checkIf(boolean[] value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code boolean} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveBooleanArrayValidator checkIf(boolean[] value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a primitive {@code char} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PrimitiveCharacterArrayValidator checkIf(char[] value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a primitive {@code char} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PrimitiveCharacterArrayValidator checkIf(char[] value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of an {@code Object} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <E>   the type of elements in the array
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static <E> ObjectArrayValidator<E[], E> checkIf(E[] value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of an {@code Object} array.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <E>   the type of elements in the array
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <E> ObjectArrayValidator<E[], E> checkIf(E[] value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Map}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
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
	public static <T extends Map<K, V>, K, V> MapValidator<T, K, V> checkIf(T value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Map}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <K>   the type of keys in the map
	 * @param <V>   the type of values in the map
	 * @param <T>   the type of the map
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T extends Map<K, V>, K, V> MapValidator<T, K, V> checkIf(T value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Path}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static PathValidator checkIf(Path value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Path}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static PathValidator checkIf(Path value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code String}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static StringValidator checkIf(String value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code String}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static StringValidator checkIf(String value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code URI}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static UriValidator checkIf(URI value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code URI}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static UriValidator checkIf(URI value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of a {@code Class}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static <T> GenericTypeValidator<T> checkIf(Class<T> value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of a {@code Class}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object
	 * @param value the value
	 * @return a validator for the value
	 */
	public static <T> GenericTypeValidator<T> checkIf(Class<T> value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of an {@code Optional}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static <T> OptionalValidator<T> checkIf(Optional<T> value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of an {@code Optional}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param <T>   the type of the class modelled by the {@code Class} object
	 * @param value the value
	 * @return a validator for the value
	 */
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static <T> OptionalValidator<T> checkIf(Optional<T> value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Validates the state of an {@code InetAddress}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @param name  the name of the value
	 * @return a validator for the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 */
	public static InetAddressValidator checkIf(InetAddress value, String name)
	{
		return DELEGATE.checkIf(value, name);
	}

	/**
	 * Validates the state of an {@code InetAddress}.
	 * <p>
	 * The returned validator captures exceptions on validation failure rather than throwing them immediately.
	 * These exceptions can be retrieved or thrown once the validation completes. Exceptions unrelated to
	 * validation failures are thrown immediately.
	 *
	 * @param value the value
	 * @return a validator for the value
	 */
	public static InetAddressValidator checkIf(InetAddress value)
	{
		return DELEGATE.checkIf(value);
	}

	/**
	 * Returns the contextual information for validators created out by this factory. The contextual information
	 * is a map of key-value pairs that can provide more details about validation failures. For example, if the
	 * message is "Password may not be empty" and the map contains the key-value pair
	 * {@code {"username": "john.smith"}}, the exception message would be:
	 * <p>
	 * {@snippet lang = output:
	 * Password may not be empty
	 * username: john.smith}
	 * <p>
	 * Note that values are wrapped in an {@code Optional} because modern maps do not support {@code null}
	 * values.
	 *
	 * @return an unmodifiable map from each entry's name to its value
	 */
	public static Map<String, Optional<Object>> getContext()
	{
		return StampedLocks.optimisticRead(CONTEXT_LOCK, DELEGATE::getContext);
	}

	/**
	 * Sets the contextual information for validators created by this factory.
	 * <p>
	 * This method adds contextual information to exception messages. The contextual information is stored as
	 * key-value pairs in a map. Values set by this method may be overridden by
	 * {@link ValidatorComponent#withContext(Object, String)}}.
	 *
	 * @param value the value of the entry
	 * @param name  the name of an entry
	 * @return the underlying validator factory
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name}:
	 *                                  <ul>
	 *                                    <li>contains whitespace</li>
	 *                                    <li>is empty</li>
	 *                                    <li>is already in use by the value being validated or the validator
	 *                                    context</li>
	 *                                  </ul>
	 */
	public static JavaValidators withContext(Object value, String name)
	{
		return StampedLocks.write(CONTEXT_LOCK, () -> DELEGATE.withContext(value, name));
	}

	/**
	 * Removes the contextual information of validators created by this factory.
	 *
	 * @param name the parameter name
	 * @return the underlying validator factory
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name}:
	 *                                  <ul>
	 *                                    <li>contains whitespace</li>
	 *                                    <li>is empty</li>
	 *                                  </ul>
	 */
	public static JavaValidators removeContext(String name)
	{
		return StampedLocks.write(CONTEXT_LOCK, () -> DELEGATE.removeContext(name));
	}

	/**
	 * Returns the global configuration shared by all validators.
	 * <p>
	 * <b>NOTE</b>: Updating this configuration affects existing and new validators.
	 *
	 * @return the global configuration updater
	 */
	@CheckReturnValue
	public static GlobalConfiguration globalConfiguration()
	{
		return DELEGATE.globalConfiguration();
	}

	private DefaultJavaValidators()
	{
	}
}