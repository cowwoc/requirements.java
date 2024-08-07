/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.validator.component.ComparableComponent;
import com.github.cowwoc.requirements10.java.validator.component.IntegerComponent;
import com.github.cowwoc.requirements10.java.validator.component.NegativeNumberComponent;
import com.github.cowwoc.requirements10.java.validator.component.PositiveNumberComponent;
import com.github.cowwoc.requirements10.java.validator.component.PrimitiveIntegerComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;
import com.github.cowwoc.requirements10.java.validator.component.ZeroNumberComponent;

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