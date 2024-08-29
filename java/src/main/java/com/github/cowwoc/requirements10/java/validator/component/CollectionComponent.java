package com.github.cowwoc.requirements10.java.validator.component;

import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;

import java.util.Collection;

/**
 * Methods that all {@code Collection} validators must contain.
 *
 * @param <S> the type of this validator
 * @param <E> the type of elements in the collection
 */
public interface CollectionComponent<S, E>
{
	/**
	 * Ensures that the collection is empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is {@code null}
	 * @throws IllegalArgumentException if the collection is not empty
	 */
	S isEmpty();

	/**
	 * Ensures that the collection is not empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is {@code null}
	 * @throws IllegalArgumentException if the collection is empty
	 */
	S isNotEmpty();

	/**
	 * Ensures that the collection contains an element.
	 *
	 * @param expected the element
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the collection does not contain {@code expected}
	 */
	S contains(E expected);

	/**
	 * Ensures that the collection does not contain {@code unwanted}.
	 *
	 * @param unwanted the unwanted element
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the collection contains {@code unwanted}
	 */
	S doesNotContain(E unwanted);

	/**
	 * Ensures that the collection contains an element.
	 *
	 * @param expected the element
	 * @param name     the name of the element
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the collection does not contain {@code expected}</li>
	 *                                  </ul>
	 */
	S contains(E expected, String name);

	/**
	 * Ensures that the collection does not contain {@code unwanted}.
	 *
	 * @param unwanted the unwanted element
	 * @param name     the name of the element
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the collection contains {@code unwanted}</li>
	 *                                  </ul>
	 */
	S doesNotContain(E unwanted, String name);

	/**
	 * Ensures that the collection consists of the same elements as {@code expected}, irrespective of their
	 * order.
	 * <p>
	 * In contrast, {@link ObjectComponent#isEqualTo(Object) isEqualTo()} requires the same element ordering.
	 * <p>
	 * This method is provided for convenience, without any implied performance benefits compared to
	 * {@link #containsExactly(Collection)}
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>the collection is missing any element in {@code expected}</li>
	 *                                    <li>the collection contains any element that is not in
	 *                                    {@code expected}</li>
	 *                                  </ul>
	 */
	S containsExactly(E[] expected);

	/**
	 * Ensures that the collection and {@code unwanted} consist of different elements, irrespective of their
	 * order.
	 * <p>
	 * This method is provided for convenience, without any implied performance benefits compared to
	 * {@link #doesNotContainExactly(Collection)}
	 *
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the collection consists of the same elements as {@code unwanted},
	 *                                  irrespective of their order
	 */
	S doesNotContainExactly(E[] unwanted);

	/**
	 * Ensures that the collection consists of the same elements as {@code expected}, irrespective of their
	 * order.
	 * <p>
	 * In contrast, {@link ObjectComponent#isEqualTo(Object) isEqualTo()} requires the same element ordering.
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>the collection is missing any element in {@code expected}</li>
	 *                                    <li>the collection contains any element that is not in
	 *                                    {@code expected}</li>
	 *                                  </ul>
	 */
	S containsExactly(Collection<E> expected);

	/**
	 * Ensures that the collection and {@code unwanted} consist of different elements, irrespective of their
	 * order.
	 *
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the collection consists of the same elements as {@code unwanted},
	 *                                  irrespective of their order
	 */
	S doesNotContainExactly(Collection<E> unwanted);

	/**
	 * Ensures that the collection consists of the same elements as {@code expected}, irrespective of their
	 * order.
	 * <p>
	 * In contrast, {@link ObjectComponent#isEqualTo(Object) isEqualTo()} requires the same element ordering.
	 * <p>
	 * This method is provided for convenience, without any implied performance benefits compared to
	 * {@link #containsExactly(Collection, String)}
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the collection and {@code expected} contain different elements,
	 *                                    irrespective of their order</li>
	 *                                  </ul>
	 */
	S containsExactly(E[] expected, String name);

	/**
	 * Ensures that the collection and {@code unwanted} consist of different elements, irrespective of their
	 * order.
	 * <p>
	 * This method is provided for convenience, without any implied performance benefits compared to
	 * {@link #doesNotContainExactly(Collection, String)}
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the collection consists of the same elements as {@code unwanted},
	 *                                    irrespective of their order</li>
	 *                                  </ul>
	 */
	S doesNotContainExactly(E[] unwanted, String name);

	/**
	 * Ensures that the collection consists of the same elements as {@code expected}, irrespective of their
	 * order.
	 * <p>
	 * In contrast, {@link ObjectComponent#isEqualTo(Object) isEqualTo()} requires the same element ordering.
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the collection and {@code expected} contain different elements,
	 *                                    irrespective of their order</li>
	 *                                  </ul>
	 */
	S containsExactly(Collection<E> expected, String name);

	/**
	 * Ensures that the collection and {@code unwanted} consist of different elements, irrespective of their
	 * order.
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the collection consists of the same elements as {@code unwanted},
	 *                                    irrespective of their order</li>
	 *                                  </ul>
	 */
	S doesNotContainExactly(Collection<E> unwanted, String name);

	/**
	 * Ensures that the collection contains any elements in {@code expected}.
	 * <p>
	 * This method is provided for convenience, without any implied performance benefits compared to
	 * {@link #containsAny(Collection)}
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the collection does not contain any element in {@code expected}
	 */
	S containsAny(E[] expected);

	/**
	 * Ensures that the collection does not contain any of the elements in {@code unwanted}.
	 * <p>
	 * This method is provided for convenience, without any implied performance benefits compared to
	 * {@link #doesNotContainAny(Collection)}
	 *
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the collection contains any of the elements in {@code unwanted}
	 */
	S doesNotContainAny(E[] unwanted);

	/**
	 * Ensures that the collection contains any elements in {@code expected}.
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the collection does not contain any element in {@code expected}
	 */
	S containsAny(Collection<E> expected);

	/**
	 * Ensures that the collection does not contain any of the elements in {@code unwanted}.
	 *
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the collection contains any of the elements in {@code unwanted}
	 */
	S doesNotContainAny(Collection<E> unwanted);

	/**
	 * Ensures that the collection contains at least one element in {@code expected}.
	 * <p>
	 * This method is provided for convenience, without any implied performance benefits compared to
	 * {@link #containsAny(Collection, String)}
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the collection does not contain any element in
	 *                                    {@code expected}</li>
	 *                                  </ul>
	 */
	S containsAny(E[] expected, String name);

	/**
	 * Ensures that the collection does not contain any of the elements in {@code unwanted}.
	 * <p>
	 * This method is provided for convenience, without any implied performance benefits compared to
	 * {@link #doesNotContainAny(Collection, String)}
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the collection contains any of the elements in
	 *                                    {@code unwanted}</li>
	 *                                  </ul>
	 */
	S doesNotContainAny(E[] unwanted, String name);

	/**
	 * Ensures that the collection contains at least one element in {@code expected}.
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the collection does not contain any element in
	 *                                    {@code expected}</li>
	 *                                  </ul>
	 */
	S containsAny(Collection<E> expected, String name);

	/**
	 * Ensures that the collection does not contain any of the elements in {@code unwanted}.
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the collection contains any of the elements in
	 *                                    {@code unwanted}</li>
	 *                                  </ul>
	 */
	S doesNotContainAny(Collection<E> unwanted, String name);

	/**
	 * Ensures that the collection contains all the elements in {@code expected}.
	 * <p>
	 * This method is provided for convenience, without any implied performance benefits compared to
	 * {@link #containsAll(Collection)}
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the collection does not contain all the elements in {@code expected}
	 */
	S containsAll(E[] expected);

	/**
	 * Allows the collection to contain some, but not all, elements from a collection.
	 * <p>
	 * This method is provided for convenience, without any implied performance benefits compared to
	 * {@link #doesNotContainAll(Collection)}
	 *
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the collection contains all the elements in {@code unwanted}
	 */
	S doesNotContainAll(E[] unwanted);

	/**
	 * Ensures that the collection contains all the elements in {@code expected}.
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the collection does not contain all the elements in {@code expected}
	 */
	S containsAll(Collection<E> expected);

	/**
	 * Allows the collection to contain some, but not all, elements from another collection.
	 *
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the collection contains all the elements in {@code unwanted}
	 */
	S doesNotContainAll(Collection<E> unwanted);

	/**
	 * Ensures that the collection contains all the elements in {@code expected}.
	 * <p>
	 * This method is provided for convenience, without any implied performance benefits compared to
	 * {@link #containsAll(Collection, String)}
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the collection does not contain all elements in
	 *                                    {@code expected}</li>
	 *                                  </ul>
	 */
	S containsAll(E[] expected, String name);

	/**
	 * Allows the collection to contain some, but not all, elements from a collection.
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the collection contains all the elements in {@code unwanted}</li>
	 *                                  </ul>
	 */
	S doesNotContainAll(E[] unwanted, String name);

	/**
	 * Ensures that the collection contains all the elements in {@code expected}.
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the collection does not contain all elements in
	 *                                    {@code expected}</li>
	 *                                  </ul>
	 */
	S containsAll(Collection<E> expected, String name);

	/**
	 * Allows the collection to contain some, but not all, elements from another collection.
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the collection contains all the elements in {@code unwanted}</li>
	 *                                  </ul>
	 */
	S doesNotContainAll(Collection<E> unwanted, String name);

	/**
	 * Ensures that the collection contains only null values, or only non-null values.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is {@code null}
	 * @throws IllegalArgumentException if the collection contains a mix of {@code null} and non-{@code null}
	 *                                  values
	 */
	S containsSameNullity();

	/**
	 * Ensures that the collection does not contain any duplicate elements.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is {@code null}
	 * @throws IllegalArgumentException if the collection contains any duplicate elements
	 */
	S doesNotContainDuplicates();

	/**
	 * Returns a validator for the collection's size.
	 *
	 * @return a validator for the collection's size
	 * @throws NullPointerException if the value is {@code null}
	 */
	PrimitiveUnsignedIntegerValidator size();
}