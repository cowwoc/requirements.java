package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.message.ObjectMessages;
import com.github.cowwoc.requirements10.java.internal.message.ValidatorMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.ValidationTarget;
import com.github.cowwoc.requirements10.java.validator.ObjectValidator;
import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
	 * @param value         the value being validated
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public AbstractObjectValidator(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<T> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
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
				ObjectMessages.isNullFailed(this).toString());
		}
		return self();
	}

	@Override
	public S isNotNull()
	{
		T validOrNull = value.or(null);
		if (validOrNull == null)
			onNull();
		return self();
	}

	@Override
	public S isReferenceEqualTo(Object expected, String name)
	{
		requireThatNameIsUnique(name);
		if (value.validationFailed(v -> v == expected))
		{
			addIllegalArgumentException(
				ObjectMessages.isReferenceEqualToFailed(this, name, expected).toString());
		}
		return self();
	}

	@Override
	public S isReferenceNotEqualTo(Object unwanted, String name)
	{
		requireThatNameIsUnique(name);
		if (value.validationFailed(v -> v != unwanted))
		{
			addIllegalArgumentException(
				ObjectMessages.isReferenceNotEqualToFailed(this, name).toString());
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
		if (value.validationFailed(expected::isTypeOf))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOfFailed(this, expected).toString());
		}
		return self();
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
		if (value.validationFailed(v -> !unwanted.isTypeOf(v)))
		{
			addIllegalArgumentException(
				ObjectMessages.isNotInstanceOfFailed(this, unwanted).toString());
		}
		return self();
	}

	@Override
	public S isEqualTo(Object expected)
	{
		return isEqualToImpl(expected, null);
	}

	@Override
	public S isEqualTo(Object expected, String name)
	{
		requireThatNameIsUnique(name);
		return isEqualToImpl(expected, name);
	}

	protected S isEqualToImpl(Object expected, String name)
	{
		if (value.validationFailed(v -> getEqualityFunction().apply(v, expected)))
		{
			addIllegalArgumentException(
				ValidatorMessages.isEqualToFailed(this, name, expected).toString());
		}
		return self();
	}

	@Override
	public S isNotEqualTo(Object unwanted)
	{
		return isNotEqualToImpl(unwanted, null);
	}

	@Override
	public S isNotEqualTo(Object unwanted, String name)
	{
		requireThatNameIsUnique(name);
		return isNotEqualToImpl(unwanted, name);
	}

	private S isNotEqualToImpl(Object unwanted, String name)
	{
		if (value.validationFailed(v -> !getEqualityFunction().apply(v, unwanted)))
		{
			addIllegalArgumentException(
				ValidatorMessages.isNotEqualToFailed(this, name, unwanted).toString());
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
		addNullPointerException(ObjectMessages.isNotNullFailed(this).toString());
	}
}