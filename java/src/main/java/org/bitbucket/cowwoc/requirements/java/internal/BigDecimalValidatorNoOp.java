/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionValidator;
import org.bitbucket.cowwoc.requirements.java.BigDecimalValidator;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

/**
 * An implementation of {@code BigDecimalValidator} that does nothing.
 */
public final class BigDecimalValidatorNoOp
	extends AbstractNumberValidatorNoOp<BigDecimalValidator, BigDecimal>
	implements BigDecimalValidator
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public BigDecimalValidatorNoOp(ApplicationScope scope, Configuration config,
	                               List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Override
	protected BigDecimalValidator getThis()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionValidator precision()
	{
		return new BigDecimalPrecisionValidatorNoOp(scope, config, failures);
	}

	@Override
	public BigDecimalValidator precision(Consumer<BigDecimalPrecisionValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public PrimitiveNumberValidator<Integer> scale()
	{
		return new PrimitiveNumberValidatorNoOp<>(scope, config, failures);
	}

	@Override
	public BigDecimalValidator scale(Consumer<PrimitiveNumberValidator<Integer>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
