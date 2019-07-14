/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.math.BigDecimal;
import java.util.List;

/**
 * An implementation of {@code PrimitiveNumberValidator} for a {@link BigDecimal}'s scale.
 */
public final class BigDecimalScaleValidatorImpl
	extends AbstractNumberValidator<PrimitiveNumberValidator<Integer>, Integer>
	implements PrimitiveNumberValidator<Integer>
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public BigDecimalScaleValidatorImpl(ApplicationScope scope, Configuration config, String name,
	                                    BigDecimal actual, List<ValidationFailure> failures)
	{
		super(scope, config, name + ".scale()", actual.scale(), failures);
	}

	@Override
	protected PrimitiveNumberValidator<Integer> getThis()
	{
		return this;
	}

	@Override
	protected PrimitiveNumberValidator<Integer> getNoOp()
	{
		return new PrimitiveNumberValidatorNoOp<>(getFailures());
	}
}
