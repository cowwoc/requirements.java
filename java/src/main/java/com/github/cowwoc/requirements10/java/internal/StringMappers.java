package com.github.cowwoc.requirements10.java.internal;

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
 * Returns the string representation of an object, which can be used to reference it in exception messages.
 */
public final class StringMappers
{
	/**
	 * The default mapper configuration.
	 */
	public static final StringMappers DEFAULT = new StringMappers();
	final Map<Optional<Class<?>>, StringMapper> typeToMapper;

	/**
	 * Creates a new instance using the default mappings.
	 */
	StringMappers()
	{
		typeToMapper = HashMap.newHashMap(23);
		typeToMapper.put(Optional.of(boolean.class), (value, seen) -> String.format("%b", value));
		typeToMapper.put(Optional.of(byte.class), (value, seen) -> String.format("%,d", (byte) value));
		typeToMapper.put(Optional.of(short.class), (value, seen) -> String.format("%,d", (short) value));
		typeToMapper.put(Optional.of(int.class), (value, seen) -> String.format("%,d", (int) value));
		typeToMapper.put(Optional.of(long.class), (value, seen) -> String.format("%,d", (long) value));
		typeToMapper.put(Optional.of(float.class), (value, seen) -> String.format("%,f", (float) value));
		typeToMapper.put(Optional.of(double.class), (value, seen) -> String.format("%,f", (double) value));
		typeToMapper.put(Optional.of(boolean[].class), (value, seen) -> Arrays.toString((boolean[]) value));
		typeToMapper.put(Optional.of(byte[].class), (value, seen) -> Arrays.toString((byte[]) value));
		typeToMapper.put(Optional.of(char[].class), (value, seen) -> Arrays.toString((char[]) value));
		typeToMapper.put(Optional.of(short[].class), (value, seen) -> Arrays.toString((short[]) value));
		typeToMapper.put(Optional.of(int[].class), (value, seen) -> Arrays.toString((int[]) value));
		typeToMapper.put(Optional.of(long[].class), (value, seen) -> Arrays.toString((long[]) value));
		typeToMapper.put(Optional.of(float[].class), (value, seen) -> Arrays.toString((float[]) value));
		typeToMapper.put(Optional.of(double[].class), (value, seen) -> Arrays.toString((double[]) value));
		typeToMapper.put(Optional.of(Object[].class), (value, seen) -> arrayToString((Object[]) value, seen));
		typeToMapper.put(Optional.of(BigDecimal.class), (value, seen) -> ((BigDecimal) value).toPlainString());
		typeToMapper.put(Optional.of(Path.class), (value, seen) -> ((Path) value).toAbsolutePath().toString());
		typeToMapper.put(Optional.of(String.class), (value, seen) -> quoteString((String) value));
		typeToMapper.put(Optional.of(List.class), this::listToString);
		typeToMapper.put(Optional.of(Set.class), this::setToString);
		typeToMapper.put(Optional.of(Map.class), this::mapToString);
		typeToMapper.put(Optional.of(Throwable.class), (value, seen) -> throwableToString(value));
	}

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
	 * @param array an array
	 * @param seen  the objects that we've seen before
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
						case Object o when o.getClass() == byte[].class -> joiner.add(toString(element, seen));
						case Object o when o.getClass() == short[].class -> joiner.add(toString(element, seen));
						case Object o when o.getClass() == int[].class -> joiner.add(toString(element, seen));
						case Object o when o.getClass() == long[].class -> joiner.add(toString(element, seen));
						case Object o when o.getClass() == float[].class -> joiner.add(toString(element, seen));
						case Object o when o.getClass() == double[].class -> joiner.add(toString(element, seen));
						case Object o when o.getClass() == boolean[].class -> joiner.add(toString(element, seen));
						case Object o when o.getClass() == char[].class -> joiner.add(toString(element, seen));
						default -> joiner.add(arrayToString((Object[]) element, seen));
					}
				}
				else
					joiner.add("...");
			}
			else
				joiner.add(toString(element, seen));
		}
		return joiner.toString();
	}

	/**
	 * Quotes a String, escaping any nested quotes.
	 *
	 * @param value a {@code String}
	 * @return the quoted string
	 */
	public static String quoteString(String value)
	{
		StringBuilder result = new StringBuilder(value.length() + 16);

		for (int codepoint : (Iterable<Integer>) value.codePoints()::iterator)
		{
			if (codepoint == '\"')
				result.append("\\\"");
			else
				result.appendCodePoint(codepoint);
		}
		result.insert(0, '"');
		result.append('"');
		return result.toString();
	}

	/**
	 * Returns the String representation of an object using the mappers.
	 *
	 * @param object an object
	 * @param seen   the objects that we've seen before
	 * @return the String representation of the object
	 */
	private String toString(Object object, Set<Object> seen)
	{
		StringMapper mapper = getMapper(object, typeToMapper);
		return mapper.apply(object, seen);
	}

	/**
	 * Returns the String representation of an object using the mappers.
	 *
	 * @param object       an object
	 * @param typeToMapper a mapping from each class to a function that the String representation of its
	 *                     objects
	 * @return the StringMapper for {@code object}
	 */
	private static StringMapper getMapper(Object object, Map<Optional<Class<?>>, StringMapper> typeToMapper)
	{
		if (object == null)
		{
			StringMapper mapper = typeToMapper.get(Optional.<Class<?>>empty());
			if (mapper != null)
				return mapper;
			return (value, seen) -> String.valueOf(value);
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
		MatchingMapper match = getTypesThatMatch(type, 0, typeToMapper);
		if (match == null)
			return (value, seen) -> String.valueOf(value);
		return match.mapper();
	}

	/**
	 * Returns a class or its closest ancestor that has an associated string mapper.
	 *
	 * @param type         a type
	 * @param depth        the recursion depth
	 * @param typeToMapper a mapping from each class to a function that the String representation of its
	 *                     objects
	 * @return a mapping between a mapped type and the recursion depth it was found at
	 */
	private static MatchingMapper getTypesThatMatch(Class<?> type, int depth,
		Map<Optional<Class<?>>, StringMapper> typeToMapper)
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
			bestMatch = getTypesThatMatch(superclass, depth + 1, typeToMapper);
		for (Class<?> i : interfaces)
		{
			MatchingMapper subMatch = getTypesThatMatch(i, depth + 1, typeToMapper);
			if (subMatch != null && (bestMatch == null || subMatch.depth < bestMatch.depth))
				bestMatch = subMatch;
		}
		return bestMatch;
	}

	/**
	 * @param object a {@code List}
	 * @param seen   the objects that we've seen before
	 * @return the String representation of an {@code Object}
	 */
	private String listToString(Object object, Set<Object> seen)
	{
		return orderedToString((List<?>) object, seen);
	}

	/**
	 * @param collection a {@code Collection}
	 * @param seen       the objects that we've seen before
	 * @return the String representation of a {@code Collection}
	 */
	private String orderedToString(Collection<?> collection, Set<Object> seen)
	{
		// We cannot use Object.toString() because Arrays.asList(array) only converts the outermost array into a
		// List. List.toString() does not invoke Arrays.deepToString(array) so any nested arrays do not display
		// correctly.
		Iterator<?> iterator = collection.iterator();
		StringJoiner joiner = new StringJoiner(", ", "[", "]");
		seen.add(collection);

		while (iterator.hasNext())
		{
			Object element = iterator.next();
			if (seen.add(element))
			{
				StringMapper elementToString = getMapper(element, typeToMapper);
				joiner.add(elementToString.apply(element, seen));
			}
			else
				joiner.add("...");
		}
		return joiner.toString();
	}

	/**
	 * @param object a {@code Set}
	 * @return the String representation of the set
	 */
	private String setToString(Object object, Set<Object> seen)
	{
		Set<?> set = (Set<?>) object;
		if (set instanceof SortedSet<?> sorted)
			return orderedToString(sorted, seen);
		try
		{
			@SuppressWarnings("unchecked")
			List<Comparable<Object>> list = new ArrayList<>((Collection<? extends Comparable<Object>>) set);
			list.sort(Comparator.naturalOrder());
			return orderedToString(list, seen);
		}
		catch (ClassCastException expected)
		{
			// Elements are not comparable to each other
		}
		return orderedToString(new ArrayList<>(set), seen);
	}

	/**
	 * @param object a {@code Map}
	 * @param seen   the objects that we've seen before
	 * @return the String representation of the map
	 */
	private String mapToString(Object object, Set<Object> seen)
	{
		if (object instanceof SortedMap<?, ?> sorted)
			return mapEntriesToString(sorted.entrySet(), seen);
		Map<?, ?> map = (Map<?, ?>) object;
		for (Entry<?, ?> entry : map.entrySet())
		{
			if (!(entry.getKey() instanceof Comparable<?>))
				return mapEntriesToString(map.entrySet(), seen);
		}
		return mapEntriesToString(new TreeMap<>(map).entrySet(), seen);
	}

	/**
	 * @param entries map entries
	 * @param seen    the objects that we've seen before
	 * @return the String representation of the map entries
	 */
	private String mapEntriesToString(Set<? extends Entry<?, ?>> entries, Set<Object> seen)
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
				StringMapper keyToString = getMapper(key, typeToMapper);
				keyAsString = keyToString.apply(key, seen);
			}
			Object valueAsString;
			if (value == entries)
				valueAsString = "(this Map)";
			else
			{
				StringMapper valueToString = getMapper(value, typeToMapper);
				valueAsString = valueToString.apply(value, seen);
			}
			joiner.add(keyAsString + "=" + valueAsString);
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
		StringMapper mapper = getMapper(object, typeToMapper);
		return mapper.apply(object, new HashSet<>());
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