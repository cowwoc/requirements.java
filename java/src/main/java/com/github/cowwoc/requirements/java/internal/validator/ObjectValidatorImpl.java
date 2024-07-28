/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.validator;

import com.github.cowwoc.requirements.java.GenericType;
import com.github.cowwoc.requirements.java.internal.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.validator.BigDecimalValidator;
import com.github.cowwoc.requirements.java.validator.BigIntegerValidator;
import com.github.cowwoc.requirements.java.validator.ClassValidator;
import com.github.cowwoc.requirements.java.validator.CollectionValidator;
import com.github.cowwoc.requirements.java.validator.ComparableValidator;
import com.github.cowwoc.requirements.java.validator.InetAddressValidator;
import com.github.cowwoc.requirements.java.validator.ListValidator;
import com.github.cowwoc.requirements.java.validator.MapValidator;
import com.github.cowwoc.requirements.java.validator.ObjectArrayValidator;
import com.github.cowwoc.requirements.java.validator.ObjectValidator;
import com.github.cowwoc.requirements.java.validator.OptionalValidator;
import com.github.cowwoc.requirements.java.validator.PathValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveBooleanArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveBooleanValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveByteArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveByteValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveCharacterArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveCharacterValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveDoubleArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveDoubleValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveFloatArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveFloatValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveIntegerArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveLongArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveLongValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveShortArrayValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveShortValidator;
import com.github.cowwoc.requirements.java.validator.StringValidator;
import com.github.cowwoc.requirements.java.validator.UriValidator;
import com.github.cowwoc.requirements.java.validator.UrlValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements.java.validator.PrimitiveIntegerValidator;

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
 * @param <T> the type of the value that is being validated
 */
public final class ObjectValidatorImpl<T> extends AbstractObjectValidator<ObjectValidator<T>, T>
	implements ObjectValidator<T>
{
	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         the value
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public ObjectValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<T> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public PrimitiveByteValidator isByte()
	{
		return convertValueTo(Byte.class, newValue ->
			new PrimitiveByteValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	/**
	 * Converts the value to a type.
	 *
	 * @param <S>     a validator for the value's expected type
	 * @param <U>     the value's expected type
	 * @param type    the value's expected type
	 * @param factory creates a validator for the expected value
	 * @return the new validator
	 */
	private <S, U> S convertValueTo(Class<U> type, ValidatorFactory<U, S> factory)
	{
		return convertValueTo(GenericType.from(type), factory);
	}

	/**
	 * Converts the value to a type.
	 *
	 * @param <S>     a validator for the value's expected type
	 * @param <U>     the value's expected type
	 * @param type    the value's expected type
	 * @param factory creates a validator for the expected value
	 * @return the new validator
	 */
	private <S, U> S convertValueTo(GenericType<U> type, ValidatorFactory<U, S> factory)
	{
		MaybeUndefined<U> newValue = switch (value.test(value -> value == null ||
			GenericType.from(value.getClass()).isSubtypeOf(type)))
		{
			case UNDEFINED, FALSE ->
			{
				addIllegalArgumentException(
					ObjectMessages.isInstanceOf(scope, this, type).toString());
				yield MaybeUndefined.undefined();
			}
			case TRUE -> value.nullToUndefined().mapDefined(value ->
			{
				@SuppressWarnings("unchecked")
				U temp = (U) value;
				return temp;
			});
		};
		return factory.apply(newValue);
	}

	@Override
	public PrimitiveShortValidator isShort()
	{
		return convertValueTo(Short.class, newValue ->
			new PrimitiveShortValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public PrimitiveIntegerValidator isInteger()
	{
		return convertValueTo(Integer.class, newValue ->
			new PrimitiveIntegerValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public PrimitiveLongValidator isLong()
	{
		return convertValueTo(Long.class, newValue ->
			new PrimitiveLongValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public PrimitiveFloatValidator isFloat()
	{
		return convertValueTo(Float.class, newValue ->
			new PrimitiveFloatValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public PrimitiveDoubleValidator isDouble()
	{
		return convertValueTo(Double.class, newValue ->
			new PrimitiveDoubleValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public PrimitiveBooleanValidator isBoolean()
	{
		return convertValueTo(Boolean.class, newValue ->
			new PrimitiveBooleanValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public PrimitiveCharacterValidator isCharacter()
	{
		return convertValueTo(Character.class, newValue ->
			new PrimitiveCharacterValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public BigIntegerValidator isBigInteger()
	{
		return convertValueTo(BigInteger.class, newValue ->
			new BigIntegerValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public BigDecimalValidator isBigDecimal()
	{
		return convertValueTo(BigDecimal.class, newValue ->
			new BigDecimalValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public <U extends Comparable<U>> ComparableValidator<U> isComparable(Class<U> type)
	{
		return isComparable(GenericType.from(type));
	}

	@Override
	public <U extends Comparable<U>> ComparableValidator<U> isComparable(GenericType<U> type)
	{
		return convertValueTo(type, newValue ->
			new ComparableValidatorImpl<>(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public <U extends Collection<E>, E> CollectionValidator<U, E> isCollection(Class<U> type)
	{
		return isCollection(GenericType.from(type));
	}

	@Override
	public <U extends Collection<E>, E> CollectionValidator<U, E> isCollection(GenericType<U> type)
	{
		return convertValueTo(type, newValue ->
			new CollectionValidatorImpl<>(scope, configuration, name, newValue, Pluralizer.ELEMENT, context,
				failures));
	}

	@Override
	public <U extends List<E>, E> ListValidator<U, E> isList(Class<U> type)
	{
		return isList(GenericType.from(type));
	}

	@Override
	public <U extends List<E>, E> ListValidator<U, E> isList(GenericType<U> type)
	{
		return convertValueTo(type, newValue ->
			new ListValidatorImpl<>(scope, configuration, name, newValue, Pluralizer.ELEMENT, context, failures));
	}

	@Override
	public PrimitiveByteArrayValidator isByteArray()
	{
		return convertValueTo(byte[].class, newValue ->
			new PrimitiveByteArrayValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public PrimitiveShortArrayValidator isShortArray()
	{
		return convertValueTo(short[].class, newValue ->
			new PrimitiveShortArrayValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public PrimitiveIntegerArrayValidator isIntArray()
	{
		return convertValueTo(int[].class, newValue ->
			new PrimitiveIntegerArrayValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public PrimitiveLongArrayValidator isLongArray()
	{
		return convertValueTo(long[].class, newValue ->
			new PrimitiveLongArrayValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public PrimitiveFloatArrayValidator isFloatArray()
	{
		return convertValueTo(float[].class, newValue ->
			new PrimitiveFloatArrayValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public PrimitiveDoubleArrayValidator isDoubleArray()
	{
		return convertValueTo(double[].class, newValue ->
			new PrimitiveDoubleArrayValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public PrimitiveBooleanArrayValidator isBooleanArray()
	{
		return convertValueTo(boolean[].class, newValue ->
			new PrimitiveBooleanArrayValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public PrimitiveCharacterArrayValidator isCharArray()
	{
		return convertValueTo(char[].class, newValue ->
			new PrimitiveCharacterArrayValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public <U> ObjectArrayValidator<U[], U> isObjectArray(Class<U> elementType)
	{
		GenericType<U> genericType = GenericType.from(elementType);
		return isObjectArray(genericType);
	}

	@Override
	public <E> ObjectArrayValidator<E[], E> isObjectArray(GenericType<E> elementType)
	{
		GenericType<E[]> arrayType = elementType.asArrayComponent();
		return convertValueTo(arrayType, newValue ->
			new ObjectArrayValidatorImpl<>(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public <U extends Map<K, V>, K, V> MapValidator<U, K, V> isMap(Class<U> type)
	{
		return isMap(GenericType.from(type));
	}

	@Override
	public <U extends Map<K, V>, K, V> MapValidator<U, K, V> isMap(GenericType<U> type)
	{
		return convertValueTo(type, newValue ->
			new MapValidatorImpl<>(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public PathValidator isPath()
	{
		return convertValueTo(Path.class, newValue ->
			new PathValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public StringValidator isString()
	{
		return convertValueTo(String.class, newValue ->
			new StringValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public UriValidator isUri()
	{
		return convertValueTo(URI.class, newValue ->
			new UriValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public UrlValidator isUrl()
	{
		return convertValueTo(URL.class, newValue ->
			new UrlValidatorImpl(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public <U> ClassValidator<U> isClass(GenericType<Class<U>> type)
	{
		return convertValueTo(type, newValue ->
			new ClassValidatorImpl<>(scope, configuration, name, newValue.mapDefined(GenericType::from), context,
				failures));
	}

	@Override
	public <U> ClassValidator<U> isGenericType(GenericType<GenericType<U>> type)
	{
		return convertValueTo(type, newValue ->
			new ClassValidatorImpl<>(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public <U> OptionalValidator<U> isOptional(GenericType<Optional<U>> type)
	{
		return convertValueTo(type, newValue ->
			new OptionalValidatorImpl<>(scope, configuration, name, newValue, context, failures));
	}

	@Override
	public InetAddressValidator isInetAddress()
	{
		return convertValueTo(InetAddress.class, newValue ->
			new InetAddressValidatorImpl(scope, configuration, name, newValue, context, failures));
	}
}