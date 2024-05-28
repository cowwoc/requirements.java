package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.java.internal.util.Strings;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.StringJoiner;
import java.util.TreeMap;

/**
 * Returns the String representation of objects.
 */
public final class MutableStringMappers
{
	private final Map<Optional<Class<?>>, StringMapper> typeToMapper;

	/**
	 * Creates a new instance using the default mappings.
	 */
	public MutableStringMappers()
	{
		typeToMapper = HashMap.newHashMap(23);
		typeToMapper.put(Optional.of(boolean.class), o -> String.format("%b", o));
		typeToMapper.put(Optional.of(byte.class), o -> String.format("%,d", (byte) o));
		typeToMapper.put(Optional.of(short.class), o -> String.format("%,d", (short) o));
		typeToMapper.put(Optional.of(int.class), o -> String.format("%,d", (int) o));
		typeToMapper.put(Optional.of(long.class), o -> String.format("%,d", (long) o));
		typeToMapper.put(Optional.of(float.class), o -> String.format("%,f", (float) o));
		typeToMapper.put(Optional.of(double.class), o -> String.format("%,f", (double) o));
		typeToMapper.put(Optional.of(boolean[].class), o -> Arrays.toString((boolean[]) o));
		typeToMapper.put(Optional.of(byte[].class), o -> Arrays.toString((byte[]) o));
		typeToMapper.put(Optional.of(char[].class), o -> Arrays.toString((char[]) o));
		typeToMapper.put(Optional.of(short[].class), o -> Arrays.toString((short[]) o));
		typeToMapper.put(Optional.of(int[].class), o -> Arrays.toString((int[]) o));
		typeToMapper.put(Optional.of(long[].class), o -> Arrays.toString((long[]) o));
		typeToMapper.put(Optional.of(float[].class), o -> Arrays.toString((float[]) o));
		typeToMapper.put(Optional.of(double[].class), o -> Arrays.toString((double[]) o));
		typeToMapper.put(Optional.of(Object[].class), o -> arrayToString((Object[]) o, new HashSet<>()));
		typeToMapper.put(Optional.of(BigDecimal.class), o -> ((BigDecimal) o).toPlainString());
		typeToMapper.put(Optional.of(Path.class), o -> ((Path) o).toAbsolutePath().toString());
		typeToMapper.put(Optional.of(String.class), o -> Strings.asJavaString((String) o));
		typeToMapper.put(Optional.of(List.class), this::listToString);
		typeToMapper.put(Optional.of(Set.class), this::setToString);
		typeToMapper.put(Optional.of(Map.class), this::mapToString);
		typeToMapper.put(Optional.of(Throwable.class), this::throwableToString);
	}

	/**
	 * Creates a new instance.
	 *
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
	 * @param object a {@code List}
	 * @return the String representation of an {@code Object}
	 */
	private String listToString(Object object)
	{
		return orderedToString((Collection<?>) object);
	}

	/**
	 * @param collection a {@code Collection}
	 * @return the String representation of a {@code Collection}
	 */
	private String orderedToString(Collection<?> collection)
	{
		// We cannot use Object.toString() because Arrays.asList(array) only converts the outermost array into a
		// List. List.toString() does not invoke Arrays.deepToString(array) so any nested arrays do not display
		// correctly.
		Iterator<?> iterator = collection.iterator();
		StringJoiner joiner = new StringJoiner(", ", "[", "]");
		while (iterator.hasNext())
		{
			Object element = iterator.next();
			if (element == collection)
				joiner.add("(this Collection)");
			else
			{
				StringMapper elementToString = getMapper(element);
				joiner.add(elementToString.apply(element));
			}
		}
		return joiner.toString();
	}

	/**
	 * @param object an {@code Object}
	 * @return the String representation of an {@code Object}
	 */
	private String setToString(Object object)
	{
		Collection<?> collection;
		if (object instanceof SortedSet<?> sorted)
			collection = sorted;
		else
		{
			Set<?> set = (Set<?>) object;
			for (Object element : set)
			{
				if (!(element instanceof Comparable<?>))
					return set.toString();
			}

			@SuppressWarnings("unchecked")
			List<Comparable<Object>> list = new ArrayList<>((Collection<? extends Comparable<Object>>) set);
			list.sort(Comparator.naturalOrder());
			collection = list;
		}
		return orderedToString(collection);
	}

	/**
	 * @param object an {@code Object}
	 * @return the String representation of a {@code Map}
	 */
	private String mapToString(Object object)
	{
		if (object instanceof SortedMap<?, ?> sorted)
			return mapEntriesToString(sorted.entrySet());
		Map<?, ?> map = (Map<?, ?>) object;
		for (Entry<?, ?> entry : map.entrySet())
		{
			if (!(entry.getKey() instanceof Comparable<?>))
				return mapEntriesToString(map.entrySet());
		}
		return mapEntriesToString(new TreeMap<>(map).entrySet());
	}

	/**
	 * @param entries map entries
	 * @return the String representation of Map {@code entries}
	 */
	private String mapEntriesToString(Set<? extends Entry<?, ?>> entries)
	{
		// We cannot use Object.toString() because Arrays.asList(array) only converts the outermost array into a
		// List. List.toString() does not invoke Arrays.deepToString(array) so any nested arrays do not display
		// correctly.
		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		for (Entry<?, ?> entry : entries)
		{
			Object key = entry.getKey();
			Object value = entry.getValue();
			Object keyAsString;
			if (key == entries)
				keyAsString = "(this Map)";
			else
			{
				StringMapper keyToString = getMapper(key);
				keyAsString = keyToString.apply(key);
			}
			Object valueAsString;
			if (value == entries)
				valueAsString = "(this Map)";
			else
			{
				StringMapper valueToString = getMapper(value);
				valueAsString = valueToString.apply(value);
			}
			joiner.add(keyAsString + "=" + valueAsString);
		}
		return joiner.toString();
	}

	/**
	 * @param array an array
	 * @param seen  the arrays we've seen before
	 * @return the "deep" String representation of the array
	 */
	private String arrayToString(Object[] array, Set<Object> seen)
	{
		// We cannot use Arrays.deepToString(array) because it does not delegate to StringMappers.toString()
		StringJoiner joiner = new StringJoiner(", ", "[", "]");
		for (Object element : array)
		{
			if (element != null && element.getClass().isArray())
			{
				if (seen.add(element))
				{
					switch (element)
					{
						case Object o when o.getClass() == byte[].class -> joiner.add(toString(element));
						case Object o when o.getClass() == short[].class -> joiner.add(toString(element));
						case Object o when o.getClass() == int[].class -> joiner.add(toString(element));
						case Object o when o.getClass() == long[].class -> joiner.add(toString(element));
						case Object o when o.getClass() == float[].class -> joiner.add(toString(element));
						case Object o when o.getClass() == double[].class -> joiner.add(toString(element));
						case Object o when o.getClass() == boolean[].class -> joiner.add(toString(element));
						case Object o when o.getClass() == char[].class -> joiner.add(toString(element));
						default -> joiner.add(arrayToString((Object[]) element, seen));
					}
				}
				else
					joiner.add("...");
			}
			else
				joiner.add(toString(element));
		}
		return joiner.toString();
	}

	/**
	 * @param object a {@code Throwable}
	 * @return the String representation of a {@code Throwable}
	 */
	private String throwableToString(Object object)
	{
		Throwable throwable = (Throwable) object;
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		return sw.toString();
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

	/**
	 * Sets the function that maps an object of the given type to a String. This method is useful for
	 * customizing the formatting of validation failure messages.
	 * <p>
	 * For non-primitive arrays, the function uses {@code Object[].class} as the type parameter.
	 *
	 * @param type   a type
	 * @param mapper a function that returns the String representation of that type's instances
	 * @return this
	 */
	public MutableStringMappers put(Class<?> type, StringMapper mapper)
	{
		typeToMapper.put(Optional.ofNullable(type), mapper);
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

	record MatchingMapper(StringMapper mapper, int depth)
	{
	}
}