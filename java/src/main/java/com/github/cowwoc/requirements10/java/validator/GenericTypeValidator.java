/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.GenericType;
import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

/**
 * Validates the state of a {@code GenericType}.
 *
 * @param <T> the type modelled by the {@code GenericType} object. For example, the type of
 *            {@code String.class} is {@code GenericType<String>}. Use {@code GenericType<?>} if the type
 *            being modeled is unknown.
 * @see GenericType<T>
 */
public interface GenericTypeValidator<T> extends
	ValidatorComponent<GenericTypeValidator<T>, GenericType<T>>,
	ObjectComponent<GenericTypeValidator<T>, GenericType<T>>
{
	/**
	 * Ensures that the value is a primitive type.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if value is not a primitive type
	 * @see Class#isPrimitive()
	 */
	GenericTypeValidator<T> isPrimitive();

	/**
	 * Ensures that the value is a superclass or superinterface of {@code subtype}.
	 *
	 * @param <U>  the type to compare to
	 * @param type the type to compare to. For types that contain type-parameters, use the
	 *             {@link #isSupertypeOf(GenericType) TypeToken} overload.
	 * @return this
	 * @throws NullPointerException     if the value or {@code type} are null
	 * @throws IllegalArgumentException if value is not a supertype of {@code subtype}
	 */
	<U> GenericTypeValidator<U> isSupertypeOf(Class<? extends U> type);

	/**
	 * Ensures that the value is a superclass or superinterface of {@code subtype}.
	 *
	 * @param <U>     the type to compare to
	 * @param subtype the type to compare to. For types without type-parameters, prefer the *
	 *                {@link #isSupertypeOf(Class) Class} overload.
	 * @return this
	 * @throws NullPointerException     if the value or {@code type} are null
	 * @throws IllegalArgumentException if value is not a supertype of {@code subtype}
	 */
	<U> GenericTypeValidator<U> isSupertypeOf(GenericType<? extends U> subtype);

	/**
	 * Ensures that the value is a subclass or subinterface of {@code supertype}.
	 *
	 * @param <U>       the type to compare to
	 * @param supertype the type to compare to. For types that contain type-parameters, use the
	 *                  {@link #isSubtypeOf(GenericType) TypeToken} overload.
	 * @return this
	 * @throws NullPointerException     if the value or {@code type} are null
	 * @throws IllegalArgumentException if value is not a subtype of {@code supertype}
	 */
	<U> GenericTypeValidator<U> isSubtypeOf(Class<? super U> supertype);

	/**
	 * Ensures that the value is a subclass or subinterface of {@code supertype}.
	 *
	 * @param <U>       the type to compare to
	 * @param supertype the type to compare to. For types without type-parameters, prefer the
	 *                  {@link #isSubtypeOf(Class) Class} overload.
	 * @return this
	 * @throws NullPointerException     if the value or {@code type} are null
	 * @throws IllegalArgumentException if value is not a subtype of {@code supertype}
	 */
	<U> GenericTypeValidator<U> isSubtypeOf(GenericType<? super U> supertype);
}
