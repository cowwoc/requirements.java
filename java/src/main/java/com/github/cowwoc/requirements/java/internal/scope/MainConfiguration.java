/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.internal.util.Maps;

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
 * Configures the behavior of a single verifier.
 */
public final class MainConfiguration implements Configuration
{
	private static final boolean CLASS_ASSERTIONS_ENABLED;

	static
	{
		CLASS_ASSERTIONS_ENABLED = Configuration.class.desiredAssertionStatus();
	}

	private final Map<String, Object> context;
	private final boolean assertionsEnabled;
	private final boolean cleanStackTrace;
	private final boolean diffEnabled;
	private final Map<Class<?>, Function<Object, String>> typeToStringConverter;
	private final ApplicationScope scope;

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
	 *
	 * @param scope the application configuration
	 * @throws AssertionError if {@code scope} is null
	 */
	public MainConfiguration(ApplicationScope scope)
	{
		assert (scope != null) : "scope may not be null";
		this.scope = scope;
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
	 * Copies a configuration.
	 *
	 * @param scope                 the application configuration
	 * @param context               the map to append to the exception message
	 * @param assertionsEnabled     true if {@code assertThat()} should invoke {@code requireThat()};
	 *                              false if {@code assertThat()} should do nothing
	 * @param cleanStackTrace       true if stack trace references to this library should be removed
	 * @param diffEnabled           indicates whether exceptions should show the difference between the
	 *                              actual and expected values
	 * @param typeToStringConverter a map from an object type to a function that converts the object to
	 *                              a String
	 * @throws AssertionError if any of the arguments are null
	 */
	private MainConfiguration(ApplicationScope scope,
	                          Map<String, Object> context,
	                          boolean assertionsEnabled,
	                          boolean cleanStackTrace,
	                          boolean diffEnabled,
	                          Map<Class<?>, Function<Object, String>> typeToStringConverter)
	{
		assert (scope != null) : "scope may not be null";
		assert (context != null) : "context may not be null";
		assert (typeToStringConverter != null) : "typeToStringConverter may not be null";
		this.scope = scope;
		this.context = Maps.unmodifiable(context);
		this.assertionsEnabled = assertionsEnabled;
		this.cleanStackTrace = cleanStackTrace;
		this.diffEnabled = diffEnabled;
		this.typeToStringConverter = Maps.unmodifiable(typeToStringConverter);
	}

	@Override
	public boolean assertionsAreEnabled()
	{
		return assertionsEnabled;
	}

	@Override
	public Configuration withAssertionsEnabled()
	{
		if (assertionsEnabled)
			return this;
		return new MainConfiguration(scope, context, true, cleanStackTrace, diffEnabled,
			typeToStringConverter);
	}

	@Override
	public Configuration withAssertionsDisabled()
	{
		if (!assertionsEnabled)
			return this;
		return new MainConfiguration(scope, context, false, cleanStackTrace, diffEnabled,
			typeToStringConverter);
	}

	@Override
	public boolean isCleanStackTrace()
	{
		return cleanStackTrace;
	}

	@Override
	public Configuration withCleanStackTrace()
	{
		if (cleanStackTrace)
			return this;
		return new MainConfiguration(scope, context, assertionsEnabled, true, diffEnabled,
			typeToStringConverter);
	}

	@Override
	public Configuration withoutCleanStackTrace()
	{
		if (!cleanStackTrace)
			return this;
		return new MainConfiguration(scope, context, assertionsEnabled, false, diffEnabled,
			typeToStringConverter);
	}

	@Override
	public Map<String, Object> getContext()
	{
		return Collections.unmodifiableMap(context);
	}

	@Override
	@Deprecated
	public Configuration putContext(String name, Object value)
	{
		return withContext(name, value);
	}

	@Override
	public Configuration withContext(String name, Object value)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		if (Objects.equals(context.get(name), value))
			return this;
		Map<String, Object> newContext = new LinkedHashMap<>(context);
		newContext.put(name, value);
		return new MainConfiguration(scope, newContext, assertionsEnabled, cleanStackTrace, diffEnabled,
			typeToStringConverter);
	}

	@Override
	@Deprecated
	public Configuration removeContext(String name)
	{
		return withoutContext(name);
	}

	@Override
	public Configuration withoutContext(String name)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		if (!context.containsKey(name))
			return this;
		Map<String, Object> newContext = new LinkedHashMap<>(context);
		newContext.remove(name);
		return new MainConfiguration(scope, newContext, assertionsEnabled, cleanStackTrace, diffEnabled,
			typeToStringConverter);
	}

	@Override
	public String createMessageWithContext(String message)
	{
		return scope.getExceptions().createMessageWithContext(this, List.of(), message);
	}

	@Override
	public boolean isDiffEnabled()
	{
		return diffEnabled;
	}

	@Override
	public Configuration withDiff()
	{
		if (diffEnabled)
			return this;
		return new MainConfiguration(scope, context, assertionsEnabled, cleanStackTrace, true,
			typeToStringConverter);
	}

	@Override
	public Configuration withoutDiff()
	{
		if (!diffEnabled)
			return this;
		return new MainConfiguration(scope, context, assertionsEnabled, cleanStackTrace, false,
			typeToStringConverter);
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
			// Converters that use instanceof to match types
			if (value instanceof List)
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
		assert (List.class.isAssignableFrom(object.getClass())) : object.getClass();
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
		assert (Set.class.isAssignableFrom(object.getClass())) : object.getClass();
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
		assert (Map.class.isAssignableFrom(object.getClass())) : object.getClass();
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
		assert (Throwable.class.isAssignableFrom(object.getClass())) : object.getClass();
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
		Map<Class<?>, Function<Object, String>> newTypeToStringConverter =
			new HashMap<>(this.typeToStringConverter);
		@SuppressWarnings("unchecked")
		Function<Object, String> unsafeConverter = (Function<Object, String>) converter;
		newTypeToStringConverter.put(type, unsafeConverter);
		return new MainConfiguration(scope, context, assertionsEnabled, cleanStackTrace, diffEnabled,
			newTypeToStringConverter);
	}

	@Override
	public <T> Configuration withoutStringConverter(Class<T> type)
	{
		if (type == null)
			throw new NullPointerException("type may not be null");
		if (!typeToStringConverter.containsKey(type))
			return this;
		Map<Class<?>, Function<Object, String>> newTypeToStringConverter =
			new HashMap<>(this.typeToStringConverter);
		newTypeToStringConverter.remove(type);
		return new MainConfiguration(scope, context, assertionsEnabled, cleanStackTrace, diffEnabled,
			newTypeToStringConverter);
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
		if (!(o instanceof MainConfiguration other))
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
