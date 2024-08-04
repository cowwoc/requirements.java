package com.github.cowwoc.requirements10.java.internal.validator.noop;

import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;
import com.github.cowwoc.requirements10.java.validator.StringValidator;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator.VALUE_IS_UNDEFINED;

public final class PrimitiveUnsignedIntegerValidatorNoOp
	implements PrimitiveUnsignedIntegerValidator
{
	private static final PrimitiveUnsignedIntegerValidatorNoOp INSTANCE =
		new PrimitiveUnsignedIntegerValidatorNoOp();

	public static PrimitiveUnsignedIntegerValidator getInstance()
	{
		return INSTANCE;
	}

	private PrimitiveUnsignedIntegerValidatorNoOp()
	{
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(Integer maximumExclusive)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(Integer maximumExclusive, String name)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(Integer maximumInclusive)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(Integer maximumInclusive, String name)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(Integer minimumInclusive)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(Integer minimumInclusive, String name)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(Integer minimumExclusive)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(Integer minimumExclusive, String name)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(Integer minimumInclusive, Integer maximumExclusive)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(Integer minimum, boolean minimumInclusive,
		Integer maximum, boolean maximumInclusive)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isMultipleOf(int factor)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isMultipleOf(int factor, String name)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotMultipleOf(int factor)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotMultipleOf(int factor, String name)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(int maximumExclusive)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThan(int maximumExclusive, String name)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(int maximumInclusive)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isLessThanOrEqualTo(int maximumInclusive, String name)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(int minimumInclusive)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThanOrEqualTo(int minimumInclusive, String name)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(int minimumExclusive)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isGreaterThan(int minimumExclusive, String name)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(int minimumInclusive, int maximumExclusive)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isBetween(int minimum, boolean minimumInclusive, int maximum,
		boolean maximumInclusive)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isPositive()
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotPositive()
	{
		return this;
	}

	@Override
	public int getValue()
	{
		throw VALUE_IS_UNDEFINED.get();
	}

	@Override
	public int getValueOrDefault(int defaultValue)
	{
		return defaultValue;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isEqualTo(int expected)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isEqualTo(int expected, String name)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotEqualTo(int unwanted)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotEqualTo(int unwanted, String name)
	{
		return this;
	}

	@Override
	public String getName()
	{
		return "";
	}

	@Override
	public Integer getValueOrDefault(Integer defaultValue)
	{
		return 0;
	}

	@Override
	public Configuration configuration()
	{
		return Configuration.DEFAULT;
	}

	@Override
	public Map<String, Object> getContext()
	{
		return Map.of();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator withContext(Object value, String name)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator and(Consumer<? super PrimitiveUnsignedIntegerValidator> validation)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator and(ValidatorComponent<?, ?>... others)
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator or(ValidatorComponent<?, ?>... others)
	{
		return this;
	}

	@Override
	public boolean validationFailed()
	{
		return true;
	}

	@Override
	public List<ValidationFailure> elseGetFailures()
	{
		return List.of();
	}

	@Override
	public boolean elseThrow()
	{
		return false;
	}

	@Override
	public List<String> elseGetMessages()
	{
		return List.of();
	}

	@Override
	public Throwable elseGetException()
	{
		return null;
	}

	@Override
	public String getContextAsString()
	{
		return "";
	}

	@Override
	public StringValidator asString()
	{
		return StringValidatorNoOp.getInstance();
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isZero()
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator isNotZero()
	{
		return this;
	}
}