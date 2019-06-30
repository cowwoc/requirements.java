/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.StringValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectValidator;
import org.bitbucket.cowwoc.requirements.java.internal.ContextGenerator;
import org.bitbucket.cowwoc.requirements.java.internal.StringValidatorImpl;
import org.bitbucket.cowwoc.requirements.java.internal.ValidationFailureImpl;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.Objects;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Default implementation of {@code ExtensibleObjectValidator}.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value
 */
public abstract class AbstractObjectValidator<S, T> implements ExtensibleObjectValidator<S, T>
{
	protected final ApplicationScope scope;
	protected final String name;
	protected final T actual;
	protected final Configuration config;
	protected final List<ValidationFailure> failures;

	/**
	 * @param scope    the application configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code name}, {@code config} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	protected AbstractObjectValidator(ApplicationScope scope, String name, T actual,
	                                  Configuration config, List<ValidationFailure> failures)
	{
		assert (scope != null) : "scope may not be null";
		assert (name != null) : "name may not be null";
		assert (!name.isEmpty()) : "name may not be empty";
		assert (config != null) : "config may not be null";
		assert (failures != null) : "failures may not be null";
		this.scope = scope;
		this.name = name;
		this.actual = actual;
		this.config = config;
		this.failures = failures;
	}

	/**
	 * @return this
	 */
	protected S getThis()
	{
		@SuppressWarnings("unchecked")
		S result = (S) this;
		return result;
	}

	@Override
	public Optional<T> getActual()
	{
		return Optional.ofNullable(actual);
	}

	@Override
	public List<ValidationFailure> getFailures()
	{
		return Collections.unmodifiableList(failures);
	}

	@Override
	public S isEqualTo(Object expected)
	{
		if (!Objects.equals(actual, expected))
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " had an unexpected value.").
				addContext(getContext(expected));
			failures.add(failure);
		}
		return getThis();
	}

	/**
	 * @param expected the expected value
	 * @return the list of name-value pairs to append to the exception message
	 */
	private List<Entry<String, Object>> getContext(Object expected)
	{
		ContextGenerator contextGenerator = new ContextGenerator(config, scope.getDiffGenerator());
		return contextGenerator.getContext("Actual", actual, "Expected", expected);
	}

	@Override
	public S isEqualTo(Object expected, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(actual, expected))
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				this.name + " must be equal to " + name + ".").
				addContext(getContext(expected));
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isNotEqualTo(Object other)
	{
		if (Objects.equals(actual, other))
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " may not be equal to " + config.toString(other));
			failures.add(failure);
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
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				this.name + " may not be equal to " + name + ".").
				addContext("Actual", actual);
			failures.add(failure);
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
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				this.name + " must be the same object as " + name + ".").
				addContext(getContext(expected));
			failures.add(failure);
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
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				this.name + " may not be the same object as " + name + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isOneOf(Collection<? super T> collection)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(collection, "collection").isNotNull();
		if (!collection.contains(actual))
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				this.name + " must be one of " + config.toString(collection) + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isNotOneOf(Collection<? super T> collection)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(collection, "collection").isNotNull();
		if (collection.contains(actual))
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				this.name + " may not be one of " + config.toString(collection) + ".").
				addContext("Actual", actual);
			failures.add(failure);
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

			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " must be an instance of " + type.getName() + ".").
				addContext("Actual.getClass()", actualClass).
				addContext("Actual", actual);
			failures.add(failure);
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
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " may not be an instance of " + type.getName() + ".").
				addContext("Actual.getClass()", actualClass).
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isNull()
	{
		if (actual != null)
		{
			// Output a diff because actual.toString() may return "null" which is misleading
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " must be null.").
				addContext(getContext(null));
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isNotNull()
	{
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(NullPointerException.class,
				name + " may not be null").
				addContext(getContext(null));
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public StringValidator asString()
	{
		String value = config.toString(actual);
		return new StringValidatorImpl(scope, name, value, config, failures);
	}

	@Override
	public S asString(Consumer<StringValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asString());
		return getThis();
	}
}
