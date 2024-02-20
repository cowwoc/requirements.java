package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.function.Function;

/**
 * A factory that creates different types of validators.
 * <p>
 * There are three kinds of validators:
 * <ul>
 *   <li>{@code requireThat()} for method preconditions.</li>
 *   <li>{@code assumeThat()} for class invariants, and method postconditions.</li>
 *   <li>{@code checkIf()} for method preconditions, class invariants and method postconditions.</li>
 * </ul>
 * <p>
 * {@code requireThat()} and {@code assumeThat()} throw an exception on the first validation failure,
 * while {@code checkIf()} collects multiple validation failures before throwing an exception at the end.
 * {@code checkIf()} is more flexible than the others, but its syntax is more verbose.
 * <p>
 * Exceptions that are thrown in response to invalid method arguments (e.g.
 * {@code isGreaterThan(null, value)} are thrown by all validators and cannot be configured. Exceptions that
 * are thrown in response to the value failing a validation check, e.g. {@code isGreaterThan(5)} on a value
 * of 0, are thrown by {@code requireThat()} and {@code assumeThat()} but are recorded by {@code checkIf()}
 * without being thrown. The type of thrown exceptions is configurable using
 * {@link ConfigurationUpdater#exceptionTransformer(Function)}.
 */
public interface Validators
{
	/**
	 * Returns the configuration used by new validators.
	 *
	 * @return the configuration used by new validators
	 */
	@CheckReturnValue
	Configuration configuration();

	/**
	 * Updates the configuration used by new validators.
	 * <p>
	 * <b>NOTE</b>: Changes are only applied when {@link ConfigurationUpdater#close()} is invoked.
	 *
	 * @return the configuration updater
	 */
	@CheckReturnValue
	ConfigurationUpdater updateConfiguration();

	/**
	 * Returns the contextual information for validations performed by this thread using any validator. The
	 * contextual information is a map of key-value pairs that can provide more details about validation
	 * failures. For example, if the message is "Password may not be empty" and the map contains the key-value
	 * pair {@code {"username": "john.smith"}}, the exception message would be:
	 * <p>
	 * {@snippet lang = output:
	 * Password may not be empty
	 * username: john.smith}
	 * <p>
	 * Values set by this method may be overridden by {@link Validator#putContext(Object, String)}}.
	 * <p>
	 * <b>NOTE</b>: This method affects existing and new validators used by current thread. Changes are
	 * reversed once {@link ScopedContext#close()} is invoked.
	 *
	 * @return the thread context updater
	 */
	@CheckReturnValue
	ScopedContext threadContext();

	/**
	 * Returns the global configuration shared by all validators.
	 * <p>
	 * <b>NOTE</b>: Updating This method affects existing and new validators.
	 *
	 * @return the global configuration updater
	 */
	@CheckReturnValue
	GlobalConfiguration globalConfiguration();
}