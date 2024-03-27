/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.type;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.type.part.ArrayPart;
import com.github.cowwoc.requirements.java.type.part.ObjectPart;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.function.Function;

/**
 * Validates the state of an {@code Object[]} value.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 *
 * @param <E> the type of elements in the array
 * @param <A> the type of the array
 */
public interface ObjectArrayValidator<E, A> extends
	Validator<ObjectArrayValidator<E, A>>,
	ObjectPart<ObjectArrayValidator<E, A>, A>,
	ArrayPart<ObjectArrayValidator<E, A>, E, A>
{
	/**
	 * Ensures that the array contains only null values, or only non-null values.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the array contains a mix of null and non-null values
	 */
	ObjectArrayValidator<E, A> containsSameNullity();
}