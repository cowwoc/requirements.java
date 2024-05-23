/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.type;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.type.part.ObjectPart;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.function.Function;

/**
 * Validates the state of a {@code Class} value.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 *
 * @param <T> the type of the class
 */
public interface ClassValidator<T> extends
	Validator<ClassValidator<T>>,
	ObjectPart<ClassValidator<T>, Class<T>>
{
	/**
	 * Ensures that the value is a superclass or superinterface of {@code type}.
	 *
	 * @param type the type to compare to
	 * @return this
	 * @throws NullPointerException     if the value or {@code type} are null
	 * @throws IllegalArgumentException if value is not a supertype of {@code type}
	 */
	ClassValidator<T> isSupertypeOf(Class<?> type);

	/**
	 * Ensures that the value is a subclass or subinterface of {@code type}.
	 *
	 * @param type the type to compare to
	 * @return this
	 * @throws NullPointerException     if the value or {@code type} are null
	 * @throws IllegalArgumentException if value is not a subtype of {@code type}
	 */
	ClassValidator<T> isSubtypeOf(Class<?> type);
}
