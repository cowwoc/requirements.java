/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator.noop;

import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.validator.ObjectValidator;
import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;
import com.github.cowwoc.requirements10.java.validator.StringValidator;

import static com.github.cowwoc.requirements10.java.internal.validator.AbstractValidator.VALUE_IS_UNDEFINED;

public final class StringValidatorNoOp extends AbstractValidatorNoOp<StringValidator, String>
	implements StringValidator
{
	private static final StringValidatorNoOp INSTANCE = new StringValidatorNoOp();

	public static StringValidatorNoOp getInstance()
	{
		return INSTANCE;
	}

	private StringValidatorNoOp()
	{
	}

	@Override
	public String getValue()
	{
		throw VALUE_IS_UNDEFINED.get();
	}

	@Override
	public String getValueOrDefault(String defaultValue)
	{
		return "";
	}

	@Override
	public StringValidator startsWith(String prefix)
	{
		return this;
	}

	@Override
	public StringValidator doesNotStartWith(String prefix)
	{
		return this;
	}

	@Override
	public StringValidator endsWith(String suffix)
	{
		return this;
	}

	@Override
	public StringValidator doesNotEndWith(String suffix)
	{
		return this;
	}

	@Override
	public StringValidator contains(String expected)
	{
		return this;
	}

	@Override
	public StringValidator doesNotContain(String unwanted)
	{
		return this;
	}

	@Override
	public StringValidator matches(String regex)
	{
		return this;
	}

	@Override
	public StringValidator isEmpty()
	{
		return this;
	}

	@Override
	public StringValidator isNotEmpty()
	{
		return this;
	}

	@Override
	public StringValidator isBlank()
	{
		return this;
	}

	@Override
	public StringValidator isNotBlank()
	{
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator length()
	{
		return PrimitiveUnsignedIntegerValidatorNoOp.getInstance();
	}

	@Override
	public StringValidator trim()
	{
		return this;
	}

	@Override
	public StringValidator isTrimmed()
	{
		return this;
	}

	@Override
	public StringValidator strip()
	{
		return this;
	}

	@Override
	public StringValidator isStripped()
	{
		return this;
	}

	@Override
	public StringValidator isNull()
	{
		return this;
	}

	@Override
	public StringValidator isNotNull()
	{
		return this;
	}

	@Override
	public StringValidator isSameReferenceAs(Object expected, String name)
	{
		return this;
	}

	@Override
	public StringValidator isNotSameReferenceAs(Object unwanted, String name)
	{
		return this;
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
	public StringValidator isNotInstanceOf(Class<?> unwanted)
	{
		return this;
	}

	@Override
	public StringValidator isNotInstanceOf(GenericType<?> unwanted)
	{
		return this;
	}

	@Override
	public StringValidator isEqualTo(Object expected)
	{
		return this;
	}

	@Override
	public StringValidator isEqualTo(Object expected, String name)
	{
		return this;
	}

	@Override
	public StringValidator isNotEqualTo(Object unwanted)
	{
		return this;
	}

	@Override
	public StringValidator isNotEqualTo(Object unwanted, String name)
	{
		return this;
	}
}