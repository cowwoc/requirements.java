/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.validator;

import io.github.cowwoc.requirements12.java.validator.component.ComparableComponent;
import io.github.cowwoc.requirements12.java.validator.component.IntegerComponent;
import io.github.cowwoc.requirements12.java.validator.component.NegativeNumberComponent;
import io.github.cowwoc.requirements12.java.validator.component.PositiveNumberComponent;
import io.github.cowwoc.requirements12.java.validator.component.PrimitiveIntegerComponent;
import io.github.cowwoc.requirements12.java.validator.component.ValidatorComponent;
import io.github.cowwoc.requirements12.java.validator.component.ZeroNumberComponent;

/**
 * Validates the state of an {@code int}.
 */
public interface PrimitiveIntegerValidator extends
	ValidatorComponent<PrimitiveIntegerValidator, Integer>,
	IntegerComponent<PrimitiveIntegerValidator>,
	PrimitiveIntegerComponent<PrimitiveIntegerValidator>,
	NegativeNumberComponent<PrimitiveIntegerValidator>,
	ZeroNumberComponent<PrimitiveIntegerValidator>,
	PositiveNumberComponent<PrimitiveIntegerValidator>,
	ComparableComponent<PrimitiveIntegerValidator, Integer>
{
}