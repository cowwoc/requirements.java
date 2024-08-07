package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.message.ComparableMessages;
import com.github.cowwoc.requirements10.java.internal.message.ObjectMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.validator.ObjectValidator;
import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Validates the state of an object.
 *
 * @param <S> the type of validator that the methods should return
 * @param <T> the type of the value that is being validated
 */
public abstract class AbstractObjectValidator<S, T> extends AbstractValidator<S, T>
	implements ObjectComponent<S, T>
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
	public AbstractObjectValidator(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<T> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public T getValue()
	{
		return value.orThrow(VALUE_IS_UNDEFINED);
	}

	@Override
	public S isNull()
	{
		if (!value.isNull())
		{
			addIllegalArgumentException(
				ObjectMessages.isNull(this).toString());
		}
		return self();
	}

	@Override
	public S isNotNull()
	{
		if (value.isNull())
			onNull();
		return self();
	}

	@Override
	public S isSameReferenceAs(Object expected, String name)
	{
		requireThatNameIsUnique(name);
		switch (value.test(value -> value == expected))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isSameReferenceAs(this, MaybeUndefined.defined(name), expected).toString());
		}
		return self();
	}

	@Override
	public S isNotSameReferenceAs(Object unwanted, String name)
	{
		requireThatNameIsUnique(name);
		switch (value.test(value -> value != unwanted))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isNotSameReferenceAs(this, MaybeUndefined.defined(name), unwanted).toString());
		}
		return self();
	}

	@Override
	public <U> ObjectValidator<U> isInstanceOf(Class<U> expected)
	{
		return isInstanceOf(GenericType.from(expected));
	}

	@Override
	public <U> ObjectValidator<U> isInstanceOf(GenericType<U> expected)
	{
		scope.getInternalValidators().requireThat(expected, "expected").isNotNull();
		switch (value.test(expected::isTypeOf))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isInstanceOf(this, expected).toString());
		}
		return assumeExpectedType();
	}

	@Override
	public S isNotInstanceOf(Class<?> unwanted)
	{
		return isNotInstanceOf(GenericType.from(unwanted));
	}

	@Override
	public S isNotInstanceOf(GenericType<?> unwanted)
	{
		scope.getInternalValidators().requireThat(unwanted, "unwanted").isNotNull();
		switch (value.test(v -> !unwanted.isTypeOf(v)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.isNotInstanceOf(this, unwanted).toString());
		}
		return self();
	}

	@Override
	public S isEqualTo(Object expected)
	{
		return isEqualToImpl(expected, MaybeUndefined.undefined());
	}

	@Override
	public S isEqualTo(Object expected, String name)
	{
		requireThatNameIsUnique(name);
		return isEqualToImpl(expected, MaybeUndefined.defined(name));
	}

	protected S isEqualToImpl(Object expected, MaybeUndefined<String> name)
	{
		switch (value.test(value -> getEqualityFunction().apply(value, expected)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ObjectMessages.compareValues(this, "must be equal to", name, expected).toString());
		}
		return self();
	}

	@Override
	public S isNotEqualTo(Object unwanted)
	{
		return isNotEqualToImpl(unwanted, MaybeUndefined.undefined());
	}

	@Override
	public S isNotEqualTo(Object unwanted, String name)
	{
		requireThatNameIsUnique(name);
		return isNotEqualToImpl(unwanted, MaybeUndefined.defined(name));
	}

	private S isNotEqualToImpl(Object unwanted, MaybeUndefined<String> name)
	{
		switch (value.test(value -> !getEqualityFunction().apply(value, unwanted)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				ComparableMessages.isNotEqualTo(this, name, unwanted).toString());
		}
		return self();
	}

	/**
	 * @return the function used to check whether two values are equal
	 */
	private BiFunction<Object, Object, Boolean> getEqualityFunction()
	{
		return switch (configuration.equalityMethod())
		{
			case OBJECT -> EqualityMethods.OBJECT;
			case COMPARABLE -> EqualityMethods.COMPARABLE;
		};
	}

	@Override
	protected void onNull()
	{
		addNullPointerException(ObjectMessages.isNotNull(this).toString());
	}
}