package com.github.cowwoc.requirements10.java;

import com.github.cowwoc.requirements10.annotation.CheckReturnValue;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

import java.util.Map;
import java.util.function.Consumer;
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
 * {@code isGreaterThan(value, null)}) are thrown by all validators and cannot be configured. Exceptions that
 * are thrown in response to the value failing a validation check, e.g. {@code isGreaterThan(5)} on a value
 * of 0 are thrown by {@code requireThat()} and {@code assumeThat()} but are recorded by {@code checkIf()}
 * without being thrown. The type of thrown exceptions is configurable using
 * {@link ConfigurationUpdater#exceptionTransformer(Function)}.
 *
 * @param <S> the type of the validator factory
 */
public interface Validators<S>
{
	/**
	 * Returns the configuration used by new validators.
	 *
	 * @return the configuration used by new validators
	 */
	@CheckReturnValue
	Configuration configuration();

	/**
	 * Updates the configuration that will be used by new validators.
	 * <p>
	 * <b>NOTE</b>: Changes are only applied when {@link ConfigurationUpdater#close()} is invoked.
	 *
	 * @return the configuration updater
	 */
	@CheckReturnValue
	ConfigurationUpdater updateConfiguration();

	/**
	 * Updates the configuration that will be used by new validators, using a fluent API that automatically
	 * applies the changes on exit. For example:
	 * {@snippet :
	 * validators.apply(v -> v.updateConfiguration().allowDiff(false)).
	 * requireThat(value, name);
	 *}
	 *
	 * @param consumer the configuration updater
	 * @return this
	 * @throws NullPointerException if {@code consumer} is null
	 */
	S updateConfiguration(Consumer<ConfigurationUpdater> consumer);

	/**
	 * Returns a new factory instance with an independent configuration. This method is commonly used to inherit
	 * and update contextual information from the original factory before passing it into a nested operation.
	 * For example,
	 * {@snippet :
	 * JavaValidators copy = validators.copy();
	 * copy.getContext().put(json.toString(), "json");
	 * nestedOperation(copy);
	 *}
	 *
	 * @return a copy of this factory
	 */
	@CheckReturnValue
	S copy();

	/**
	 * Returns the contextual information inherited by validators created out by this factory. The contextual
	 * information is a map of key-value pairs that can provide more details about validation failures. For
	 * example, if the message is "Password may not be empty" and the map contains the key-value pair
	 * {@code {"username": "john.smith"}}, the exception message would be:
	 * {@snippet lang = output:
	 * Password may not be empty
	 * username: john.smith}
	 *
	 * @return an unmodifiable map from each entry's name to its value
	 */
	Map<String, Object> getContext();

	/**
	 * Sets the contextual information for validators created by this factory.
	 * <p>
	 * This method adds contextual information to exception messages. The contextual information is stored as
	 * key-value pairs in a map. Values set by this method may be overridden by
	 * {@link ValidatorComponent#withContext(Object, String)}}.
	 *
	 * @param value the value of the entry
	 * @param name  the name of an entry
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is not a multiple of {@code factor}</li>
	 *                                  </ul>
	 */
	S withContext(Object value, String name);

	/**
	 * Removes the contextual information of validators created by this factory.
	 *
	 * @param name the parameter name
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name}:
	 *                                  <ul>
	 *                                    <li>contains whitespace</li>
	 *                                    <li>is empty</li>
	 *                                  </ul>
	 */
	S removeContext(String name);

	/**
	 * Returns the global configuration shared by all validators.
	 * <p>
	 * <b>NOTE</b>: Updating this configuration affects existing and new validators.
	 *
	 * @return the global configuration updater
	 */
	@CheckReturnValue
	GlobalConfiguration globalConfiguration();
}