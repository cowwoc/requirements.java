package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.EqualMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.part.ObjectPart;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Validates the state of an object.
 *
 * @param <S> the type of validator that the methods should return
 * @param <T> the type of the value that is being validated
 */
public abstract class AbstractObjectValidator<S, T> extends AbstractValidator<S>
	implements ObjectPart<S, T>
{
	protected T value;

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public AbstractObjectValidator(ApplicationScope scope, Configuration configuration,
		String name, T value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, context, failures);
		this.value = value;
	}

	@Override
	public T getValue()
	{
		return value;
	}

	@Override
	public S isNull()
	{
		if (value != null)
		{
			addIllegalArgumentException(
				ObjectMessages.isNull(scope, this, name, value).toString());
		}
		return self();
	}

	@Override
	public S isNotNull()
	{
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, name).toString());
		}
		return self();
	}

	@Override
	public S isSameReferenceAs(Object expected, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");

		if (value != expected)
		{
			addIllegalArgumentException(
				ObjectMessages.isSameReferenceAs(scope, this, this.name, value, name, expected).toString());
		}
		return self();
	}

	@Override
	public S isNotSameReferenceAs(Object unwanted, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");

		if (value == unwanted)
		{
			addIllegalArgumentException(
				ObjectMessages.isNotSameReferenceAs(scope, this, this.name, value, name, unwanted).toString());
		}
		return self();
	}

	@Override
	public S isInstanceOf(Class<?> expected)
	{
		scope.getInternalValidator().requireThat(expected, "Expected").isNotNull();
		if (!expected.isInstance(value))
		{
			addIllegalArgumentException(
				ObjectMessages.isInstanceOf(scope, this, this.name, value, !hasFailed(), expected).
					toString());
		}
		return self();
	}

	@Override
	public S isNotInstanceOf(Class<?> unwanted)
	{
		scope.getInternalValidator().requireThat(unwanted, "unwanted").isNotNull();
		if (unwanted.isInstance(value))
		{
			addIllegalArgumentException(
				ObjectMessages.isNotInstanceOf(scope, this, this.name, value, !hasFailed(), unwanted).
					toString());
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
		scope.getInternalValidator().requireThat(name, "name").isStripped();
		if (name.equals(this.name))
		{
			throw new IllegalArgumentException("\"name\" may not be equal to the name of the value.\n" +
			                                   "Actual: " + name);
		}
		return isEqualToImpl(expected, name);
	}

	private S isEqualToImpl(Object expected, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				EqualMessages.isEqualTo(scope, this, this.name, null, false, name, expected).toString());
		}
		else if (EqualMessages.isMixedNullity(value, expected) ||
		         (value != null && !getEqualityFunction().apply(value, expected)))
		{
			addIllegalArgumentException(
				EqualMessages.isEqualTo(scope, this, this.name, value, true, name, expected).toString());
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
		scope.getInternalValidator().requireThat(name, "name").isStripped();
		if (name.equals(this.name))
		{
			throw new IllegalArgumentException("\"name\" may not be equal to the name of the value.\n" +
			                                   "Actual: " + name);
		}
		return isNotEqualToImpl(unwanted, name);
	}

	private S isNotEqualToImpl(Object unwanted, String name)
	{
		if (hasFailed() || (EqualMessages.isSameNullity(value, unwanted) &&
		                    (value == null || getEqualityFunction().apply(value, unwanted))))
		{
			addIllegalArgumentException(
				EqualMessages.isNotEqualTo(scope, this, this.name, name, unwanted).toString());
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
	public StringValidatorImpl asString()
	{
		return new StringValidatorImpl(scope, this, "String.valueOf(" + name + ")", String.valueOf(value));
	}
}