/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.validator.component.CollectionComponent;
import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

import java.util.Collection;
import java.util.function.Function;

/**
 * Validates the state of a {@code Collection}.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 *
 * @param <T> the type of the collection
 * @param <E> the type of elements in the collection
 */
public interface CollectionValidator<T extends Collection<E>, E> extends
	ValidatorComponent<CollectionValidator<T, E>, T>,
	ObjectComponent<CollectionValidator<T, E>, T>,
	CollectionComponent<CollectionValidator<T, E>, E>
{
}