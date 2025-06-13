/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements11.java.validator;

import com.github.cowwoc.requirements11.java.validator.component.CollectionComponent;
import com.github.cowwoc.requirements11.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements11.java.validator.component.ValidatorComponent;

import java.util.Collection;

/**
 * Validates the state of a {@code Collection}.
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