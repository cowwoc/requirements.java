/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

/**
 * Validates the state of an {@link Object}.
 *
 * @param <T> the type of the value that is being validated
 */
public interface ObjectValidator<T> extends
	ValidatorComponent<ObjectValidator<T>, T>,
	ObjectComponent<ObjectValidator<T>, T>
{
}