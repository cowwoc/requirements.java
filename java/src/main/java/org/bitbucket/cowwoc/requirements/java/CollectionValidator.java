/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleCollectionValidator;

import java.util.Collection;

/**
 * Validates the requirements of a {@link Collection}.
 *
 * @param <C> the type of the collection
 * @param <E> the type of elements in the collection
 */
public interface CollectionValidator<C extends Collection<E>, E>
	extends ExtensibleCollectionValidator<CollectionValidator<C, E>, C, E>
{
}