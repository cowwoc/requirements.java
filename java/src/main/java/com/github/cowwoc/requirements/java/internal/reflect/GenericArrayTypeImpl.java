package com.github.cowwoc.requirements.java.internal.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/**
 * An implementation of GenericArrayType.
 */
public final class GenericArrayTypeImpl implements GenericArrayType
{
	private final Type componentType;

	/**
	 * Creates a new GenericArrayType.
	 *
	 * @param componentType the type of the array component
	 * @throws AssertionError if {@code componentType} is null
	 */
	public GenericArrayTypeImpl(Type componentType)
	{
		assert componentType != null;
		this.componentType = componentType;
	}

	@Override
	public Type getGenericComponentType()
	{
		return componentType;
	}

	@Override
	public int hashCode()
	{
		return componentType.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof GenericArrayType other && other.getGenericComponentType().equals(componentType);
	}

	@Override
	public String toString()
	{
		return componentType + "[]";
	}
}