package com.github.cowwoc.requirements10.java.internal.util;

import com.github.cowwoc.requirements10.java.GenericType;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A value that may be undefined: This class is not intended to replace {@code null} references but rather to
 * introduce an additional state where a nullable value may be undefined.
 *
 * @param <T> the type of the value
 */
public class MaybeUndefined<T>
{
	// DESIGN: Instead of using an Optional (where null denotes an undefined value), we chose to create a
	// new class. This enables us to call methods on potentially undefined values while avoiding IDE warnings
	// related to null Optional values and the use of Optional for class fields and method parameters.
	private static final MaybeUndefined<?> UNDEFINED = new MaybeUndefined<>(false, null);
	private final boolean defined;
	private final T value;

	/**
	 * Creates a value that may be undefined.
	 *
	 * @param defined {@code true} if the value is defined, or {@code false} if undefined
	 * @param value   the value
	 */
	public MaybeUndefined(boolean defined, T value)
	{
		this.defined = defined;
		this.value = value;
	}

	/**
	 * Returns an undefined value.
	 *
	 * @param <T> the type of the value
	 * @return an undefined value
	 */
	@SuppressWarnings("unchecked")
	public static <T> MaybeUndefined<T> undefined()
	{
		return (MaybeUndefined<T>) UNDEFINED;
	}

	/**
	 * Returns a well-defined value.
	 *
	 * @param <T>   the type of the value
	 * @param value a value
	 * @return a well-defined value
	 */
	public static <T> MaybeUndefined<T> defined(T value)
	{
		return new MaybeUndefined<>(true, value);
	}

	/**
	 * Returns the defined value, or a default value if undefined. A value of {@code null} does not hold any
	 * special significance. It does not imply that the value is undefined.
	 *
	 * @param defaultValue a value
	 * @return the defined value, or {@code defaultValue} if the value is undefined
	 */
	public T orDefault(T defaultValue)
	{
		if (defined)
			return value;
		return defaultValue;
	}

	/**
	 * Returns the defined value, or a default value if undefined. A value of {@code null} does not hold any
	 * special significance. It does not imply that the value is undefined.
	 *
	 * @param defaultValue a supplier that returns the default value
	 * @return the defined value, or {@code defaultValue} if the value is undefined
	 */
	public T orSuppliedDefault(Supplier<T> defaultValue)
	{
		if (defined)
			return value;
		return defaultValue.get();
	}

	/**
	 * Consumes the value if it is defined. If the value is undefined, no action is taken.
	 *
	 * @param consumer consumes the value if it is defined
	 */
	public void ifDefined(Consumer<? super T> consumer)
	{
		if (defined)
			consumer.accept(value);
	}

	/**
	 * Applies a function to the value if it is defined. If the value is undefined, no action is taken.
	 *
	 * @param <U>    the type of value returned by the mapper
	 * @param mapper the function to apply to the value if it is defined
	 * @return {@code this} if the value is undefined; otherwise, a {@code MaybeUndefined} instance wrapping the
	 * result of applying the mapper to the value
	 */
	public <U> MaybeUndefined<U> mapDefined(Function<? super T, ? extends U> mapper)
	{
		if (!defined)
			return self();
		U newValue = mapper.apply(value);
		if (newValue == value)
			return self();
		return defined(newValue);
	}

	/**
	 * Applies a function to the value if it is defined and not null. If the value is undefined or null, no
	 * action is taken.
	 *
	 * @param <U>    the type of value returned by the mapper
	 * @param mapper the function to apply to the value if it is defined and not null
	 * @return {@code this} if the value is undefined or null; otherwise, a {@code MaybeUndefined} instance
	 * wrapping the result of applying the mapper to the value
	 */
	public <U> MaybeUndefined<U> mapNotNull(Function<? super T, ? extends U> mapper)
	{
		if (!defined || value == null)
			return self();
		U newValue = mapper.apply(value);
		if (newValue == value)
			return self();
		return defined(newValue);
	}

	/**
	 * Converts a null value to an undefined value. If the value is undefined or non-null, no action is taken.
	 *
	 * @return an undefined value if the original value was null; otherwise, returns {@code this}
	 */
	public MaybeUndefined<T> nullToUndefined()
	{
		if (defined && value == null)
			return undefined();
		return this;
	}

	/**
	 * @param <U> the type to cast {@code this} to
	 * @return this
	 */
	private <U> U self()
	{
		@SuppressWarnings("unchecked")
		U temp = (U) this;
		return temp;
	}

	/**
	 * Returns the value or throws an exception if undefined.
	 *
	 * @param <U>               the type of exception to throw
	 * @param exceptionSupplier returns the exception to throw if the value is undefined
	 * @return the value
	 * @throws U if the value is undefined
	 */
	public <U extends Throwable> T orThrow(Supplier<? extends U> exceptionSupplier) throws U
	{
		if (defined)
			return value;
		throw exceptionSupplier.get();
	}

	/**
	 * Checks if the value is defined.
	 *
	 * @return {@code true} if the value is defined; {@code false} otherwise
	 */
	public boolean isDefined()
	{
		return defined;
	}

	/**
	 * Checks if the value is undefined.
	 *
	 * @return {@code true} if the value is undefined; {@code false} otherwise
	 */
	public boolean isUndefined()
	{
		return !defined;
	}

	/**
	 * Checks if the value is null.
	 *
	 * @return {@code true} if the value is null; {@code false} otherwise
	 */
	public boolean isNull()
	{
		return defined && value == null;
	}

	/**
	 * Evaluates a condition on the value. If the value is undefined, no action is taken.
	 *
	 * @param condition a condition
	 * @return a constant indicating whether the condition is true, false, or the value was undefined
	 */
	public ValidationResult test(Predicate<T> condition)
	{
		if (!defined)
			return ValidationResult.UNDEFINED;
		if (condition.test(value))
			return ValidationResult.TRUE;
		return ValidationResult.FALSE;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(defined, value);
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof MaybeUndefined<?> other && other.defined == defined &&
			Objects.equals(other.value, value);
	}

	@Override
	public String toString()
	{
		if (!defined)
			return "undefined";
		if (value == null)
			return "null";
		return GenericType.from(value.getClass()) + ": " + value;
	}

	/**
	 * The result of the validation.
	 */
	public enum ValidationResult
	{
		/**
		 * The value was undefined.
		 */
		UNDEFINED,
		/**
		 * The value matched the condition.
		 */
		TRUE,
		/**
		 * The value did not match the condition.
		 */
		FALSE
	}
}