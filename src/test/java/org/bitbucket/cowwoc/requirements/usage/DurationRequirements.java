package org.bitbucket.cowwoc.requirements.usage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Range;
import java.time.Duration;
import java.util.Map;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.ComparableRequirements;
import org.bitbucket.cowwoc.requirements.Requirements;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

public final class DurationRequirements implements ComparableRequirements<Duration>
{
	/**
	 * Creates new DurationRequirements.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @return Requirements for the parameter
	 * @throws NullPointerException if name is null
	 */
	public static DurationRequirements requireThat(Duration parameter, String name)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		if (name.trim().isEmpty())
			throw new IllegalArgumentException("name may not be empty");
		return new DurationRequirements(parameter, name, new Configuration(null, ImmutableMap.of()));
	}
	private final Duration parameter;
	private final String name;
	private final Configuration config;
	private final ComparableRequirements<Duration> asComparable;

	/**
	 * Creates new DurationRequirements.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines how to handle a requirements violation
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	private DurationRequirements(Duration parameter, String name, Configuration config)
	{
		assert (name != null);
		assert (config != null);
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asComparable = Requirements.requireThat(parameter, name).
			withException(config.getExceptionOverride()).
			withContext(config.getContext());
	}

	@Override
	public DurationRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new DurationRequirements(parameter, name, newConfig);
	}

	@Override
	public DurationRequirements withContext(Map<String, Object> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new DurationRequirements(parameter, name, newConfig);
	}

	/**
	 * Ensures that the parameter is positive.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not positive
	 */
	public DurationRequirements isPositive() throws IllegalArgumentException
	{
		if (parameter.getSeconds() > 0)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be positive.", name),
			"Actual", parameter);
	}

	@Override
	public DurationRequirements isGreaterThan(Duration value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isGreaterThan(value, name);
		return this;
	}

	@Override
	public DurationRequirements isGreaterThan(Duration value)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isGreaterThan(value);
		return this;
	}

	@Override
	public DurationRequirements isGreaterThanOrEqualTo(Duration value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public DurationRequirements isGreaterThanOrEqualTo(Duration value)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public DurationRequirements isLessThan(Duration value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isLessThan(value, name);
		return this;
	}

	@Override
	public DurationRequirements isLessThan(Duration value)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isLessThan(value);
		return this;
	}

	@Override
	public DurationRequirements isLessThanOrEqualTo(Duration value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public DurationRequirements isLessThanOrEqualTo(Duration value)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public DurationRequirements isIn(Range<Duration> range) throws NullPointerException,
		IllegalArgumentException
	{
		asComparable.isIn(range);
		return this;
	}

	@Override
	public DurationRequirements isEqualTo(Duration value) throws IllegalArgumentException
	{
		asComparable.isEqualTo(value);
		return this;
	}

	@Override
	public DurationRequirements isEqualTo(Duration value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isEqualTo(value, name);
		return this;
	}

	@Override
	public DurationRequirements isNotEqualTo(Duration value) throws IllegalArgumentException
	{
		asComparable.isNotEqualTo(value);
		return this;
	}

	@Override
	public DurationRequirements isNotEqualTo(Duration value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public DurationRequirements isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isInstanceOf(type);
		return this;
	}

	@Override
	public DurationRequirements isNull() throws IllegalArgumentException
	{
		asComparable.isNull();
		return this;
	}

	@Override
	public DurationRequirements isNotNull() throws NullPointerException
	{
		asComparable.isNotNull();
		return this;
	}

	@Override
	public DurationRequirements isolate(Consumer<ComparableRequirements<Duration>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
