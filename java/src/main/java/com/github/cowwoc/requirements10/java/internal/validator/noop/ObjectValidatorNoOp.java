/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator.noop;

import com.github.cowwoc.requirements10.java.validator.ObjectValidator;

public final class ObjectValidatorNoOp extends AbstractObjectValidatorNoOp<ObjectValidator<Object>, Object>
	implements ObjectValidator<Object>
{
	private static final ObjectValidatorNoOp INSTANCE = new ObjectValidatorNoOp();

	@SuppressWarnings("unchecked")
	public static <T> ObjectValidator<T> getInstance()
	{
		return (ObjectValidator<T>) INSTANCE;
	}

	private ObjectValidatorNoOp()
	{
	}
}