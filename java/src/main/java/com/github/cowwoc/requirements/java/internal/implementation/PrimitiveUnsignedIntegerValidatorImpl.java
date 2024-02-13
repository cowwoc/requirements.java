package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.ComparableMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.NumberMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements.java.type.PrimitiveUnsignedIntegerValidator;

import java.util.List;
import java.util.Map;

public final class PrimitiveUnsignedIntegerValidatorImpl extends AbstractValidator<PrimitiveUnsignedIntegerValidator>
	implements PrimitiveUnsignedIntegerValidator
{
	private final int value;
	private final String nameOfParent;
	private final Object parent;
	private final Pluralizer pluralizer;

	/**
	 * Creates a new validator as a result of a validation.
	 *
	 * @param scope      the application configuration
	 * @param validator  the validator
	 * @param name       the name of the value
	 * @param value      (optional) the value
	 * @param parent     the object that contains this value
	 * @param pluralizer (optional) the type of items in the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public PrimitiveUnsignedIntegerValidatorImpl(ApplicationScope scope, AbstractValidator<?> validator,
		String name, int value, Object parent, Pluralizer pluralizer)
	{
		this(scope, validator.configuration(), name, value, validator.name, parent, pluralizer, validator.context,
			validator.failures);
	}

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param nameOfValue   the name of the value
	 * @param value         (optional) the value
	 * @param nameOfParent  the name of the object that contains this value
	 * @param parent        the object that contains this value
	 * @param pluralizer    (optional) the type of items in the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	private PrimitiveUnsignedIntegerValidatorImpl(ApplicationScope scope, Configuration configuration,
		String nameOfValue, int value, String nameOfParent, Object parent, Pluralizer pluralizer,
		Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, nameOfValue, context, failures);
		this.value = value;

		assert nameOfParent != null;
		assert hasFailed() || parent != null : "hasFailed: " + hasFailed() + ", parent: " + parent;
		this.nameOfParent = nameOfParent;
		this.parent = parent;
		this.pluralizer = pluralizer;
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	private static boolean isMultipleOf(int number, int factor)
	{
		return factor != 0 && (number == 0 || (number % factor == 0));
	}

	@Override
	public int getValue()
	{
		return value;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isEqualTo(int expected)
	{
		return isEqualToImpl(expected, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isEqualTo(int expected, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isEqualToImpl(expected, name);
	}

	private PrimitiveUnsignedIntegerValidator isEqualToImpl(int expected, String name)
	{
		if (hasFailed())
		{
			String operation;
			String nameOfValue;
			if (pluralizer == null)
			{
				operation = "must be equal to";
				nameOfValue = this.name;
			}
			else
			{
				operation = "must contain";
				nameOfValue = nameOfParent;
			}
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this, nameOfValue, null, operation, name, expected,
					pluralizer).toString());
		}
		else if (value != expected)
		{
			String operation;
			String nameOfValue;
			if (pluralizer == null)
			{
				operation = "must be equal to";
				nameOfValue = this.name;
			}
			else
			{
				operation = "must contain";
				nameOfValue = nameOfParent;
			}
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this, nameOfValue, value, operation, name, expected,
						pluralizer).
					addContext(parent, nameOfParent).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotEqualTo(int unwanted)
	{
		return isNotEqualToImpl(unwanted, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotEqualTo(int unwanted, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped();
		if (name.equals(this.name))
		{
			throw new IllegalArgumentException("\"name\" may not be equal to the same name as the value.\n" +
			                                   "Actual: " + name);
		}
		return isNotEqualToImpl(unwanted, name);
	}

	private PrimitiveUnsignedIntegerValidator isNotEqualToImpl(int unwanted, String name)
	{
		if (hasFailed())
		{
			String operation;
			String nameOfValue;
			if (pluralizer == null)
			{
				operation = "may not be equal to";
				nameOfValue = this.name;
			}
			else
			{
				operation = "may not contain";
				nameOfValue = nameOfParent;
			}
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this, nameOfValue, null, operation, name, unwanted,
						pluralizer).
					toString());
		}
		else if (value == unwanted)
		{
			String operation;
			String nameOfValue;
			if (pluralizer == null)
			{
				operation = "may not be equal to";
				nameOfValue = this.name;
			}
			else
			{
				operation = "may not contain";
				nameOfValue = nameOfParent;
			}
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this, nameOfValue, null, operation, name, unwanted,
						pluralizer).
					addContext(parent, nameOfParent).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidatorImpl isNegative()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNegative(scope, this, this.name, null).
					addContext(parent, nameOfParent).
					toString());
		}
		else if (value >= 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNegative(scope, this, this.name, value).
					addContext(parent, nameOfParent).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidatorImpl isNotNegative()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotNegative(scope, this, this.name, null).
					addContext(parent, nameOfParent).
					toString());
		}
		else if (value < 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotNegative(scope, this, this.name, value).
					addContext(parent, nameOfParent).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidatorImpl isZero()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isZero(scope, this, this.name, null).
					addContext(parent, nameOfParent).
					toString());
		}
		else if (value != 0)
		{
			addIllegalArgumentException(
				NumberMessages.isZero(scope, this, this.name, value).
					addContext(parent, nameOfParent).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidatorImpl isNotZero()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotZero(scope, this, this.name).
					addContext(parent, nameOfParent).
					toString());
		}
		else if (value == 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotZero(scope, this, this.name).
					addContext(parent, nameOfParent).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidatorImpl isPositive()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isPositive(scope, this, this.name, null).
					addContext(parent, nameOfParent).
					toString());
		}
		else if (value <= 0)
		{
			addIllegalArgumentException(
				NumberMessages.isPositive(scope, this, this.name, value).
					addContext(parent, nameOfParent).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidatorImpl isNotPositive()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotPositive(scope, this, this.name, null).
					addContext(parent, nameOfParent).
					toString());
		}
		else if (value <= 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotPositive(scope, this, this.name, value).
					addContext(parent, nameOfParent).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidatorImpl isMultipleOf(int factor)
	{
		return isMultipleOfImpl(factor, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidatorImpl isMultipleOf(int factor, String name)
	{
		scope.getInternalValidator().requireThat("name", name).isStripped();
		return isMultipleOfImpl(factor, name);
	}

	private PrimitiveUnsignedIntegerValidatorImpl isMultipleOfImpl(int factor, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isMultipleOf(scope, this, this.name, null, name, factor).
					addContext(parent, nameOfParent).
					toString());
		}
		else if (!isMultipleOf(value, factor))
		{
			addIllegalArgumentException(
				NumberMessages.isMultipleOf(scope, this, this.name, value, name, factor).
					addContext(parent, nameOfParent).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidatorImpl isNotMultipleOf(int factor)
	{
		return isNotMultipleOfImpl(factor, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidatorImpl isNotMultipleOf(int factor, String name)
	{
		scope.getInternalValidator().requireThat("name", name).isStripped();
		return isNotMultipleOfImpl(factor, name);
	}

	private PrimitiveUnsignedIntegerValidatorImpl isNotMultipleOfImpl(int factor, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotMultipleOf(scope, this, this.name, null, name, factor).
					addContext(parent, nameOfParent).
					toString());
		}
		else if (isMultipleOf(value, factor))
		{
			addIllegalArgumentException(
				NumberMessages.isNotMultipleOf(scope, this, this.name, value, name, factor).
					addContext(parent, nameOfParent).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(int maximumExclusive)
	{
		return isLessThanImpl(maximumExclusive, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(int maximumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isLessThanImpl(maximumExclusive, name);
	}

	private PrimitiveUnsignedIntegerValidator isLessThanImpl(int maximumExclusive, String name)
	{
		if (hasFailed())
		{
			String operation;
			if (pluralizer == null)
				operation = "must be less than";
			else
				operation = "must contain less than";
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this, this.name, null, operation, name,
						maximumExclusive, pluralizer).
					addContext(parent, nameOfParent).
					toString());
		}
		else if (value >= maximumExclusive)
		{
			String operation;
			if (pluralizer == null)
				operation = "must be less than";
			else
				operation = "must contain less than";
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this, this.name, value, operation, name,
						maximumExclusive, pluralizer).
					addContext(parent, nameOfParent).
					addContext(value, "Actual").
					toString());
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(int maximumInclusive)
	{
		return isLessThanOrEqualToImpl(maximumInclusive, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(int maximumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isLessThanOrEqualToImpl(maximumInclusive, name);
	}

	private PrimitiveUnsignedIntegerValidator isLessThanOrEqualToImpl(int maximumInclusive, String name)
	{
		if (hasFailed())
		{
			String operation;
			if (pluralizer == null)
				operation = "must be less than or equal to";
			else
				operation = "must contain at most";
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this, this.name, null, operation, name,
						maximumInclusive, pluralizer).
					addContext(parent, nameOfParent).
					toString());
		}
		else if (value > maximumInclusive)
		{
			String operation;
			if (pluralizer == null)
				operation = "must be less than or equal to";
			else
				operation = "must contain at most";
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this, this.name, value, operation, name,
						maximumInclusive, pluralizer).
					addContext(parent, nameOfParent).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(int minimumInclusive)
	{
		return isGreaterThanOrEqualToImpl(minimumInclusive, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(int minimumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isGreaterThanOrEqualToImpl(minimumInclusive, name);
	}

	private PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualToImpl(int minimumInclusive, String name)
	{
		if (hasFailed())
		{
			String operation;
			if (pluralizer == null)
				operation = "must be greater than or equal to";
			else
				operation = "must contain at least";
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this, this.name, null, operation, name,
						minimumInclusive, pluralizer).
					addContext(parent, nameOfParent).
					toString());
		}
		else if (value < minimumInclusive)
		{
			String operation;
			if (pluralizer == null)
				operation = "must be greater than or equal to";
			else
				operation = "must contain at least";
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this, this.name, value, operation, name,
						minimumInclusive, pluralizer).
					addContext(parent, nameOfParent).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(int minimumExclusive)
	{
		return isGreaterThanImpl(minimumExclusive, null);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(int minimumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the name of the value");
		return isGreaterThanImpl(minimumExclusive, name);
	}

	private PrimitiveUnsignedIntegerValidator isGreaterThanImpl(int minimumExclusive, String name)
	{
		if (hasFailed())
		{
			String operation;
			if (pluralizer == null)
				operation = "must be greater than";
			else
				operation = "must contain more than";
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this, this.name, null, operation, name,
						minimumExclusive, pluralizer).
					addContext(parent, nameOfParent).
					toString());
		}
		else if (value < minimumExclusive)
		{
			String operation;
			if (pluralizer == null)
				operation = "must be greater than";
			else
				operation = "must contain more than";
			addIllegalArgumentException(
				ComparableMessages.getExpectedVsActual(scope, this, this.name, value, operation, name,
						minimumExclusive, pluralizer).
					addContext(parent, nameOfParent).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(int minimumInclusive, int maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(int minimum, boolean minimumInclusive, int maximum,
		boolean maximumInclusive)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isBetween(scope, this, this.name, null, minimum, minimumInclusive,
						maximum, maximumInclusive, pluralizer).
					addContext(parent, nameOfParent).
					toString());
		}
		else if (minimumFailed(minimum, minimumInclusive) || maximumFailed(maximum, maximumInclusive))
		{
			addIllegalArgumentException(
				ComparableMessages.isBetween(scope, this, this.name, value, minimum, minimumInclusive,
						maximum, maximumInclusive, pluralizer).
					addContext(parent, nameOfParent).
					toString());
		}
		return this;
	}

	private boolean minimumFailed(int minimum, boolean minimumInclusive)
	{
		if (minimumInclusive)
			return value < minimum;
		return value <= minimum;
	}

	private boolean maximumFailed(int maximum, boolean maximumInclusive)
	{
		if (maximumInclusive)
			return value > maximum;
		return value >= maximum;
	}

	@Override
	public StringValidatorImpl asString()
	{
		return new StringValidatorImpl(scope, this, name, String.valueOf(value));
	}
}