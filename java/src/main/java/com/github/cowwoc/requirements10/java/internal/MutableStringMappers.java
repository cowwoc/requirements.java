package com.github.cowwoc.requirements10.java.internal;

import com.github.cowwoc.requirements10.annotation.CheckReturnValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Returns the String representation of objects.
 */
public final class MutableStringMappers
{
	private final Map<Optional<Class<?>>, StringMapper> typeToMapper;

	/**
	 * Creates a copy of an existing instance.
	 *
	 * @param typeToMapper a map from a type to a function that returns its String representation
	 * @throws NullPointerException if {@code typeToMapper} is null
	 */
	private MutableStringMappers(Map<Optional<Class<?>>, StringMapper> typeToMapper)
	{
		this.typeToMapper = new HashMap<>(typeToMapper);
	}

	/**
	 * Returns a mutable copy of the StringMappers.
	 *
	 * @param mappers a {@code StringMappers} object
	 * @return a mutable copy of the StringMappers
	 */
	@CheckReturnValue
	public static MutableStringMappers from(StringMappers mappers)
	{
		return new MutableStringMappers(mappers.typeToMapper);
	}

	/**
	 * Returns an immutable copy of the mapper configuration.
	 *
	 * @return an immutable copy of the mapper configuration
	 */
	@CheckReturnValue
	public StringMappers toImmutable()
	{
		return new StringMappers(typeToMapper);
	}

	/**
	 * Sets the function that maps an object of the given type to a String. This method is useful for
	 * customizing the formatting of validation failure messages.
	 * <p>
	 * For non-primitive arrays, the function uses {@code Object[].class} as the type parameter.
	 *
	 * @param type   a type
	 * @param mapper a function that returns the String representation of the type's instances
	 * @return this
	 */
	public MutableStringMappers put(Class<?> type, StringMapper mapper)
	{
		typeToMapper.put(Optional.ofNullable(type), mapper);
		return this;
	}

	/**
	 * Returns the function that maps an object of the given type to a String.
	 * @param type a type
	 * @param mapper a function that returns the String representation of the type's instances
	 * @return this
	 */
	public MutableStringMappers putIfAbsent(Class<?> type, StringMapper mapper)
	{
		typeToMapper.putIfAbsent(Optional.ofNullable(type), mapper);
		return this;
	}

	/**
	 * Removes a mapper for a type.
	 *
	 * @param type the type
	 * @return this
	 */
	public MutableStringMappers remove(Class<?> type)
	{
		typeToMapper.remove(Optional.<Class<?>>ofNullable(type));
		return this;
	}

	@Override
	public int hashCode()
	{
		return typeToMapper.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof MutableStringMappers other && other.typeToMapper.equals(typeToMapper);
	}

	@Override
	public String toString()
	{
		return typeToMapper.toString();
	}
}