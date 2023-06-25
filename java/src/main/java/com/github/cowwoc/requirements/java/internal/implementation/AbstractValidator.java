package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.MultipleFailuresException;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.MessageBuilder;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.Exceptions;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Validates the state of a value, recording failures without throwing an exception.
 *
 * @param <S> the type of validator that the methods should return
 */
public abstract class AbstractValidator<S> implements Validator<S>
{
	public static final String DEFAULT_NAME = "value";
	/**
	 * The contextual information of this validator.
	 */
	public final Map<String, Object> context;
	/**
	 * The list of validation failures.
	 */
	public final List<ValidationFailure> failures;
	/**
	 * The application configuration.
	 */
	protected final ApplicationScope scope;
	/**
	 * The validator configuration.
	 */
	protected Configuration configuration;
	/**
	 * the name of the value.
	 */
	protected String name;

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	protected AbstractValidator(ApplicationScope scope, Configuration configuration, String name,
		Map<String, Object> context, List<ValidationFailure> failures)
	{
		assert (scope != null) : "scope may not be null";
		assert (configuration != null) : "config may not be null";

		if (name == null)
			throw new NullPointerException("name may not be null");
		if (!StringValidatorImpl.isStripped(name))
		{
			throw new IllegalArgumentException("name may not contain leading or trailing whitespace.\n" +
			                                   "Actual: \"" + name + "\"");
		}
		if (name.isEmpty())
			throw new IllegalArgumentException("name may not be empty");

		assert (context != null) : "context may not be null";
		assert (failures != null) : "failures may not be null";
		this.scope = scope;
		this.configuration = configuration;
		this.name = name;
		this.context = context;
		this.failures = failures;
	}

	/**
	 * @return the application configuration.
	 */
	public ApplicationScope getScope()
	{
		return scope;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public S apply(Consumer<? super S> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(self());
		return self();
	}

	@Override
	public S and(Validator<?> other)
	{
		failures.addAll(other.elseGetFailures());
		return self();
	}

	/**
	 * Adds a validation failure and throws an exception if the validator is configured to throw an exception on
	 * failure.
	 *
	 * @param message          a message that explains what went wrong
	 * @param cause            the underlying cause of the exception
	 * @param exceptionBuilder creates the exception associated with this failure
	 */
	public void addFailure(String message, Throwable cause, ExceptionBuilder exceptionBuilder)
	{
		ValidationFailureImpl failure = new ValidationFailureImpl(configuration, message, cause,
			exceptionBuilder);
		failures.add(failure);
		if (configuration.throwOnFailure())
		{
			Throwable updatedThrowable = failure.getException();
			switch (updatedThrowable)
			{
				case RuntimeException e -> throw e;
				case Error e -> throw e;
				default -> throw new AssertionError(updatedThrowable);
			}
		}
	}

	/**
	 * Adds a {@code NullPointerException} validation failure and throws an exception if the validator is
	 * configured to throw an exception on failure.
	 *
	 * @param message a message that explains what went wrong
	 */
	protected void addNullPointerException(String message)
	{
		addFailure(message, null, (theMessage, cause) -> new NullPointerException(theMessage));
	}

	/**
	 * Adds an {@code IllegalArgumentException} validation failure and throws an exception if the validator is
	 * configured to throw an exception on failure.
	 *
	 * @param message a message that explains what went wrong
	 */
	protected void addIllegalArgumentException(String message)
	{
		addFailure(message, null, IllegalArgumentException::new);
	}

	/**
	 * Applies the exception transformer to {@code throwable}.
	 *
	 * @param throwable a throwable
	 * @return the updated throwable
	 */
	private Throwable transformException(Throwable throwable)
	{
		Throwable updatedThrowable = configuration.exceptionTransformer().apply(throwable);
		if (updatedThrowable == null)
			return throwable;
		return updatedThrowable;
	}

	@Override
	public Configuration configuration()
	{
		return configuration;
	}

	/**
	 * @return this
	 */
	protected S self()
	{
		@SuppressWarnings("unchecked")
		S castedThis = (S) this;
		return castedThis;
	}

	@Override
	public List<ValidationFailure> elseGetFailures()
	{
		return List.copyOf(failures);
	}

	@Override
	public boolean elseThrow()
	{
		Throwable throwable = elseGetException();
		return switch (throwable)
		{
			case null -> true;
			case RuntimeException e -> throw e;
			case Error e -> throw e;
			default -> throw new AssertionError("Unexpected exception type: " + throwable);
		};
	}

	@Override
	public List<String> elseGetMessages()
	{
		List<String> messages = new ArrayList<>(failures.size());
		for (ValidationFailure failure : failures)
			messages.add(failure.getMessage());
		return messages;
	}

	@Override
	public Throwable elseGetException()
	{
		if (failures.isEmpty())
			return null;
		if (failures.size() == 1)
		{
			ValidationFailure failure = failures.getFirst();
			return failure.getException();
		}
		MultipleFailuresException multipleFailuresException = new MultipleFailuresException(failures);
		if (configuration.cleanStackTrace())
			Exceptions.removeLibraryFromStackTrace(multipleFailuresException);
		return multipleFailuresException;
	}

	/**
	 * Indicates whether a past validation has failed. The value being validated can be used only if no failure
	 * has occurred. A failure results in all subsequent validations failing.
	 *
	 * @return true if a failure occurred
	 */
	protected boolean hasFailed()
	{
		return !failures.isEmpty();
	}

	@Override
	public Map<String, Object> context()
	{
		return Collections.unmodifiableMap(context);
	}

	@Override
	public S context(Object value, String name)
	{
		if (name == null)
			throw new NullPointerException("name may not be null");
		if (name.equals(this.name))
		{
			throw new IllegalArgumentException("\"name\" may not be equal to the name of the value.\n" +
			                                   "Actual: " + name);
		}
		if (value == null)
			context.remove(name);
		else
			context.put(name, value);
		return self();
	}

	@Override
	public String getContextAsString()
	{
		return new MessageBuilder(scope, this, null).toString();
	}
}