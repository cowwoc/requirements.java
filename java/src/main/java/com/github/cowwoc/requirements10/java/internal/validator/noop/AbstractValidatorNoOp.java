package com.github.cowwoc.requirements10.java.internal.validator.noop;

import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.validator.StringValidator;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractValidatorNoOp<S, T> implements ValidatorComponent<S, T>
{
	protected AbstractValidatorNoOp()
	{
	}

	@Override
	public String getName()
	{
		return "";
	}

	@Override
	public T getValueOrDefault(T defaultValue)
	{
		return defaultValue;
	}

	@Override
	public Configuration configuration()
	{
		return Configuration.DEFAULT;
	}

	@Override
	public Map<String, Object> getContext()
	{
		return Map.of();
	}

	@Override
	public S withContext(Object value, String name)
	{
		return self();
	}

	protected S self()
	{
		@SuppressWarnings("unchecked")
		S self = (S) this;
		return self;
	}

	@Override
	public S and(Consumer<? super S> validation)
	{
		return self();
	}

	@Override
	public S and(ValidatorComponent<?, ?>... others)
	{
		return self();
	}

	@Override
	public S or(ValidatorComponent<?, ?>... others)
	{
		return self();
	}

	@Override
	public boolean validationFailed()
	{
		return true;
	}

	@Override
	public List<ValidationFailure> elseGetFailures()
	{
		return List.of();
	}

	@Override
	public boolean elseThrow()
	{
		return false;
	}

	@Override
	public List<String> elseGetMessages()
	{
		return List.of();
	}

	@Override
	public Throwable elseGetException()
	{
		return null;
	}

	@Override
	public String getContextAsString()
	{
		return "";
	}

	@Override
	public StringValidator asString()
	{
		return StringValidatorNoOp.getInstance();
	}
}
