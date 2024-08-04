package com.github.cowwoc.requirements10.java.internal.message;

import com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator;

/**
 * Generates failure messages for numbers.
 */
public final class NumberMessages
{
	private NumberMessages()
	{
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isNegative(AbstractValidator<?, T> validator)
	{
		return Messages.constraint(validator, "must be negative");
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isNotNegative(AbstractValidator<?, T> validator)
	{
		return Messages.constraint(validator, "may not be negative");
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isZero(AbstractValidator<?, T> validator)
	{
		return Messages.constraint(validator, "must be zero");
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotZero(AbstractValidator<?, ?> validator)
	{
		return Messages.constraint(validator, "may not be zero");
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isPositive(AbstractValidator<?, T> validator)
	{
		return Messages.constraint(validator, "must be positive");
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isNotPositive(AbstractValidator<?, T> validator)
	{
		return Messages.constraint(validator, "may not be positive");
	}

	/**
	 * @param <T>        the type of the value
	 * @param validator  the validator
	 * @param factorName the name of the factor
	 * @param factor     the value being multiplied by
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isMultipleOf(AbstractValidator<?, T> validator,
		MaybeUndefined<String> factorName, Object factor)
	{
		return ComparableMessages.compareValues(validator, "must be a multiple of", factorName, factor);
	}

	/**
	 * @param <T>        the type of the value
	 * @param validator  the validator
	 * @param factorName the name of the factor
	 * @param factor     the value being multiplied by
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isNotMultipleOf(AbstractValidator<?, T> validator,
		MaybeUndefined<String> factorName, Object factor)
	{
		return ComparableMessages.compareValues(validator, "may not be a multiple of", factorName, factor);
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a failure message for this validation
	 */
	public static <T> MessageBuilder isWholeNumber(AbstractValidator<?, T> validator)
	{
		return Messages.constraint(validator, "must be a whole number");
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a failure message for this validation
	 */
	public static <T> MessageBuilder isNotWholeNumber(AbstractValidator<?, T> validator)
	{
		return Messages.constraint(validator, "may not be a whole number");
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a failure message for this validation
	 */
	public static <T> MessageBuilder isNumber(AbstractValidator<?, T> validator)
	{
		return Messages.constraint(validator, "must be a well-defined number");
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a failure message for this validation
	 */
	public static <T> MessageBuilder isNotNumber(AbstractValidator<?, T> validator)
	{
		return Messages.constraint(validator, "may not be a well-defined number");
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a failure message for this validation
	 */
	public static <T> MessageBuilder isFinite(AbstractValidator<?, T> validator)
	{
		return Messages.constraint(validator, "must be a finite number");
	}

	/**
	 * @param <T>       the type of the value
	 * @param validator the validator
	 * @return a failure message for this validation
	 */
	public static <T> MessageBuilder isInfinite(AbstractValidator<?, T> validator)
	{
		return Messages.constraint(validator, "must be an infinite number");
	}
}