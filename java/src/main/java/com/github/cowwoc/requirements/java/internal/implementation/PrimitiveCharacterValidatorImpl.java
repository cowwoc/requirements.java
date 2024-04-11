package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.ComparableMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.EqualMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.PrimitiveCharacterValidator;

import java.util.List;
import java.util.Map;

public final class PrimitiveCharacterValidatorImpl extends AbstractValidator<PrimitiveCharacterValidator>
	implements PrimitiveCharacterValidator
{
	private final char value;

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
	public PrimitiveCharacterValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		char value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, context, failures);
		this.value = value;
	}

	@Override
	public char getValue()
	{
		return value;
	}

	@Override
	public char getValueOrDefault(char defaultValue)
	{
		if (hasFailed())
			return defaultValue;
		return value;
	}

	@Override
	public PrimitiveCharacterValidator isEqualTo(char expected)
	{
		return isEqualToImpl(expected, null);
	}

	@Override
	public PrimitiveCharacterValidator isEqualTo(char expected, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		return isEqualToImpl(expected, name);
	}

	private PrimitiveCharacterValidator isEqualToImpl(char expected, String name)
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
	public PrimitiveCharacterValidator isNotEqualTo(char unwanted)
	{
		return isNotEqualToImpl(unwanted, null);
	}

	@Override
	public PrimitiveCharacterValidator isNotEqualTo(char unwanted, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped();
		if (name.equals(this.name))
		{
			throw new IllegalArgumentException("\"name\" may not be equal to the same name as the value.\n" +
				"Actual: " + name);
		}
		return isNotEqualToImpl(unwanted, name);
	}

	private PrimitiveCharacterValidator isNotEqualToImpl(char unwanted, String name)
	{
		if (hasFailed() || value == unwanted)
		{
			addIllegalArgumentException(
				EqualMessages.isNotEqualTo(scope, this, this.name, name, unwanted).toString());
		}
		return this;
	}

	@Override
	public PrimitiveCharacterValidator isLessThan(char maximumExclusive)
	{
		return isLessThanImpl(maximumExclusive, null);
	}

	@Override
	public PrimitiveCharacterValidator isLessThan(char maximumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		return isLessThanImpl(maximumExclusive, name);
	}

	private PrimitiveCharacterValidator isLessThanImpl(char maximumExclusive, String name)
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
		return this;
	}

	@Override
	public PrimitiveCharacterValidator isLessThanOrEqualTo(char maximumInclusive)
	{
		return isLessThanOrEqualToImpl(maximumInclusive, null);
	}

	@Override
	public PrimitiveCharacterValidator isLessThanOrEqualTo(char maximumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		return isLessThanOrEqualToImpl(maximumInclusive, name);
	}

	private PrimitiveCharacterValidator isLessThanOrEqualToImpl(char maximumInclusive, String name)
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
	public PrimitiveCharacterValidator isGreaterThanOrEqualTo(char minimumInclusive)
	{
		return isGreaterThanOrEqualToImpl(minimumInclusive, null);
	}

	@Override
	public PrimitiveCharacterValidator isGreaterThanOrEqualTo(char minimumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isGreaterThanOrEqualToImpl(minimumInclusive, name);
	}

	private PrimitiveCharacterValidator isGreaterThanOrEqualToImpl(char minimumInclusive, String name)
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
	public PrimitiveCharacterValidator isGreaterThan(char minimumExclusive)
	{
		return isGreaterThanImpl(minimumExclusive, null);
	}

	@Override
	public PrimitiveCharacterValidator isGreaterThan(char minimumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isGreaterThanImpl(minimumExclusive, name);
	}

	private PrimitiveCharacterValidator isGreaterThanImpl(char minimumExclusive, String name)
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
	public PrimitiveCharacterValidator isBetween(char minimumInclusive, char maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	@Override
	public PrimitiveCharacterValidator isBetween(char minimum, boolean minimumInclusive, char maximum,
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

	private boolean minimumFailed(char minimum, boolean minimumInclusive)
	{
		if (minimumInclusive)
		{
			return value < minimum;
		}
		return value <= minimum;
	}

	private boolean maximumFailed(char maximum, boolean maximumInclusive)
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