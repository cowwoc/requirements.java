/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleCollectionValidator;
import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

import java.util.Collection;

/**
 * Validates the requirements of a {@link Collection}.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectValidator}) imply {@link #isNotNull()}.
 *
 * @param <C> the type of the collection
 * @param <E> the type of elements in the collection
 */
public interface CollectionValidator<C extends Collection<E>, E>
	extends ExtensibleCollectionValidator<CollectionValidator<C, E>, C, E>
{
}