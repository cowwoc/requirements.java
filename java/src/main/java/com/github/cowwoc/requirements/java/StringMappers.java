package com.github.cowwoc.requirements.java;

import java.util.Map;
import java.util.Optional;

/**
 * Returns the String representation of objects.
 */
public final class StringMappers
{
	/**
	 * The default mapper configuration.
	 */
	public static final StringMappers DEFAULT = new MutableStringMappers().toImmutable();
	final Map<Optional<Class<?>>, StringMapper> typeToMapper;

	/**
	 * Creates a new instance.
	 *
	 * @param typeToMapper a mapping from each class to a function that the String representation of its
	 *                     objects
	 * @throws NullPointerException if {@code typeToMapper} is null
	 */
	StringMappers(Map<Optional<Class<?>>, StringMapper> typeToMapper)
	{
		this.typeToMapper = Map.copyOf(typeToMapper);
	}

	/**
	 * Returns the String representation of an object using the mappers.
	 *
	 * @param object an object
	 * @return the String representation of the object
	 */
	public String toString(Object object)
	{
		StringMapper mapper = getMapper(object);
		return mapper.apply(object);
	}

	/**
	 * Returns the String representation of an object using the mappers.
	 *
	 * @param object an object
	 * @return the StringMapper for {@code object}
	 */
	private StringMapper getMapper(Object object)
	{
		if (object == null)
		{
			StringMapper mapper = typeToMapper.get(Optional.<Class<?>>empty());
			if (mapper != null)
				return mapper;
			return String::valueOf;
		}
		Class<?> type = object.getClass();
		StringMapper mapper = typeToMapper.get(Optional.<Class<?>>of(type));
		if (mapper != null)
			return mapper;
		if (type.isArray() && Object.class.isAssignableFrom(type.componentType()))
		{
			// Treat arrays of different object types as Object[]
			type = Object[].class;
		}
		MatchingMapper match = getTypesThatMatch(type, 0);
		if (match == null)
			return Object::toString;
		return match.mapper;
	}

	/**
	 * Returns a class or its closest ancestor that has an associated string mapper.
	 *
	 * @param type  a type
	 * @param depth the recursion depth
	 * @return a mapping between a mapped type and the recursion depth it was found at
	 */
	private MatchingMapper getTypesThatMatch(Class<?> type, int depth)
	{
		StringMapper mapper = typeToMapper.get(Optional.<Class<?>>of(type));
		if (mapper != null)
			return new MatchingMapper(mapper, depth);

		Class<?> superclass = type.getSuperclass();
		if (superclass != null)
		{
			mapper = typeToMapper.get(Optional.<Class<?>>of(superclass));
			if (mapper != null)
				return new MatchingMapper(mapper, depth);
		}

		Class<?>[] interfaces = type.getInterfaces();
		for (Class<?> i : interfaces)
		{
			mapper = typeToMapper.get(Optional.<Class<?>>of(i));
			if (mapper != null)
				return new MatchingMapper(mapper, depth);
		}

		MatchingMapper bestMatch;
		if (superclass == null)
			bestMatch = null;
		else
			bestMatch = getTypesThatMatch(superclass, depth + 1);
		for (Class<?> i : interfaces)
		{
			MatchingMapper subMatch = getTypesThatMatch(i, depth + 1);
			if (subMatch != null && (bestMatch == null || subMatch.depth < bestMatch.depth))
				bestMatch = subMatch;
		}
		return bestMatch;
	}

	@Override
	public int hashCode()
	{
		return typeToMapper.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof StringMappers other && other.typeToMapper.equals(typeToMapper);
	}

	@Override
	public String toString()
	{
		return typeToMapper.toString();
	}

	record MatchingMapper(StringMapper mapper, int depth)
	{
	}
}