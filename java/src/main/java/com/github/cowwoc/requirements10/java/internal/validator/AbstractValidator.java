package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.JavaValidators;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.ValidationFailures;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.ValidationTarget;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder.quoteName;

/**
 * Validates the state of a value, recording failures without throwing an exception.
 *
 * @param <S> the type of validator that the methods should return
 * @param <T> the type of the value that is being validated
 */
public abstract class AbstractValidator<S, T> implements ValidatorComponent<S, T>
{
	public static final Supplier<IllegalStateException> VALUE_IS_UNDEFINED = () ->
		new IllegalStateException("value is invalid");
	/**
	 * The application configuration.
	 */
	protected final ApplicationScope scope;
	/**
	 * The validator configuration.
	 */
	protected final Configuration configuration;
	/**
	 * The name of the value.
	 */
	protected final String name;
	/**
	 * The value being validated.
	 */
	public final ValidationTarget<T> value;
	/**
	 * The contextual information of this validator. Values are wrapped in an {@code Optional}.
	 */
	protected final Map<String, Optional<Object>> context;
	/**
	 * The list of validation failures.
	 */
	protected final List<ValidationFailure> failures;

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
	protected AbstractValidator(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<T> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		assert (scope != null) : "scope may not be null";
		assert (configuration != null) : "configuration may not be null";

		if (name == null)
			throw new NullPointerException("name may not be null");
		if (name.isEmpty())
			throw new IllegalArgumentException("name may not be empty");
		if (containsWhitespace(name))
		{
			throw new IllegalArgumentException("name may not contain whitespace.\n" +
				"actual: \"" + name + "\"");
		}

		assert (value != null) : "value may not be null";
		assert (context != null) : "context may not be null";
		assert (failures != null) : "failures may not be null";
		this.scope = scope;
		this.configuration = configuration;
		this.name = name;
		this.value = value;
		this.context = context;
		this.failures = failures;
	}

	/**
	 * @param value a string
	 * @return {@code true} if the value contains any whitespace characters
	 */
	private static boolean containsWhitespace(String value)
	{
		int length = value.length();
		if (length == 0)
			return true;
		for (int i = 0; i < length; ++i)
		{
			int codepoint = value.codePointAt(i);
			if (Character.isWhitespace(codepoint))
				return true;
		}
		return false;
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
	public boolean validationFailed()
	{
		return !failures.isEmpty();
	}

	@Override
	public T getValueOrDefault(T defaultValue)
	{
		return value.or(defaultValue);
	}

	@Override
	public S and(Consumer<? super S> validation)
	{
		if (validation == null)
			throw new NullPointerException("validation may not be null");
		validation.accept(self());
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
			exceptionBuilder, Set.of());
		failures.add(failure);
		if (configuration.throwOnFailure())
		{
			Throwable throwable = failure.getException();
			switch (throwable)
			{
				case RuntimeException e -> throw e;
				case Error e -> throw e;
				default -> throw new AssertionError(throwable);
			}
		}
	}

	/**
	 * Adds a validation failure and throws an exception if the validator is configured to throw an exception on
	 * failure.
	 *
	 * @param <E>              a checked exception that is thrown by the validation method
	 * @param message          a message that explains what went wrong
	 * @param cause            the underlying cause of the exception
	 * @param exceptionBuilder creates the exception associated with this failure
	 * @param checkedException a checked exception that is thrown by the validation method
	 */
	public <E extends Exception> void addFailure(String message, Throwable cause,
		ExceptionBuilder exceptionBuilder, Class<E> checkedException) throws E
	{
		ValidationFailureImpl failure = new ValidationFailureImpl(configuration, message, cause, exceptionBuilder,
			Set.of(checkedException));
		failures.add(failure);
		if (configuration.throwOnFailure())
		{
			Throwable throwable = failure.getException();
			switch (throwable)
			{
				case RuntimeException e -> throw e;
				case Error e -> throw e;
				default ->
				{
					Class<? extends Throwable> type = throwable.getClass();
					if (checkedException.isAssignableFrom(type))
					{
						@SuppressWarnings("unchecked")
						E e = (E) throwable;
						throw e;
					}
					throw new AssertionError(throwable);
				}
			}
		}
	}

	/**
	 * Adds a {@code NullPointerException} validation failure and throws an exception if the validator is
	 * configured to throw an exception on failure.
	 *
	 * @param message a message that explains what went wrong
	 * @throws NullPointerException if {@link Configuration#throwOnFailure()}
	 */
	protected void addNullPointerException(String message)
	{
		addFailure(message, null, (theMessage, cause) ->
		{
			NullPointerException newException = new NullPointerException(theMessage);
			if (cause != null)
				newException.initCause(cause);
			return newException;
		});
	}

	/**
	 * Equivalent to
	 * {@link #addIllegalArgumentException(String, Throwable) addIllegalArgumentException(message, null)}.
	 *
	 * @param message a message that explains what went wrong
	 * @throws IllegalArgumentException if {@link Configuration#throwOnFailure()}
	 */
	protected void addIllegalArgumentException(String message)
	{
		addFailure(message, null, IllegalArgumentException::new);
	}

	/**
	 * Adds an {@code IllegalArgumentException} validation failure and throws an exception if the validator is
	 * configured to throw an exception on failure.
	 *
	 * @param message a message that explains what went wrong
	 * @param cause   the underlying cause of the exception
	 * @throws IllegalArgumentException if {@link Configuration#throwOnFailure()}
	 */
	protected void addIllegalArgumentException(String message, Throwable cause)
	{
		addFailure(message, cause, IllegalArgumentException::new);
	}

	/**
	 * Adds an {@code IllegalArgumentException} validation failure and throws an exception if the validator is
	 * configured to throw an exception on failure.
	 *
	 * @param message a message that explains what went wrong
	 * @param cause   the underlying cause of the exception
	 * @throws IOException if {@link Configuration#throwOnFailure()}
	 */
	protected void addIOException(String message, IOException cause) throws IOException
	{
		addFailure(message, cause, IOException::new, IOException.class);
	}

	/**
	 * Returns the validator's configuration.
	 *
	 * @return the validator's configuration
	 */
	public Configuration configuration()
	{
		return configuration;
	}

	/**
	 * Returns this validator as the expected type.
	 *
	 * @param <U> the expected return type
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected <U> U self()
	{
		return (U) this;
	}

	@Override
	public ValidationFailures elseGetFailures()
	{
		return new ValidationFailures(configuration().cleanStackTrace(), failures);
	}

	@Override
	public boolean elseThrow()
	{
		Throwable throwable = elseGetFailures().getException();
		return switch (throwable)
		{
			case null -> true;
			case RuntimeException e -> throw e;
			case Error e -> throw e;
			default -> throw new AssertionError("Unexpected exception type: " + throwable);
		};
	}

	@Override
	public Map<String, Optional<Object>> getContext()
	{
		return Map.copyOf(context);
	}

	@Override
	public S withContext(Object value, String name)
	{
		requireThatNameIsUnique(name, false);
		context.put(name, Optional.ofNullable(value));
		return self();
	}

	@Override
	public String getContextAsString()
	{
		return new MessageBuilder(this, "").toString();
	}

	/**
	 * Ensures that a name does not conflict with other variable names that are already in use by the
	 * validator.
	 *
	 * @param name the name of the parameter
	 * @return the internal validator of the name
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name}:
	 *                                  <ul>
	 *                                    <li>contains whitespace</li>
	 *                                    <li>is empty</li>
	 *                                    <li>is already in use by the value being validated or the validator
	 *                                    context</li>
	 *                                  </ul>
	 */
	protected JavaValidators requireThatNameIsUnique(String name)
	{
		return requireThatNameIsUnique(name, true);
	}

	/**
	 * Ensures that a name does not conflict with other variable names that are already in use by the
	 * validator.
	 *
	 * @param name         the name of the parameter
	 * @param checkContext {@code false} to allow the name to be used even if it conflicts with an existing name
	 *                     in the validator context
	 * @return the internal validator of the name
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name}:
	 *                                  <ul>
	 *                                    <li>contains whitespace</li>
	 *                                    <li>is empty</li>
	 *                                    <li>is already in use by the value being validated or the validator
	 *                                    context</li>
	 *                                  </ul>
	 */
	protected JavaValidators requireThatNameIsUnique(String name, boolean checkContext)
	{
		JavaValidators internalValidators = scope.getInternalValidators();
		internalValidators.requireThat(name, "name").isNotEmpty();
		if (containsWhitespace(name))
			throw new IllegalArgumentException("name may not contain whitespace");

		if (name.equals(this.name))
		{
			throw new IllegalArgumentException("The name \"" + name + "\" is already in use by the value " +
				"being validated. Choose a different name.");
		}
		if (checkContext && context.containsKey(name))
		{
			throw new IllegalArgumentException("The name \"" + name + "\" is already in use by the validator " +
				"context. Choose a different name.");
		}
		return internalValidators;
	}

	/**
	 * @param name        the name of the value ({@code null} if undefined)
	 * @param namePrefix  the string to prepend to the name if the name is undefined
	 * @param value       a value
	 * @param valuePrefix the string to prepend to the value if the name is undefined
	 * @return the prefixed name if it is defined; otherwise, the prefixed string representation of the value
	 */
	public String getNameOrValue(String namePrefix, String name, String valuePrefix, Object value)
	{
		if (name == null)
			return valuePrefix + configuration().stringMappers().toString(value);
		return namePrefix + quoteName(name);
	}

	/**
	 * Fails the validation if the value is "null".
	 */
	protected abstract void failOnNull();
}