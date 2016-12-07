/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

import java.util.Collection;
import org.bitbucket.cowwoc.requirements.ContainerSizeVerifier;

/**
 * Verifies an array parameter.
 * <p>
 * @param <S> the type of the non-SPI interface extending this interface
 * @param <E> the type of elements in the array
 * @author Gili Tzabari
 */
public interface ArrayVerifierSpi<S extends ArrayVerifierSpi<S, E>, E>
	extends ObjectVerifierSpi<S, E[]>
{
	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not empty
	 */
	S isEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is empty
	 */
	S isNotEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param element the element that must exist
	 * @return this
	 * @throws IllegalArgumentException if the array does not contain {@code element}
	 */
	S contains(E element) throws IllegalArgumentException;

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param element the element that must exist
	 * @param name    the name of the element
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the array does not contain {@code element}; if
	 *                                  {@code name} is empty
	 */
	S contains(E element, String name)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value contains exactly the specified elements; nothing less, nothing more.
	 *
	 * @param elements the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the array is missing any elements in {@code elements};
	 *                                  if the array contains elements not found in
	 *                                  {@code elements}
	 */
	S containsExactly(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value contains exactly the specified elements; nothing less, nothing more.
	 *
	 * @param elements the elements that must exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the array is missing any elements in {@code elements};
	 *                                  if the array contains elements not found in
	 *                                  {@code elements}; if {@code name} is empty
	 */
	S containsExactly(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value contains any of multiple elements.
	 *
	 * @param elements the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the array does not contain any of {@code elements}
	 */
	S containsAny(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value contains any of multiple elements.
	 *
	 * @param elements the elements that must exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the array does not contain any of {@code elements}; if
	 *                                  {@code name} is empty
	 */
	S containsAny(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value contains all of multiple elements.
	 *
	 * @param elements the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the array does not contain all of {@code elements}
	 */
	S containsAll(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value contains all of multiple elements.
	 *
	 * @param elements the elements that must exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the array does not contain all of {@code elements}; if
	 *                                  {@code name} is empty
	 */
	S containsAll(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @return this
	 * @throws IllegalArgumentException if the array contains {@code element}
	 */
	S doesNotContain(E element) throws IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @param name    the name of the element
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the array contains {@code element}; if {@code name} is
	 *                                  empty
	 */
	S doesNotContain(E element, String name)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain any of multiple elements.
	 *
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the array contains any of {@code elements}
	 */
	S doesNotContainAny(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain any of multiple elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the array contains any of {@code elements}; if
	 *                                  {@code name} is empty
	 */
	S doesNotContainAny(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain all of multiple elements.
	 *
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the array contains all of {@code elements}
	 */
	S doesNotContainAll(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain all of multiple elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the array contains all of {@code elements}; if
	 *                                  {@code name} is empty
	 */
	S doesNotContainAll(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain any duplicate elements.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the array contains any duplicate elements
	 */
	S doesNotContainDuplicates()
		throws IllegalArgumentException;

	/**
	 * @return verifier for the array's length
	 */
	ContainerSizeVerifier length();
}
