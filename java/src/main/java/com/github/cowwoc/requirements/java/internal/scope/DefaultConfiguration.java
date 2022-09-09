/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.internal.util.Strings;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * The default implementation of {@code Configuration}.
 */
public final class DefaultConfiguration implements Configuration
{
	private static final boolean CLASS_ASSERTIONS_ENABLED;

	static
	{
		CLASS_ASSERTIONS_ENABLED = Configuration.class.desiredAssertionStatus();
	}

	// NOTE: This class must contain at least one "final" field. See explanation in copy() method.
	private final Map<String, Object> context;
	private final Map<Class<?>, Function<Object, String>> typeToStringConverter;
	private boolean assertionsEnabled;
	private boolean cleanStackTrace;
	private boolean diffEnabled;

	/**
	 * Creates a new configuration:
	 * <ul>
	 * <li>With an empty context.</li>
	 * <li>Whose assertions are enabled if
	 * <a href="http://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html#enable-disable">
	 * assertions are enabled on this class</a>.</li>
	 * <li>That omits exception stack traces that reference this library.</li>
	 * <li>That shows the difference between the actual and expected values.</li>
	 * <li>That invokes {@code Arrays.toString()} for arrays and {@code Object.toString()} for all
	 * other objects to convert them to a {@code String}.</li>
	 * </ul>
	 */
	DefaultConfiguration()
	{
		this.context = new LinkedHashMap<>();
		this.assertionsEnabled = CLASS_ASSERTIONS_ENABLED;
		this.cleanStackTrace = true;
		this.diffEnabled = true;
		this.typeToStringConverter = new HashMap<>(13);
		ThreadLocal<NumberFormat> decimalFormat = ThreadLocal.withInitial(NumberFormat::getInstance);

		typeToStringConverter.put(boolean[].class, o -> Arrays.toString((boolean[]) o));
		typeToStringConverter.put(byte[].class, o -> Arrays.toString((byte[]) o));
		typeToStringConverter.put(char[].class, o -> Arrays.toString((char[]) o));
		typeToStringConverter.put(short[].class, o -> Arrays.toString((short[]) o));
		typeToStringConverter.put(int[].class, o -> Arrays.toString((int[]) o));
		typeToStringConverter.put(long[].class, o -> Arrays.toString((long[]) o));
		typeToStringConverter.put(float[].class, o -> Arrays.toString((float[]) o));
		typeToStringConverter.put(double[].class, o -> Arrays.toString((double[]) o));
		typeToStringConverter.put(Object[].class, o -> Arrays.deepToString((Object[]) o));
		typeToStringConverter.put(Integer.class, o -> decimalFormat.get().format(o));
		typeToStringConverter.put(Long.class, o -> decimalFormat.get().format(o));
		typeToStringConverter.put(BigDecimal.class, o -> ((BigDecimal) o).toPlainString());
		typeToStringConverter.put(Path.class, o -> ((Path) o).toAbsolutePath().toString());
	}

	/**
	 * Copies an existing configuration.
	 *
	 * @param other the configuration to copy
	 * @throws AssertionError if {@code other} is null
	 */
	private DefaultConfiguration(DefaultConfiguration other)
	{
		assert (other != null) : "other may not be null";
		this.context = new LinkedHashMap<>(other.context);
		this.assertionsEnabled = other.assertionsEnabled;
		this.cleanStackTrace = other.cleanStackTrace;
		this.diffEnabled = other.diffEnabled;
		this.typeToStringConverter = new HashMap<>(other.typeToStringConverter);
	}

	@Override
	public Configuration copy()
	{
		// Per https://shipilev.net/blog/2014/safe-public-construction/ section "A final field was written" this
		// object is safe for publication because it contains at least one final field.
		return new DefaultConfiguration(this);
	}

	@Override
	public boolean assertionsAreEnabled()
	{
		return assertionsEnabled;
	}

	@Override
	public Configuration withAssertionsEnabled()
	{
		this.assertionsEnabled = true;
		return this;
	}

	@Override
	public Configuration withAssertionsDisabled()
	{
		this.assertionsEnabled = false;
		return this;
	}

	@Override
	public boolean isCleanStackTrace()
	{
		return cleanStackTrace;
	}

	@Override
	public Configuration withCleanStackTrace()
	{
		this.cleanStackTrace = true;
		return this;
	}

	@Override
	public Configuration withoutCleanStackTrace()
	{
		this.cleanStackTrace = false;
		return this;
	}

	@Override
	public Map<String, Object> getContext()
	{
		return Collections.unmodifiableMap(context);
	}

	@Override
	public Configuration withContext(String name, Object value)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		context.put(name, value);
		return this;
	}

	@Override
	public Configuration withoutContext(String name)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		context.remove(name);
		return this;
	}

	@Override
	public Configuration withoutAnyContext()
	{
		context.clear();
		return this;
	}

	@Override
	public boolean isDiffEnabled()
	{
		return diffEnabled;
	}

	@Override
	public Configuration withDiff()
	{
		this.diffEnabled = true;
		return this;
	}

	@Override
	public Configuration withoutDiff()
	{
		this.diffEnabled = false;
		return this;
	}

	@Override
	public String toString(Object value)
	{
		return getToStringConverter(value).apply(value);
	}

	/**
	 * Returns a function that returns the {@code String} representation of an object.
	 *
	 * @param value a value
	 * @return a function that returns the {@code String} representation of the value
	 */
	private Function<Object, String> getToStringConverter(Object value)
	{
		if (value == null)
			return Objects::toString;
		Class<?> type = value.getClass();
		Function<Object, String> converter = typeToStringConverter.get(type);
		if (converter == null)
		{
			// Special converters that use instanceof to match types
			if (value instanceof String valueAsString)
				converter = text -> Strings.asJavaString(valueAsString);
			else if (value instanceof List)
				converter = this::listToString;
			else if (value instanceof Set)
				converter = this::setToString;
			else if (value instanceof Map)
				converter = this::mapToString;
			else if (value instanceof Throwable)
				converter = this::throwableToString;
			else if (type.isArray() && !type.getComponentType().isPrimitive())
			{
				converter = typeToStringConverter.get(Object[].class);
				if (converter == null)
					converter = Objects::toString;
			}
			else
				converter = Objects::toString;
		}
		return converter;
	}

	/**
	 * @param object a {@code List}
	 * @return the String representation of {@code object}
	 */
	private String listToString(Object object)
	{
		return orderedToString((Collection<?>) object);
	}

	/**
	 * @param collection a {@code Collection}
	 * @return the String representation of {@code collection}
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
				Function<Object, String> elementToString = getToStringConverter(element);
				joiner.add(elementToString.apply(element));
			}
		}
		return joiner.toString();
	}

	/**
	 * @param object an {@code Object}
	 * @return the String representation of {@code object}
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
	 * @return the String representation of {@code map}
	 */
	private String mapToString(Object object)
	{
		if (object instanceof SortedMap<?, ?> sorted)
			return orderedMapToString(sorted);
		Map<?, ?> map = (Map<?, ?>) object;
		for (Entry<?, ?> entry : map.entrySet())
		{
			if (!(entry.getKey() instanceof Comparable<?>))
				return orderedMapToString(map);
		}
		return orderedMapToString(new TreeMap<>(map));
	}

	/**
	 * @param orderedMap a {@code Map} that may have been reordered
	 * @return the String representation of {@code sortedMap}
	 */
	private String orderedMapToString(Map<?, ?> orderedMap)
	{
		// We cannot use Object.toString() because Arrays.asList(array) only converts the outermost array into a
		// List. List.toString() does not invoke Arrays.deepToString(array) so any nested arrays do not display
		// correctly.
		Set<? extends Entry<?, ?>> entries = orderedMap.entrySet();

		StringJoiner joiner = new StringJoiner(", ", "{", "}");
		for (Entry<?, ?> entry : entries)
		{
			Object key = entry.getKey();
			Object value = entry.getValue();
			Object keyAsString;
			if (key == orderedMap)
				keyAsString = "(this Map)";
			else
			{
				Function<Object, String> keyToString = getToStringConverter(key);
				keyAsString = keyToString.apply(key);
			}
			Object valueAsString;
			if (value == orderedMap)
				valueAsString = "(this Map)";
			else
			{
				Function<Object, String> valueToString = getToStringConverter(value);
				valueAsString = valueToString.apply(key);
			}
			joiner.add(keyAsString + "=" + valueAsString);
		}
		return joiner.toString();
	}

	/**
	 * @param object a {@code Throwable}
	 * @return the String representation of {@code object}
	 */
	private String throwableToString(Object object)
	{
		Throwable throwable = (Throwable) object;
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		return sw.toString();
	}

	@Override
	public <T> Configuration withStringConverter(Class<T> type, Function<T, String> converter)
	{
		if (type == null)
			throw new NullPointerException("type may not be null");
		if (converter == null)
			throw new NullPointerException("converter may not be null");
		if (typeToStringConverter.get(type) == converter)
			return this;
		@SuppressWarnings("unchecked")
		Function<Object, String> unsafeConverter = (Function<Object, String>) converter;
		typeToStringConverter.put(type, unsafeConverter);
		return this;
	}

	@Override
	public <T> Configuration withoutStringConverter(Class<T> type)
	{
		if (type == null)
			throw new NullPointerException("type may not be null");
		if (!typeToStringConverter.containsKey(type))
			return this;
		typeToStringConverter.remove(type);
		return this;
	}

	@Override
	public Configuration withConfiguration(Configuration configuration)
	{
		if (configuration == null)
			throw new NullPointerException("configuration may not be null");
		return configuration;
	}

	public int hashCode()
	{
		int hash = 3;
		hash = 23 * hash + context.hashCode();
		hash = 23 * hash + Boolean.hashCode(assertionsEnabled);
		hash = 23 * hash + Boolean.hashCode(diffEnabled);
		return 23 * hash + typeToStringConverter.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this)
			return true;
		if (!(o instanceof DefaultConfiguration other))
			return false;
		return assertionsEnabled == other.assertionsEnabled && context.equals(other.context) &&
			diffEnabled == other.diffEnabled && typeToStringConverter.equals(other.typeToStringConverter);
	}

	@Override
	public String toString()
	{
		return "Configuration[context=" + context + ", assertionsEnabled=" + assertionsEnabled +
			", diffEnabled=" + diffEnabled + ", typeToStringConverter=" + typeToStringConverter + "]";
	}
}