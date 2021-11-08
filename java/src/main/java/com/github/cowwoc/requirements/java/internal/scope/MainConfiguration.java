/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.scope;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.internal.util.Maps;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
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
	public MainConfiguration()
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
		typeToStringConverter.put(Object[].class, o -> Arrays.toString((Object[]) o));
		typeToStringConverter.put(Integer.class, o -> decimalFormat.get().format(o));
		typeToStringConverter.put(Long.class, o -> decimalFormat.get().format(o));
		typeToStringConverter.put(BigDecimal.class, o -> ((BigDecimal) o).toPlainString());
		typeToStringConverter.put(Path.class, o -> ((Path) o).toAbsolutePath().toString());
	}

	/**
	 * Copies a configuration.
	 *
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
	private MainConfiguration(Map<String, Object> context,
	                          boolean assertionsEnabled,
	                          boolean cleanStackTrace,
	                          boolean diffEnabled,
	                          Map<Class<?>, Function<Object, String>> typeToStringConverter)
	{
		assert (context != null) : "context may not be null";
		assert (typeToStringConverter != null) : "typeToStringConverter may not be null";
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
		return new MainConfiguration(context, true, cleanStackTrace, diffEnabled,
			typeToStringConverter);
	}

	@Override
	public Configuration withAssertionsDisabled()
	{
		if (!assertionsEnabled)
			return this;
		return new MainConfiguration(context, false, cleanStackTrace, diffEnabled,
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
		return new MainConfiguration(context, assertionsEnabled, true, diffEnabled,
			typeToStringConverter);
	}

	@Override
	public Configuration withoutCleanStackTrace()
	{
		if (!cleanStackTrace)
			return this;
		return new MainConfiguration(context, assertionsEnabled, false, diffEnabled,
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
		return new MainConfiguration(newContext, assertionsEnabled, cleanStackTrace, diffEnabled,
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
		return new MainConfiguration(newContext, assertionsEnabled, cleanStackTrace, diffEnabled,
			typeToStringConverter);
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
		return new MainConfiguration(context, assertionsEnabled, cleanStackTrace, true,
			typeToStringConverter);
	}

	@Override
	public Configuration withoutDiff()
	{
		if (!diffEnabled)
			return this;
		return new MainConfiguration(context, assertionsEnabled, cleanStackTrace, false,
			typeToStringConverter);
	}

	@Override
	public String toString(Object value)
	{
		if (value == null)
			return "null";
		Class<?> type = value.getClass();
		Function<Object, String> converter;
		if (type.isArray() && !type.getComponentType().isPrimitive())
			converter = typeToStringConverter.get(Object[].class);
		else
			converter = typeToStringConverter.get(type);
		if (converter != null)
			return converter.apply(value);
		return value.toString();
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
		return new MainConfiguration(context, assertionsEnabled, cleanStackTrace, diffEnabled,
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
		return new MainConfiguration(context, assertionsEnabled, cleanStackTrace, diffEnabled,
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
