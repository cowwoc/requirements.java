/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.diff.string.DiffGenerator;
import org.bitbucket.cowwoc.requirements.diff.string.DiffResult;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of ObjectRequirements.
 * <p>
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
final class ObjectRequirementsImpl<T> implements ObjectRequirements<T>
{
	private static final DiffGenerator DIFF_GENERATOR = new DiffGenerator();
	private final T parameter;
	private final String name;
	private final Configuration config;

	/**
	 * Creates new ObjectRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	ObjectRequirementsImpl(T parameter, String name, Configuration config)
		throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
		this.parameter = parameter;
		this.name = name;
		this.config = config;
	}

	@Override
	public ObjectRequirements<T> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new ObjectRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public ObjectRequirements<T> withContext(Map<String, Object> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new ObjectRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public ObjectRequirements<T> isNull() throws IllegalArgumentException
	{
		if (parameter == null)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be null.", name),
			"Actual", parameter);
	}

	@Override
	public ObjectRequirements<T> isNotNull() throws NullPointerException
	{
		if (parameter != null)
			return this;
		throw config.createException(NullPointerException.class,
			String.format("%s may not be null", name));
	}

	@Override
	public ObjectRequirements<T> isEqualTo(T value) throws IllegalArgumentException
	{
		if (Objects.equals(parameter, value))
			return this;
		DiffResult result = DIFF_GENERATOR.diff(parameter.toString(), value.toString());
		Map<String, Object> context = new LinkedHashMap<>(3);
		context.put("Actual", result.getActual());
		result.getMiddle().ifPresent(theValue -> context.put("Diff", theValue));
		context.put("Expected", result.getExpected());
		throw config.createException(IllegalArgumentException.class,
			String.format("%s had an unexpected value.", name),
			context);
	}

	@Override
	public ObjectRequirements<T> isEqualTo(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(parameter, value))
			return this;
		DiffResult result = DIFF_GENERATOR.diff(parameter.toString(), value.toString());
		Map<String, Object> context = new LinkedHashMap<>(3);
		context.put("Actual", result.getActual());
		result.getMiddle().ifPresent(theValue -> context.put("Diff", theValue));
		context.put("Expected", result.getExpected());
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be equal to %s.", this.name, name),
			context);
	}

	@Override
	public ObjectRequirements<T> isNotEqualTo(T value) throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			return this;

		throw config.createException(IllegalArgumentException.class,
			String.format("%s must not be equal to %s", name, value));
	}

	@Override
	public ObjectRequirements<T> isNotEqualTo(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(parameter, value))
			return this;

		throw config.createException(IllegalArgumentException.class,
			String.format("%s must not be equal to %s.", this.name, name),
			"Actual", value);
	}

	@Override
	public ObjectRequirements<T> isIn(Collection<T> collection)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(collection, "collection").isNotNull();
		if (collection.contains(parameter))
			return this;

		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be one of %s.", this.name, collection),
			"Actual", parameter);
	}

	@Override
	public ObjectRequirements<T> isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(type, "type").isNotNull();
		if (type.isInstance(parameter))
			return this;

		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be an instance of %s.", name, type),
			"Actual: %s", parameter.getClass());
	}

	@Override
	public ObjectRequirements<T> isolate(Consumer<ObjectRequirements<T>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
