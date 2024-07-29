package com.github.cowwoc.requirements10.java.validator.component;

import com.github.cowwoc.requirements10.java.validator.CollectionValidator;
import com.github.cowwoc.requirements10.java.validator.ListValidator;
import com.github.cowwoc.requirements10.java.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.validator.PrimitiveUnsignedIntegerValidator;

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
 * @param <T> the type of the array
 * @param <E> the type of elements in the array
 */
public interface ArrayComponent<S extends ArrayComponent<S, T, E>, T, E>
{
	// REMINDERS:
	// * <A> cannot be derived from <E>. Example, for long[], E = Long, A = long[].
	// * Need two methods for contains*() because one compares the elements of a long[] to a long[]. The other
	//   compares the elements of a long[] to a Collection<Long>.

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
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the array does not contain {@code expected}</li>
	 *                                  </ul>
	 */
	S contains(E expected, String name);

	/**
	 * Ensures that the array consists of the same elements as {@code expected}, irrespective of their order.
	 * <p>
	 * In contrast, {@link ObjectComponent#isEqualTo(Object) isEqualTo()} requires the same element ordering.
	 *
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>the array is missing any element in {@code expected}</li>
	 *                                    <li>the array contains any element that is not in
	 *                                    {@code expected}</li>
	 *                                  </ul>
	 */
	S containsExactly(T expected);

	/**
	 * Ensures that the array consists of the same elements as {@code expected}, irrespective of their order.
	 * <p>
	 * In contrast, {@link ObjectComponent#isEqualTo(Object) isEqualTo()} requires the same element ordering.
	 *
	 * @param <C>      the type of collection to compare against
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>the array is missing any element in {@code expected}</li>
	 *                                    <li>the array contains any element that is not in
	 *                                    {@code expected}</li>
	 *                                  </ul>
	 */
	<C extends Collection<E>> S containsExactly(C expected);

	/**
	 * Ensures that the array consists of the same elements as {@code expected}, irrespective of their order.
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
	 *                                    <li>the array and {@code expected} contain different elements,
	 *                                    irrespective of their order</li>
	 *                                  </ul>
	 */
	S containsExactly(T expected, String name);

	/**
	 * Ensures that the array consists of the same elements as {@code expected}, irrespective of their order.
	 * <p>
	 * In contrast, {@link ObjectComponent#isEqualTo(Object) isEqualTo()} requires the same element ordering.
	 *
	 * @param <C>      the type of collection to compare against
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
	 *                                    <li>the array and {@code expected} contain different elements,
	 *                                    irrespective of their order</li>
	 *                                  </ul>
	 */
	<C extends Collection<E>> S containsExactly(C expected, String name);

	/**
	 * Ensures that the array contains any elements in {@code expected}.
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
	 * @param <C>      the type of collection to compare against
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the array does not contain any element in {@code expected}
	 */
	<C extends Collection<E>> S containsAny(C expected);

	/**
	 * Ensures that the array contains at least one element in {@code expected}.
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
	 *                                    <li>the array does not contain any element in {@code expected}</li>
	 *                                  </ul>
	 */
	S containsAny(T expected, String name);

	/**
	 * Ensures that the array contains at least one element in {@code expected}.
	 *
	 * @param <C>      the type of collection to compare against
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
	 *                                    <li>the array does not contain any element in {@code expected}/li>
	 *                                  </ul>
	 */
	<C extends Collection<E>> S containsAny(C expected, String name);

	/**
	 * Ensures that the array contains all the elements in {@code expected}.
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
	 * @param <C>      the type of collection to compare against
	 * @param expected the desired elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the array does not contain all the elements in {@code expected}
	 */
	<C extends Collection<E>> S containsAll(C expected);

	/**
	 * Ensures that the array contains all the elements in {@code expected}.
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
	 *                                    <li>the array does not contain all the elements in
	 *                                    {@code expected}/li>
	 *                                  </ul>
	 */
	S containsAll(T expected, String name);

	/**
	 * Ensures that the array contains all the elements in {@code expected}.
	 *
	 * @param <C>      the type of collection to compare against
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
	 *                                    <li>the array does not contain all elements in {@code expected}</li>
	 *                                  </ul>
	 */
	<C extends Collection<E>> S containsAll(C expected, String name);

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
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the array contains {@code unwanted}</li>
	 *                                  </ul>
	 */
	S doesNotContain(E unwanted, String name);

	/**
	 * Ensures that the array and {@code unwanted} consist of different elements, irrespective of their order.
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
	 * @param <C>      the type of collection to compare against
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the array consists of the same elements as {@code unwanted},
	 *                                  irrespective of their order
	 */
	<C extends Collection<E>> S doesNotContainExactly(C unwanted);

	/**
	 * Ensures that the array and {@code unwanted} consist of different elements, irrespective of their order.
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
	 *                                    <li>the array consists of the same elements as {@code unwanted},
	 *                                    irrespective of their order</li>
	 *                                  </ul>
	 */
	S doesNotContainExactly(T unwanted, String name);

	/**
	 * Ensures that the array and {@code unwanted} consist of different elements, irrespective of their order.
	 *
	 * @param <C>      the type of collection to compare against
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
	 *                                    <li>the array consists of the same elements as {@code unwanted},
	 *                                    irrespective of their order</li>
	 *                                  </ul>
	 */
	<C extends Collection<E>> S doesNotContainExactly(C unwanted, String name);

	/**
	 * Ensures that the array does not contain any of the elements in {@code unwanted}.
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
	 * @param <C>      the type of collection to compare against
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the array contains any of the elements in {@code unwanted}
	 */
	<C extends Collection<E>> S doesNotContainAny(C unwanted);

	/**
	 * Ensures that the array does not contain any of the elements in {@code unwanted}.
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
	 *                                    <li>the array contains any of the elements in {@code unwanted}</li>
	 *                                  </ul>
	 */
	S doesNotContainAny(T unwanted, String name);

	/**
	 * Ensures that the array does not contain any of the elements in {@code unwanted}.
	 *
	 * @param <C>      the type of collection to compare against
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
	 *                                    <li>the array contains any of the elements in {@code unwanted}</li>
	 *                                  </ul>
	 */
	<C extends Collection<E>> S doesNotContainAny(C unwanted, String name);

	/**
	 * Allows the array to contain some, but not all, elements from a collection.
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
	 * @param <C>      the type of collection to compare against
	 * @param unwanted the unwanted elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the array contains all the elements of {@code unwanted}
	 */
	<C extends Collection<E>> S doesNotContainAll(C unwanted);

	/**
	 * Allows the array to contain some, but not all, elements from a collection.
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
	 *                                    <li>the array contains all the elements in {@code unwanted}</li>
	 *                                  </ul>
	 */
	S doesNotContainAll(T unwanted, String name);

	/**
	 * Allows the array to contain some, but not all, elements from a collection.
	 *
	 * @param <C>      the type of collection to compare against
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
	 *                                    <li>the array contains all the elements in {@code unwanted}</li>
	 *                                  </ul>
	 */
	<C extends Collection<E>> S doesNotContainAll(C unwanted, String name);

	/**
	 * Ensures that the array is sorted.
	 *
	 * @param comparator the comparator that defines the expected order of the elements
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
	CollectionValidator<Collection<E>, E> asCollection();

	/**
	 * Returns a validator for the array as a list.
	 *
	 * @return a validator for the array as a list
	 * @throws NullPointerException if the value is null
	 */
	ListValidator<List<E>, E> asList();
}