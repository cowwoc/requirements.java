/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

import java.util.function.Function;

/**
 * Validates the state of a {@code Class}.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 *
 * @param <T> the type of the class modelled by the {@code Class} object. For example, the type of
 *            {@code String.class} is {@code Class<String>}. Use {@code Class<?>} if the class being modeled
 *            is unknown.
 * @see GenericType<T>
 */
public interface ClassValidator<T> extends
	ValidatorComponent<ClassValidator<T>, GenericType<T>>,
	ObjectComponent<ClassValidator<T>, GenericType<T>>
{
	/**
	 * Ensures that the value is a primitive type.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if value is not a primitive type
	 * @see Class#isPrimitive()
	 */
	ClassValidator<T> isPrimitive();

	/**
	 * Ensures that the value is a superclass or superinterface of {@code type}.
	 *
	 * @param <U>  the type to compare to
	 * @param type the type to compare to. For types that contain type-parameters, use the
	 *             {@link #isSupertypeOf(GenericType) TypeToken} overload.
	 * @return this
	 * @throws NullPointerException     if the value or {@code type} are null
	 * @throws IllegalArgumentException if value is not a supertype of {@code type}
	 */
	<U> ClassValidator<U> isSupertypeOf(Class<? extends U> type);

	/**
	 * Ensures that the value is a superclass or superinterface of {@code type}.
	 *
	 * @param <U>  the type to compare to
	 * @param type the type to compare to. For types without type-parameters, prefer the *
	 *             {@link #isSupertypeOf(Class) Class} overload.
	 * @return this
	 * @throws NullPointerException     if the value or {@code type} are null
	 * @throws IllegalArgumentException if value is not a supertype of {@code type}
	 */
	<U> ClassValidator<U> isSupertypeOf(GenericType<? extends U> type);

	/**
	 * Ensures that the value is a subclass or subinterface of {@code type}.
	 *
	 * @param <U>  the type to compare to
	 * @param type the type to compare to. For types that contain type-parameters, use the
	 *             {@link #isSubtypeOf(GenericType) TypeToken} overload.
	 * @return this
	 * @throws NullPointerException     if the value or {@code type} are null
	 * @throws IllegalArgumentException if value is not a subtype of {@code type}
	 */
	<U> ClassValidator<U> isSubtypeOf(Class<? super U> type);

	/**
	 * Ensures that the value is a subclass or subinterface of {@code type}.
	 *
	 * @param <U>  the type to compare to
	 * @param type the type to compare to. For types without type-parameters, prefer the
	 *             {@link #isSubtypeOf(Class) Class} overload.
	 * @return this
	 * @throws NullPointerException     if the value or {@code type} are null
	 * @throws IllegalArgumentException if value is not a subtype of {@code type}
	 */
	<U> ClassValidator<U> isSubtypeOf(GenericType<? super U> type);
}
