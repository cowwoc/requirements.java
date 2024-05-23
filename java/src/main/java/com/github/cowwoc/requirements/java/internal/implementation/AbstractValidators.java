package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.EqualityMethod;
import com.github.cowwoc.requirements.java.GlobalConfiguration;
import com.github.cowwoc.requirements.java.MutableStringMappers;
import com.github.cowwoc.requirements.java.StringMappers;
import com.github.cowwoc.requirements.java.Validators;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.CloseableLock;
import com.github.cowwoc.requirements.java.internal.util.ReentrantStampedLock;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.github.cowwoc.requirements.java.DefaultJavaValidators.assumeThat;

/**
 * @param <S> the type of the validator factory
 */
public abstract class AbstractValidators<S> implements Validators<S>
{
	protected final ApplicationScope scope;
	private final ReentrantStampedLock requireThatLock = new ReentrantStampedLock();
	private final ReentrantStampedLock assumeThatLock = new ReentrantStampedLock();
	private final ReentrantStampedLock checkIfLock = new ReentrantStampedLock();
	private Configuration requireThatConfiguration;
	private Configuration assumeThatConfiguration;
	private Configuration checkIfConfiguration;
	protected final Map<String, Object> context = new HashMap<>();

	/**
	 * Creates a new instance.
	 *
	 * @param scope         the application configuration
	 * @param configuration the configuration to use for new validators
	 * @throws NullPointerException if any of the arguments are null
	 */
	protected AbstractValidators(ApplicationScope scope, Configuration configuration)
	{
		if (scope == null)
			throw new NullPointerException("scope may not be null");
		if (configuration == null)
			throw new NullPointerException("configuration may not be null");
		this.scope = scope;
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
	 * Returns a function that converts the thrown exception to {@code AssertionError}.
	 *
	 * @return a function that converts the thrown exception to {@code AssertionError}
	 */
	private static Function<Throwable, Throwable> convertToAssertionError()
	{
		return t ->
		{
			AssertionError replacement = new AssertionError(t.getMessage(), t.getCause());
			for (Throwable suppressed : t.getSuppressed())
				replacement.addSuppressed(suppressed);
			return replacement;
		};
	}

	/**
	 * @return this
	 */
	protected S self()
	{
		@SuppressWarnings("unchecked")
		S castedThis = (S) this;
		return castedThis;
	}

	@Override
	public Configuration configuration()
	{
		return requireThatLock.optimisticRead(() -> this.requireThatConfiguration);
	}

	/**
	 * Returns the configuration for {@code assumeThat()} factory methods.
	 *
	 * @return the configuration for {@code assumeThat()} factory methods
	 */
	protected Configuration getAssumeThatConfiguration()
	{
		return assumeThatLock.optimisticRead(() -> this.assumeThatConfiguration);
	}

	/**
	 * Returns the configuration for {@code checkIf()} factory methods.
	 *
	 * @return the configuration for {@code checkIf()} factory methods
	 */
	protected Configuration getCheckIfConfiguration()
	{
		return checkIfLock.optimisticRead(() -> this.checkIfConfiguration);
	}

	@Override
	public ConfigurationUpdater updateConfiguration()
	{
		return updateConfiguration(this::setConfiguration);
	}

	/**
	 * Updates the configuration used by new validators.
	 * <p>
	 * <b>NOTE</b>: Changes are only applied when {@link ConfigurationUpdater#close()} is invoked.
	 *
	 * @param setConfiguration a method that sets the validator factory's configuration
	 * @return the configuration updater
	 * @throws NullPointerException if {@code setConfiguration} is null
	 */
	@CheckReturnValue
	public ConfigurationUpdater updateConfiguration(Consumer<Configuration> setConfiguration)
	{
		return new UpdatableConfigurationImpl(setConfiguration);
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
		try (CloseableLock unused = requireThatLock.write())
		{
			this.requireThatConfiguration = configuration;
		}
		try (CloseableLock unused = assumeThatLock.write())
		{
			this.assumeThatConfiguration = MutableConfiguration.from(configuration).
				exceptionTransformer(convertToAssertionError()).toImmutable();
		}
		try (CloseableLock unused = checkIfLock.write())
		{
			this.checkIfConfiguration = MutableConfiguration.from(configuration).
				throwOnFailure(false).toImmutable();
		}
	}

	@Override
	public Map<String, Object> getContext()
	{
		return Collections.unmodifiableMap(context);
	}

	@Override
	public GlobalConfiguration globalConfiguration()
	{
		return scope.getGlobalConfiguration();
	}

	/**
	 * Updates the configuration used by new validators.
	 */
	public final class UpdatableConfigurationImpl implements ConfigurationUpdater
	{
		// REMINDER: Per https://shipilev.net/blog/2014/safe-public-construction/ section
		// "A final field was written" objects are safe for publication if they contain at least one final field.
		private final Consumer<Configuration> setConfiguration;
		private final MutableStringMappers mutableStringMappers;
		private boolean cleanStackTrace;
		private boolean includeDiff;
		private EqualityMethod equalityMethod;
		private boolean lazyExceptions;
		private Function<Throwable, ? extends Throwable> exceptionTransformer;
		private boolean changed;
		private boolean closed;

		/**
		 * Creates a new configuration updater.
		 *
		 * @param setConfiguration a method that sets the validator factory's configuration
		 * @throws NullPointerException if {@code setConfiguration} is null
		 */
		private UpdatableConfigurationImpl(Consumer<Configuration> setConfiguration)
		{
			assert assumeThat(setConfiguration, "setConfiguration").isNotNull().elseThrow();
			this.setConfiguration = setConfiguration;

			Configuration configuration = AbstractValidators.this.configuration();
			this.cleanStackTrace = configuration.cleanStackTrace();
			this.includeDiff = configuration.includeDiff();
			this.equalityMethod = configuration.equalityMethod();
			this.mutableStringMappers = MutableStringMappers.from(configuration.stringMappers());
			this.lazyExceptions = configuration.lazyExceptions();
			this.exceptionTransformer = configuration.exceptionTransformer();
		}

		@Override
		public boolean cleanStackTrace()
		{
			return cleanStackTrace;
		}

		@Override
		public UpdatableConfigurationImpl cleanStackTrace(boolean cleanStackTrace)
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
		public boolean includeDiff()
		{
			return includeDiff;
		}

		@Override
		public UpdatableConfigurationImpl includeDiff(boolean includeDiff)
		{
			ensureOpen();
			if (includeDiff != this.includeDiff)
			{
				this.includeDiff = includeDiff;
				changed = true;
			}
			return this;
		}

		@Override
		public EqualityMethod equalityMethod()
		{
			return equalityMethod;
		}

		@Override
		public UpdatableConfigurationImpl equalityMethod(EqualityMethod equalityMethod)
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
		public boolean lazyExceptions()
		{
			ensureOpen();
			return lazyExceptions;
		}

		@Override
		public ConfigurationUpdater lazyExceptions(boolean lazyExceptions)
		{
			ensureOpen();
			if (lazyExceptions != this.lazyExceptions)
			{
				this.lazyExceptions = lazyExceptions;
				changed = true;
			}
			return this;
		}

		@Override
		public Function<Throwable, ? extends Throwable> exceptionTransformer()
		{
			return exceptionTransformer;
		}

		@Override
		public UpdatableConfigurationImpl exceptionTransformer(
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
				throw new IllegalStateException("ConfigurationUpdater is closed");
		}

		/**
		 * Updates this validator's configuration.
		 *
		 * @throws IllegalArgumentException if {@code limits} is empty
		 */
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
			this.setConfiguration.accept(new Configuration(cleanStackTrace, includeDiff,
				equalityMethod, immutableStringMappers, lazyExceptions, oldConfiguration.throwOnFailure(),
				exceptionTransformer));
		}

		@Override
		public String toString()
		{
			return "cleanStackTrace: " + cleanStackTrace + ", includeDiff: " + includeDiff + ", equalityMethod: " +
				equalityMethod + ", stringMappers: " + mutableStringMappers + ", lazyExceptions: " +
				lazyExceptions + ", exceptionTransformer: " + exceptionTransformer;
		}
	}
}