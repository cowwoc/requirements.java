package com.github.cowwoc.requirements.java.type.part;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.MultipleFailuresException;
import com.github.cowwoc.requirements.java.MutableStringMappers;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.Validators;
import com.github.cowwoc.requirements.java.type.StringValidator;

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
 */
public interface Validator<S>
{
	/**
	 * Returns the name of the value.
	 *
	 * @return the name of the value
	 */
	String getName();

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
	 * @throws NullPointerException if {@code name} is null
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
	 * @see #and(Validator)
	 */
	S apply(Consumer<? super S> consumer);

	/**
	 * Adds the validation failures from another validator to this one. For example:
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
	S and(Validator<?> other);

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
	 *   <li>Returns {@code null} if the validation passed.</li>
	 *   <li>Returns {@code MultipleFailuresException} if there were multiple failures.</li>
	 *   <li>Returns {@code Throwable} if there was one failure.</li>
	 * </ol>
	 *
	 * @return the exception or null
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
	 * {@link MutableStringMappers#toString(Object)}.
	 *
	 * @return a validator for the String representation of the value
	 */
	StringValidator asString();
}