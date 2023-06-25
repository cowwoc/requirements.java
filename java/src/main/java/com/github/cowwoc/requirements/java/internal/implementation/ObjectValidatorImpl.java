/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.type.CollectionValidator;
import com.github.cowwoc.requirements.java.type.ListValidator;
import com.github.cowwoc.requirements.java.type.MapValidator;
import com.github.cowwoc.requirements.java.type.ObjectArrayValidator;
import com.github.cowwoc.requirements.java.type.ObjectValidator;
import com.github.cowwoc.requirements.java.type.OptionalValidator;
import com.github.cowwoc.requirements.java.type.PathValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveBooleanArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveByteArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveCharacterArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveDoubleArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveFloatArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveIntegerArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveLongArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveShortArrayValidator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
	 * Creates a new validator as a result of a validation.
	 *
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public ObjectValidatorImpl(ApplicationScope scope, AbstractValidator<?> validator, String name, T value)
	{
		this(scope, validator.configuration(), name, value, validator.context, validator.failures);
	}

	/**
	 * Creates a new validator.
	 *
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public ObjectValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		T value)
	{
		this(scope, configuration, name, value, new HashMap<>(), new ArrayList<>());
	}

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	private ObjectValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		T value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public T getValue()
	{
		return value;
	}

	@Override
	public PrimitiveByteValidatorImpl isByte()
	{
		if (hasFailed())
			return new PrimitiveByteValidatorImpl(scope, this, name, (byte) 0);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Byte))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, Byte.class).
					toString());
			return new PrimitiveByteValidatorImpl(scope, this, name, (byte) 0);
		}
		return new PrimitiveByteValidatorImpl(scope, this, name, (byte) value);
	}

	@Override
	public PrimitiveShortValidatorImpl isShort()
	{
		if (hasFailed())
			return new PrimitiveShortValidatorImpl(scope, this, name, (short) 0);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Short))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, Short.class).
					toString());
			return new PrimitiveShortValidatorImpl(scope, this, name, (short) 0);
		}
		return new PrimitiveShortValidatorImpl(scope, this, name, (short) value);
	}

	@Override
	public PrimitiveIntegerValidatorImpl isInt()
	{
		if (hasFailed())
			return new PrimitiveIntegerValidatorImpl(scope, this, name, 0);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Integer))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, Integer.class).
					toString());
			return new PrimitiveIntegerValidatorImpl(scope, this, name, 0);
		}
		return new PrimitiveIntegerValidatorImpl(scope, this, name, (int) value);
	}

	@Override
	public PrimitiveLongValidatorImpl isLong()
	{
		if (hasFailed())
			return new PrimitiveLongValidatorImpl(scope, this, name, 0L);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Long))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, Long.class).
					toString());
			return new PrimitiveLongValidatorImpl(scope, this, name, 0L);
		}
		return new PrimitiveLongValidatorImpl(scope, this, name, (long) value);
	}

	@Override
	public PrimitiveFloatValidatorImpl isFloat()
	{
		if (hasFailed())
			return new PrimitiveFloatValidatorImpl(scope, this, name, 0.0f);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Float))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, Float.class).
					toString());
			return new PrimitiveFloatValidatorImpl(scope, this, name, 0.0f);
		}
		return new PrimitiveFloatValidatorImpl(scope, this, name, (float) value);
	}

	@Override
	public PrimitiveDoubleValidatorImpl isDouble()
	{
		if (hasFailed())
			return new PrimitiveDoubleValidatorImpl(scope, this, name, 0.0);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Double))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, Double.class).
					toString());
			return new PrimitiveDoubleValidatorImpl(scope, this, name, 0.0);
		}
		return new PrimitiveDoubleValidatorImpl(scope, this, name, (double) value);
	}

	@Override
	public PrimitiveBooleanValidatorImpl isBoolean()
	{
		if (hasFailed())
			return new PrimitiveBooleanValidatorImpl(scope, this, name, false);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Boolean))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, Boolean.class).
					toString());
			return new PrimitiveBooleanValidatorImpl(scope, this, name, false);
		}
		return new PrimitiveBooleanValidatorImpl(scope, this, name, (boolean) value);
	}

	@Override
	public PrimitiveCharacterValidatorImpl isChar()
	{
		if (hasFailed())
			return new PrimitiveCharacterValidatorImpl(scope, this, name, '-');
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Character))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, Character.class).
					toString());
			return new PrimitiveCharacterValidatorImpl(scope, this, name, '-');
		}
		return new PrimitiveCharacterValidatorImpl(scope, this, name, (char) value);
	}

	@Override
	public BigIntegerValidatorImpl isBigInteger()
	{
		if (hasFailed())
			return new BigIntegerValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof BigInteger))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, BigInteger.class).
					toString());
			return new BigIntegerValidatorImpl(scope, this, name, null);
		}
		return new BigIntegerValidatorImpl(scope, this, name, (BigInteger) value);
	}

	@Override
	public BigDecimalValidatorImpl isBigDecimal()
	{
		if (hasFailed())
			return new BigDecimalValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof BigDecimal))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, BigDecimal.class).
					toString());
			return new BigDecimalValidatorImpl(scope, this, name, null);
		}
		return new BigDecimalValidatorImpl(scope, this, name, (BigDecimal) value);
	}

	@Override
	public ComparableValidatorImpl<?> isComparable()
	{
		if (hasFailed())
			return new ComparableValidatorImpl<>(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Comparable<?>))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, Comparable.class).
					toString());
			return new ComparableValidatorImpl<>(scope, this, name, null);
		}
		return new ComparableValidatorImpl<>(scope, this, name, (BigDecimal) value);
	}

	@Override
	public CollectionValidator<?, ? extends Collection<?>> isCollection()
	{
		if (hasFailed())
			return new CollectionValidatorImpl<>(scope, this, name, null, Pluralizer.ELEMENT);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Collection<?>))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, Collection.class).
					toString());
			return new CollectionValidatorImpl<>(scope, this, name, null, Pluralizer.ELEMENT);
		}
		return new CollectionValidatorImpl<>(scope, this, name, (Collection<?>) value,
			Pluralizer.ELEMENT);
	}

	@Override
	public ListValidator<?, ? extends List<?>> isList()
	{
		if (hasFailed())
			return new ListValidatorImpl<>(scope, this, name, null, Pluralizer.ELEMENT);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof List<?>))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, List.class).
					toString());
			return new ListValidatorImpl<>(scope, this, name, null, Pluralizer.ELEMENT);
		}
		return new ListValidatorImpl<>(scope, this, name, (List<?>) value, Pluralizer.ELEMENT);
	}

	@Override
	public PrimitiveByteArrayValidator isByteArray()
	{
		if (hasFailed())
			return new PrimitiveByteArrayValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof byte[]))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, byte[].class).
					toString());
			return new PrimitiveByteArrayValidatorImpl(scope, this, name, null);
		}
		return new PrimitiveByteArrayValidatorImpl(scope, this, name, (byte[]) value);
	}

	@Override
	public PrimitiveShortArrayValidator isShortArray()
	{
		if (hasFailed())
			return new PrimitiveShortArrayValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof short[]))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, short[].class).
					toString());
			return new PrimitiveShortArrayValidatorImpl(scope, this, name, null);
		}
		return new PrimitiveShortArrayValidatorImpl(scope, this, name, (short[]) value);
	}

	@Override
	public PrimitiveIntegerArrayValidator isIntArray()
	{
		if (hasFailed())
			return new PrimitiveIntegerArrayValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof int[]))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, int[].class).
					toString());
			return new PrimitiveIntegerArrayValidatorImpl(scope, this, name, null);
		}
		return new PrimitiveIntegerArrayValidatorImpl(scope, this, name, (int[]) value);
	}

	@Override
	public PrimitiveLongArrayValidator isLongArray()
	{
		if (hasFailed())
			return new PrimitiveLongArrayValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof long[]))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, long[].class).
					toString());
			return new PrimitiveLongArrayValidatorImpl(scope, this, name, null);
		}
		return new PrimitiveLongArrayValidatorImpl(scope, this, name, (long[]) value);
	}

	@Override
	public PrimitiveFloatArrayValidator isFloatArray()
	{
		if (hasFailed())
			return new PrimitiveFloatArrayValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof float[]))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, float[].class).
					toString());
			return new PrimitiveFloatArrayValidatorImpl(scope, this, name, null);
		}
		return new PrimitiveFloatArrayValidatorImpl(scope, this, name, (float[]) value);
	}

	@Override
	public PrimitiveDoubleArrayValidator isDoubleArray()
	{
		if (hasFailed())
			return new PrimitiveDoubleArrayValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof double[]))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, double[].class).
					toString());
			return new PrimitiveDoubleArrayValidatorImpl(scope, this, name, null);
		}
		return new PrimitiveDoubleArrayValidatorImpl(scope, this, name, (double[]) value);
	}

	@Override
	public PrimitiveBooleanArrayValidator isBooleanArray()
	{
		if (hasFailed())
			return new PrimitiveBooleanArrayValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof boolean[]))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, boolean[].class).
					toString());
			return new PrimitiveBooleanArrayValidatorImpl(scope, this, name, null);
		}
		return new PrimitiveBooleanArrayValidatorImpl(scope, this, name, (boolean[]) value);
	}

	@Override
	public PrimitiveCharacterArrayValidator isCharArray()
	{
		if (hasFailed())
			return new PrimitiveCharacterArrayValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof char[]))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, char[].class).
					toString());
			return new PrimitiveCharacterArrayValidatorImpl(scope, this, name, null);
		}
		return new PrimitiveCharacterArrayValidatorImpl(scope, this, name, (char[]) value);
	}

	@Override
	public ObjectArrayValidator<?, ?> isObjectArray()
	{
		if (hasFailed())
			return new ObjectArrayValidatorImpl<>(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Object[]))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, Object[].class).
					toString());
			return new ObjectArrayValidatorImpl<>(scope, this, name, null);
		}
		return new ObjectArrayValidatorImpl<>(scope, this, name, (Object[]) value);
	}

	@Override
	public MapValidator<?, ?, ? extends Map<?, ?>> isMap()
	{
		if (hasFailed())
			return new MapValidatorImpl<>(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Map<?, ?>))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, Map.class).
					toString());
			return new MapValidatorImpl<>(scope, this, name, null);
		}
		return new MapValidatorImpl<>(scope, this, name, (Map<?, ?>) value);
	}

	@Override
	public PathValidator isPath()
	{
		if (hasFailed())
			return new PathValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Path))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, Path.class).
					toString());
			return new PathValidatorImpl(scope, this, name, null);
		}
		return new PathValidatorImpl(scope, this, name, (Path) value);
	}

	@Override
	public StringValidatorImpl isString()
	{
		if (hasFailed())
			return new StringValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof String))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, String.class).
					toString());
			return new StringValidatorImpl(scope, this, name, null);
		}
		return new StringValidatorImpl(scope, this, name, (String) value);
	}

	@Override
	public UriValidatorImpl isUri()
	{
		if (hasFailed())
			return new UriValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof URI))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, URI.class).
					toString());
			return new UriValidatorImpl(scope, this, name, null);
		}
		return new UriValidatorImpl(scope, this, name, (URI) value);
	}

	@Override
	public UrlValidatorImpl isUrl()
	{
		if (hasFailed())
			return new UrlValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof URL))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, URL.class).
					toString());
			return new UrlValidatorImpl(scope, this, name, null);
		}
		return new UrlValidatorImpl(scope, this, name, (URL) value);
	}

	@Override
	public ClassValidatorImpl<?> isClass()
	{
		if (hasFailed())
			return new ClassValidatorImpl<>(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Class<?>))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, Class.class).
					toString());
			return new ClassValidatorImpl<>(scope, this, name, null);
		}
		return new ClassValidatorImpl<>(scope, this, name, (Class<?>) value);
	}

	@Override
	public OptionalValidator<?> isOptional()
	{
		if (hasFailed())
			return new OptionalValidatorImpl<>(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof Optional<?>))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, Optional.class).
					toString());
			return new OptionalValidatorImpl<>(scope, this, name, null);
		}
		return new OptionalValidatorImpl<>(scope, this, name, (Optional<?>) value);
	}

	@Override
	public InetAddressValidatorImpl isInetAddress()
	{
		if (hasFailed())
			return new InetAddressValidatorImpl(scope, this, name, null);
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!(value instanceof InetAddress))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, true, InetAddress.class).
					toString());
			return new InetAddressValidatorImpl(scope, this, name, null);
		}
		return new InetAddressValidatorImpl(scope, this, name, (InetAddress) value);
	}
}