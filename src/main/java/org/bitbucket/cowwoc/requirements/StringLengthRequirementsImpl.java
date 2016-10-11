/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import org.bitbucket.cowwoc.requirements.util.Exceptions;

/**
 * Default implementation of StringLengthRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class StringLengthRequirementsImpl implements StringLengthRequirements
{
	/**
	 * @param amount an amount
	 * @return "character" or "characters" depending on whether {@code amount} is plural
	 */
	private static String getSingularOrPlural(int amount)
	{
		if (amount == 1)
			return "character";
		return "characters";
	}
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
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	StringLengthRequirementsImpl(String parameter, String name, Configuration config)
		throws AssertionError
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.string = parameter;
		this.parameter = parameter.length();
		this.name = name + ".length()";
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(this.parameter, name, config);
	}

	/**
	 * Constructor meant to be invoked by {@link #withException(Class)}.
	 *
	 * @param string    the {@code String}
	 * @param parameter the length of the {@code String}
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	private StringLengthRequirementsImpl(String string, int parameter, String name,
		Configuration config) throws AssertionError
	{
		assert (string != null): "string may not be null";
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
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
	public StringLengthRequirements addContext(String key, Object value)
		throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		return new StringLengthRequirementsImpl(string, parameter, name, newConfig);
	}

	@Override
	public StringLengthRequirements withContext(List<Entry<String, Object>> context)
		throws NullPointerException
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
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", string).
			build();
	}

	@Override
	public StringLengthRequirements isGreaterThanOrEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter >= value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) %s. It contained %,d %s.",
				this.name, name, value, getSingularOrPlural(value), parameter,
				getSingularOrPlural(parameter))).
			addContext("Actual", string).
			build();
	}

	@Override
	public StringLengthRequirements isGreaterThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter > value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain more than %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", string).
			build();
	}

	@Override
	public StringLengthRequirements isGreaterThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter > value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) %s. It contained %,d %s", this.name, name,
				value, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", string).
			build();
	}

	@Override
	public StringLengthRequirements isLessThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter <= value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may contain at most %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", string).
			build();
	}

	@Override
	public StringLengthRequirements isLessThanOrEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter <= value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may contain at most %s (%,d) %s. It contained %,d %s", this.name, name,
				value, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", string).
			build();
	}

	@Override
	public StringLengthRequirements isLessThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter < value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain less than %,d %s. It contained %,d %s.",
				this.name, value, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", string).
			build();
	}

	@Override
	public StringLengthRequirements isLessThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter < value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain less than %s (%,d) %s. It contained %,d %s.",
				this.name, name, value, getSingularOrPlural(value), parameter,
				getSingularOrPlural(parameter))).
			addContext("Actual", string).
			build();
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
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least one character. It contained %,d characters.", name,
				parameter)).
			addContext("Actual", string).
			build();
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
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be empty. It contained %,d %s.", name, parameter,
				getSingularOrPlural(parameter))).
			addContext("Actual", string).
			build();
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
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s cannot have a negative length", name), null);
	}

	@Override
	public StringLengthRequirements isIn(Integer first, Integer last)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(first, "first").isNotNull();
		Requirements.requireThat(last, "last").isNotNull().isGreaterThanOrEqualTo(first, "first");
		if (parameter >= first && parameter <= last)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain [%d, %d] characters. It contained %,d %s.", name,
				first, last, parameter, getSingularOrPlural(parameter))).
			addContext("Actual", string).
			build();
	}

	@Override
	public StringLengthRequirements isEqualTo(Integer value) throws IllegalArgumentException
	{
		if (Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", string).
			build();
	}

	@Override
	public StringLengthRequirements isEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		if (Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) %s. It contained %,d %s.", this.name, name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", string).
			build();
	}

	@Override
	public StringLengthRequirements isNotEqualTo(Integer value) throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not contain %,d %s, but did.", name, value,
				getSingularOrPlural(value))).
			addContext("Actual", string).
			build();
	}

	@Override
	public StringLengthRequirements isNotEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not contain %s (%,d) %s, but did.", this.name, name, value,
				getSingularOrPlural(value))).
			addContext("Actual", string).
			build();
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
