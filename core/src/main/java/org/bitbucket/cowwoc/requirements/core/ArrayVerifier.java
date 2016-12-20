/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.Collection;
import org.bitbucket.cowwoc.requirements.core.ext.ObjectVerifierExtension;

/**
 * Verifies an array parameter.
 *
 * @param <E> the type of elements in the array
 * @author Gili Tzabari
 */
public interface ArrayVerifier<E>
	extends ObjectVerifierExtension<ArrayVerifier<E>, E[]>, Verifier<ArrayVerifier<E>>
{
	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not empty
	 */
	ArrayVerifier<E> isEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is empty
	 */
	ArrayVerifier<E> isNotEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param element the element that must exist
	 * @return this
	 * @throws IllegalArgumentException if the array does not contain {@code element}
	 */
	ArrayVerifier<E> contains(E element) throws IllegalArgumentException;

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
	ArrayVerifier<E> contains(E element, String name)
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
	ArrayVerifier<E> containsExactly(Collection<E> elements)
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
	ArrayVerifier<E> containsExactly(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value contains any of multiple elements.
	 *
	 * @param elements the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the array does not contain any of {@code elements}
	 */
	ArrayVerifier<E> containsAny(Collection<E> elements)
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
	ArrayVerifier<E> containsAny(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value contains all of multiple elements.
	 *
	 * @param elements the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the array does not contain all of {@code elements}
	 */
	ArrayVerifier<E> containsAll(Collection<E> elements)
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
	ArrayVerifier<E> containsAll(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @return this
	 * @throws IllegalArgumentException if the array contains {@code element}
	 */
	ArrayVerifier<E> doesNotContain(E element) throws IllegalArgumentException;

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
	ArrayVerifier<E> doesNotContain(E element, String name)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain any of multiple elements.
	 *
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the array contains any of {@code elements}
	 */
	ArrayVerifier<E> doesNotContainAny(Collection<E> elements)
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
	ArrayVerifier<E> doesNotContainAny(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain all of multiple elements.
	 *
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the array contains all of {@code elements}
	 */
	ArrayVerifier<E> doesNotContainAll(Collection<E> elements)
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
	ArrayVerifier<E> doesNotContainAll(Collection<E> elements, String name)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain any duplicate elements.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the array contains any duplicate elements
	 */
	ArrayVerifier<E> doesNotContainDuplicates()
		throws IllegalArgumentException;

	/**
	 * @return verifier for the array's length
	 */
	ContainerSizeVerifier length();
}
