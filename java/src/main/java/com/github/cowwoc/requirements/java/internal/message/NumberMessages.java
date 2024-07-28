package com.github.cowwoc.requirements.java.internal.message;

import com.github.cowwoc.requirements.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements.java.internal.validator.AbstractValidator;

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
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isNegative(ApplicationScope scope, AbstractValidator<?, T> validator)
	{
		return Messages.constraint(scope, validator, "must be negative");
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isNotNegative(ApplicationScope scope, AbstractValidator<?, T> validator)
	{
		return Messages.constraint(scope, validator, "may not be negative");
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isZero(ApplicationScope scope, AbstractValidator<?, T> validator)
	{
		return Messages.constraint(scope, validator, "must be zero");
	}

	/**
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotZero(ApplicationScope scope, AbstractValidator<?, ?> validator)
	{
		return Messages.constraint(scope, validator, "may not be zero");
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isPositive(ApplicationScope scope, AbstractValidator<?, T> validator)
	{
		return Messages.constraint(scope, validator, "must be positive");
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isNotPositive(ApplicationScope scope, AbstractValidator<?, T> validator)
	{
		return Messages.constraint(scope, validator, "may not be positive");
	}

	/**
	 * @param <T>        the type of the value
	 * @param scope      the application configuration
	 * @param validator  the validator
	 * @param factorName the name of the factor
	 * @param factor     the value being multiplied by
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isMultipleOf(ApplicationScope scope, AbstractValidator<?, T> validator,
		MaybeUndefined<String> factorName, Object factor)
	{
		return ComparableMessages.compareValues(scope, validator, "must be a multiple of", factorName, factor);
	}

	/**
	 * @param <T>        the type of the value
	 * @param scope      the application configuration
	 * @param validator  the validator
	 * @param factorName the name of the factor
	 * @param factor     the value being multiplied by
	 * @return a message for the validation failure
	 */
	public static <T> MessageBuilder isNotMultipleOf(ApplicationScope scope, AbstractValidator<?, T> validator,
		MaybeUndefined<String> factorName, Object factor)
	{
		return ComparableMessages.compareValues(scope, validator, "may not be a multiple of", factorName, factor);
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a failure message for this validation
	 */
	public static <T> MessageBuilder isWholeNumber(ApplicationScope scope, AbstractValidator<?, T> validator)
	{
		return Messages.constraint(scope, validator, "must be a whole number");
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a failure message for this validation
	 */
	public static <T> MessageBuilder isNotWholeNumber(ApplicationScope scope, AbstractValidator<?, T> validator)
	{
		return Messages.constraint(scope, validator, "may not be a whole number");
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a failure message for this validation
	 */
	public static <T> MessageBuilder isNumber(ApplicationScope scope, AbstractValidator<?, T> validator)
	{
		return Messages.constraint(scope, validator, "must be a well-defined number");
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a failure message for this validation
	 */
	public static <T> MessageBuilder isNotNumber(ApplicationScope scope, AbstractValidator<?, T> validator)
	{
		return Messages.constraint(scope, validator, "may not be a well-defined number");
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a failure message for this validation
	 */
	public static <T> MessageBuilder isFinite(ApplicationScope scope, AbstractValidator<?, T> validator)
	{
		return Messages.constraint(scope, validator, "must be a finite number");
	}

	/**
	 * @param <T>       the type of the value
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @return a failure message for this validation
	 */
	public static <T> MessageBuilder isInfinite(ApplicationScope scope, AbstractValidator<?, T> validator)
	{
		return Messages.constraint(scope, validator, "must be an infinite number");
	}
}