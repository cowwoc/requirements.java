package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.EqualityMethod;
import com.github.cowwoc.requirements.java.GlobalConfiguration;
import com.github.cowwoc.requirements.java.MutableStringMappers;
import com.github.cowwoc.requirements.java.ScopedContext;
import com.github.cowwoc.requirements.java.StringMappers;
import com.github.cowwoc.requirements.java.Validators;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.CloseableLock;
import com.github.cowwoc.requirements.java.internal.util.ReentrantStampedLock;

import java.util.function.Function;

/**
 * @param <S> the type of validator that the methods should return
 */
public abstract class AbstractValidators<S> implements Validators
{
	protected final ApplicationScope scope;
	private final ReentrantStampedLock assumeThatLock = new ReentrantStampedLock();
	private final ReentrantStampedLock checkIfLock = new ReentrantStampedLock();
	private Configuration configuration;
	private Configuration assumeThatConfiguration;
	private Configuration checkIfConfiguration;

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
		this.configuration = configuration;
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
		return configuration;
	}

	@Override
	public ConfigurationUpdater updateConfiguration()
	{
		return new UpdatableConfigurationImpl();
	}

	/**
	 * Set the configuration used by new validators.
	 *
	 * @param configuration the updated configuration
	 * @throws NullPointerException if {@code configuration} is null
	 */
	public void setConfiguration(Configuration configuration)
	{
		if (configuration == null)
			throw new NullPointerException("configuration may not be null");
		this.configuration = configuration;
		try (CloseableLock unused = assumeThatLock.write())
		{
			assumeThatConfiguration = null;
		}
		try (CloseableLock unused = checkIfLock.write())
		{
			checkIfConfiguration = null;
		}
	}

	/**
	 * Returns the configuration for {@code requireThat()} factory methods.
	 *
	 * @return the configuration for {@code requireThat()} factory methods
	 */
	protected Configuration getRequireThatConfiguration()
	{
		return configuration();
	}

	/**
	 * Returns the configuration for {@code assumeThat()} factory methods.
	 *
	 * @return the configuration for {@code assumeThat()} factory methods
	 */
	protected Configuration getAssumeThatConfiguration()
	{
		Configuration assumeThatConfiguration = assumeThatLock.optimisticRead(() ->
			this.assumeThatConfiguration);
		if (assumeThatConfiguration != null)
			return assumeThatConfiguration;
		try (CloseableLock unused = assumeThatLock.write())
		{
			this.assumeThatConfiguration = assumeThatConfiguration = MutableConfiguration.from(configuration()).
				exceptionTransformer(convertToAssertionError()).toImmutable();
		}
		return assumeThatConfiguration;
	}

	/**
	 * Returns the configuration for {@code checkIf()} factory methods.
	 *
	 * @return the configuration for {@code checkIf()} factory methods
	 */
	protected Configuration getCheckIfConfiguration()
	{
		Configuration checkIfConfiguration = checkIfLock.optimisticRead(() ->
			this.checkIfConfiguration);
		if (checkIfConfiguration != null)
			return checkIfConfiguration;
		try (CloseableLock unused = checkIfLock.write())
		{
			this.checkIfConfiguration = checkIfConfiguration = MutableConfiguration.from(configuration()).
				throwOnFailure(false).toImmutable();
		}
		return checkIfConfiguration;
	}

	@Override
	public ScopedContext threadContext()
	{
		return new ScopedContextImpl(scope);
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
		 */
		private UpdatableConfigurationImpl()
		{
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

		/**
		 * Specifies that this library should be excluded from exception stack traces, except when that also
		 * removes user code.
		 *
		 * @param cleanStackTrace {@code true} if stack traces should be modified, {@code false} otherwise
		 * @return this
		 * @throws IllegalStateException if the updater is closed
		 */
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

		/**
		 * Specifies whether exception messages should include a diff that compares actual and expected values if
		 * they are too long. The threshold for "too long" is not specified.
		 *
		 * @param includeDiff {@code true} if exception messages should include a diff, {@code false} otherwise
		 * @return this
		 * @throws IllegalStateException if the updater is closed
		 */
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

		/**
		 * Sets the equality method that determines whether two values are equivalent. The equality method is only
		 * used when both arguments are not null.
		 *
		 * @param equalityMethod the equality method to use
		 * @return this
		 * @throws IllegalStateException if the updater is closed
		 */
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

		/**
		 * Returns the configuration used to map contextual values to a String. Supports common types such as
		 * arrays, numbers, collections, maps, paths and exceptions.
		 *
		 * @return a function that takes an object and returns the {@code String} representation of the object
		 * @throws IllegalStateException if the updater is closed
		 */
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

		/**
		 * Transform the validation exception into a suitable runtime exception or error. The input and output of
		 * the function must be subclasses of {@code RuntimeException} or {@code Error}. If the output is not, it
		 * is wrapped in a {@code WrappedCheckedException}. If the function returns {@code null} the input
		 * exception will be thrown.
		 *
		 * @param exceptionTransformer a function that transforms the validation exception
		 * @return this
		 * @throws NullPointerException  if {@code transformer} is null
		 * @throws IllegalStateException if the updater is closed
		 */
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
			setConfiguration(new Configuration(cleanStackTrace, includeDiff,
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