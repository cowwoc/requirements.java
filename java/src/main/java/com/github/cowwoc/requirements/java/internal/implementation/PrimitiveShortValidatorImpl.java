package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.ComparableMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.EqualMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.NumberMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.PrimitiveShortValidator;

import java.util.List;
import java.util.Map;

public final class PrimitiveShortValidatorImpl extends AbstractValidator<PrimitiveShortValidator>
	implements PrimitiveShortValidator
{
	private final short value;

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
	public PrimitiveShortValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		short value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, context, failures);
		this.value = value;
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	private static boolean isMultipleOf(short number, short factor)
	{
		return factor != (short) 0 && (number == (short) 0 || (number % factor == 0));
	}

	@Override
	public short getValue()
	{
		return value;
	}

	@Override
	public short getValueOrDefault(short defaultValue)
	{
		if (hasFailed())
			return defaultValue;
		return value;
	}

	@Override
	public PrimitiveShortValidator isEqualTo(short expected)
	{
		return isEqualToImpl(expected, null);
	}

	@Override
	public PrimitiveShortValidator isEqualTo(short expected, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");

		return isEqualToImpl(expected, name);
	}

	private PrimitiveShortValidator isEqualToImpl(short expected, String name)
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
	public PrimitiveShortValidator isNotEqualTo(short unwanted)
	{
		return isNotEqualToImpl(unwanted, null);
	}

	@Override
	public PrimitiveShortValidator isNotEqualTo(short unwanted, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped();
		if (name.equals(this.name))
		{
			throw new IllegalArgumentException("\"name\" may not be equal to the name of the value.\n" +
			                                   "Actual: " + name);
		}
		return isNotEqualToImpl(unwanted, name);
	}

	private PrimitiveShortValidator isNotEqualToImpl(short unwanted, String name)
	{
		if (hasFailed() || value == unwanted)
		{
			addIllegalArgumentException(
				EqualMessages.isNotEqualTo(scope, this, this.name, name, unwanted).toString());
		}
		return this;
	}

	@Override
	public PrimitiveShortValidator isNegative()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNegative(scope, this, this.name, null).toString());
		}
		else if (value >= (short) 0)
		{
			addIllegalArgumentException(NumberMessages.isNegative(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveShortValidator isNotNegative()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(NumberMessages.isNotNegative(scope, this, this.name, null).toString());
		}
		else if (value < (short) 0)
		{
			addIllegalArgumentException(NumberMessages.isNotNegative(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveShortValidator isZero()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(NumberMessages.isZero(scope, this, this.name, null).toString());
		}
		else if (value != (short) 0)
		{
			addIllegalArgumentException(NumberMessages.isZero(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveShortValidator isNotZero()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(NumberMessages.isNotZero(scope, this, this.name).toString());
		}
		else if (value == (short) 0)
		{
			addIllegalArgumentException(NumberMessages.isNotZero(scope, this, this.name).toString());
		}
		return this;
	}

	@Override
	public PrimitiveShortValidator isPositive()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(NumberMessages.isPositive(scope, this, this.name, null).toString());
		}
		else if (value <= (short) 0)
		{
			addIllegalArgumentException(NumberMessages.isPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveShortValidator isNotPositive()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(NumberMessages.isNotPositive(scope, this, this.name, null).toString());
		}
		else if (value > (short) 0)
		{
			addIllegalArgumentException(NumberMessages.isNotPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveShortValidator isMultipleOf(short factor)
	{
		return isMultipleOfImpl(factor, null);
	}

	@Override
	public PrimitiveShortValidator isMultipleOf(short factor, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isMultipleOfImpl(factor, name);
	}

	private PrimitiveShortValidator isMultipleOfImpl(short factor, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(NumberMessages.isMultipleOf(scope, this, this.name, null, name, factor).toString())
			;
		}
		else if (!isMultipleOf(value, factor))
		{
			addIllegalArgumentException(NumberMessages.isMultipleOf(scope, this, this.name, value, name, factor).toString());
		}
		return this;
	}

	@Override
	public PrimitiveShortValidator isNotMultipleOf(short factor)
	{
		return isNotMultipleOfImpl(factor, null);
	}

	@Override
	public PrimitiveShortValidator isNotMultipleOf(short factor, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isNotMultipleOfImpl(factor, name);
	}

	private PrimitiveShortValidator isNotMultipleOfImpl(short factor, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(NumberMessages.isNotMultipleOf(scope, this, this.name, null, name, factor).toString())
			;
		}
		else if (isMultipleOf(value, factor))
		{
			addIllegalArgumentException(NumberMessages.isNotMultipleOf(scope, this, this.name, value, name,
				factor).toString());
		}
		return this;
	}

	@Override
	public PrimitiveShortValidator isLessThan(short maximumExclusive)
	{
		return isLessThanImpl(maximumExclusive, null);
	}

	@Override
	public PrimitiveShortValidator isLessThan(short maximumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isLessThanImpl(maximumExclusive, name);
	}

	private PrimitiveShortValidator isLessThanImpl(short maximumExclusive, String name)
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
	public PrimitiveShortValidator isLessThanOrEqualTo(short maximumInclusive)
	{
		return isLessThanOrEqualToImpl(maximumInclusive, null);
	}

	@Override
	public PrimitiveShortValidator isLessThanOrEqualTo(short maximumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isLessThanOrEqualToImpl(maximumInclusive, name);
	}

	private PrimitiveShortValidator isLessThanOrEqualToImpl(short maximumInclusive, String name)
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
				ComparableMessages.isLessThanOrEqualTo(scope, this, this.name, value, name,
					maximumInclusive).toString());
		}
		return this;
	}

	@Override
	public PrimitiveShortValidator isGreaterThanOrEqualTo(short minimumInclusive)
	{
		return isGreaterThanOrEqualToImpl(minimumInclusive, null);
	}

	@Override
	public PrimitiveShortValidator isGreaterThanOrEqualTo(short minimumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isGreaterThanOrEqualToImpl(minimumInclusive, name);
	}

	private PrimitiveShortValidator isGreaterThanOrEqualToImpl(short minimumInclusive, String name)
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
	public PrimitiveShortValidator isGreaterThan(short minimumExclusive)
	{
		return isGreaterThanImpl(minimumExclusive, null);
	}

	@Override
	public PrimitiveShortValidator isGreaterThan(short minimumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isGreaterThanImpl(minimumExclusive, name);
	}

	private PrimitiveShortValidator isGreaterThanImpl(short minimumExclusive, String name)
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
	public PrimitiveShortValidator isBetween(short minimumInclusive, short maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	@Override
	public PrimitiveShortValidator isBetween(short minimum, boolean minimumInclusive, short maximum,
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

	private boolean minimumFailed(short minimum, boolean minimumInclusive)
	{
		if (minimumInclusive)
		{
			return value < minimum;
		}
		return value <= minimum;
	}

	private boolean maximumFailed(short maximum, boolean maximumInclusive)
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
		return new StringValidatorImpl(scope, configuration, "String.valueOf(" + name + ")",
			String.valueOf(value), context, failures);
	}
}