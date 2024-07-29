package com.github.cowwoc.requirements10.java.validator.component;

import com.github.cowwoc.requirements10.annotation.CheckReturnValue;
import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.MultipleFailuresException;
import com.github.cowwoc.requirements10.java.StringMappers;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.Validators;
import com.github.cowwoc.requirements10.java.validator.StringValidator;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Methods that all validators must contain.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the {@link Configuration#exceptionTransformer()} setting. This
 * allows users to customize the exception handling behavior of the class.
 *
 * @param <S> the type of validator
 * @param <T> the type of the value that is being validated
 */
public interface ValidatorComponent<S, T>
{
	/**
	 * Returns the name of the value.
	 *
	 * @return the name of the value
	 */
	String getName();

	/**
	 * Returns the value that is being validated.
	 *
	 * @param defaultValue the fallback value in case of a validation failure
	 * @return the value, or {@code defaultValue} if a previous validation failed
	 */
	@CheckReturnValue
	T getValueOrDefault(T defaultValue);

	/**
	 * Returns the validator's configuration.
	 *
	 * @return the validator's configuration
	 */
	Configuration configuration();

	/**
	 * Returns the contextual information for upcoming validations carried out by this validator. The contextual
	 * information is a map of key-value pairs that can provide more details about validation failures. For
	 * example, if the message is "Password may not be empty" and the map contains the key-value pair
	 * {@code {"username": "john.smith"}}, the exception message would be:
	 * <p>
	 * {@snippet lang = output:
	 * Password may not be empty
	 * username: john.smith}
	 *
	 * @return an unmodifiable map from each entry's name to its value
	 * @see Validators#getContext()
	 */
	Map<String, Object> getContext();

	/**
	 * Sets the contextual information for upcoming validations.
	 * <p>
	 * This method adds contextual information to exception messages. The contextual information is stored as
	 * key-value pairs in a map. Values set by this method override any values that are set using
	 * {@link Validators#withContext(Object, String)}.
	 * <p>
	 * There is no way to remove contextual information from a validator. Thread-level contextual information is
	 * removed automatically.
	 *
	 * @param value the value of the entry
	 * @param name  the name of an entry
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name}:
	 *                                  <ul>
	 *                                    <li>contains whitespace</li>
	 *                                    <li>is empty</li>
	 *                                    <li>is already in use by the value being validated or the validator
	 *                                    context</li>
	 *                                  </ul>
	 */
	S withContext(Object value, String name);

	/**
	 * Executes a nested validation. This facilitates validating multiple properties of the same value. For
	 * example:
	 * <p>
	 * {@snippet :
	 * requireThat(nameToFrequency, "nameToFrequency").
	 *   apply(m -> m.size().isPositive()).
	 *   apply(m -> m.keySet().contains("John"));
	 *}
	 * <p>
	 * This validator is shared by nested validations, so any mutations by nested validations affect the
	 * original validator.
	 *
	 * @param consumer the nested validation
	 * @return this
	 * @throws NullPointerException if {@code consumer} is null
	 * @see #and(ValidatorComponent)
	 */
	S apply(Consumer<? super S> consumer);

	/**
	 * Appends the validation failures from another validator to this one. For example,
	 * <p>
	 * {@snippet :
	 * 	  requireThat(name, "name").length().isGreaterThan(5).
	 *      and(requireThat(nameToFrequency, "nameToFrequency").keySet().contains(name));
	 *}
	 *
	 * @param other the other validator
	 * @return this
	 * @see #apply(Consumer)
	 */
	S and(ValidatorComponent<?, ?> other);

	/**
	 * Checks if any validation has failed.
	 *
	 * @return {@code true} if at least one validation has failed
	 */
	boolean validationFailed();

	/**
	 * Returns the list of failed validations.
	 *
	 * @return an unmodifiable list of failed validations
	 */
	List<ValidationFailure> elseGetFailures();

	/**
	 * Throws an exception if a validation failed; otherwise, returns {@code true}.
	 *
	 * @return true if the validation passed
	 * @throws RuntimeException          if a method precondition was violated
	 * @throws Error                     if a class invariant or method postcondition was violated
	 * @throws MultipleFailuresException if more than one validation failed. This exception contains a list of
	 *                                   the failures.
	 * @see ConfigurationUpdater#exceptionTransformer(Function) Changing the type of exception that is thrown
	 */
	boolean elseThrow();

	/**
	 * Returns the validation failure messages.
	 *
	 * @return an empty list if the validation was successful
	 */
	List<String> elseGetMessages();

	/**
	 * Returns the exception for the validation failures, if any.
	 *
	 * <ol>
	 *   <li>Returns {@code null} if no validation has failed.</li>
	 *   <li>Returns {@code MultipleFailuresException} if there were multiple failures.</li>
	 *   <li>Returns {@code Throwable} if there was one failure.</li>
	 * </ol>
	 *
	 * @return the exception or {@code null} if no validation has failed
	 */
	Throwable elseGetException();

	/**
	 * Returns the contextual information associated with this validator.
	 *
	 * @return the contextual information associated with this validator
	 */
	String getContextAsString();

	/**
	 * Returns a validator for the String representation of the value.
	 * <p>
	 * <b>NOTE</b>: This method uses {@link String#valueOf(Object)} for conversion, not
	 * {@link StringMappers#toString(Object)}.
	 *
	 * @return a validator for the String representation of the value
	 */
	StringValidator asString();
}