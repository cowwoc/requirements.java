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

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Default implementation of {@code ExtensibleObjectValidator}.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value being validated
 */
public abstract class AbstractObjectValidator<S, T> implements ExtensibleObjectValidator<S, T>
{
	protected final ApplicationScope scope;
	protected final Configuration config;
	protected String name;
	protected T actual;
	protected boolean fatalFailure;
	protected final List<ValidationFailure> failures;

	/**
	 * @param scope        the application configuration
	 * @param config       the instance configuration
	 * @param name         the name of the value
	 * @param actual       the actual value
	 * @param failures     the list of validation failures
	 * @param fatalFailure true if validation stopped as the result of a fatal failure
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is blank.
	 */
	protected AbstractObjectValidator(ApplicationScope scope, Configuration config, String name, T actual,
		List<ValidationFailure> failures, boolean fatalFailure)
	{
		assert (scope != null) : "scope may not be null";
		assert (config != null) : "config may not be null";
		assert (name != null) : "name may not be null";
		assert (!name.isBlank()) : "name may not be blank";
		assert (failures != null) : "failures may not be null";
		this.scope = scope;
		this.config = config;
		this.name = name;
		this.actual = actual;
		this.fatalFailure = fatalFailure;
		// Intentionally avoid making a defensive copy of the list because some validators delegate some
		// (but not all) methods to a secondary validator. For example: ArrayValidatorImpl delegates some
		// methods to ListValidatorImpl.
		this.failures = failures;
	}

	/**
	 * @return this
	 */
	protected abstract S getThis();

	/**
	 * Adds a validation failure.
	 *
	 * @param failure the failure to add
	 */
	protected void addFailure(ValidationFailure failure)
	{
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
		return isEqualTo(expected, Objects::equals);
	}

	@Override
	public S isEqualTo(Object expected, String name)
	{
		return isEqualTo(expected, name, Objects::equals);
	}

	@Override
	public S isNotEqualTo(Object other)
	{
		return isNotEqualTo(other, Objects::equals);
	}

	@Override
	public S isNotEqualTo(Object other, String name)
	{
		return isNotEqualTo(other, name, Objects::equals);
	}

	@Override
	public S isSameObjectAs(Object expected, String name)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotBlank();
		if (actual != expected)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must be the same object as " + name + ".").
				addContext(getContext(expected, false));
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
		ContextGenerator contextGenerator = new ContextGenerator(config, scope).
			actualName("Actual").
			actualValue(actual).
			expectedName("Expected").
			expectedValue(expected);
		if (expectedInMessage)
			contextGenerator.expectedInMessage();
		return contextGenerator.build();
	}

	@Override
	public S isNotSameObjectAs(Object other, String name)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotBlank();
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
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(collection, "collection").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
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
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(collection, "collection").isNotNull();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
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
		if (fatalFailure)
			return getThis();
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
		if (fatalFailure)
			return getThis();
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
		if (fatalFailure)
			return getThis();
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
		if (fatalFailure)
			return getThis();
		if (actual != null)
			return getThis();
		ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
			this.name + " may not be null");
		addFailure(failure);
		fatalFailure = true;
		return getThis();
	}

	@Override
	public StringValidator asString()
	{
		String value = config.toString(actual);
		return new StringValidatorImpl(scope, config, name, value, failures, fatalFailure);
	}

	@Override
	public S asString(Consumer<StringValidator> consumer)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asString());
		return getThis();
	}

	/**
	 * Ensures that the actual value is equal to an expected value.
	 *
	 * @param expected the expected value
	 * @param equals   returns true if {@code actual} is equal to {@code expected}
	 * @return the updated validator
	 * @throws NullPointerException if {@code equals} is null
	 */
	protected S isEqualTo(Object expected, BiFunction<Object, Object, Boolean> equals)
	{
		if (fatalFailure)
			return getThis();
		if (!equals.apply(actual, expected))
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
	 * Ensures that the actual value is equal to the expected value.
	 *
	 * @param expected the expected value
	 * @param name     the name of the expected value
	 * @param equals   returns true if {@code actual} is equal to {@code expected}
	 * @return the updated validator
	 * @throws NullPointerException     if {@code name} or {@code equals} are null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	protected S isEqualTo(Object expected, String name, BiFunction<Object, Object, Boolean> equals)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotBlank();
		if (!equals.apply(actual, expected))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must be equal to " + name + ".").
				addContext(getContext(expected, false));
			addFailure(failure);
		}
		return getThis();
	}

	/**
	 * Ensures that the actual value is not equal to another value.
	 *
	 * @param other  the value to compare to
	 * @param equals returns true if {@code actual} is equal to {@code expected}
	 * @return the updated validator
	 * @throws NullPointerException if {@code equals} is null
	 */
	protected S isNotEqualTo(Object other, BiFunction<Object, Object, Boolean> equals)
	{
		if (fatalFailure)
			return getThis();
		if (equals.apply(actual, other))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not be equal to " + config.toString(other));
			addFailure(failure);
		}
		return getThis();
	}

	/**
	 * Ensures that the actual value is not equal to another variable.
	 *
	 * @param other  the value to compare to
	 * @param name   the name of {@code other}'s variable
	 * @param equals returns true if {@code actual} is equal to {@code expected}
	 * @return the updated validator
	 * @throws NullPointerException     if {@code name} or {@code equals} are null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	protected S isNotEqualTo(Object other, String name, BiFunction<Object, Object, Boolean> equals)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotBlank();
		if (equals.apply(actual, other))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " may not be equal to " + name + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @param length the length of the array or collection
	 * @return the updated validator
	 * @throws NullPointerException if {@code length} is null
	 */
	protected S isEmpty(Supplier<Integer> length)
	{
		if (fatalFailure)
			return getThis();
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (length.get() != 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be empty.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @param length the length of the array or collection
	 * @return the updated validator
	 * @throws NullPointerException if {@code length} is null
	 */
	protected S isNotEmpty(Supplier<Integer> length)
	{
		if (fatalFailure)
			return getThis();
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (length.get() == 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not be empty");
			addFailure(failure);
		}
		return getThis();
	}

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param <E>      the type of elements in the collection
	 * @param element  the element that must exist
	 * @param contains returns true if the array or collection contains {@code element}
	 * @return the updated validator
	 * @throws NullPointerException if {@code contains} is null
	 */
	protected <E> S contains(E element, Function<E, Boolean> contains)
	{
		if (fatalFailure)
			return getThis();
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (!contains.apply(element))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must contain " + element + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param <E>      the type of elements in the collection
	 * @param element  the element that must exist
	 * @param name     the name of the expected element
	 * @param contains returns true if the array or collection contains {@code element}
	 * @return the updated validator
	 * @throws NullPointerException     if {@code name} or {@code contains} are null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	protected <E> S contains(E element, String name, Function<E, Boolean> contains)
	{
		if (fatalFailure)
			return getThis();
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotBlank();
		if (!contains.apply(element))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must contain " + name + ".").
				addContext("Actual", actual).
				addContext("Missing", element);
			addFailure(failure);
		}
		return getThis();
	}

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param <E>      the type of elements in the collection
	 * @param element  the element that must not exist
	 * @param contains returns true if the array or collection contains {@code element}
	 * @return the updated validator
	 * @throws NullPointerException if {@code contains} is null
	 */
	protected <E> S doesNotContain(E element, Function<E, Boolean> contains)
	{
		if (fatalFailure)
			return getThis();
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (contains.apply(element))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " may not contain " + element + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param <E>      the type of elements in the collection
	 * @param element  the element that must not exist
	 * @param name     the name of the element
	 * @param contains returns true if the array or collection contains {@code element}
	 * @return the updated validator
	 * @throws NullPointerException     if {@code name} or {@code contains} are null
	 * @throws IllegalArgumentException if {@code name} is blank
	 */
	protected <E> S doesNotContain(E element, String name, Function<E, Boolean> contains)
	{
		if (fatalFailure)
			return getThis();
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotBlank();

		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return getThis();
		}
		if (contains.apply(element))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " may not contain " + name + ".").
				addContext("Actual", actual).
				addContext("Unwanted", element);
			addFailure(failure);
		}
		return getThis();
	}
}