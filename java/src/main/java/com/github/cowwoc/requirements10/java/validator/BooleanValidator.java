/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

/**
 * Validates the state of a {@code Boolean}.
 */
public interface BooleanValidator extends
	ValidatorComponent<BooleanValidator, Boolean>,
	ObjectComponent<BooleanValidator, Boolean>
{
	/**
	 * Ensures that the value is {@code true}.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is {@code false}
	 */
	BooleanValidator isTrue();

	/**
	 * Ensures that the value is {@code false}.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is {@code true}
	 */
	BooleanValidator isFalse();
}