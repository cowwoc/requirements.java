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
	 * @param value         (optional) the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public ObjectValidatorImpl(ApplicationScope scope, Configuration configuration, String name, T value,
		Map<String, Object> context, List<ValidationFailure> failures)
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
			return new PrimitiveByteValidatorImpl(scope, configuration, name, (byte) 0, context, failures);
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
			return new PrimitiveByteValidatorImpl(scope, configuration, name, (byte) 0, context, failures);
		}
		return new PrimitiveByteValidatorImpl(scope, configuration, name, (byte) value, context, failures);
	}

	@Override
	public PrimitiveShortValidatorImpl isShort()
	{
		if (hasFailed())
			return new PrimitiveShortValidatorImpl(scope, configuration, name, (short) 0, context, failures);
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
			return new PrimitiveShortValidatorImpl(scope, configuration, name, (short) 0, context, failures);
		}
		return new PrimitiveShortValidatorImpl(scope, configuration, name, (short) value, context, failures);
	}

	@Override
	public PrimitiveIntegerValidatorImpl isInt()
	{
		if (hasFailed())
			return new PrimitiveIntegerValidatorImpl(scope, configuration, name, 0, context, failures);
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
			return new PrimitiveIntegerValidatorImpl(scope, configuration, name, 0, context, failures);
		}
		return new PrimitiveIntegerValidatorImpl(scope, configuration, name, (int) value, context, failures);
	}

	@Override
	public PrimitiveLongValidatorImpl isLong()
	{
		if (hasFailed())
			return new PrimitiveLongValidatorImpl(scope, configuration, name, 0L, context, failures);
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
			return new PrimitiveLongValidatorImpl(scope, configuration, name, 0L, context, failures);
		}
		return new PrimitiveLongValidatorImpl(scope, configuration, name, (long) value, context, failures);
	}

	@Override
	public PrimitiveFloatValidatorImpl isFloat()
	{
		if (hasFailed())
			return new PrimitiveFloatValidatorImpl(scope, configuration, name, 0.0f, context, failures);
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
			return new PrimitiveFloatValidatorImpl(scope, configuration, name, 0.0f, context, failures);
		}
		return new PrimitiveFloatValidatorImpl(scope, configuration, name, (float) value, context, failures);
	}

	@Override
	public PrimitiveDoubleValidatorImpl isDouble()
	{
		if (hasFailed())
			return new PrimitiveDoubleValidatorImpl(scope, configuration, name, 0.0, context, failures);
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
			return new PrimitiveDoubleValidatorImpl(scope, configuration, name, 0.0, context, failures);
		}
		return new PrimitiveDoubleValidatorImpl(scope, configuration, name, (double) value, context, failures);
	}

	@Override
	public PrimitiveBooleanValidatorImpl isBoolean()
	{
		if (hasFailed())
			return new PrimitiveBooleanValidatorImpl(scope, configuration, name, false, context, failures);
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
			return new PrimitiveBooleanValidatorImpl(scope, configuration, name, false, context, failures);
		}
		return new PrimitiveBooleanValidatorImpl(scope, configuration, name, (boolean) value, context, failures);
	}

	@Override
	public PrimitiveCharacterValidatorImpl isChar()
	{
		if (hasFailed())
			return new PrimitiveCharacterValidatorImpl(scope, configuration, name, '-', context, failures);
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
			return new PrimitiveCharacterValidatorImpl(scope, configuration, name, '-', context, failures);
		}
		return new PrimitiveCharacterValidatorImpl(scope, configuration, name, (char) value, context, failures);
	}

	@Override
	public BigIntegerValidatorImpl isBigInteger()
	{
		if (hasFailed())
			return new BigIntegerValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new BigIntegerValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new BigIntegerValidatorImpl(scope, configuration, name, (BigInteger) value, context, failures);
	}

	@Override
	public BigDecimalValidatorImpl isBigDecimal()
	{
		if (hasFailed())
			return new BigDecimalValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new BigDecimalValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new BigDecimalValidatorImpl(scope, configuration, name, (BigDecimal) value, context, failures);
	}

	@Override
	public ComparableValidatorImpl<?> isComparable()
	{
		if (hasFailed())
			return new ComparableValidatorImpl<>(scope, configuration, name, null, context, failures);
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
			return new ComparableValidatorImpl<>(scope, configuration, name, null, context, failures);
		}
		return new ComparableValidatorImpl<>(scope, configuration, name, (BigDecimal) value, context, failures);
	}

	@Override
	public CollectionValidator<?, ? extends Collection<?>> isCollection()
	{
		if (hasFailed())
		{
			return new CollectionValidatorImpl<>(scope, configuration, name, null, Pluralizer.ELEMENT, context,
				failures);
		}
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
			return new CollectionValidatorImpl<>(scope, configuration, name, null, Pluralizer.ELEMENT, context,
				failures);
		}
		return new CollectionValidatorImpl<>(scope, configuration, name, (Collection<?>) value,
			Pluralizer.ELEMENT, context, failures);
	}

	@Override
	public ListValidator<?, ? extends List<?>> isList()
	{
		if (hasFailed())
		{
			return new ListValidatorImpl<>(scope, configuration, name, null, Pluralizer.ELEMENT, context,
				failures);
		}
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
			return new ListValidatorImpl<>(scope, configuration, name, null, Pluralizer.ELEMENT, context,
				failures);
		}
		return new ListValidatorImpl<>(scope, configuration, name, (List<?>) value, Pluralizer.ELEMENT, context,
			failures);
	}

	@Override
	public PrimitiveByteArrayValidator isByteArray()
	{
		if (hasFailed())
			return new PrimitiveByteArrayValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new PrimitiveByteArrayValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new PrimitiveByteArrayValidatorImpl(scope, configuration, name, (byte[]) value, context, failures);
	}

	@Override
	public PrimitiveShortArrayValidator isShortArray()
	{
		if (hasFailed())
			return new PrimitiveShortArrayValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new PrimitiveShortArrayValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new PrimitiveShortArrayValidatorImpl(scope, configuration, name, (short[]) value, context, failures);
	}

	@Override
	public PrimitiveIntegerArrayValidator isIntArray()
	{
		if (hasFailed())
			return new PrimitiveIntegerArrayValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new PrimitiveIntegerArrayValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new PrimitiveIntegerArrayValidatorImpl(scope, configuration, name, (int[]) value, context,
			failures);
	}

	@Override
	public PrimitiveLongArrayValidator isLongArray()
	{
		if (hasFailed())
			return new PrimitiveLongArrayValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new PrimitiveLongArrayValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new PrimitiveLongArrayValidatorImpl(scope, configuration, name, (long[]) value, context, failures);
	}

	@Override
	public PrimitiveFloatArrayValidator isFloatArray()
	{
		if (hasFailed())
			return new PrimitiveFloatArrayValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new PrimitiveFloatArrayValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new PrimitiveFloatArrayValidatorImpl(scope, configuration, name, (float[]) value, context,
			failures);
	}

	@Override
	public PrimitiveDoubleArrayValidator isDoubleArray()
	{
		if (hasFailed())
			return new PrimitiveDoubleArrayValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new PrimitiveDoubleArrayValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new PrimitiveDoubleArrayValidatorImpl(scope, configuration, name, (double[]) value, context,
			failures);
	}

	@Override
	public PrimitiveBooleanArrayValidator isBooleanArray()
	{
		if (hasFailed())
			return new PrimitiveBooleanArrayValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new PrimitiveBooleanArrayValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new PrimitiveBooleanArrayValidatorImpl(scope, configuration, name, (boolean[]) value, context,
			failures);
	}

	@Override
	public PrimitiveCharacterArrayValidator isCharArray()
	{
		if (hasFailed())
			return new PrimitiveCharacterArrayValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new PrimitiveCharacterArrayValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new PrimitiveCharacterArrayValidatorImpl(scope, configuration, name, (char[]) value, context,
			failures);
	}

	@Override
	public ObjectArrayValidator<?, ?> isObjectArray()
	{
		if (hasFailed())
			return new ObjectArrayValidatorImpl<>(scope, configuration, name, null, context, failures);
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
			return new ObjectArrayValidatorImpl<>(scope, configuration, name, null, context, failures);
		}
		return new ObjectArrayValidatorImpl<>(scope, configuration, name, (Object[]) value, context, failures);
	}

	@Override
	public MapValidator<?, ?, ? extends Map<?, ?>> isMap()
	{
		if (hasFailed())
			return new MapValidatorImpl<>(scope, configuration, name, null, context, failures);
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
			return new MapValidatorImpl<>(scope, configuration, name, null, context, failures);
		}
		return new MapValidatorImpl<>(scope, configuration, name, (Map<?, ?>) value, context, failures);
	}

	@Override
	public PathValidator isPath()
	{
		if (hasFailed())
			return new PathValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new PathValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new PathValidatorImpl(scope, configuration, name, (Path) value, context, failures);
	}

	@Override
	public StringValidatorImpl isString()
	{
		if (hasFailed())
			return new StringValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new StringValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new StringValidatorImpl(scope, configuration, name, (String) value, context, failures);
	}

	@Override
	public UriValidatorImpl isUri()
	{
		if (hasFailed())
			return new UriValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new UriValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new UriValidatorImpl(scope, configuration, name, (URI) value, context, failures);
	}

	@Override
	public UrlValidatorImpl isUrl()
	{
		if (hasFailed())
			return new UrlValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new UrlValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new UrlValidatorImpl(scope, configuration, name, (URL) value, context, failures);
	}

	@Override
	public ClassValidatorImpl<?> isClass()
	{
		if (hasFailed())
			return new ClassValidatorImpl<>(scope, configuration, name, null, context, failures);
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
			return new ClassValidatorImpl<>(scope, configuration, name, null, context, failures);
		}
		return new ClassValidatorImpl<>(scope, configuration, name, (Class<?>) value, context, failures);
	}

	@Override
	public OptionalValidator<?> isOptional()
	{
		if (hasFailed())
			return new OptionalValidatorImpl<>(scope, configuration, name, null, context, failures);
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
			return new OptionalValidatorImpl<>(scope, configuration, name, null, context, failures);
		}
		return new OptionalValidatorImpl<>(scope, configuration, name, (Optional<?>) value, context, failures);
	}

	@Override
	public InetAddressValidatorImpl isInetAddress()
	{
		if (hasFailed())
			return new InetAddressValidatorImpl(scope, configuration, name, null, context, failures);
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
			return new InetAddressValidatorImpl(scope, configuration, name, null, context, failures);
		}
		return new InetAddressValidatorImpl(scope, configuration, name, (InetAddress) value, context, failures);
	}
}