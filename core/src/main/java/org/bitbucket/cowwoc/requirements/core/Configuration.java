/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Supplier;
import org.bitbucket.cowwoc.requirements.internal.core.util.ToStringSupplier;

/**
 * A verifier's configuration.
 * <p>
 * This class is immutable.
 *
 * @author Gili Tzabari
 * @see Configurable
 */
@SuppressWarnings(
	{
		"NestedAssignment", "AssertWithSideEffects"
	})
public final class Configuration implements Configurable
{
	private static final boolean CLASS_ASSERTIONS_ENABLED;

	static
	{
		boolean assertionsEnabled = false;
		assert (assertionsEnabled = true);
		CLASS_ASSERTIONS_ENABLED = assertionsEnabled;
	}
	private final List<Entry<String, Object>> context;
	private final Optional<Class<? extends RuntimeException>> exception;
	private final boolean assertionsEnabled;
	private final boolean diffEnabled;

	/**
	 * Creates a new configuration:
	 * <p>
	 * <ul>
	 * <li>With an empty context.</li>
	 * <li>That throws the default exception type.</li>
	 * <li>Whose assertions are enabled if <a href="http://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html#enable-disable">assertions are enabled on this class</a>.</li>
	 * <li>That shows the difference between the actual and expected values.</li>
	 * </ul>
	 */
	public Configuration()
	{
		this.context = Collections.emptyList();
		this.exception = Optional.empty();
		this.assertionsEnabled = CLASS_ASSERTIONS_ENABLED;
		this.diffEnabled = true;
	}

	/**
	 * Copies a configuration.
	 *
	 * @param context           the list of key-value pairs to append to the exception message
	 * @param exception         the type of exception to throw
	 * @param assertionsEnabled true if {@code assertThat()} should invoke {@code requireThat()};
	 *                          false if {@code assertThat()} should do nothing
	 * @param diffEnabled       indicates whether exceptions should show the difference between the
	 *                          actual and expected values
	 * @throws AssertionError if any of the arguments are null
	 */
	private Configuration(List<Entry<String, Object>> context,
		Optional<Class<? extends RuntimeException>> exception, boolean assertionsEnabled,
		boolean diffEnabled)
	{
		assert (context != null): "context may not be null";
		assert (exception != null): "exception may not be null";
		this.context = Collections.unmodifiableList(context);
		this.exception = exception;
		this.assertionsEnabled = assertionsEnabled;
		this.diffEnabled = diffEnabled;
	}

	/**
	 * @return true if {@code assertThat()} should delegate to {@code requireThat()}; false if it
	 *         shouldn't do anything
	 */
	@Override
	@SuppressWarnings("deprecation")
	public boolean assertionsAreEnabled()
	{
		return assertionsEnabled;
	}

	@Override
	public Configuration withAssertionsEnabled()
	{
		if (assertionsEnabled)
			return this;
		return new Configuration(context, exception, true, diffEnabled);
	}

	@Override
	public Configuration withAssertionsDisabled()
	{
		if (!assertionsEnabled)
			return this;
		return new Configuration(context, exception, false, diffEnabled);
	}

	/**
	 * Returns the type of exception that will be thrown if a verification fails.
	 *
	 * @return {@code Optional.empty()} if the default exception type will get thrown
	 * @see #withException(Class)
	 * @see #withDefaultException()
	 */
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
		return new Configuration(context, newException, assertionsEnabled, diffEnabled);
	}

	@Override
	public Configuration withDefaultException()
	{
		Optional<Class<? extends RuntimeException>> newException = Optional.empty();
		if (this.exception.equals(newException))
			return this;
		return new Configuration(context, newException, assertionsEnabled, diffEnabled);
	}

	/**
	 * @return an unmodifiable list of key-value pairs to append to the exception message
	 * @see #addContext(String, Object)
	 */
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public List<Entry<String, Object>> getContext()
	{
		return context;
	}

	@Override
	public Configuration addContext(String key, Object value)
	{
		if (key == null)
			throw new NullPointerException("key may not be null");
		List<Entry<String, Object>> newContext = new ArrayList<>(context.size() + 1);
		newContext.addAll(context);
		newContext.add(new SimpleImmutableEntry<>(key, value));
		return new Configuration(newContext, exception, assertionsEnabled, diffEnabled);
	}

	@Override
	public Configuration addContext(String key, Supplier<String> value)
	{
		return addContext(key, new ToStringSupplier(value));
	}

	/**
	 * @return true if exceptions should show the difference between the actual and expected values
	 */
	public boolean isDiffEnabled()
	{
		return diffEnabled;
	}

	@Override
	public Configuration withDiff()
	{
		if (this.diffEnabled)
			return this;
		return new Configuration(context, exception, assertionsEnabled, true);
	}

	@Override
	public Configuration withoutDiff()
	{
		if (!this.diffEnabled)
			return this;
		return new Configuration(context, exception, assertionsEnabled, false);
	}

	@Override
	public Configurable withConfiguration(Configuration configuration)
	{
		if (configuration == null)
			throw new NullPointerException("configuration may not be null");
		return configuration;
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 23 * hash + this.context.hashCode();
		hash = 23 * hash + this.exception.hashCode();
		hash = 23 * hash + Boolean.hashCode(this.assertionsEnabled);
		hash = 23 * hash + Boolean.hashCode(this.diffEnabled);
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this)
			return true;
		if (!(o instanceof Configuration))
			return false;
		Configuration other = (Configuration) o;
		return assertionsEnabled == other.assertionsEnabled && context.equals(other.context) &&
			exception.equals(other.exception) && diffEnabled == other.diffEnabled;
	}

	@Override
	public String toString()
	{
		return "Configuration[context=" + context + ", exception=" + exception +
			", assertionsEnabled=" + assertionsEnabled + ", diffEnabled=" + diffEnabled + "]";
	}
}
