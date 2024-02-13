package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.ComparableMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.EqualMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.NumberMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.PrimitiveDoubleValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PrimitiveDoubleValidatorImpl extends AbstractValidator<PrimitiveDoubleValidator>
	implements PrimitiveDoubleValidator
{
	private final double value;

	/**
	 * Creates a new validator as a result of a validation.
	 *
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public PrimitiveDoubleValidatorImpl(ApplicationScope scope, AbstractValidator<?> validator, String name,
		double value)
	{
		this(scope, validator.configuration(), name, value, validator.context, validator.failures);
	}

	/**
	 * Creates a new validator.
	 *
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public PrimitiveDoubleValidatorImpl(ApplicationScope scope, Configuration configuration,
		String name, double value)
	{
		this(scope, configuration, name, value, HashMap.newHashMap(2), new ArrayList<>(1));
	}

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	private PrimitiveDoubleValidatorImpl(ApplicationScope scope, Configuration configuration,
		String name, double value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, context, failures);
		this.value = value;
	}

	/**
	 * @param value a value
	 * @return true if {@code value} is a whole number
	 */
	private static boolean isWholeNumber(double value)
	{
		// Based on https://stackoverflow.com/a/9909417/14731
		return (value % 1) == 0;
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	private static boolean isMultipleOf(double number, double factor)
	{
		return factor != 0.0 && (number == 0.0 || (number % factor == 0));
	}

	@Override
	public double getValue()
	{
		return value;
	}

	@Override
	public PrimitiveDoubleValidator isEqualTo(double expected)
	{
		return isEqualToImpl(expected, null);
	}

	@Override
	public PrimitiveDoubleValidator isEqualTo(double expected, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");

		return isEqualToImpl(expected, name);
	}

	private PrimitiveDoubleValidator isEqualToImpl(double expected, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				EqualMessages.isEqualTo(scope, this, this.name, null, false, name, expected).toString());
		}
		else if (value != expected)
		{
			addIllegalArgumentException(
				EqualMessages.isEqualTo(scope, this, this.name, value, true, name, expected).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isNotEqualTo(double unwanted)
	{
		return isNotEqualToImpl(unwanted, null);
	}

	@Override
	public PrimitiveDoubleValidator isNotEqualTo(double unwanted, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped();
		if (name.equals(this.name))
		{
			throw new IllegalArgumentException("\"name\" may not be equal to the same name as the value.\n" +
			                                   "Actual: " + name);
		}
		return isNotEqualToImpl(unwanted, name);
	}

	private PrimitiveDoubleValidator isNotEqualToImpl(double unwanted, String name)
	{
		if (hasFailed() || value == unwanted)
		{
			addIllegalArgumentException(
				EqualMessages.isNotEqualTo(scope, this, this.name, name, unwanted).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isNegative()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNegative(scope, this, this.name, null).toString());
		}
		else if (value >= 0.0)
		{
			addIllegalArgumentException(
				NumberMessages.isNegative(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isNotNegative()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotNegative(scope, this, this.name, null).toString());
		}
		else if (value < 0.0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotNegative(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isZero()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isZero(scope, this, this.name, null).toString());
		}
		else if (value != 0.0)
		{
			addIllegalArgumentException(
				NumberMessages.isZero(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isNotZero()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotZero(scope, this, this.name).toString());
		}
		else if (value == 0.0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotZero(scope, this, this.name).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isPositive()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isPositive(scope, this, this.name, null).toString());
		}
		else if (value <= 0.0)
		{
			addIllegalArgumentException(
				NumberMessages.isPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isNotPositive()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotPositive(scope, this, this.name, null).toString());
		}
		else if (value > 0.0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isNumber()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNumber(scope, this, this.name, null).toString());
		}
		else if (Double.isNaN(value))
		{
			addIllegalArgumentException(
				NumberMessages.isNumber(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isNotNumber()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotNumber(scope, this, this.name, null).toString());
		}
		else if (!Double.isNaN(value))
		{
			addIllegalArgumentException(
				NumberMessages.isNotNumber(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isFinite()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isFinite(scope, this, this.name, null).toString());
		}
		else if (!Double.isFinite(value))
		{
			addIllegalArgumentException(
				NumberMessages.isFinite(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isInfinite()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isInfinite(scope, this, this.name, null).toString());
		}
		else if (!Double.isInfinite(value))
		{
			addIllegalArgumentException(
				NumberMessages.isInfinite(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isWholeNumber()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isWholeNumber(scope, this, this.name, null).toString());
		}
		else if (!isWholeNumber(value))
		{
			addIllegalArgumentException(
				NumberMessages.isWholeNumber(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isNotWholeNumber()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotWholeNumber(scope, this, this.name, null).toString());
		}
		else if (isWholeNumber(value))
		{
			addIllegalArgumentException(
				NumberMessages.isNotWholeNumber(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isMultipleOf(double factor)
	{
		return isMultipleOfImpl(factor, null);
	}

	@Override
	public PrimitiveDoubleValidator isMultipleOf(double factor, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isMultipleOfImpl(factor, name);
	}

	private PrimitiveDoubleValidator isMultipleOfImpl(double factor, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isMultipleOf(scope, this, this.name, null, name, factor).toString());
		}
		else if (!isMultipleOf(value, factor))
		{
			addIllegalArgumentException(
				NumberMessages.isMultipleOf(scope, this, this.name, value, name, factor).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isNotMultipleOf(double factor)
	{
		return isNotMultipleOfImpl(factor, null);
	}

	@Override
	public PrimitiveDoubleValidator isNotMultipleOf(double factor, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isNotMultipleOfImpl(factor, name);
	}

	private PrimitiveDoubleValidator isNotMultipleOfImpl(double factor, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotMultipleOf(scope, this, this.name, null, name, factor).toString());
		}
		else if (isMultipleOf(value, factor))
		{
			addIllegalArgumentException(
				NumberMessages.isNotMultipleOf(scope, this, this.name, value, name, factor).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isLessThan(double maximumExclusive)
	{
		return isLessThanImpl(maximumExclusive, null);
	}

	@Override
	public PrimitiveDoubleValidator isLessThan(double maximumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isLessThanImpl(maximumExclusive, name);
	}

	private PrimitiveDoubleValidator isLessThanImpl(double maximumExclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThan(scope, this, this.name, null, name, maximumExclusive).
					toString());
		}
		else if (value >= maximumExclusive)
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThan(scope, this, this.name, value, name, maximumExclusive).
					toString());
		}
		return self();
	}

	@Override
	public PrimitiveDoubleValidator isLessThanOrEqualTo(double maximumInclusive)
	{
		return isLessThanOrEqualToImpl(maximumInclusive, null);
	}

	@Override
	public PrimitiveDoubleValidator isLessThanOrEqualTo(double maximumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isLessThanOrEqualToImpl(maximumInclusive, name);
	}

	private PrimitiveDoubleValidator isLessThanOrEqualToImpl(double maximumInclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThanOrEqualTo(scope, this, this.name, null, name,
					maximumInclusive).toString());
		}
		else if (value > maximumInclusive)
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThanOrEqualTo(scope, this, this.name, value, name, maximumInclusive).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isGreaterThanOrEqualTo(double minimumInclusive)
	{
		return isGreaterThanOrEqualToImpl(minimumInclusive, null);
	}

	@Override
	public PrimitiveDoubleValidator isGreaterThanOrEqualTo(double minimumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isGreaterThanOrEqualToImpl(minimumInclusive, name);
	}

	private PrimitiveDoubleValidator isGreaterThanOrEqualToImpl(double minimumInclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThanOrEqualTo(scope, this, this.name, null, name,
					minimumInclusive).toString());
		}
		else if (value < minimumInclusive)
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThanOrEqualTo(scope, this, this.name, value, name,
					minimumInclusive).toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isGreaterThan(double minimumExclusive)
	{
		return isGreaterThanImpl(minimumExclusive, null);
	}

	@Override
	public PrimitiveDoubleValidator isGreaterThan(double minimumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isGreaterThanImpl(minimumExclusive, name);
	}

	private PrimitiveDoubleValidator isGreaterThanImpl(double minimumExclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThan(scope, this, this.name, null, name, minimumExclusive).
					toString());
		}
		else if (value <= minimumExclusive)
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThan(scope, this, this.name, value, name, minimumExclusive).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveDoubleValidator isBetween(double minimumInclusive, double maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	@Override
	public PrimitiveDoubleValidator isBetween(double minimum, boolean minimumInclusive, double maximum,
		boolean maximumInclusive)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isBetween(scope, this, this.name, null, minimum, minimumInclusive,
					maximum, maximumInclusive, null).toString());
		}
		else if (minimumFailed(minimum, minimumInclusive) || maximumFailed(maximum, maximumInclusive))
		{
			addIllegalArgumentException(
				ComparableMessages.isBetween(scope, this, this.name, value, minimum, minimumInclusive,
					maximum, maximumInclusive, null).toString());
		}
		return this;
	}

	private boolean minimumFailed(double minimum, boolean minimumInclusive)
	{
		if (minimumInclusive)
		{
			return value < minimum;
		}
		return value <= minimum;
	}

	private boolean maximumFailed(double maximum, boolean maximumInclusive)
	{
		if (maximumInclusive)
		{
			return value > maximum;
		}
		return value >= maximum;
	}

	@Override
	public StringValidatorImpl asString()
	{
		return new StringValidatorImpl(scope, this, "String.valueOf(" + name + ")", String.valueOf(value));
	}
}