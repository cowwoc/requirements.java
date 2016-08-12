/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of StringLengthRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class StringLengthRequirementsImpl implements StringLengthRequirements
{
	private final String string;
	private final int parameter;
	private final String name;
	private final Configuration config;
	private final PrimitiveIntegerRequirements asInt;

	/**
	 * Creates new StringLengthRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	StringLengthRequirementsImpl(String parameter, String name, Configuration config)
		throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
		this.string = parameter;
		this.parameter = parameter.length();
		this.name = name + ".length()";
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(this.parameter, name, config);
	}

	/**
	 * Constructor meant to be invoked by {@link #withException(Class)}.
	 *
	 * @param string            the {@code String}
	 * @param parameter         the length of the {@code String}
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @param context           additional key-value pairs to output if a requirement is not met
	 */
	private StringLengthRequirementsImpl(String string, int parameter, String name,
		Configuration config)
	{
		assert (string != null);
		assert (name != null);
		this.string = string;
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(this.parameter, name, config);
	}

	@Override
	public StringLengthRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new StringLengthRequirementsImpl(string, parameter, name, newConfig);
	}

	@Override
	public StringLengthRequirements withContext(Map<String, Object> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new StringLengthRequirementsImpl(string, parameter, name, newConfig);
	}

	@Override
	public StringLengthRequirements isGreaterThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter >= value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain at least %,d characters. It contained %,d characters.", name,
				value, parameter),
			"Actual", string);
	}

	@Override
	public StringLengthRequirements isGreaterThanOrEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter >= value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) characters. It contained %,d characters.",
				this.name, name, value, parameter),
			"Actual", string);
	}

	@Override
	public StringLengthRequirements isGreaterThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter > value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain more than %,d characters. It contained %,d characters.", name,
				value, parameter),
			"Actual", string);
	}

	@Override
	public StringLengthRequirements isGreaterThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter > value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) characters. It contained " +
				"%,d characters", this.name, name, value, parameter),
			"Actual", string);
	}

	@Override
	public StringLengthRequirements isLessThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter <= value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may contain at most %,d characters. It contained %,d characters.", name,
				value, parameter),
			"Actual", string);
	}

	@Override
	public StringLengthRequirements isLessThanOrEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter <= value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may contain at most %s (%,d) characters. It contained " +
				"%,d characters", this.name, name, value, parameter),
			"Actual", string);
	}

	@Override
	public StringLengthRequirements isLessThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter < value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain less than %,d characters. It contained %,d characters.",
				this.name, value, parameter),
			"Actual", string);
	}

	@Override
	public StringLengthRequirements isLessThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter < value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain less than %s (%,d) characters. It contained %,d characters.",
				this.name, name, value, parameter),
			"Actual", string);
	}

	@Override
	public StringLengthRequirements isNotPositive() throws IllegalArgumentException
	{
		return isZero();
	}

	@Override
	public StringLengthRequirements isPositive() throws IllegalArgumentException
	{
		if (parameter > 0)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain at least one character. It contained %,d characters.", name,
				parameter),
			"Actual", string);
	}

	@Override
	public StringLengthRequirements isNotZero() throws IllegalArgumentException
	{
		return isPositive();
	}

	@Override
	public StringLengthRequirements isZero() throws IllegalArgumentException
	{
		if (parameter == 0)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be empty. It contained %,d characters.", name, parameter),
			"Actual", string);
	}

	@Override
	public StringLengthRequirements isNotNegative() throws IllegalArgumentException
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public StringLengthRequirements isNegative() throws IllegalArgumentException
	{
		throw new IllegalArgumentException(String.format("%s cannot have a negative length", name));
	}

	@Override
	public StringLengthRequirements isIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(range, "range").isNotNull();
		if (range.contains(parameter))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain %s characters. It contained %,d characters.", name, range,
				parameter),
			"Actual", string);
	}

	@Override
	public StringLengthRequirements isEqualTo(Integer value) throws IllegalArgumentException
	{
		if (Objects.equals(parameter, value))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain %,d characters. It contained %,d characters.", name, value,
				parameter),
			"Actual", string);
	}

	@Override
	public StringLengthRequirements isEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		if (Objects.equals(parameter, value))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) characters. It contained %,d characters.", this.name,
				name, value, parameter),
			"Actual", string);
	}

	@Override
	public StringLengthRequirements isNotEqualTo(Integer value) throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must not contain %,d characters, but did.", name, value),
			"Actual", string);
	}

	@Override
	public StringLengthRequirements isNotEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must not contain %s (%,d) characters, but did.", this.name, name, value),
			"Actual", string);
	}

	@Override
	@Deprecated
	public StringLengthRequirements isNull() throws IllegalArgumentException
	{
		asInt.isNull();
		return this;
	}

	@Override
	public StringLengthRequirements isNotNull() throws NullPointerException
	{
		asInt.isNotNull();
		return this;
	}

	@Override
	public StringLengthRequirements isIn(Collection<Integer> collection) throws NullPointerException,
		IllegalArgumentException
	{
		asInt.isIn(collection);
		return this;
	}

	@Override
	public StringLengthRequirements isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isInstanceOf(type);
		return this;
	}

	@Override
	public StringLengthRequirements isolate(Consumer<StringLengthRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
