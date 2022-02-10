/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.extension;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.StringValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;
import com.github.cowwoc.requirements.java.internal.StringValidatorImpl;
import com.github.cowwoc.requirements.java.internal.ValidationFailureImpl;
import com.github.cowwoc.requirements.java.internal.diff.ContextGenerator;
import com.github.cowwoc.requirements.java.internal.diff.ContextLine;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Default implementation of {@code ExtensibleObjectValidator}.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value being validated
 */
public abstract class AbstractObjectValidator<S, T> implements ExtensibleObjectValidator<S, T>
{
	/**
	 * An empty list of failures.
	 */
	public static final List<ValidationFailure> NO_FAILURES = Collections.emptyList();
	protected final ApplicationScope scope;
	protected final Configuration config;
	protected String name;
	protected T actual;
	private List<ValidationFailure> failures;

	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	protected AbstractObjectValidator(ApplicationScope scope, Configuration config, String name, T actual,
	                                  List<ValidationFailure> failures)
	{
		assert (scope != null) : "scope may not be null";
		assert (config != null) : "config may not be null";
		assert (name != null) : "name may not be null";
		assert (!name.isEmpty()) : "name may not be empty";
		assert (failures != null) : "failures may not be null";
		this.scope = scope;
		this.config = config;
		this.name = name;
		this.actual = actual;
		this.failures = failures;
	}

	/**
	 * @return this
	 */
	protected abstract S getThis();

	/**
	 * @return a validator that does nothing
	 */
	protected abstract S getNoOp();

	/**
	 * Adds a validation failure.
	 *
	 * @param failure the failure to add
	 */
	protected void addFailure(ValidationFailure failure)
	{
		if (failures == NO_FAILURES)
			failures = new ArrayList<>();
		failures.add(failure);
	}

	/**
	 * @return the application configuration
	 */
	public ApplicationScope getScope()
	{
		return scope;
	}

	/**
	 * @return the instance configuration
	 */
	public Configuration getConfiguration()
	{
		return config;
	}

	/**
	 * Returns the name of the actual value.
	 *
	 * @return the name of the actual value
	 */
	public String getName()
	{
		return name;
	}

	@Override
	public boolean isActualAvailable()
	{
		return true;
	}

	@Override
	public T getActual()
	{
		return actual;
	}

	@Override
	public List<ValidationFailure> getFailures()
	{
		return failures;
	}

	@Override
	public S isEqualTo(Object expected)
	{
		if (!Objects.equals(actual, expected))
		{
			String expectedAsString = config.toString(expected);
			int terminalWidth = scope.getGlobalConfiguration().getTerminalWidth();
			String message = name + " must be equal to " + expectedAsString + ".";
			ValidationFailure failure;
			if (message.length() < terminalWidth)
			{
				failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class, message).
					addContext(getContext(expected, true));
			}
			else
			{
				failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
					name + " had an unexpected value.").
					addContext(getContext(expected, false));
			}
			addFailure(failure);
		}
		return getThis();
	}

	/**
	 * @param expected          the expected value
	 * @param expectedInMessage true if the expected value is already mentioned in the failure message
	 * @return the list of name-value pairs to append to the exception message
	 */
	protected List<ContextLine> getContext(Object expected, boolean expectedInMessage)
	{
		ContextGenerator contextGenerator = new ContextGenerator(config, scope);
		return contextGenerator.getContext("Actual", actual, "Expected", expected,
			expectedInMessage);
	}

	@Override
	public S isEqualTo(Object expected, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(actual, expected))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must be equal to " + name + ".").
				addContext(getContext(expected, false));
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isNotEqualTo(Object other)
	{
		if (Objects.equals(actual, other))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not be equal to " + config.toString(other));
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isNotEqualTo(Object other, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(actual, other))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " may not be equal to " + name + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isSameObjectAs(Object expected, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual != expected)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must be the same object as " + name + ".").
				addContext(getContext(expected, false));
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isNotSameObjectAs(Object other, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual == other)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " may not be the same object as " + name + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isOneOf(Collection<? super T> collection)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(collection, "collection").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			return getNoOp();
		}
		if (!collection.contains(actual))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must be one of " + config.toString(collection) + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isNotOneOf(Collection<? super T> collection)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(collection, "collection").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			return getNoOp();
		}
		if (collection.contains(actual))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " may not be one of " + config.toString(collection) + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isInstanceOf(Class<?> type)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(type, "type").isNotNull();
		if (!type.isInstance(actual))
		{
			String actualClass;
			if (actual == null)
				actualClass = "null";
			else
				actualClass = actual.getClass().getName();

			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be an instance of " + type.getName() + ".").
				addContext("Actual.getClass()", actualClass).
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isNotInstanceOf(Class<?> type)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(type, "type").isNotNull();
		if (type.isInstance(actual))
		{
			String actualClass = actual.getClass().getName();
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not be an instance of " + type.getName() + ".").
				addContext("Actual.getClass()", actualClass).
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isNull()
	{
		if (actual != null)
		{
			// Output a diff because actual.toString() may return "null" which is misleading
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be null.").
				addContext(getContext(null, true));
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isNotNull()
	{
		if (actual != null)
			return getThis();
		ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
			this.name + " may not be null");
		addFailure(failure);
		return getNoOp();
	}

	@Override
	public StringValidator asString()
	{
		String value = config.toString(actual);
		return new StringValidatorImpl(scope, config, name, value, failures);
	}

	@Override
	public S asString(Consumer<StringValidator> consumer)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asString());
		return getThis();
	}
}
