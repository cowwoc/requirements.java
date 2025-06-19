/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.validator;

import io.github.cowwoc.requirements12.java.validator.component.CollectionComponent;
import io.github.cowwoc.requirements12.java.validator.component.ObjectComponent;
import io.github.cowwoc.requirements12.java.validator.component.ValidatorComponent;

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