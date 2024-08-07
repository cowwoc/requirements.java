package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.MultipleFailuresException;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.JavaValidators;
import com.github.cowwoc.requirements10.java.internal.message.section.MessageBuilder;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.Exceptions;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.validator.StringValidator;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Validates the state of a value, recording failures without throwing an exception.
 *
 * @param <S> the type of validator that the methods should return
 * @param <T> the type of the value that is being validated
 */
public abstract class AbstractValidator<S, T> implements ValidatorComponent<S, T>
{
	public static final Supplier<IllegalStateException> VALUE_IS_UNDEFINED = () ->
		new IllegalStateException("value is undefined");
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
	protected String name;
	/**
	 * The value being validated.
	 */
	protected MaybeUndefined<T> value;
	/**
	 * The contextual information of this validator.
	 */
	protected final Map<String, Object> context;
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
		MaybeUndefined<T> value, Map<String, Object> context, List<ValidationFailure> failures)
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
		return value.orDefault(defaultValue);
	}

	/**
	 * Consumes the value if it is defined. If the value is undefined, no action is taken.
	 *
	 * @param consumer consumes the value if it is defined
	 */
	public void ifDefined(Consumer<? super T> consumer)
	{
		value.ifDefined(consumer);
	}

	@Override
	public S and(Consumer<? super S> validation)
	{
		if (validation == null)
			throw new NullPointerException("validation may not be null");
		validation.accept(self());
		return self();
	}

	@Override
	public S and(ValidatorComponent<?, ?>... others)
	{
		if (others == null)
			throw new NullPointerException("others may not be null");
		for (ValidatorComponent<?, ?> other : others)
			failures.addAll(other.elseGetFailures());
		return self();
	}

	@Override
	public S or(ValidatorComponent<?, ?>... others)
	{
		if (others == null)
			throw new NullPointerException("others may not be null");
		List<ValidationFailure> newFailures = new ArrayList<>();
		for (ValidatorComponent<?, ?> other : others)
		{
			List<ValidationFailure> otherFailures = other.elseGetFailures();
			if (otherFailures.isEmpty())
			{
				failures.clear();
				return self();
			}
			newFailures.addAll(otherFailures);
		}
		failures.addAll(newFailures);
		return self();
	}

	/**
	 * Adds a validation failure and throws an exception if the validator is configured to throw an exception on
	 * failure. The value is set to undefined.
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
		value = MaybeUndefined.undefined();
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
		ValidationFailureImpl failure = new ValidationFailureImpl(configuration, message, cause,
			exceptionBuilder, Set.of(checkedException));
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
		S self = (S) this;
		return self;
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
		Throwable throwable;
		if (failures.size() == 1)
		{
			ValidationFailure failure = failures.getFirst();
			throwable = failure.getException();
		}
		else
			throwable = new MultipleFailuresException(failures);
		if (configuration.cleanStackTrace())
			Exceptions.removeLibraryFromStackTrace(throwable);
		return throwable;
	}

	@Override
	public Map<String, Object> getContext()
	{
		return Map.copyOf(context);
	}

	@Override
	public S withContext(Object value, String name)
	{
		requireThatNameIsUnique(name, false);
		if (value == null)
			context.remove(name);
		else
			context.put(name, value);
		return self();
	}

	@Override
	public String getContextAsString()
	{
		return new MessageBuilder(this, "").toString();
	}

	@Override
	public StringValidator asString()
	{
		if (value.isNull())
			onNull();
		return new StringValidatorImpl(scope, configuration, "String.valueOf(" + name + ")",
			value.mapDefined(String::valueOf), context, failures);
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
	 * Returns a validator that assumes that the value has the expected type.
	 *
	 * @return the updated validator
	 */
	protected <U> U assumeExpectedType()
	{
		@SuppressWarnings("unchecked")
		U newType = (U) this;
		return newType;
	}

	/**
	 * Invoked by a validation if the value is null. Sets the value to {@code undefined}.
	 */
	protected abstract void onNull();
}