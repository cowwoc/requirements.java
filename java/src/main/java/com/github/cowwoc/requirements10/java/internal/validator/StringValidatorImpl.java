/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.message.ObjectMessages;
import com.github.cowwoc.requirements10.java.internal.message.StringMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.internal.util.ObjectAndSize;
import com.github.cowwoc.requirements10.java.internal.util.Pluralizer;
import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;
import com.github.cowwoc.requirements10.java.validator.StringValidator;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class StringValidatorImpl extends AbstractObjectValidator<StringValidator, String>
	implements StringValidator
{
	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         the value
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public StringValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<String> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public StringValidator isEmpty()
	{
		if (value.isNull())
			onNull();
		switch (value.test(String::isEmpty))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isEmpty(this).toString());
		}
		return this;
	}

	@Override
	public StringValidator isNotEmpty()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !value.isEmpty()))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isNotEmpty(this).toString());
		}
		return this;
	}

	@Override
	public StringValidator isBlank()
	{
		if (value.isNull())
			onNull();
		switch (value.test(String::isBlank))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.isBlank(this).toString());
		}
		return this;
	}

	@Override
	public StringValidator isNotBlank()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !value.isBlank()))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.isNotBlank(this).toString());
		}
		return this;
	}

	@Override
	public StringValidator trim()
	{
		return update(String::trim, "trim()");
	}

	/**
	 * Updates the value.
	 *
	 * @param operation the operation to apply on the value
	 * @param name      the name of the operation
	 */
	private StringValidator update(Function<String, String> operation, String name)
	{
		if (value.isNull())
			onNull();
		MaybeUndefined<String> oldValue = value;
		MaybeUndefined<String> newValue = oldValue.mapDefined(operation);
		if (!oldValue.equals(newValue))
			this.name += "." + name;
		value = newValue;
		return this;
	}

	@Override
	public StringValidator isTrimmed()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value ->
		{
			int length = value.length();
			if (length == 0)
				return true;
			boolean foundWhitespace = (value.charAt(0) & 0xff) <= ' ';
			if (!foundWhitespace && length > 1)
				foundWhitespace = (value.charAt(length - 1) & 0xff) <= ' ';
			return !foundWhitespace;
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.isTrimmed(this).toString());
		}
		return this;
	}

	@Override
	public StringValidator strip()
	{
		return update(String::strip, "strip()");
	}

	@Override
	public StringValidator isStripped()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value ->
		{
			int length = value.length();
			if (length == 0)
				return true;
			int codepoint = value.codePointAt(0);
			boolean foundWhitespace = Character.isWhitespace(codepoint);
			if (!foundWhitespace && length > 1)
			{
				codepoint = value.codePointAt(length - 1);
				foundWhitespace = Character.isWhitespace(codepoint);
			}
			return !foundWhitespace;
		}))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.isStripped(this).toString());
		}
		return this;
	}

	@Override
	public StringValidator startsWith(String prefix)
	{
		scope.getInternalValidators().requireThat(prefix, "prefix").isNotNull();
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.startsWith(prefix)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.startsWith(this, prefix).toString());
		}
		return this;
	}

	@Override
	public StringValidator doesNotStartWith(String prefix)
	{
		scope.getInternalValidators().requireThat(prefix, "prefix").isNotNull();
		if (value.isNull())
			onNull();
		switch (value.test(value -> !value.startsWith(prefix)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.doesNotStartWith(this, prefix).toString());
		}
		return this;
	}

	@Override
	public StringValidator endsWith(String suffix)
	{
		scope.getInternalValidators().requireThat(suffix, "suffix").isNotNull();
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.endsWith(suffix)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.endsWith(this, suffix).toString());
		}
		return this;
	}

	@Override
	public StringValidator doesNotEndWith(String suffix)
	{
		scope.getInternalValidators().requireThat(suffix, "suffix").isNotNull();
		if (value.isNull())
			onNull();
		switch (value.test(value -> !value.endsWith(suffix)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.doesNotEndWith(this, suffix).toString());
		}
		return this;
	}

	@Override
	public StringValidator contains(String expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.contains(expected)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.contains(this, expected).toString());
		}
		return this;
	}

	@Override
	public StringValidator doesNotContain(String unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		if (value.isNull())
			onNull();
		switch (value.test(value -> !value.contains(unwanted)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.doesNotContain(this, unwanted).toString());
		}
		return this;
	}

	@Override
	public StringValidator matches(String regex)
	{
		scope.getInternalValidators().requireThat(regex, "regex").isNotNull();
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.matches(regex)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				StringMessages.matches(this, regex).toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator length()
	{
		if (value.isNull())
			onNull();
		return new ObjectSizeValidatorImpl(scope, configuration, name,
			value.nullToUndefined().mapDefined(value -> new ObjectAndSize(value, value.length())),
			name + ".length()", Pluralizer.CHARACTER, context, failures);
	}
}