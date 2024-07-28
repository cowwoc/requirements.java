package com.github.cowwoc.requirements.java.validator.component;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;

import java.util.function.Function;

/**
 * Methods that all decimal {@code Number} validators must contain.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 *
 * @param <S> the type of this validator
 */
public interface DecimalNumberComponent<S>
{
	/**
	 * Ensures that the value is a whole number.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>not a whole number</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	S isWholeNumber();

	/**
	 * Ensures that the value is not a whole number.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is a whole number
	 */
	S isNotWholeNumber();
}