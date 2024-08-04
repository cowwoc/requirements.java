/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator.noop;

import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.validator.ObjectValidator;
import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

import java.util.function.Consumer;

import static com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator.VALUE_IS_UNDEFINED;

public abstract class AbstractObjectValidatorNoOp<S, T> extends AbstractValidatorNoOp<S, T>
	implements ValidatorComponent<S, T>,
	ObjectComponent<S, T>
{
	protected AbstractObjectValidatorNoOp()
	{
	}

	@Override
	public T getValue()
	{
		throw VALUE_IS_UNDEFINED.get();
	}

	@Override
	public S isNull()
	{
		return self();
	}

	@Override
	public S isNotNull()
	{
		return self();
	}

	@Override
	public S isSameReferenceAs(Object expected, String name)
	{
		return self();
	}

	@Override
	public S isNotSameReferenceAs(Object unwanted, String name)
	{
		return self();
	}

	@Override
	public <U> ObjectValidator<U> isInstanceOf(Class<U> expected)
	{
		return ObjectValidatorNoOp.getInstance();
	}

	@Override
	public <U> ObjectValidator<U> isInstanceOf(GenericType<U> expected)
	{
		return ObjectValidatorNoOp.getInstance();
	}

	@Override
	public S isNotInstanceOf(Class<?> unwanted)
	{
		return self();
	}

	@Override
	public S isNotInstanceOf(GenericType<?> unwanted)
	{
		return self();
	}

	@Override
	public S isEqualTo(Object expected)
	{
		return self();
	}

	@Override
	public S isEqualTo(Object expected, String name)
	{
		return self();
	}

	@Override
	public S isNotEqualTo(Object unwanted)
	{
		return self();
	}

	@Override
	public S isNotEqualTo(Object unwanted, String name)
	{
		return self();
	}

	@Override
	public S and(Consumer<? super S> validation)
	{
		return self();
	}
}