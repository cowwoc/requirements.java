package com.github.cowwoc.requirements.java.type.part;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.type.ListValidator;
import com.github.cowwoc.requirements.java.type.ObjectArrayValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveUnsignedIntegerValidator;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Methods that all {@code Collection} validators must contain.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 *
 * @param <S> the type of this validator
 * @param <E> the type of elements in the collection
 */
public interface CollectionPart<S extends CollectionPart<S, E>, E>
{
	/**
	 * Ensures that the collection is empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the collection is not empty
	 */
	S isEmpty();

	/**
	 * Ensures that the collection is not empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the collection is empty
	 */
	S isNotEmpty();

	/**
	 * Ensures that the collection contains an element.
	 *
	 * @param expected the element
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the collection does not contain {@code expected}
	 */
	S contains(E expected);

	/**
	 * Ensures that the collection contains an element.
	 *
	 * @param expected the element
	 * @param name     the name of the element
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the collection does not contain {@code expected}.
	 */
	S contains(E expected, String name);

	/**
	 * Ensures that the collection consists of the same elements as {@code expected}, irrespective of their
	 * order.
	 * <p>
	 * In contrast, {@link ObjectPart#isEqualTo(Object) isEqualTo()} requires the same element ordering.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #containsExactly(Collection)}
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the collection is missing any element in {@code expected}; if the
	 *                                  collection contains any element that is not in {@code expected}
	 */
	S containsExactly(E[] expected);

	/**
	 * Ensures that the collection consists of the same elements as {@code expected}, irrespective of their
	 * order.
	 * <p>
	 * In contrast, {@link ObjectPart#isEqualTo(Object) isEqualTo()} requires the same element ordering.
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the collection is missing any element in {@code expected}; if the
	 *                                  collection contains any element that is not in {@code expected}
	 */
	S containsExactly(Collection<E> expected);

	/**
	 * Ensures that the collection consists of the same elements as {@code expected}, irrespective of their
	 * order.
	 * <p>
	 * In contrast, {@link ObjectPart#isEqualTo(Object) isEqualTo()} requires the same element ordering.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #containsExactly(Collection, String)}
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected array
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the collection and {@code expected} contain different elements,
	 *                                  irrespective of their order.
	 */
	S containsExactly(E[] expected, String name);

	/**
	 * Ensures that the collection consists of the same elements as {@code expected}, irrespective of their
	 * order.
	 * <p>
	 * In contrast, {@link ObjectPart#isEqualTo(Object) isEqualTo()} requires the same element ordering.
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the collection and {@code expected} contain different elements,
	 *                                  irrespective of their order.
	 */
	S containsExactly(Collection<E> expected, String name);

	/**
	 * Ensures that the collection contains any elements in {@code expected}.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #containsAny(Collection)}
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the collection does not contain any element in {@code expected}
	 */
	S containsAny(E[] expected);

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
	 * Ensures that the collection contains at least one element in {@code expected}.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #containsAny(Collection, String)}
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected array
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the collection does not contain any element in {@code expected}.
	 */
	S containsAny(E[] expected, String name);

	/**
	 * Ensures that the collection contains at least one element in {@code expected}.
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the collection does not contain any element in {@code expected}.
	 */
	S containsAny(Collection<E> expected, String name);

	/**
	 * Ensures that the collection contains all the elements in {@code expected}.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #containsAll(Collection)}
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the collection does not contain all the elements in {@code expected}
	 */
	S containsAll(E[] expected);

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
	 * Ensures that the collection contains all the elements in {@code expected}.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #containsAll(Collection, String)}
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected array
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the collection does not contain all elements in {@code expected}.
	 */
	S containsAll(E[] expected, String name);

	/**
	 * Ensures that the collection contains all the elements in {@code expected}.
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the collection does not contain all elements in {@code expected}.
	 */
	S containsAll(Collection<E> expected, String name);

	/**
	 * Ensures that the collection contains only null values, or only non-null values.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the collection contains a mix of null and non-null values
	 */
	S containsSameNullity();

	/**
	 * Ensures that the collection does not contain {@code unwanted}.
	 *
	 * @param unwanted the unwanted element
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the collection contains {@code unwanted}
	 */
	S doesNotContain(E unwanted);

	/**
	 * Ensures that the collection does not contain {@code unwanted}.
	 *
	 * @param unwanted the unwanted element
	 * @param name     the name of the element
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the collection contains {@code unwanted}.
	 */
	S doesNotContain(E unwanted, String name);

	/**
	 * Ensures that the collection and {@code unwanted} consist of different elements, irrespective of their
	 * order.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
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
	 * Ensures that the collection and {@code unwanted} consist of different elements, irrespective of their
	 * order.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #doesNotContainExactly(Collection, String)}
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted array
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the collection consists of the same elements as {@code unwanted},
	 *                                  irrespective of their order
	 */
	S doesNotContainExactly(E[] unwanted, String name);

	/**
	 * Ensures that the collection and {@code unwanted} consist of different elements, irrespective of their
	 * order.
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the collection consists of the same elements as {@code unwanted},
	 *                                  irrespective of their order
	 */
	S doesNotContainExactly(Collection<E> unwanted, String name);

	/**
	 * Ensures that the collection does not contain any of the elements in {@code unwanted}.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #doesNotContainAny(Collection)}
	 *
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the collection contains any of the elements in {@code unwanted}
	 */
	S doesNotContainAny(E[] unwanted);

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
	 * Ensures that the collection does not contain any of the elements in {@code unwanted}.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #doesNotContainAny(Collection, String)}
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted array
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the collection contains any of the elements in {@code unwanted}.
	 */
	S doesNotContainAny(E[] unwanted, String name);

	/**
	 * Ensures that the collection does not contain any of the elements in {@code unwanted}.
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the collection contains any of the elements in {@code unwanted}.
	 */
	S doesNotContainAny(Collection<E> unwanted, String name);

	/**
	 * Allows the collection to contain some, but not all, elements from an array.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #doesNotContainAll(Collection)}
	 *
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the collection contains all the elements in {@code unwanted}
	 */
	S doesNotContainAll(E[] unwanted);

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
	 * Allows the collection to contain some, but not all, elements from an array.
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted array
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the collection contains all the elements in {@code unwanted}.
	 */
	S doesNotContainAll(E[] unwanted, String name);

	/**
	 * Allows the collection to contain some, but not all, elements from another collection.
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the collection contains all the elements in {@code unwanted}.
	 */
	S doesNotContainAll(Collection<E> unwanted, String name);

	/**
	 * Ensures that the collection does not contain any duplicate elements.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the collection contains any duplicate elements
	 */
	S doesNotContainDuplicates();

	/**
	 * Returns a validator for the collection's size.
	 *
	 * @return a validator for the collection's size
	 * @throws NullPointerException if the value is null
	 */
	PrimitiveUnsignedIntegerValidator size();

	/**
	 * Returns a validator for an array that contains the collection's elements.
	 *
	 * @param type the type of elements in the collection
	 * @return a validator for the array
	 * @throws NullPointerException if the value or {@code type} are null
	 */
	ObjectArrayValidator<E, E[]> asArray(Class<E> type);

	/**
	 * Returns a validator for the collection as a list.
	 *
	 * @return a validator for the collection as a list
	 * @throws NullPointerException if the value is null
	 */
	ListValidator<E, List<E>> asList();
}