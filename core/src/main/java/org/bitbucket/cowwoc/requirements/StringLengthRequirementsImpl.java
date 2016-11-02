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
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import org.bitbucket.cowwoc.requirements.spi.ExceptionBuilder;
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
	private final SingletonScope scope;
	private final String string;
	private final int parameter;
	private final String name;
	private final Configuration config;
	private final PrimitiveIntegerRequirements asInt;

	/**
	 * Creates new StringLengthRequirementsImpl.
	 * <p>
	 * @param scope     the system configuration
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	StringLengthRequirementsImpl(SingletonScope scope, String parameter, String name,
		Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.string = parameter;
		this.parameter = parameter.length();
		this.name = name;
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(scope, this.parameter, name, config);
	}

	/**
	 * Constructor meant to be invoked by {@link #withException(Class)}.
	 *
	 * @param scope     the system configuration
	 * @param string    the {@code String}
	 * @param parameter the length of the {@code String}
	 * @param name      the name of the parameter
	 * @param config    the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	private StringLengthRequirementsImpl(SingletonScope scope, String string, int parameter,
		String name, Configuration config)
	{
		assert (string != null): "string may not be null";
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		this.scope = scope;
		this.string = string;
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(scope, this.parameter, name, config);
	}

	@Override
	public StringLengthRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new StringLengthRequirementsImpl(scope, string, parameter, name, newConfig);
	}

	@Override
	public StringLengthRequirements addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new StringLengthRequirementsImpl(scope, string, parameter, name, newConfig);
	}

	@Override
	public StringLengthRequirements withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new StringLengthRequirementsImpl(scope, string, parameter, name, newConfig);
	}

	@Override
	public StringLengthRequirements isGreaterThanOrEqualTo(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter >= value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter)));
		if (parameter > 0)
			eb.addContext("Actual", string);
		throw eb.build();
	}

	@Override
	public StringLengthRequirements isGreaterThanOrEqualTo(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter >= value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) %s. It contained %,d %s.",
				this.name, name, value, getSingularOrPlural(value), parameter,
				getSingularOrPlural(parameter)));
		if (parameter > 0)
			eb.addContext("Actual", string);
		throw eb.build();
	}

	@Override
	public StringLengthRequirements isGreaterThan(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter > value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain more than %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter)));
		if (parameter > 0)
			eb.addContext("Actual", string);
		throw eb.build();
	}

	@Override
	public StringLengthRequirements isGreaterThan(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter > value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) %s. It contained %,d %s", this.name, name,
				value, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter)));
		if (parameter > 0)
			eb.addContext("Actual", string);
		throw eb.build();
	}

	@Override
	public StringLengthRequirements isLessThanOrEqualTo(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter <= value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain more than %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter)));
		if (parameter > 0)
			eb.addContext("Actual", string);
		throw eb.build();
	}

	@Override
	public StringLengthRequirements isLessThanOrEqualTo(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter <= value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain more than %s (%,d) %s. It contained %,d %s", this.name,
				name, value, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter)));
		if (parameter > 0)
			eb.addContext("Actual", string);
		throw eb.build();
	}

	@Override
	public StringLengthRequirements isLessThan(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter < value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain less than %,d %s. It contained %,d %s.",
				this.name, value, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter)));
		if (parameter > 0)
			eb.addContext("Actual", string);
		throw eb.build();
	}

	@Override
	public StringLengthRequirements isLessThan(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter < value)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain less than %s (%,d) %s. It contained %,d %s.",
				this.name, name, value, getSingularOrPlural(value), parameter,
				getSingularOrPlural(parameter)));
		if (parameter > 0)
			eb.addContext("Actual", string);
		throw eb.build();
	}

	@Override
	public StringLengthRequirements isNotPositive()
	{
		return isZero();
	}

	@Override
	public StringLengthRequirements isPositive()
	{
		if (parameter > 0)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least one character. It contained %,d characters.", name,
				parameter));
		if (parameter > 0)
			eb.addContext("Actual", string);
		throw eb.build();
	}

	@Override
	public StringLengthRequirements isNotZero()
	{
		return isPositive();
	}

	@Override
	public StringLengthRequirements isZero()
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
	public StringLengthRequirements isNotNegative()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public StringLengthRequirements isNegative()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s cannot have a negative length", name), null);
	}

	@Override
	public StringLengthRequirements isIn(Integer first, Integer last)
	{
		Requirements.requireThat(first, "first").isNotNull();
		Requirements.requireThat(last, "last").isNotNull().isGreaterThanOrEqualTo(first, "first");
		if (parameter >= first && parameter <= last)
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain [%d, %d] characters. It contained %,d %s.", name,
				first, last, parameter, getSingularOrPlural(parameter)));
		if (parameter > 0)
			eb.addContext("Actual", string);
		throw eb.build();
	}

	@Override
	public StringLengthRequirements isEqualTo(Integer value)
	{
		if (Objects.equals(parameter, value))
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter)));
		if (parameter > 0)
			eb.addContext("Actual", string);
		throw eb.build();
	}

	@Override
	public StringLengthRequirements isEqualTo(Integer value, String name)
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
	public StringLengthRequirements isNotEqualTo(Integer value)
	{
		if (!Objects.equals(parameter, value))
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not contain %,d %s, but did.", name, value,
				getSingularOrPlural(value)));
		if (parameter > 0)
			eb.addContext("Actual", string);
		throw eb.build();
	}

	@Override
	public StringLengthRequirements isNotEqualTo(Integer value, String name)
	{
		if (!Objects.equals(parameter, value))
			return this;
		ExceptionBuilder eb = config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not contain %s (%,d) %s, but did.", this.name, name, value,
				getSingularOrPlural(value)));
		if (parameter > 0)
			eb.addContext("Actual", string);
		throw eb.build();
	}

	@Override
	@Deprecated
	public StringLengthRequirements isNull()
	{
		asInt.isNull();
		return this;
	}

	@Override
	public StringLengthRequirements isNotNull()
	{
		asInt.isNotNull();
		return this;
	}

	@Override
	public StringLengthRequirements isIn(Collection<Integer> collection)
	{
		asInt.isIn(collection);
		return this;
	}

	@Override
	public StringLengthRequirements isInstanceOf(Class<?> type)
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
