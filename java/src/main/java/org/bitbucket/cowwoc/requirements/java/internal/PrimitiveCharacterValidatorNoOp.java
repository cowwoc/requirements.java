/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractComparableValidatorNoOp;

import java.util.List;

/**
 * A {@code PrimitiveCharacterValidator} that does nothing.
 */
public final class PrimitiveCharacterValidatorNoOp
	extends AbstractComparableValidatorNoOp<PrimitiveCharacterValidator, Character>
	implements PrimitiveCharacterValidator
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public PrimitiveCharacterValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected PrimitiveCharacterValidator getThis()
	{
		return this;
	}
}
