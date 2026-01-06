/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.message;

import io.github.cowwoc.requirements13.java.internal.message.section.MessageBuilder;
import io.github.cowwoc.requirements13.java.internal.validator.AbstractValidator;

/**
 * Generates failure messages for numbers.
 */
public final class NumberMessages
{
	private NumberMessages()
	{
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNegativeFailed(AbstractValidator<?, ?> validator)
	{
		return ValidatorMessages.constraintFailed(validator, "must be negative");
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotNegativeFailed(AbstractValidator<?, ?> validator)
	{
		return ValidatorMessages.constraintFailed(validator, "may not be negative");
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isZeroFailed(AbstractValidator<?, ?> validator)
	{
		return ValidatorMessages.constraintFailed(validator, "must be zero");
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotZeroFailed(AbstractValidator<?, ?> validator)
	{
		return ValidatorMessages.constraintFailed(validator, "may not be zero");
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isPositiveFailed(AbstractValidator<?, ?> validator)
	{
		return ValidatorMessages.constraintFailed(validator, "must be positive");
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotPositiveFailed(AbstractValidator<?, ?> validator)
	{
		return ValidatorMessages.constraintFailed(validator, "may not be positive");
	}

	/**
	 * @param validator  the validator
	 * @param factorName the name of the factor ({@code null} if undefined)
	 * @param factor     the value being multiplied by
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isMultipleOfFailed(AbstractValidator<?, ?> validator, String factorName,
		Object factor)
	{
		return ComparableMessages.compareValues(validator, "must be a multiple of", factorName, factor);
	}

	/**
	 * @param validator  the validator
	 * @param factorName the name of the factor ({@code null} if undefined)
	 * @param factor     the value being multiplied by
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotMultipleOfFailed(AbstractValidator<?, ?> validator, String factorName,
		Object factor)
	{
		return ComparableMessages.compareValues(validator, "may not be a multiple of", factorName, factor);
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isWholeNumberFailed(AbstractValidator<?, ?> validator)
	{
		return ValidatorMessages.constraintFailed(validator, "must be a whole number");
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotWholeNumberFailed(AbstractValidator<?, ?> validator)
	{
		return ValidatorMessages.constraintFailed(validator, "may not be a whole number");
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNumberFailed(AbstractValidator<?, ?> validator)
	{
		return ValidatorMessages.constraintFailed(validator, "must be a well-defined number");
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isNotNumberFailed(AbstractValidator<?, ?> validator)
	{
		return ValidatorMessages.constraintFailed(validator, "may not be a well-defined number");
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isFiniteFailed(AbstractValidator<?, ?> validator)
	{
		return ValidatorMessages.constraintFailed(validator, "must be a finite number");
	}

	/**
	 * @param validator the validator
	 * @return a message for the validation failure
	 */
	public static MessageBuilder isInfiniteFailed(AbstractValidator<?, ?> validator)
	{
		return ValidatorMessages.constraintFailed(validator, "must be an infinite number");
	}
}