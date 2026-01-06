/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.util;

import io.github.cowwoc.requirements13.java.GenericType;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Represents a value that is being validated.
 * <p>
 * This class is not intended to replace {@code null} references but rather to record additional information
 * alongside them.
 * <p>
 * Instead of throwing an exception when an {@code null} value is accessed, the system marks it as invalid and
 * continues to record validation failures.
 *
 * @param <T> the type of the value
 */
public class ValidationTarget<T>
{
	private static final ValidationTarget<?> INVALID = new ValidationTarget<>(false, null);
	private final boolean valid;
	private final T value;

	/**
	 * Creates a value that may be invalid.
	 *
	 * @param valid {@code true} if the value is valid, or {@code false} if invalid
	 * @param value the value
	 */
	public ValidationTarget(boolean valid, T value)
	{
		this.valid = valid;
		this.value = value;
	}

	/**
	 * Returns an invalid value.
	 *
	 * @param <T> the type of the value
	 * @return an invalid value
	 */
	@SuppressWarnings("unchecked")
	public static <T> ValidationTarget<T> invalid()
	{
		return (ValidationTarget<T>) INVALID;
	}

	/**
	 * Returns a valid value.
	 *
	 * @param <T>   the type of the value
	 * @param value a value
	 * @return a valid value
	 */
	public static <T> ValidationTarget<T> valid(T value)
	{
		return new ValidationTarget<>(true, value);
	}

	/**
	 * Returns the valid value, or a default value if invalid. A value of {@code null} does not hold any special
	 * significance. It does not imply that the value is invalid.
	 *
	 * @param defaultValue a value
	 * @return the valid value, or {@code defaultValue} if the value is invalid
	 */
	public T or(T defaultValue)
	{
		if (valid)
			return value;
		return defaultValue;
	}

	/**
	 * Returns the valid value, or a default value if invalid. A value of {@code null} does not hold any special
	 * significance. It does not imply that the value is invalid.
	 *
	 * @param defaultValue a supplier that returns the default value
	 * @return the valid value, or {@code defaultValue} if the value is invalid
	 */
	public T orGet(Supplier<T> defaultValue)
	{
		if (valid)
			return value;
		return defaultValue.get();
	}

	/**
	 * Consumes the value if it is valid. If the value is invalid, no action is taken.
	 *
	 * @param consumer consumes the value if it is valid
	 */
	public void ifValid(Consumer<? super T> consumer)
	{
		if (valid)
			consumer.accept(value);
	}

	/**
	 * Applies a function to the value if it is valid. If the value is invalid, no action is taken.
	 *
	 * @param <U>    the type of value returned by the mapper
	 * @param mapper the function to apply to the value if it is valid
	 * @return {@code this} if the value is invalid; otherwise, a {@code MaybeInvalid} instance wrapping the
	 * result of applying the mapper to the value
	 */
	public <U> ValidationTarget<U> map(Function<? super T, ? extends U> mapper)
	{
		if (!valid)
			return self();
		U newValue = mapper.apply(value);
		if (newValue == value)
			return self();
		return valid(newValue);
	}

	/**
	 * Converts a null value to an invalid value. If the value is invalid or non-{@code null}, no action is
	 * taken.
	 *
	 * @return an invalid value if the original value was null; otherwise, returns {@code this}
	 */
	public ValidationTarget<T> nullToInvalid()
	{
		if (valid && value == null)
			return invalid();
		return this;
	}

	/**
	 * @param <U> the type to cast {@code this} to
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	private <U> U self()
	{
		return (U) this;
	}

	/**
	 * Returns the value or throws an exception if invalid.
	 *
	 * @param <U>               the type of exception to throw
	 * @param exceptionSupplier returns the exception to throw if the value is invalid
	 * @return the value
	 * @throws U if the value is invalid
	 */
	public <U extends Throwable> T orThrow(Supplier<? extends U> exceptionSupplier) throws U
	{
		if (valid)
			return value;
		throw exceptionSupplier.get();
	}

	/**
	 * Checks if the value is valid.
	 *
	 * @return {@code true} if the value is valid; {@code false} otherwise
	 */
	public boolean isValid()
	{
		return valid;
	}

	/**
	 * Checks if the value is valid and equal to {@code null}.
	 *
	 * @return {@code true} if the value is valid and equal to {@code null}; {@code false} otherwise
	 */
	public boolean isNull()
	{
		return valid && value == null;
	}

	/**
	 * Evaluates a condition on the value.
	 *
	 * @param condition the condition to evaluate
	 * @return {@code true} if the value is invalid, {@code null}, or if the {@code condition} returns
	 * {@code false}; otherwise, returns {@code false}
	 */
	public boolean validationFailed(Predicate<T> condition)
	{
		return !valid || value == null || !condition.test(value);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(valid, value);
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof ValidationTarget<?> other && other.valid == valid &&
			Objects.equals(other.value, value);
	}

	@Override
	public String toString()
	{
		if (!valid)
			return "invalid";
		if (value == null)
			return "null";
		return GenericType.from(value.getClass()) + ": " + value;
	}
}