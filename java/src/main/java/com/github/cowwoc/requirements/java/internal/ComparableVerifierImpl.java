/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ComparableValidator;
import com.github.cowwoc.requirements.java.ComparableVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractComparableVerifier;

/**
 * Default implementation of {@code ComparableVerifier}.
 *
 * @param <T> the type of objects that the value may be compared to
 */
public final class ComparableVerifierImpl<T extends Comparable<? super T>>
	extends AbstractComparableVerifier<ComparableVerifier<T>, ComparableValidator<T>, T>
	implements ComparableVerifier<T>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public ComparableVerifierImpl(ComparableValidator<T> validator)
	{
		super(validator);
	}

	@Override
	protected ComparableVerifier<T> getThis()
	{
		return this;
	}
}
