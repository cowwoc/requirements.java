package com.github.cowwoc.requirements11.java.validator.component;

/**
 * Methods that all decimal {@code Number} validators must contain.
 *
 * @param <S> the type of this validator
 */
public interface DecimalNumberComponent<S>
{
	/**
	 * Ensures that the value is a whole number.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is {@code null}
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
	 * @throws NullPointerException     if the value is {@code null}
	 * @throws IllegalArgumentException if the value is a whole number
	 */
	S isNotWholeNumber();
}