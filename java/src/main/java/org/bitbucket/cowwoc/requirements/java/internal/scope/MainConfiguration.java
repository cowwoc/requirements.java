/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.scope;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.util.Maps;
import org.bitbucket.cowwoc.requirements.java.internal.util.Objects;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;

/**
 * Configures the behavior of a single verifier.
 * <p>
 * This class is immutable.
 */
@SuppressWarnings({"ConstantConditions", "AssertWithSideEffects"})
public final class MainConfiguration implements Configuration
{
	private static final boolean CLASS_ASSERTIONS_ENABLED;

	static
	{
		boolean assertionsEnabled = false;
		assert (assertionsEnabled = true);
		CLASS_ASSERTIONS_ENABLED = assertionsEnabled;
	}

	private final Map<String, Object> context;
	private final Optional<Class<? extends RuntimeException>> exception;
	private final boolean assertionsEnabled;
	private final boolean diffEnabled;
	private final Map<Class<?>, Function<Object, String>> typeToStringConverter;

	/**
	 * Creates a new configuration:
	 * <ul>
	 * <li>With an empty context.</li>
	 * <li>That throws the default exception type.</li>
	 * <li>Whose assertions are enabled if
	 * <a href="http://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html#enable-disable">
	 * assertions are enabled on this class</a>.</li>
	 * <li>That shows the difference between the actual and expected values.</li>
	 * <li>That invokes {@code Arrays.toString()} for arrays and {@code Object.toString()} for all
	 * other objects to convert them to a {@code String}.</li>
	 * </ul>
	 */
	public MainConfiguration()
	{
		this.context = Collections.emptyMap();
		this.exception = Optional.empty();
		this.assertionsEnabled = CLASS_ASSERTIONS_ENABLED;
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
	 * @param exception             the type of exception to throw
	 * @param assertionsEnabled     true if {@code assertThat()} should invoke {@code requireThat()};
	 *                              false if {@code assertThat()} should do nothing
	 * @param diffEnabled           indicates whether exceptions should show the difference between the
	 *                              actual and expected values
	 * @param typeToStringConverter map from an object type to a function that converts the object to
	 *                              a String
	 * @throws AssertionError if any of the arguments are null
	 */
	private MainConfiguration(Map<String, Object> context,
	                          Optional<Class<? extends RuntimeException>> exception,
	                          boolean assertionsEnabled,
	                          boolean diffEnabled,
	                          Map<Class<?>, Function<Object, String>> typeToStringConverter)
	{
		assert (context != null) : "context may not be null";
		assert (exception != null) : "exception may not be null";
		assert (typeToStringConverter != null) : "typeToStringConverter may not be null";
		this.context = Maps.unmodifiable(context);
		this.exception = exception;
		this.assertionsEnabled = assertionsEnabled;
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
		return new MainConfiguration(context, exception, true, diffEnabled, typeToStringConverter);
	}

	@Override
	public Configuration withAssertionsDisabled()
	{
		if (!assertionsEnabled)
			return this;
		return new MainConfiguration(context, exception, false, diffEnabled, typeToStringConverter);
	}

	@Override
	public Optional<Class<? extends RuntimeException>> getException()
	{
		return exception;
	}

	@Override
	public Configuration withException(Class<? extends RuntimeException> exception)
	{
		if (exception == null)
			throw new NullPointerException("exception may not be null");
		Optional<Class<? extends RuntimeException>> newException = Optional.of(exception);
		if (this.exception.equals(newException))
			return this;
		return new MainConfiguration(context, newException, assertionsEnabled, diffEnabled,
			typeToStringConverter);
	}

	@Override
	public Configuration withDefaultException()
	{
		Optional<Class<? extends RuntimeException>> newException = Optional.empty();
		if (this.exception.equals(newException))
			return this;
		return new MainConfiguration(context, newException, assertionsEnabled, diffEnabled,
			typeToStringConverter);
	}

	@Override
	public Map<String, Object> getContext()
	{
		return context;
	}

	@Override
	public Configuration putContext(String name, Object value)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		Object oldValue = context.get(name);
		if (Objects.equals(oldValue, value))
			return this;
		Map<String, Object> newContext = new HashMap<>((int) Math.ceil((context.size() + 1) / 0.75));
		newContext.putAll(context);
		newContext.put(name, value);
		return new MainConfiguration(newContext, exception, assertionsEnabled, diffEnabled,
			typeToStringConverter);
	}

	@Override
	public Configuration removeContext(String name)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		if (!context.containsKey(name))
			return this;
		Map<String, Object> newContext = new HashMap<>((int) Math.ceil((context.size() - 1) / 0.75));
		for (Entry<String, Object> entry : context.entrySet())
			if (!entry.getKey().equals(name))
				newContext.put(entry.getKey(), entry.getValue());
		return new MainConfiguration(newContext, exception, assertionsEnabled, diffEnabled,
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
		if (this.diffEnabled)
			return this;
		return new MainConfiguration(context, exception, assertionsEnabled, true, typeToStringConverter);
	}

	@Override
	public Configuration withoutDiff()
	{
		if (!this.diffEnabled)
			return this;
		return new MainConfiguration(context, exception, assertionsEnabled, false, typeToStringConverter);
	}

	@Override
	public String toString(Object o)
	{
		if (o == null)
			return "null";
		Class<?> type = o.getClass();
		Function<Object, String> converter;
		if (type.isArray() && !type.getComponentType().isPrimitive())
			converter = typeToStringConverter.get(Object[].class);
		else
			converter = typeToStringConverter.get(type);
		if (converter != null)
			return converter.apply(o);
		return o.toString();
	}

	@Override
	public <T> Configuration withStringConverter(Class<T> type, Function<T, String> converter)
	{
		if (type == null)
			throw new NullPointerException("type may not be null");
		if (converter == null)
			throw new NullPointerException("converter may not be null");
		Function<?, String> existingConverter = typeToStringConverter.get(type);
		if (converter.equals(existingConverter))
			return this;
		Map<Class<?>, Function<Object, String>> newTypeToStringConverter = new HashMap<>(typeToStringConverter);
		@SuppressWarnings("unchecked")
		Function<Object, String> unsafeConverter = (Function<Object, String>) converter;
		newTypeToStringConverter.put(type, unsafeConverter);
		return new MainConfiguration(context, exception, assertionsEnabled, false,
			newTypeToStringConverter);
	}

	@Override
	public <T> Configuration withoutStringConverter(Class<T> type)
	{
		if (type == null)
			throw new NullPointerException("type may not be null");
		if (!typeToStringConverter.containsKey(type))
			return this;
		Map<Class<?>, Function<Object, String>> newTypeToStringConverter = new HashMap<>(typeToStringConverter);
		newTypeToStringConverter.remove(type);
		return new MainConfiguration(context, exception, assertionsEnabled, false, newTypeToStringConverter);
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
		hash = 23 * hash + exception.hashCode();
		hash = 23 * hash + Boolean.hashCode(assertionsEnabled);
		hash = 23 * hash + Boolean.hashCode(diffEnabled);
		return 23 * hash + typeToStringConverter.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this)
			return true;
		if (!(o instanceof MainConfiguration))
			return false;
		MainConfiguration other = (MainConfiguration) o;
		return assertionsEnabled == other.assertionsEnabled && context.equals(other.context) &&
			exception.equals(other.exception) && diffEnabled == other.diffEnabled &&
			typeToStringConverter.equals(other.typeToStringConverter);
	}

	@Override
	public String toString()
	{
		return "Configuration[context=" + context + ", exception=" + exception + ", assertionsEnabled=" +
			assertionsEnabled + ", " + "diffEnabled=" + diffEnabled + ", typeToStringConverter=" +
			typeToStringConverter + "]";
	}
}
