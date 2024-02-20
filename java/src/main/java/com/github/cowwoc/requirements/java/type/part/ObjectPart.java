package com.github.cowwoc.requirements.java.type.part;

import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;

import java.util.function.Function;

/**
 * Methods that all object validators must contain.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 *
 * @param <S> the type of this validator
 * @param <T> the type of the value that is being validated
 */
public interface ObjectPart<S, T>
{
	/**
	 * Returns the value that is being validated.
	 *
	 * @return the value that is being validated
	 */
	@CheckReturnValue
	T getValue();

	/**
	 * Ensures that the value is null.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the value is not null
	 */
	S isNull();

	/**
	 * Ensures that the value is not null.
	 * <p>
	 * This method should be used to validate method arguments that are assigned to class fields but not
	 * accessed right away (such as constructor and setter arguments). It should also be used to validate any
	 * method arguments when the validator contains
	 * {@link Validator#getContext() additional contextual information}. Otherwise, the default Java handler is
	 * preferable because it throws {@code NullPointerException} with a helpful message.
	 *
	 * @return this
	 * @throws NullPointerException if the value is null
	 */
	S isNotNull();

	/**
	 * Ensures that the value is the same reference {@code expected}.
	 *
	 * @param expected the expected object
	 * @param name     the name of the expected object
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value does not reference the same value as {@code expected}.
	 */
	S isSameReferenceAs(Object expected, String name);

	/**
	 * Ensures that the value is not the same reference as {@code unwanted}.
	 *
	 * @param unwanted the unwanted object
	 * @param name     the name of the unwanted object
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value references the same value as {@code unwanted}
	 */
	S isNotSameReferenceAs(Object unwanted, String name);

	/**
	 * Ensures that the object is an instance of a class.
	 *
	 * @param expected the desired class
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the value is not an instance of the expected class
	 */
	S isInstanceOf(Class<?> expected);

	/**
	 * Ensures that the object is not an instance of a class.
	 *
	 * @param unwanted the unwanted class
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the value is an instance of the unwanted class
	 */
	S isNotInstanceOf(Class<?> unwanted);

	/**
	 * Ensures that the object is equal to {@code expected}.
	 *
	 * @param expected the expected value
	 * @return this
	 * @throws IllegalArgumentException if the value is not equal to {@code expected}
	 * @see <a href="https://github.com/cowwoc/requirements/docs/Textual_Diff.md">An explanation of the output
	 * format</a>
	 * @see ConfigurationUpdater#equalityMethod() The method used to compare objects
	 */
	S isEqualTo(Object expected);

	/**
	 * Ensures that the object is equal to {@code expected}.
	 *
	 * @param expected the expected value
	 * @param name     the name of the expected value
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is not equal to {@code expected}.
	 * @see <a href="https://github.com/cowwoc/requirements/docs/Textual_Diff.md">An explanation of the output
	 * format</a>
	 * @see ConfigurationUpdater#equalityMethod() The method used to compare objects
	 */
	S isEqualTo(Object expected, String name);

	/**
	 * Ensures that the object is not equal to {@code unwanted}.
	 *
	 * @param unwanted the unwanted value
	 * @return this
	 * @see <a href="https://github.com/cowwoc/requirements/docs/Textual_Diff.md">An explanation of the output
	 * format</a>
	 * @see ConfigurationUpdater#equalityMethod() The method used to compare objects
	 */
	S isNotEqualTo(Object unwanted);

	/**
	 * Ensures that the object is not equal to {@code unwanted}.
	 *
	 * @param unwanted the unwanted value
	 * @param name     the name of the other value
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is equal to the {@code unwanted} value.
	 * @see <a href="https://github.com/cowwoc/requirements/docs/Textual_Diff.md">An explanation of the output
	 * format</a>
	 * @see ConfigurationUpdater#equalityMethod() The method used to compare objects
	 */
	S isNotEqualTo(Object unwanted, String name);
}