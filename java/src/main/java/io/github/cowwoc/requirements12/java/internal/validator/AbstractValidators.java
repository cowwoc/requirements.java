/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.internal.validator;

import io.github.cowwoc.requirements12.annotation.CheckReturnValue;
import io.github.cowwoc.requirements12.java.GlobalConfiguration;
import io.github.cowwoc.requirements12.java.Validators;
import io.github.cowwoc.requirements12.java.internal.Configuration;
import io.github.cowwoc.requirements12.java.internal.ConfigurationUpdater;
import io.github.cowwoc.requirements12.java.internal.EqualityMethod;
import io.github.cowwoc.requirements12.java.internal.MutableStringMappers;
import io.github.cowwoc.requirements12.java.internal.StringMappers;
import io.github.cowwoc.requirements12.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements12.java.internal.util.StampedLocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @param <S> the type of the validator factory
 */
public abstract class AbstractValidators<S> implements Validators<S>
{
	/**
	 * A function that converts the thrown exception to {@code AssertionError}.
	 */
	private static final Function<Throwable, Throwable> CONVERT_TO_ASSERTION_ERROR = t ->
	{
		AssertionError replacement = new AssertionError(t.getMessage(), t.getCause());
		for (Throwable suppressed : t.getSuppressed())
			replacement.addSuppressed(suppressed);
		return replacement;
	};
	private final StampedLock requireThatLock = new StampedLock();
	private final StampedLock assertThatLock = new StampedLock();
	private final StampedLock checkIfLock = new StampedLock();
	protected final ApplicationScope scope;
	private Configuration requireThatConfiguration;
	private Configuration assertThatConfiguration;
	private Configuration checkIfConfiguration;
	protected final Map<String, Optional<Object>> context = new HashMap<>();

	/**
	 * Creates a new instance.
	 *
	 * @param scope         the application configuration
	 * @param configuration the configuration to use for new validators
	 * @throws NullPointerException if {@code scope} or {@code configuration} are null
	 */
	@SuppressWarnings("this-escape")
	protected AbstractValidators(ApplicationScope scope, Configuration configuration)
	{
		if (scope == null)
			throw new NullPointerException("scope may not be null");
		this.scope = scope;
		// Suppress "this-escape" because this method is only invoked after the state is fully initialized
		setConfiguration(configuration);
	}

	/**
	 * @return the application configuration
	 */
	public ApplicationScope getScope()
	{
		return scope;
	}

	/**
	 * @return this
	 */
	protected S self()
	{
		@SuppressWarnings("unchecked")
		S self = (S) this;
		return self;
	}

	/**
	 * Returns the configuration used by new validators.
	 *
	 * @return the configuration used by new validators
	 */
	@CheckReturnValue
	public Configuration configuration()
	{
		return getRequireThatConfiguration();
	}

	/**
	 * Returns the configuration for {@code requireThat()} factory methods.
	 *
	 * @return the configuration for {@code requireThat()} factory methods
	 */
	public Configuration getRequireThatConfiguration()
	{
		return StampedLocks.optimisticRead(requireThatLock, () -> this.requireThatConfiguration);
	}

	/**
	 * Returns the configuration for {@code assertThat()} factory methods.
	 *
	 * @return the configuration for {@code assertThat()} factory methods
	 */
	protected Configuration getAssertThatConfiguration()
	{
		return StampedLocks.optimisticRead(assertThatLock, () -> this.assertThatConfiguration);
	}

	/**
	 * Returns the configuration for {@code checkIf()} factory methods.
	 *
	 * @return the configuration for {@code checkIf()} factory methods
	 */
	protected Configuration getCheckIfConfiguration()
	{
		return StampedLocks.optimisticRead(checkIfLock, () -> this.checkIfConfiguration);
	}

	/**
	 * Updates the configuration that will be used by new validators.
	 * <p>
	 * <b>NOTE</b>: Changes are only applied when {@link ConfigurationUpdater#close()} is invoked.
	 *
	 * @return the configuration updater
	 */
	@CheckReturnValue
	public final ConfigurationUpdater updateConfiguration()
	{
		// Method must be final because it is invoked by subclass constructors:
		// https://pmd.github.io/pmd/pmd_rules_java_errorprone.html#constructorcallsoverridablemethod
		return new ConfigurationUpdaterImpl(this::setConfiguration);
	}

	/**
	 * Returns a configuration updater that sets the validator factory's configuration.
	 *
	 * @param setConfiguration a method that sets the validator factory's configuration
	 * @return the configuration updater
	 * @throws NullPointerException if {@code setConfiguration} is null
	 */
	@CheckReturnValue
	public ConfigurationUpdater updateAndSetConfiguration(Consumer<Configuration> setConfiguration)
	{
		return new ConfigurationUpdaterImpl(setConfiguration);
	}

	/**
	 * Set the configuration used by new validators.
	 *
	 * @param configuration the updated configuration
	 * @throws NullPointerException if {@code configuration} is null
	 */
	public final void setConfiguration(Configuration configuration)
	{
		if (configuration == null)
			throw new NullPointerException("configuration may not be null");
		StampedLocks.write(requireThatLock, () -> this.requireThatConfiguration = configuration);
		StampedLocks.write(assertThatLock, () ->
		{
			this.assertThatConfiguration = MutableConfiguration.from(configuration).
				throwOnFailure(false).exceptionTransformer(CONVERT_TO_ASSERTION_ERROR).toImmutable();
		});
		StampedLocks.write(checkIfLock, () ->
		{
			this.checkIfConfiguration = MutableConfiguration.from(configuration).
				throwOnFailure(false).toImmutable();
		});
	}

	@Override
	public Map<String, Optional<Object>> getContext()
	{
		return Map.copyOf(context);
	}

	@Override
	public GlobalConfiguration globalConfiguration()
	{
		return scope.getGlobalConfiguration();
	}

	/**
	 * Updates the configuration that will be used by new validators.
	 */
	private final class ConfigurationUpdaterImpl implements ConfigurationUpdater
	{
		// REMINDER: Per https://shipilev.net/blog/2014/safe-public-construction/ section
		// "A final field was written" objects are safe for publication if they contain at least one final field.
		private final Consumer<Configuration> setConfiguration;
		private final MutableStringMappers mutableStringMappers;
		private boolean cleanStackTrace;
		private boolean allowDiff;
		private EqualityMethod equalityMethod;
		private boolean recordStacktrace;
		private Function<Throwable, ? extends Throwable> exceptionTransformer;
		private boolean changed;
		private boolean closed;

		/**
		 * Creates a new configuration updater.
		 *
		 * @param setConfiguration a method that sets the validator factory's configuration
		 * @throws NullPointerException if {@code setConfiguration} is null
		 */
		private ConfigurationUpdaterImpl(Consumer<Configuration> setConfiguration)
		{
			assert setConfiguration != null;
			this.setConfiguration = setConfiguration;

			Configuration configuration = AbstractValidators.this.configuration();
			this.cleanStackTrace = configuration.cleanStackTrace();
			this.allowDiff = configuration.allowDiff();
			this.equalityMethod = configuration.equalityMethod();
			this.mutableStringMappers = MutableStringMappers.from(configuration.stringMappers());
			this.recordStacktrace = configuration.recordStacktrace();
			this.exceptionTransformer = configuration.exceptionTransformer();
		}

		@Override
		public boolean cleanStackTrace()
		{
			ensureOpen();
			return cleanStackTrace;
		}

		@Override
		public ConfigurationUpdater cleanStackTrace(boolean cleanStackTrace)
		{
			ensureOpen();
			if (cleanStackTrace != this.cleanStackTrace)
			{
				this.cleanStackTrace = cleanStackTrace;
				this.changed = true;
			}
			return this;
		}

		@Override
		public boolean allowDiff()
		{
			ensureOpen();
			return allowDiff;
		}

		@Override
		public ConfigurationUpdater allowDiff(boolean allowDiff)
		{
			ensureOpen();
			if (allowDiff != this.allowDiff)
			{
				this.allowDiff = allowDiff;
				changed = true;
			}
			return this;
		}

		@Override
		public EqualityMethod equalityMethod()
		{
			ensureOpen();
			return equalityMethod;
		}

		@Override
		public ConfigurationUpdater equalityMethod(EqualityMethod equalityMethod)
		{
			ensureOpen();
			if (equalityMethod != this.equalityMethod)
			{
				this.equalityMethod = equalityMethod;
				changed = true;
			}
			return this;
		}

		@Override
		public MutableStringMappers stringMappers()
		{
			ensureOpen();
			return mutableStringMappers;
		}

		@Override
		public boolean recordStacktrace()
		{
			ensureOpen();
			return recordStacktrace;
		}

		@Override
		public ConfigurationUpdater recordStacktrace(boolean recordStacktrace)
		{
			ensureOpen();
			if (recordStacktrace != this.recordStacktrace)
			{
				this.recordStacktrace = recordStacktrace;
				changed = true;
			}
			return this;
		}

		@Override
		public Function<Throwable, ? extends Throwable> exceptionTransformer()
		{
			ensureOpen();
			return exceptionTransformer;
		}

		@Override
		public ConfigurationUpdater exceptionTransformer(
			Function<Throwable, ? extends Throwable> exceptionTransformer)
		{
			ensureOpen();
			if (exceptionTransformer != this.exceptionTransformer)
			{
				this.exceptionTransformer = exceptionTransformer;
				changed = true;
			}
			return this;
		}

		/**
		 * @throws IllegalStateException if the updater is closed
		 */
		private void ensureOpen()
		{
			if (closed)
				throw new IllegalStateException("The changes have already been applied");
		}

		@Override
		public void close()
		{
			if (closed)
				return;
			closed = true;
			Configuration oldConfiguration = AbstractValidators.this.configuration();
			StringMappers immutableStringMappers = mutableStringMappers.toImmutable();
			changed |= !immutableStringMappers.equals(oldConfiguration.stringMappers());
			if (!changed)
				return;
			this.setConfiguration.accept(new Configuration(cleanStackTrace, allowDiff, equalityMethod,
				immutableStringMappers, recordStacktrace, oldConfiguration.throwOnFailure(), exceptionTransformer));
		}

		@Override
		public String toString()
		{
			return "cleanStackTrace: " + cleanStackTrace + ", allowDiff: " + allowDiff + ", equalityMethod: " +
				equalityMethod + ", stringMappers: " + mutableStringMappers + ", recordStacktrace: " +
				recordStacktrace + ", exceptionTransformer: " + exceptionTransformer;
		}
	}
}