package com.github.cowwoc.requirements.java.type.part;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.type.CollectionValidator;
import com.github.cowwoc.requirements.java.type.ListValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveUnsignedIntegerValidator;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * Methods that all array validators must contain.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 *
 * @param <S> the type this validator
 * @param <E> the type of elements in the array
 * @param <T> the type of the array
 */
public interface ArrayPart<S extends ArrayPart<S, E, T>, E, T>
{
	/**
	 * Ensures that the array is empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the array is not empty
	 */
	S isEmpty();

	/**
	 * Ensures that the array is not empty.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the array is empty
	 */
	S isNotEmpty();

	/**
	 * Ensures that the array contains an element.
	 *
	 * @param expected the element
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the array does not contain {@code expected}
	 */
	S contains(E expected);

	/**
	 * Ensures that the array contains an element.
	 *
	 * @param expected the element
	 * @param name     the name of the element
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the array does not contain {@code expected}.
	 */
	S contains(E expected, String name);

	/**
	 * Ensures that the array consists of the same elements as {@code expected}, irrespective of their order.
	 * <p>
	 * In contrast, {@link ObjectPart#isEqualTo(Object) isEqualTo()} requires the same element ordering.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #containsExactly(Collection)}
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the array is missing any element in {@code expected}; if the array
	 *                                  contains any element that is not in {@code expected}
	 */
	S containsExactly(T expected);

	/**
	 * Ensures that the array consists of the same elements as {@code expected}, irrespective of their order.
	 * <p>
	 * In contrast, {@link ObjectPart#isEqualTo(Object) isEqualTo()} requires the same element ordering.
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the array is missing any element in {@code expected}; if the array
	 *                                  contains any element that is not in {@code expected}
	 */
	S containsExactly(Collection<E> expected);

	/**
	 * Ensures that the array consists of the same elements as {@code expected}, irrespective of their order.
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
	 *                                  the array and {@code expected} contain different elements, irrespective
	 *                                  of their order.
	 */
	S containsExactly(T expected, String name);

	/**
	 * Ensures that the array consists of the same elements as {@code expected}, irrespective of their order.
	 * <p>
	 * In contrast, {@link ObjectPart#isEqualTo(Object) isEqualTo()} requires the same element ordering.
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the array and {@code expected} contain different elements, irrespective
	 *                                  of their order.
	 */
	S containsExactly(Collection<E> expected, String name);

	/**
	 * Ensures that the array contains any elements in {@code expected}.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #containsAny(Collection)}
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the array does not contain any element in {@code expected}
	 */
	S containsAny(T expected);

	/**
	 * Ensures that the array contains any elements in {@code expected}.
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the array does not contain any element in {@code expected}
	 */
	S containsAny(Collection<E> expected);

	/**
	 * Ensures that the array contains at least one element in {@code expected}.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #containsAny(Collection, String)}
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected array
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the array does not contain any element in {@code expected}.
	 */
	S containsAny(T expected, String name);

	/**
	 * Ensures that the array contains at least one element in {@code expected}.
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the array does not contain any element in {@code expected}.
	 */
	S containsAny(Collection<E> expected, String name);

	/**
	 * Ensures that the array contains all the elements in {@code expected}.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #containsAll(Collection)}
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the array does not contain all the elements in {@code expected}
	 */
	S containsAll(T expected);

	/**
	 * Ensures that the array contains all the elements in {@code expected}.
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the array does not contain all the elements in {@code expected}
	 */
	S containsAll(Collection<E> expected);

	/**
	 * Ensures that the array contains all the elements in {@code expected}.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #containsAll(Collection, String)}
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected array
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the array does not contain all elements in {@code expected}.
	 */
	S containsAll(T expected, String name);

	/**
	 * Ensures that the array contains all the elements in {@code expected}.
	 *
	 * @param expected the desired elements
	 * @param name     the name of the expected collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the array does not contain all elements in {@code expected}.
	 */
	S containsAll(Collection<E> expected, String name);

	/**
	 * Ensures that the array does not contain {@code unwanted}.
	 *
	 * @param unwanted the unwanted element
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if the array contains {@code unwanted}
	 */
	S doesNotContain(E unwanted);

	/**
	 * Ensures that the array does not contain {@code unwanted}.
	 *
	 * @param unwanted the unwanted element
	 * @param name     the name of the element
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the array contains {@code unwanted}.
	 */
	S doesNotContain(E unwanted, String name);

	/**
	 * Ensures that the array and {@code unwanted} consist of different elements, irrespective of their order.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #doesNotContainExactly(Collection)}
	 *
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the array consists of the same elements as {@code unwanted},
	 *                                  irrespective of their order
	 */
	S doesNotContainExactly(T unwanted);

	/**
	 * Ensures that the array and {@code unwanted} consist of different elements, irrespective of their order.
	 *
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the array consists of the same elements as {@code unwanted},
	 *                                  irrespective of their order
	 */
	S doesNotContainExactly(Collection<E> unwanted);

	/**
	 * Ensures that the array and {@code unwanted} consist of different elements, irrespective of their order.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #doesNotContainExactly(Collection, String)}
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted array
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the array consists of the same elements as {@code unwanted},
	 *                                  irrespective of their order
	 */
	S doesNotContainExactly(T unwanted, String name);

	/**
	 * Ensures that the array and {@code unwanted} consist of different elements, irrespective of their order.
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the array consists of the same elements as {@code unwanted},
	 *                                  irrespective of their order
	 */
	S doesNotContainExactly(Collection<E> unwanted, String name);

	/**
	 * Ensures that the array does not contain any of the elements in {@code unwanted}.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #doesNotContainAny(Collection)}
	 *
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the array contains any of the elements in {@code unwanted}
	 */
	S doesNotContainAny(T unwanted);

	/**
	 * Ensures that the array does not contain any of the elements in {@code unwanted}.
	 *
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the array contains any of the elements in {@code unwanted}
	 */
	S doesNotContainAny(Collection<E> unwanted);

	/**
	 * Ensures that the array does not contain any of the elements in {@code unwanted}.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #doesNotContainAny(Collection, String)}
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted array
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the array contains any of the elements in {@code unwanted}.
	 */
	S doesNotContainAny(T unwanted, String name);

	/**
	 * Ensures that the array does not contain any of the elements in {@code unwanted}.
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the array contains any of the elements in {@code unwanted}.
	 */
	S doesNotContainAny(Collection<E> unwanted, String name);

	/**
	 * Allows the array to contain some, but not all, elements from an array.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #doesNotContainAll(Collection)}
	 *
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the array contains all the elements of {@code unwanted}
	 */
	S doesNotContainAll(T unwanted);

	/**
	 * Allows the array to contain some, but not all, elements from a collection.
	 *
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the array contains all the elements of {@code unwanted}
	 */
	S doesNotContainAll(Collection<E> unwanted);

	/**
	 * Allows the array to contain some, but not all, elements from an array.
	 * <p>
	 * This method is provided for convenience and may not be any more efficient than
	 * {@link #doesNotContainAll(Collection, String)}
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted array
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the array contains all the elements in {@code unwanted}.
	 */
	S doesNotContainAll(T unwanted, String name);

	/**
	 * Allows the array to contain some, but not all, elements from a collection.
	 *
	 * @param unwanted the unwanted elements
	 * @param name     the name of the unwanted collection
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the array contains all the elements in {@code unwanted}.
	 */
	S doesNotContainAll(Collection<E> unwanted, String name);

	/**
	 * Ensures that the array is sorted.
	 *
	 * @param comparator the comparator that defines the order of the elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code comparator} are null
	 * @throws IllegalArgumentException if the array is not sorted
	 * @see Comparator#naturalOrder()
	 */
	S isSorted(Comparator<E> comparator);

	/**
	 * Ensures that the array does not contain any duplicate elements.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the array contains any duplicate elements
	 */
	S doesNotContainDuplicates();

	/**
	 * Returns a validator for the array's length.
	 *
	 * @return a validator for the array's length
	 * @throws NullPointerException if the value is null
	 */
	PrimitiveUnsignedIntegerValidator length();

	/**
	 * Returns a validator for the array as a collection.
	 *
	 * @return a validator for the array as a collection
	 * @throws NullPointerException if the value is null
	 */
	CollectionValidator<E, Collection<E>> asCollection();

	/**
	 * Returns a validator for the array as a list.
	 *
	 * @return a validator for the array as a list
	 * @throws NullPointerException if the value is null
	 */
	ListValidator<E, List<E>> asList();
}