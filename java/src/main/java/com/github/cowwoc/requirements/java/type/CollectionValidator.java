/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.type;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.type.part.CollectionPart;
import com.github.cowwoc.requirements.java.type.part.ObjectPart;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.Collection;
import java.util.function.Function;

/**
 * Validates the state of a {@code Collection} value.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 *
 * @param <E> the type of elements in the collection
 * @param <T> the type of the collection
 */
public interface CollectionValidator<E, T extends Collection<E>> extends
	Validator<CollectionValidator<E, T>>,
	ObjectPart<CollectionValidator<E, T>, T>,
	CollectionPart<CollectionValidator<E, T>, E>
{
}