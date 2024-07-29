/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;
import com.github.cowwoc.requirements10.java.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Validates the state of an {@link Object}.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 *
 * @param <T> the type of the value that is being validated
 */
public interface ObjectValidator<T> extends
	ValidatorComponent<ObjectValidator<T>, T>,
	ObjectComponent<ObjectValidator<T>, T>
{
	/**
	 * Ensures that the value is a {@code Byte}.
	 *
	 * @return a validator for the {@code byte} representation of the value
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Byte}
	 */
	PrimitiveByteValidator isByte();

	/**
	 * Ensures that the value is a {@code Short}.
	 *
	 * @return a validator for the {@code short} representation of the value
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Short}
	 */
	PrimitiveShortValidator isShort();

	/**
	 * Ensures that the value is an {@code Integer}.
	 *
	 * @return a validator for the {@code int} representation of the value
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not an {@code Integer}
	 */
	PrimitiveIntegerValidator isInteger();

	/**
	 * Ensures that the value is a {@code Long}.
	 *
	 * @return a validator for the {@code long} representation of the value
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Long}
	 */
	PrimitiveLongValidator isLong();

	/**
	 * Ensures that the value is a {@code Float}.
	 *
	 * @return a validator for the {@code float} representation of the value
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Float}
	 */
	PrimitiveFloatValidator isFloat();

	/**
	 * Ensures that the value is a {@code Double}.
	 *
	 * @return a validator for the {@code double} representation of the value
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Double}
	 */
	PrimitiveDoubleValidator isDouble();

	/**
	 * Ensures that the value is a {@code Boolean}.
	 *
	 * @return a validator for the {@code boolean} representation of the value
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Boolean}
	 * @see Boolean#parseBoolean(String)
	 */
	PrimitiveBooleanValidator isBoolean();

	/**
	 * Ensures that the value is a {@code Character}.
	 *
	 * @return a validator for the {@code char} representation of the value
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Character}
	 * @see Character#valueOf(char)
	 */
	PrimitiveCharacterValidator isCharacter();

	/**
	 * Ensures that the value is a {@code BigInteger}.
	 *
	 * @return a validator for the {@code BigInteger}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code BigInteger}
	 */
	BigIntegerValidator isBigInteger();

	/**
	 * Ensures that the value is a {@code BigDecimal}.
	 *
	 * @return a validator for the {@code BigDecimal}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code BigDecimal}
	 */
	BigDecimalValidator isBigDecimal();

	/**
	 * Ensures that the value is {@code Comparable}.
	 *
	 * @param <U>  the type of {@code Comparable} to assume the value is. The type parameters cannot be
	 *             validated at runtime, so their value must be assumed.
	 * @param type the type of {@code Comparable} to assume the value is. For types that contain
	 *             type-parameters, use the {@link #isComparable(GenericType) TypeToken} overload.
	 * @return a validator for the {@code Comparable}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Comparable} subtype of the specified
	 *                                  {@code type}
	 */
	<U extends Comparable<U>> ComparableValidator<U> isComparable(Class<U> type);

	/**
	 * Ensures that the value is {@code Comparable}.
	 *
	 * @param <U>  the type of {@code Comparable} to assume the value is. The type parameters cannot be
	 *             validated at runtime, so their value must be assumed.
	 * @param type the type of {@code Comparable} to assume the value is. For types without type-parameters,
	 *             prefer the {@link #isComparable(Class) Class} overload.
	 * @return a validator for the {@code Comparable}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Comparable} subtype of the specified
	 *                                  {@code type}
	 */
	<U extends Comparable<U>> ComparableValidator<U> isComparable(GenericType<U> type);

	/**
	 * Ensures that the value is a {@code Collection}.
	 *
	 * @param <U>  the type of {@code Collection} to assume the value is. The type parameters cannot be
	 *             validated at runtime, so their value must be assumed.
	 * @param <E>  the type of elements to assume is inside the collection
	 * @param type the type of {@code Collection} to cast the value to. For types that contain type-parameters,
	 *             use the {@link #isCollection(GenericType) TypeToken} overload.
	 * @return a validator for the {@code Collection}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Collection} subtype of the specified
	 *                                  {@code type}
	 */
	<U extends Collection<E>, E> CollectionValidator<U, E> isCollection(Class<U> type);

	/**
	 * Ensures that the value is a {@code Collection}.
	 *
	 * @param <U>  the type of {@code Collection} to assume the value is. The type parameters cannot be
	 *             validated at runtime, so their value must be assumed.
	 * @param <E>  the type of elements to assume is inside the collection
	 * @param type the type of {@code Collection} to cast the value to. For types without type-parameters,
	 *             prefer the {@link #isCollection(Class) Class} overload.
	 * @return a validator for the {@code Collection}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Collection} subtype of the specified
	 *                                  {@code type}
	 */
	<U extends Collection<E>, E> CollectionValidator<U, E> isCollection(GenericType<U> type);

	/**
	 * Ensures that the value is a {@code List}.
	 *
	 * @param <U>  the type of {@code List} to assume the value is. The type parameters cannot be validated at
	 *             runtime, so their value must be assumed.
	 * @param <E>  the type of elements to assume is inside the list
	 * @param type the type of {@code List} to cast the value to. For types that contain type-parameters, use
	 *             the {@link #isList(GenericType) TypeToken} overload.
	 * @return a validator for the {@code List}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code List} subtype of the specified
	 *                                  {@code type}
	 */
	<U extends List<E>, E> ListValidator<U, E> isList(Class<U> type);

	/**
	 * Ensures that the value is a {@code List}.
	 *
	 * @param <U>  the type of {@code List} to assume the value is. The type parameters cannot be validated at
	 *             runtime, so their value must be assumed.
	 * @param <E>  the type of elements to assume is inside the list
	 * @param type the type of {@code List} to cast the value to. For types without type-parameters, prefer the
	 *             {@link #isList(Class) Class} overload.
	 * @return a validator for the {@code List}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code List} subtype of the specified
	 *                                  {@code type}
	 */
	<U extends List<E>, E> ListValidator<U, E> isList(GenericType<U> type);

	/**
	 * Ensures that the value is a {@code byte[]}.
	 *
	 * @return a validator for the {@code byte[]}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code byte[]}
	 */
	PrimitiveByteArrayValidator isByteArray();

	/**
	 * Ensures that the value is a {@code short[]}.
	 *
	 * @return a validator for the {@code short[]}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code short[]}
	 */
	PrimitiveShortArrayValidator isShortArray();

	/**
	 * Ensures that the value is an {@code int[]}.
	 *
	 * @return a validator for the {@code int[]}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not an {@code int[]}
	 */
	PrimitiveIntegerArrayValidator isIntArray();

	/**
	 * Ensures that the value is a {@code long[]}.
	 *
	 * @return a validator for the {@code long[]}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code long[]}
	 */
	PrimitiveLongArrayValidator isLongArray();

	/**
	 * Ensures that the value is a {@code float[]}.
	 *
	 * @return a validator for the {@code float[]}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code float[]}
	 */
	PrimitiveFloatArrayValidator isFloatArray();

	/**
	 * Ensures that the value is a {@code double[]}.
	 *
	 * @return a validator for the {@code double[]}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code double[]}
	 */
	PrimitiveDoubleArrayValidator isDoubleArray();

	/**
	 * Ensures that the value is a {@code boolean[]}.
	 *
	 * @return a validator for the {@code boolean[]}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code boolean[]}
	 */
	PrimitiveBooleanArrayValidator isBooleanArray();

	/**
	 * Ensures that the value is a {@code char[]}.
	 *
	 * @return a validator for the {@code char[]}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code char[]}
	 */
	PrimitiveCharacterArrayValidator isCharArray();

	/**
	 * Ensures that the value is an {@code object[]}.
	 *
	 * @param <U>         the type of elements to assume is inside the array. The type parameters cannot be
	 *                    validated at runtime, so their value must be assumed.
	 * @param elementType the type of elements to assume is inside the array. For types that contain
	 *                    type-parameters, use the {@link #isObjectArray(GenericType) TypeToken} overload.
	 * @return a validator for the {@code object[]}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not an {@code Object[]} subtype of the specified
	 *                                  {@code type}
	 */
	<U> ObjectArrayValidator<U[], U> isObjectArray(Class<U> elementType);

	/**
	 * Ensures that the value is an {@code object[]}.
	 *
	 * @param <E>         the type of elements to assume is inside the array. The type parameters cannot be
	 *                    validated at runtime, so their value must be assumed.
	 * @param elementType the type of elements to assume is inside the array. For types without type-parameters,
	 *                    prefer the {@link #isObjectArray(Class) Class} overload.
	 * @return a validator for the {@code object[]}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not an {@code Object[]} subtype of the specified
	 *                                  {@code type}
	 */
	<E> ObjectArrayValidator<E[], E> isObjectArray(GenericType<E> elementType);

	/**
	 * Ensures that the value is a {@code Map}.
	 *
	 * @param <U>  the type of {@code Map} to assume the value is. The type parameters cannot be validated at
	 *             runtime, so their value must be assumed.
	 * @param <K>  the type of keys to assume is inside the map
	 * @param <V>  the type of values to assume is inside the map
	 * @param type the type of {@code Map} to cast the value to. For types that contain type-parameters, use the
	 *             {@link #isMap(GenericType) TypeToken} overload.
	 * @return a validator for the {@code Map}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Map} subtype of the specified {@code type}
	 */
	<U extends Map<K, V>, K, V> MapValidator<U, K, V> isMap(Class<U> type);

	/**
	 * Ensures that the value is a {@code Map}.
	 *
	 * @param <U>  the type of {@code Map} to assume the value is. The type parameters cannot be validated at
	 *             runtime, so their value must be assumed.
	 * @param <K>  the type of keys to assume is inside the map
	 * @param <V>  the type of values to assume is inside the map
	 * @param type the type of {@code Map} to cast the value to. For types without type-parameters, prefer the
	 *             {@link #isMap(Class) Class} overload.
	 * @return a validator for the {@code Map}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Map} subtype of the specified {@code type}
	 */
	<U extends Map<K, V>, K, V> MapValidator<U, K, V> isMap(GenericType<U> type);

	/**
	 * Ensures that the value is a {@code Path}.
	 *
	 * @return a validator for the {@code Path}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Path}
	 */
	PathValidator isPath();

	/**
	 * Ensures that the value is a {@code String}.
	 *
	 * @return a validator for the {@code String}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code String}
	 */
	StringValidator isString();

	/**
	 * Ensures that the value is a {@code URI}.
	 *
	 * @return a validator for the {@code URI}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code URI}
	 */
	UriValidator isUri();

	/**
	 * Ensures that the value is a {@code URL}.
	 *
	 * @return a validator for the {@code URL}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code URL}
	 */
	UrlValidator isUrl();

	/**
	 * Ensures that the value is a {@code Class}.
	 *
	 * @param <U>  the type of {@code Class} to assume the value is. The type parameters cannot be validated at
	 *             runtime, so their value must be assumed.
	 * @param type the type of {@code Class} to assume the value is
	 * @return a validator for the {@code Class}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Class} subtype of the specified
	 *                                  {@code type}
	 */
	<U> ClassValidator<U> isClass(GenericType<Class<U>> type);

	/**
	 * Ensures that the value is a {@code Class}.
	 *
	 * @param <U>  the type of {@code Class} to assume the value is. The type parameters cannot be validated at
	 *             runtime, so their value must be assumed.
	 * @param type the type of {@code Class} to assume the value is
	 * @return a validator for the {@code Class}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a {@code Class} subtype of the specified
	 *                                  {@code type}
	 */
	<U> ClassValidator<U> isGenericType(GenericType<GenericType<U>> type);

	/**
	 * Ensures that the value is an {@code Optional}.
	 *
	 * @param <U>  the type of {@code Optional} to assume the value is. The type parameters cannot be validated
	 *             at runtime, so their value must be assumed.
	 * @param type the type of {@code Optional} to assume the value is.
	 * @return a validator for the {@code Optional}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not an {@code Optional} subtype of the specified
	 *                                  {@code type}
	 */
	<U> OptionalValidator<U> isOptional(GenericType<Optional<U>> type);

	/**
	 * Ensures that the value is an {@code InetAddress}.
	 *
	 * @return a validator for the {@code InetAddress}
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not an {@code InetAddress}
	 */
	InetAddressValidator isInetAddress();
}