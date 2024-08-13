package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.message.CollectionMessages;
import com.github.cowwoc.requirements10.java.internal.message.ComparableMessages;
import com.github.cowwoc.requirements10.java.internal.message.NumberMessages;
import com.github.cowwoc.requirements10.java.internal.message.ObjectMessages;
import com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.internal.util.Numbers;
import com.github.cowwoc.requirements10.java.internal.util.ObjectAndSize;
import com.github.cowwoc.requirements10.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;

import java.util.List;
import java.util.Map;

/**
 * Validates the state of an object's size.
 */
public final class ObjectSizeValidatorImpl
	extends AbstractPrimitiveValidator<PrimitiveUnsignedIntegerValidator, Integer>
	implements PrimitiveUnsignedIntegerValidator
{
	private final String objectName;
	private final MaybeUndefined<ObjectAndSize> objectAndSize;
	private final Pluralizer pluralizer;

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param objectName    the name of the object
	 * @param objectAndSize the object and its size
	 * @param sizeName      the name of the object's size
	 * @param pluralizer    the type of elements in the object
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public ObjectSizeValidatorImpl(ApplicationScope scope, Configuration configuration, String objectName,
		MaybeUndefined<ObjectAndSize> objectAndSize, String sizeName, Pluralizer pluralizer,
		Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, sizeName, objectAndSize.mapDefined(ObjectAndSize::size), context, failures);

		assert objectName != null;
		assert pluralizer != null;

		this.objectName = objectName;
		this.objectAndSize = objectAndSize;
		this.pluralizer = pluralizer;
	}

	@Override
	public int getValue()
	{
		return value.orThrow(VALUE_IS_UNDEFINED);
	}

	@Override
	public int getValueOrDefault(int defaultValue)
	{
		return value.orDefault(defaultValue);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isEqualTo(int expected)
	{
		return isEqualToImpl(expected, MaybeUndefined.undefined());
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isEqualTo(int expected, String name)
	{
		requireThatNameIsUnique(name);
		return isEqualToImpl(expected, MaybeUndefined.defined(name));
	}

	private PrimitiveUnsignedIntegerValidator isEqualToImpl(int expected, MaybeUndefined<String> name)
	{
		switch (value.test(value -> value != null && value == expected))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsSize(this, objectName, objectAndSize, "must contain", name, expected,
					pluralizer).toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotEqualTo(int unwanted)
	{
		return isNotEqualToImpl(unwanted, MaybeUndefined.undefined());
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotEqualTo(int unwanted, String name)
	{
		requireThatNameIsUnique(name);
		return isNotEqualToImpl(unwanted, MaybeUndefined.defined(name));
	}

	private PrimitiveUnsignedIntegerValidator isNotEqualToImpl(int unwanted, MaybeUndefined<String> name)
	{
		switch (value.test(value -> value != unwanted))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsSize(this, objectName, objectAndSize, "may not contain", name, unwanted,
					pluralizer).toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isZero()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value == 0))
		{
			case UNDEFINED, FALSE ->
			{
				MessageBuilder messageBuilder = ObjectMessages.isEmpty(this);
				objectAndSize.ifDefined(value -> messageBuilder.withContext(value.object(), objectName));
				addIllegalArgumentException(messageBuilder.toString());
			}
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotZero()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value != 0))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isNotEmpty(this).toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isPositive()
	{
		return isNotZero();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotPositive()
	{
		return isZero();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(Integer maximumExclusive)
	{
		return isLessThanImpl(maximumExclusive, MaybeUndefined.undefined());
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(Integer maximumExclusive, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(maximumExclusive, "maximumExclusive").isNotNull();
		return isLessThanImpl(maximumExclusive, MaybeUndefined.defined(name));
	}

	private PrimitiveUnsignedIntegerValidator isLessThanImpl(Integer maximumExclusive,
		MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value < maximumExclusive))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsSize(this, objectName, objectAndSize, "must contain less than", name,
					maximumExclusive, pluralizer).toString());
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(Integer maximumInclusive)
	{
		scope.getInternalValidators().requireThat(maximumInclusive, "maximumInclusive").isNotNull();
		return isLessThanOrEqualToImpl(maximumInclusive, MaybeUndefined.undefined());
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(Integer maximumInclusive, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(maximumInclusive, "maximumInclusive").isNotNull();
		return isLessThanOrEqualToImpl(maximumInclusive, MaybeUndefined.defined(name));
	}

	private PrimitiveUnsignedIntegerValidator isLessThanOrEqualToImpl(Integer maximumInclusive,
		MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value <= maximumInclusive))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsSize(this, objectName, objectAndSize, "may not contain more than", name,
					maximumInclusive, pluralizer).toString());
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(Integer minimumInclusive)
	{
		scope.getInternalValidators().requireThat(minimumInclusive, "minimumInclusive").isNotNull();
		return isGreaterThanOrEqualToImpl(minimumInclusive, MaybeUndefined.undefined());
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(Integer minimumInclusive, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(minimumInclusive, "minimumInclusive").isNotNull();
		return isGreaterThanOrEqualToImpl(minimumInclusive, MaybeUndefined.defined(name));
	}

	private PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualToImpl(Integer minimumInclusive,
		MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value >= minimumInclusive))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsSize(this, objectName, objectAndSize, "must contain at least", name,
					minimumInclusive, pluralizer).toString());
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(Integer minimumExclusive)
	{
		scope.getInternalValidators().requireThat(minimumExclusive, "minimumExclusive").isNotNull();
		return isGreaterThanImpl(minimumExclusive, MaybeUndefined.undefined());
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(Integer minimumExclusive, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(minimumExclusive, "minimumExclusive").isNotNull();
		return isGreaterThanImpl(minimumExclusive, MaybeUndefined.defined(name));
	}

	private PrimitiveUnsignedIntegerValidator isGreaterThanImpl(Integer minimumExclusive,
		MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value > minimumExclusive))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.containsSize(this, objectName, objectAndSize, "must contain more than", name,
					minimumExclusive, pluralizer).toString());
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(Integer minimumInclusive, Integer maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(Integer minimum, boolean minimumIsInclusive,
		Integer maximum, boolean maximumIsInclusive)
	{
		scope.getInternalValidators().requireThat(minimum, "minimum").isLessThanOrEqualTo(maximum, "maximum");
		if (value.isNull())
			onNull();
		switch (value.test(value ->
		{
			if (minimumIsInclusive)
			{
				if (value.compareTo(minimum) < 0)
					return false;
			}
			else if (value.compareTo(minimum) <= 0)
				return false;
			if (maximumIsInclusive)
				return value.compareTo(maximum) <= 0;
			return value.compareTo(maximum) < 0;
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				CollectionMessages.sizeIsBetween(this, objectName, objectAndSize, minimum, minimumIsInclusive,
					maximum, maximumIsInclusive, pluralizer).toString());
		}
		return self();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isMultipleOf(int factor)
	{
		return isMultipleOfImpl(factor, MaybeUndefined.undefined());
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isMultipleOf(int factor, String name)
	{
		requireThatNameIsUnique(name);
		return isMultipleOfImpl(factor, MaybeUndefined.defined(name));
	}

	private PrimitiveUnsignedIntegerValidator isMultipleOfImpl(int factor, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> Numbers.isMultipleOf(value, factor)))
		{
			case UNDEFINED, FALSE ->
			{
				MessageBuilder messageBuilder = NumberMessages.isMultipleOf(this, name, factor);
				objectAndSize.ifDefined(value -> messageBuilder.withContext(value.object(), objectName));
				addIllegalArgumentException(messageBuilder.toString());
			}
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotMultipleOf(int factor)
	{
		return isNotMultipleOfImpl(factor, MaybeUndefined.undefined());
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotMultipleOf(int factor, String name)
	{
		requireThatNameIsUnique(name);
		return isNotMultipleOfImpl(factor, MaybeUndefined.defined(name));
	}

	private PrimitiveUnsignedIntegerValidator isNotMultipleOfImpl(int factor, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !Numbers.isMultipleOf(value, factor)))
		{
			case UNDEFINED, FALSE ->
			{
				MessageBuilder messageBuilder = NumberMessages.isNotMultipleOf(this, name, factor);
				objectAndSize.ifDefined(value -> messageBuilder.withContext(value.object(), objectName));
				addIllegalArgumentException(messageBuilder.toString());
			}
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(int maximumExclusive)
	{
		return isLessThanImpl(maximumExclusive, MaybeUndefined.undefined());
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(int maximumExclusive, String name)
	{
		requireThatNameIsUnique(name);
		return isLessThanImpl(maximumExclusive, MaybeUndefined.defined(name));
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(int maximumInclusive)
	{
		return isLessThanOrEqualToImpl(maximumInclusive, MaybeUndefined.undefined());
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(int maximumInclusive, String name)
	{
		requireThatNameIsUnique(name);
		return isLessThanOrEqualToImpl(maximumInclusive, MaybeUndefined.defined(name));
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(int minimumInclusive)
	{
		return isGreaterThanOrEqualToImpl(minimumInclusive, MaybeUndefined.undefined());
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(int minimumInclusive, String name)
	{
		requireThatNameIsUnique(name);
		return isGreaterThanOrEqualToImpl(minimumInclusive, MaybeUndefined.defined(name));
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(int minimumExclusive)
	{
		return isGreaterThanImpl(minimumExclusive, MaybeUndefined.undefined());
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(int minimumExclusive, String name)
	{
		requireThatNameIsUnique(name);
		return isGreaterThanImpl(minimumExclusive, MaybeUndefined.defined(name));
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(int minimumInclusive, int maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(int minimum, boolean minimumIsInclusive, int maximum,
		boolean maximumIsInclusive)
	{
		scope.getInternalValidators().requireThat(minimum, "minimum").isLessThanOrEqualTo(maximum, "maximum");
		if (value.isNull())
			onNull();
		switch (value.test(value -> isBetween(value, minimum, minimumIsInclusive, maximum, maximumIsInclusive)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ComparableMessages.isBetween(this, minimum, minimumIsInclusive, maximum, maximumIsInclusive).
					toString());
		}
		return this;
	}

	private boolean isBetween(int value, int minimum, boolean minimumIsInclusive, int maximum,
		boolean maximumIsInclusive)
	{
		if (minimumIsInclusive)
		{
			if (value < minimum)
				return false;
		}
		else if (value <= minimum)
			return false;
		if (maximumIsInclusive)
			return value <= maximum;
		return value < maximum;
	}
}