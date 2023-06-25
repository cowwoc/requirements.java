package com.github.cowwoc.requirements.java;

/**
 * Contextual information that is accessible only within the scope's duration, from creation to
 * {@link #close()}.
 */
public interface ScopedContext extends AutoCloseable
{
	/**
	 * Looks up contextual information by its name.
	 * <p>
	 * To check if a parameter exists or has a {@code null} value, use {@link #containsName(String)}.
	 *
	 * @param name the parameter name
	 * @return the value of the parameter or {@code null} if no match was found
	 */
	Object get(String name);

	/**
	 * Returns {@code true} if a parameter exists.
	 *
	 * @param name the parameter name
	 * @return {@code true} if the parameter exists
	 */
	boolean containsName(String name);

	/**
	 * Adds or updates contextual information.
	 *
	 * @param value the parameter value
	 * @param name  the parameter name
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	ScopedContext put(Object value, String name);

	/**
	 * Removes contextual information.
	 *
	 * @param name the parameter name
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 */
	ScopedContext remove(String name);

	/**
	 * Reverses any changes applied to the context.
	 */
	@Override
	void close();
}