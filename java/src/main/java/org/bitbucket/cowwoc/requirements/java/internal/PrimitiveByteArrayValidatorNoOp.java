/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveByteArrayValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayValidatorNoOp;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * An implementation of {@code PrimitiveByteArrayValidator} that does nothing.
 */
public final class PrimitiveByteArrayValidatorNoOp
	extends AbstractArrayValidatorNoOp<PrimitiveByteArrayValidator, Byte, byte[]>
	implements PrimitiveByteArrayValidator
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public PrimitiveByteArrayValidatorNoOp(ApplicationScope scope, Configuration config,
	                                       List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Override
	protected PrimitiveByteArrayValidator getThis()
	{
		return this;
	}
}
