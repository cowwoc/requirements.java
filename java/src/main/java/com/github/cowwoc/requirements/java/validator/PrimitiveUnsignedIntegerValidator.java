/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.validator;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.validator.component.ComparableComponent;
import com.github.cowwoc.requirements.java.validator.component.IntegerComponent;
import com.github.cowwoc.requirements.java.validator.component.PositiveNumberComponent;
import com.github.cowwoc.requirements.java.validator.component.PrimitiveIntegerComponent;
import com.github.cowwoc.requirements.java.validator.component.ValidatorComponent;
import com.github.cowwoc.requirements.java.validator.component.ZeroNumberComponent;

import java.util.function.Function;

/**
 * Validates the state of an {@code int} that does not support negative numbers.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 */
public interface PrimitiveUnsignedIntegerValidator extends
	ValidatorComponent<PrimitiveUnsignedIntegerValidator, Integer>,
	IntegerComponent<PrimitiveUnsignedIntegerValidator>,
	PrimitiveIntegerComponent<PrimitiveUnsignedIntegerValidator>,
	ZeroNumberComponent<PrimitiveUnsignedIntegerValidator>,
	PositiveNumberComponent<PrimitiveUnsignedIntegerValidator>,
	ComparableComponent<PrimitiveUnsignedIntegerValidator, Integer>
{
}