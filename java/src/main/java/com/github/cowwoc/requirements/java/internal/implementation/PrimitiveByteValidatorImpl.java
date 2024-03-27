package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.ComparableMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.EqualMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.NumberMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.PrimitiveByteValidator;

import java.util.List;
import java.util.Map;

public final class PrimitiveByteValidatorImpl extends AbstractValidator<PrimitiveByteValidator>
	implements PrimitiveByteValidator
{
	private final byte value;

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
	public PrimitiveByteValidatorImpl(ApplicationScope scope, Configuration configuration,
		String name, byte value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, context, failures);
		this.value = value;
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	private static boolean isMultipleOf(byte number, byte factor)
	{
		return factor != (byte) 0 && (number == (byte) 0 || (number % factor == 0));
	}

	@Override
	public byte getValue()
	{
		return value;
	}

	@Override
	public byte getValueOrDefault(byte defaultValue)
	{
		if (hasFailed())
			return defaultValue;
		return value;
	}

	@Override
	public PrimitiveByteValidator isEqualTo(byte expected)
	{
		return isEqualToImpl(expected, null);
	}

	@Override
	public PrimitiveByteValidator isEqualTo(byte expected, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		return isEqualToImpl(expected, name);
	}

	private PrimitiveByteValidator isEqualToImpl(byte expected, String name)
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
	public PrimitiveByteValidator isNotEqualTo(byte unwanted)
	{
		return isNotEqualToImpl(unwanted, null);
	}

	@Override
	public PrimitiveByteValidator isNotEqualTo(byte unwanted, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped();
		if (name.equals(this.name))
		{
			throw new IllegalArgumentException("\"name\" may not be equal to the same name as the value.\n" +
			                                   "Actual: " + name);
		}
		return isNotEqualToImpl(unwanted, name);
	}

	private PrimitiveByteValidator isNotEqualToImpl(byte unwanted, String name)
	{
		if (hasFailed() || value == unwanted)
		{
			addIllegalArgumentException(
				EqualMessages.isNotEqualTo(scope, this, this.name, name, unwanted).toString());
		}
		return this;
	}

	@Override
	public PrimitiveByteValidator isNegative()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNegative(scope, this, this.name, null).toString());
		}
		else if (value >= (byte) 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNegative(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveByteValidator isNotNegative()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotNegative(scope, this, this.name, null).toString());
		}
		else if (value < (byte) 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotNegative(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveByteValidator isZero()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isZero(scope, this, this.name, null).toString());
		}
		else if (value != (byte) 0)
		{
			addIllegalArgumentException(
				NumberMessages.isZero(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveByteValidator isNotZero()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotZero(scope, this, this.name).toString());
		}
		else if (value == (byte) 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotZero(scope, this, this.name).toString());
		}
		return this;
	}

	@Override
	public PrimitiveByteValidator isPositive()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isPositive(scope, this, this.name, null).toString());
		}
		else if (value <= (byte) 0)
		{
			addIllegalArgumentException(
				NumberMessages.isPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveByteValidator isNotPositive()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotPositive(scope, this, this.name, null).toString());
		}
		else if (value > (byte) 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveByteValidator isMultipleOf(byte factor)
	{
		return isMultipleOfImpl(factor, null);
	}

	@Override
	public PrimitiveByteValidator isMultipleOf(byte factor, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		return isMultipleOfImpl(factor, name);
	}

	private PrimitiveByteValidator isMultipleOfImpl(byte factor, String name)
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
	public PrimitiveByteValidator isNotMultipleOf(byte factor)
	{
		return isNotMultipleOfImpl(factor, null);
	}

	@Override
	public PrimitiveByteValidator isNotMultipleOf(byte factor, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		return isNotMultipleOfImpl(factor, name);
	}

	private PrimitiveByteValidator isNotMultipleOfImpl(byte factor, String name)
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
	public PrimitiveByteValidator isLessThan(byte maximumExclusive)
	{
		return isLessThanImpl(maximumExclusive, null);
	}

	@Override
	public PrimitiveByteValidator isLessThan(byte maximumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isLessThanImpl(maximumExclusive, name);
	}

	private PrimitiveByteValidator isLessThanImpl(byte maximumExclusive, String name)
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
	public PrimitiveByteValidator isLessThanOrEqualTo(byte maximumInclusive)
	{
		return isLessThanOrEqualToImpl(maximumInclusive, null);
	}

	@Override
	public PrimitiveByteValidator isLessThanOrEqualTo(byte maximumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isLessThanOrEqualToImpl(maximumInclusive, name);
	}

	private PrimitiveByteValidator isLessThanOrEqualToImpl(byte maximumInclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThanOrEqualTo(scope, this, this.name, null, name, maximumInclusive).
					toString());
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
	public PrimitiveByteValidator isGreaterThanOrEqualTo(byte minimumInclusive)
	{
		return isGreaterThanOrEqualToImpl(minimumInclusive, null);
	}

	@Override
	public PrimitiveByteValidator isGreaterThanOrEqualTo(byte minimumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isGreaterThanOrEqualToImpl(minimumInclusive, name);
	}

	private PrimitiveByteValidator isGreaterThanOrEqualToImpl(byte minimumInclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThanOrEqualTo(scope, this, this.name, null, name, minimumInclusive).
					toString());
		}
		else if (value < minimumInclusive)
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThanOrEqualTo(scope, this, this.name, value, name, minimumInclusive).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveByteValidator isGreaterThan(byte minimumExclusive)
	{
		return isGreaterThanImpl(minimumExclusive, null);
	}

	@Override
	public PrimitiveByteValidator isGreaterThan(byte minimumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isGreaterThanImpl(minimumExclusive, name);
	}

	private PrimitiveByteValidator isGreaterThanImpl(byte minimumExclusive, String name)
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
	public PrimitiveByteValidator isBetween(byte minimumInclusive, byte maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	@Override
	public PrimitiveByteValidator isBetween(byte minimum, boolean minimumInclusive, byte maximum,
		boolean maximumInclusive)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isBetween(scope, this, this.name, null, minimum, minimumInclusive, maximum,
					maximumInclusive, null).toString());
		}
		else if (minimumFailed(minimum, minimumInclusive) || maximumFailed(maximum, maximumInclusive))
		{
			addIllegalArgumentException(
				ComparableMessages.isBetween(scope, this, this.name, value, minimum, minimumInclusive, maximum,
					maximumInclusive, null).toString());
		}
		return this;
	}

	private boolean minimumFailed(byte minimum, boolean minimumInclusive)
	{
		if (minimumInclusive)
		{
			return value < minimum;
		}
		return value <= minimum;
	}

	private boolean maximumFailed(byte maximum, boolean maximumInclusive)
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